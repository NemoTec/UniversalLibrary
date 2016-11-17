package com.nemo.android.widget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nemo.ul.comparator.AppWrapperComparator;
import com.nemo.ul.comparator.AppWrapperComparator.AppInfoWrapper;
import com.nemo.ul.java.ULStringUtil;
import com.nemo.ul.util.ULIconUtils;
import com.nemo.ul.R;


public class ListViewTestActivity extends Activity {
    private static final String TAG = "ListViewTestActivity";

    List<AppInfoWrapper> mAppList = new ArrayList<AppInfoWrapper>();
    List<String> mList1 = new ArrayList<String>();
    List<String> mList2 = new ArrayList<String>();

    private AppInfoAdapter mAdapter;
    private ListView mListView;
    private View mListContainer;
    private View mLoadingContainer;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.layout_listview_test_activity);
        initActionBar();
        initView();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        /*
        if (mAppList != null && mAppList.size() > 0) {
            List<String> listRet = new ArrayList<String>();
            for (int i = 0; i < mAppList.size(); i++) {
                AppInfoWrapper appWrapper = mAppList.get(i);
                
                String pkg = appWrapper.pkgName;
                listRet.add(pkg);
            }
            mAdapter.notifyDataSetChanged();
        }
        */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(
                    ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE,
                    ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE
                            | ActionBar.DISPLAY_SHOW_HOME);
        }
    }

    private void initView() {
        mListContainer = findViewById(R.id.list_container);
        mLoadingContainer = findViewById(R.id.loading_container);
        mLoadingContainer.setVisibility(View.VISIBLE);

        mAdapter = new AppInfoAdapter();
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setFooterDividersEnabled(false);
        mListView.setDividerHeight(0);
        mListView.setDivider(null);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AppInfoWrapper appWrapper = mAppList.get(pos);
                String title = appWrapper.label;
                String pkgName = appWrapper.pkgName;
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("pkgName", pkgName);
                startActivity(intent);

                if (true) {
                    updateItemView(pos);
                }

            }
        });
    }

    public void updateItemView(int position) {
        int visibleFirstPosi = mListView.getFirstVisiblePosition();
        int visibleLastPosi = mListView.getLastVisiblePosition();
        if (position >= visibleFirstPosi && position <= visibleLastPosi) {
            View view = mListView.getChildAt(position - visibleFirstPosi);
            ListItemView holder = (ListItemView) view;
            holder.setTitle("");
        }
    }

    public void refreshStats() {

        List<ApplicationInfo> appList = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : appList) {
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                continue;
            }

            CharSequence labelSeq = applicationInfo.loadLabel(getPackageManager());
            String label = null;
            if (null != labelSeq) {
                label = ULStringUtil.deleteMessyCode(labelSeq.toString().trim());
            }
            if (TextUtils.isEmpty(label)) {
                label = applicationInfo.packageName;
            }

            AppInfoWrapper appWrapper = new AppInfoWrapper();
            appWrapper.label = label;
            appWrapper.pkgName = applicationInfo.packageName;
            appWrapper.appInfo = applicationInfo;
            mAppList.add(appWrapper);
        }
        if (null == mAppList || mAppList.isEmpty()) {
            mHandler.sendEmptyMessage(0);
            return;
        }

        sortAppPref(mAppList);
        mHandler.sendEmptyMessage(0);
    }

    private void sortAppPref(List<AppInfoWrapper> appList) {
        Collections.sort(appList, new AppWrapperComparator(this, mList1, mList2));
    }

    private class AppInfoAdapter extends BaseAdapter {
        public AppInfoAdapter() {

        }

        @Override
        public int getCount() {
            return mAppList != null ? mAppList.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return (mAppList != null && mAppList.size() > 0) ? mAppList.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new ListItemView(ListViewTestActivity.this);
            }
            ListItemView infoView = (ListItemView) convertView;

            if (mAppList != null && mAppList.size() > 0) {
                AppInfoWrapper appWrapper = mAppList.get(position);
                ApplicationInfo applicationInfo = appWrapper.appInfo;
                Drawable icon = applicationInfo.loadIcon(getPackageManager());
                if (null == icon) {
                    icon = getResources().getDrawable(android.R.drawable.sym_def_app_icon);
                }
                icon = ULIconUtils.getDrawableForListUse(ListViewTestActivity.this, icon);

                infoView.setTitle(appWrapper.label);
                infoView.setIcon(icon);
            }

            return convertView;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mLoadingContainer.setVisibility(View.GONE);
                mListContainer.setVisibility(View.VISIBLE);
                mListView.setAdapter(mAdapter);
            }
        }
    };

    /*
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
                final String pkgName = intent.getData().getSchemeSpecificPart();
                if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
                    return;
                }

                if (!TextUtils.isEmpty(pkgName) && removeRedDot(pkgName)
                        && (mAppList != null && mAppList.size() > 0)) {
                    for (int i = 0; i < mAppList.size(); i++) {
                        String pkg = mAppList.get(i).pkgName;
                        if (pkgName.equals(pkg)) {
                            mAppList.remove(i);
                            mAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }

            }
        }
    };

    private IAIDLInterface mService = null;
    ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName cpnName, IBinder service) {
            mService = IGuardElfAIDLInterface.Stub.asInterface(service);
            new Thread() {
                @Override
                public void run() {
                    refreshStats();
                }
            }.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName cpnName) {
            mService = null;
            rebind();
        }
    };

    private void rebind() {
        bindService(new Intent(this, IAIDLInterface.class), mConn, BIND_AUTO_CREATE);
    }
    */
}
