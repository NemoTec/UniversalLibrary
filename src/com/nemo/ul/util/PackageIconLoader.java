package com.coloros.safecenter.virusdetect.util;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

/**
 * Asynchronously loads package icon and maintains cache of icon. The class is
 * mostly single-threaded. The only two methods accessed by the loader thread
 * are {@link #cacheDrawable} and {@link #obtainPackageNamesToLoad}. Those
 * methods access concurrent hash maps shared with the main thread.
 */
public class PackageIconLoader implements Callback {
    private static final String TAG = "PackageIconLoader";
    private static final String LOADER_THREAD_NAME = "package_icon_loader_thread";
    private static final boolean DEBUG = true;

    /**
     * Type of message sent by the UI thread to itself to indicate that some
     * photos need to be loaded.
     */
    private static final int MESSAGE_REQUEST_LOADING = 1;

    /**
     * Type of message sent by the loader thread to indicate that some photos
     * have been loaded.
     */
    private static final int MESSAGE_PHOTOS_LOADED = 2;

    /**
     * The resource ID of the image to be used when the photo is unavailable or
     * being loaded.
     */
    private final int mDefaultResourceId;

    /**
     * Maintains the state of a particular photo.
     */
    private static class BitmapHolder {
        private static final int NEEDED = 0;
        private static final int LOADING = 1;
        private static final int LOADED = 2;

        int state;
        SoftReference<Drawable> bitmapRef;
    }

    private final BitmapHolder mEmptyBmpHolder;

    /**
     * A soft cache for package icon.
     */
    private final ConcurrentHashMap<String, BitmapHolder> mBitmapCache = new ConcurrentHashMap<String, BitmapHolder>();

    /**
     * A map from ImageView to the corresponding package name. Please note that
     * this package name may change before the package icon loading request is
     * started.
     */
    private final ConcurrentHashMap<ImageView, String> mPendingRequests = new ConcurrentHashMap<ImageView, String>();

    /**
     * Handler for messages sent to the UI thread.
     */
    private final Handler mMainThreadHandler = new Handler(this);

    /**
     * Thread responsible for loading photos from the database. Created upon the
     * first request.
     */
    private LoaderThread mLoaderThread;

    /**
     * A gate to make sure we only send one instance of MESSAGE_PHOTOS_NEEDED at
     * a time.
     */
    private boolean mLoadingRequested;

    /**
     * Flag indicating if the image loading is paused.
     */
    private boolean mPaused;

    private final Context mContext;

    /**
     * Constructor.
     *
     * @param context
     *            content context
     * @param defaultResourceId
     *            the image resource ID to be used when there is no photo for a
     *            contact
     */
    public PackageIconLoader(Context context, int defaultResourceId) {
        mDefaultResourceId = defaultResourceId;
        mContext = context;
        mEmptyBmpHolder = new BitmapHolder();
        mEmptyBmpHolder.state = BitmapHolder.LOADED;
    }

    /**
     * Load photo into the supplied image view. If the photo is already cached,
     * it is displayed immediately. Otherwise a request is sent to load the
     * photo from the database.
     */
    public void loadPhoto(ImageView view, String pacakgeName) {
        if (TextUtils.isEmpty(pacakgeName)) {
            // No icon is needed
            view.setImageResource(mDefaultResourceId);
            mPendingRequests.remove(view);
        } else {
            boolean loaded = loadCachedDrawable(view, pacakgeName);
            if (loaded) {
                mPendingRequests.remove(view);
            } else {
                mPendingRequests.put(view, pacakgeName);
                if (!mPaused) {
                    // Send a request to start loading photos
                    requestLoading();
                }
            }
        }
    }

    /**
     * Checks if the photo is present in cache. If so, sets the photo on the
     * view, otherwise sets the state of the photo to
     * {@link BitmapHolder#NEEDED} and temporarily set the image to the default
     * resource ID.
     */
    private boolean loadCachedDrawable(ImageView view, String packageName) {
        BitmapHolder newHolder = new BitmapHolder();

        BitmapHolder holder = mBitmapCache.putIfAbsent(packageName, newHolder);
        if (holder == null) {
            holder = newHolder;
        } else if (holder.state == BitmapHolder.LOADED) {
            // Null bitmap reference means that database contains no bytes for
            // the photo
            if (holder.bitmapRef == null) {
                view.setImageResource(mDefaultResourceId);
                return true;
            }

            Drawable bitmap = holder.bitmapRef.get();
            if (bitmap != null) {
                view.setImageDrawable(bitmap);
                return true;
            }

            // Null bitmap means that the soft reference was released by the GC
            // and we need to reload the photo.
            holder.bitmapRef = null;
        }

        // The bitmap has not been loaded - should display the placeholder
        // image.
        view.setImageResource(mDefaultResourceId);
        holder.state = BitmapHolder.NEEDED;
        return false;
    }

    /**
     * Stops loading images, kills the image loader thread and clears all
     * caches.
     */
    public void stop() {
        pause();

        if (mLoaderThread != null) {
            mLoaderThread.quit();
            mLoaderThread = null;
        }

        clear();
    }

    public void clear() {
        mPendingRequests.clear();

        // set all drawable call back to null to realease memory.
        for (Map.Entry<String, BitmapHolder> entry: mBitmapCache.entrySet()) {
            BitmapHolder holder = entry.getValue();
            if (null != holder.bitmapRef) {
                Drawable drawable = holder.bitmapRef.get();
                if (null != drawable) {
                    drawable.setCallback(null);
                }
            }
        }

        mBitmapCache.clear();
    }

    /**
     * Temporarily stops loading photos from the database.
     */
    public void pause() {
        mPaused = true;
    }

    /**
     * Resumes loading photos from the database.
     */
    public void resume() {
        mPaused = false;
        if (!mPendingRequests.isEmpty()) {
            requestLoading();
        }
    }

    /**
     * Sends a message to this thread itself to start loading images. If the
     * current view contains multiple image views, all of those image views will
     * get a chance to request their respective photos before any of those
     * requests are executed. This allows us to load images in bulk.
     */
    private void requestLoading() {
        if (!mLoadingRequested) {
            mLoadingRequested = true;
            mMainThreadHandler.sendEmptyMessage(MESSAGE_REQUEST_LOADING);
        }
    }

    /**
     * Processes requests on the main thread.
     */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
        case MESSAGE_REQUEST_LOADING: {
            mLoadingRequested = false;
            if (!mPaused) {
                if (mLoaderThread == null) {
                    mLoaderThread = new LoaderThread(
                            mContext.getPackageManager());
                    mLoaderThread.start();
                }

                mLoaderThread.requestLoading();
            }
            return true;
        }

        case MESSAGE_PHOTOS_LOADED: {
            if (!mPaused) {
                processLoadedImages();
            }
            return true;
        }
        }
        return false;
    }

    /**
     * Goes over pending loading requests and displays loaded photos. If some of
     * the photos still haven't been loaded, sends another request for image
     * loading.
     */
    private void processLoadedImages() {
        Iterator<Map.Entry<ImageView, String>> iterator = mPendingRequests.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ImageView, String> entry = iterator.next();
            ImageView view = entry.getKey();
            String packageName = entry.getValue();

            boolean loaded = loadCachedDrawable(view, packageName);
            if (loaded) {
                iterator.remove();
            }
        }

        if (!mPendingRequests.isEmpty()) {
            requestLoading();
        }
    }

    /**
     * Stores the supplied bitmap in cache.
     */
    private void cacheDrawable(String packageName, Drawable appIcon) {
        if (mPaused) {
            return;
        }

        if (null == appIcon) {
            mBitmapCache.put(packageName, mEmptyBmpHolder);
            return;
        }

        BitmapHolder holder = new BitmapHolder();
        holder.state = BitmapHolder.LOADED;
        try {
            holder.bitmapRef = new SoftReference<Drawable>(appIcon);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            // Do nothing - the photo will appear to be missing
        }

        mBitmapCache.put(packageName, holder);
    }

    /**
     * Populates an array of photo IDs that need to be loaded.
     */
    private void obtainPackageNamesToLoad(ArrayList<String> lstPackageNames) {
        lstPackageNames.clear();

        /*
         * Since the call is made from the loader thread, the map could be
         * changing during the iteration. That's not really a problem:
         * ConcurrentHashMap will allow those changes to happen without throwing
         * exceptions. Since we may miss some requests in the situation of
         * concurrent change, we will need to check the map again once loading
         * is complete.
         */
        Iterator<String> iterator = mPendingRequests.values().iterator();
        while (iterator.hasNext()) {
            String packageName = iterator.next();
            BitmapHolder holder = mBitmapCache.get(packageName);
            if (holder != null && holder.state == BitmapHolder.NEEDED) {
                // Assuming atomic behavior
                holder.state = BitmapHolder.LOADING;
                lstPackageNames.add(packageName);
            }
        }
    }

    /**
     * The thread that performs loading of photos from the database.
     */
    private class LoaderThread extends HandlerThread implements Callback {
        private final PackageManager mPackageManager;
        private final ArrayList<String> mPackageNames = new ArrayList<String>();
        private Handler mLoaderThreadHandler;

        public LoaderThread(PackageManager packageManager) {
            super(LOADER_THREAD_NAME);
            mPackageManager = packageManager;
        }

        /**
         * Sends a message to this thread to load requested photos.
         */
        public void requestLoading() {
            if (mLoaderThreadHandler == null) {
                mLoaderThreadHandler = new Handler(getLooper(), this);
            }
            mLoaderThreadHandler.sendEmptyMessage(0);
        }

        /**
         * Receives the above message, loads photos and then sends a message to
         * the main thread to process them.
         */
        @Override
        public boolean handleMessage(Message msg) {
            obtainPackageNamesToLoad(mPackageNames);
            loadPackageIcon();
            mMainThreadHandler.sendEmptyMessage(MESSAGE_PHOTOS_LOADED);
            return true;
        }

        private void loadPackageIcon() {
            if (mPackageNames.isEmpty()) {
                return;
            }

            for (String packageName : mPackageNames) {
                Drawable drawable = getPackageIcon(mContext, packageName);

                if (drawable == null) { //may be apk files.
                    drawable = getApkIcon(mContext, packageName);
                }

                cacheDrawable(packageName, drawable);
            }

            mPackageNames.clear();
        }
    }

    //or ULPackageUtil.getPackageIcon().
    private Drawable getPackageIcon(Context context, String pkgName) {
        try {
            PackageManager pkgManager = context.getPackageManager();
            return pkgManager.getApplicationIcon(pkgName);
        } catch (NameNotFoundException e) {
            Log.e(TAG, "getPackageIcon() NameNotFoundException: " + e.getMessage());
            return null;
        }
    }

    //or ULPackageUtil.getUninstallApkIcon().
    private Drawable getApkIcon(Context context, String archiveFilePath) {
        Drawable icon = null;

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(archiveFilePath,
                    PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                ApplicationInfo appInfo = packageInfo.applicationInfo;
                appInfo.sourceDir = archiveFilePath;
                appInfo.publicSourceDir = archiveFilePath;
                icon = appInfo.loadIcon(packageManager);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        if (icon == null) {
            icon = context.getResources().getDrawable(android.R.drawable.sym_def_app_icon);
        }

        return icon;
    }

}
