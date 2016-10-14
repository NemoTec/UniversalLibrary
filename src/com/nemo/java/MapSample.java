package com.nemo;

class MapSample {
/*
public void clear() {
    final ConcurrentHashMap<String, BitmapHolder> mBitmapCache = new ConcurrentHashMap<String, BitmapHolder>();

    // set all drawable call back to null to realease memory.
    for (Map.Entry<String, BitmapHolder> entry: mBitmapCache.entrySet()) {
        String path = entry.getKey();
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
    
    
    
public void run() {
            Iterator<Map.Entry<String, String>> iterator = mPkgList.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String path = entry.getKey();
                String pkgName = entry.getValue();
                
                try {
                    mContext.getPackageManager().deletePackage(pkgName, null, 0);
                } catch (Exception e) {

                }

                deleteVirusDataFromeDataBase(VirusConst.APK_TYPE_INSTALL, path);
                iterator.remove();
            }
        }
        
        
//ConcurrentHashMap的putIfAbsent用来做缓冲相当不错，多线程安全的

V put(K key, V value)
Associates the specified value with the specified key in this map (optional operation). If the map previously contained a mapping for the key, the old value is replaced by the specified value. (A map m is said to contain a mapping for a key k if and only if m.containsKey(k) would return true.)  


V putIfAbsent(K key, V value)
If the specified key is not already associated with a value, associate it with the given value. This is equivalent to
   if (!map.containsKey(key))
       return map.put(key, value);
   else
       return map.get(key);
except that the action is performed atomically.
*/
    
}