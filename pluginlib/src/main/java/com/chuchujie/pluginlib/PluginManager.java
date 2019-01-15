package com.chuchujie.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {

    private DexClassLoader mDexClassLoader;

    private Context mContext;

    private Resources pluginResource;

    private PackageInfo pluginPackageArchiveInfo;

    private PluginManager() {

    }

    public static PluginManager getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        public static PluginManager instance = new PluginManager();
    }



    public void loadApk(String dexPath) {
        mDexClassLoader = new DexClassLoader(dexPath, mContext.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath(), null, mContext.getClassLoader());
        pluginPackageArchiveInfo = mContext.getPackageManager().getPackageArchiveInfo(dexPath, PackageManager.GET_ACTIVITIES);
        AssetManager assetManager = null;
        try {
            assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        pluginResource = new Resources(
                assetManager,
                mContext.getResources().getDisplayMetrics(),
                mContext.getResources().getConfiguration());


        //对于非sdk接口的hide field和方法，Android P进行限制调用，可以参考如下文章https://juejin.im/post/5b8959f96fb9a019fe685eb3
        //为了适配P，整体原则就是能绕过non-sdk方法就想办法找到兼容替代方法，实在不行就把TargetSdk 降低到28
        //以下是对目前使用的到Hook方面以及插件化中在P中的限制api 调用
        //---------------------
        //作者：Trilen
        //来源：CSDN
        //原文：https://blog.csdn.net/u010019468/article/details/82805131
        //版权声明：本文为博主原创文章，转载请附上博文链接！



    }

    public Resources getPluginResource() {
        return pluginResource;
    }

    public void setApplicationContext(Context applicationContext) {
        this.mContext = applicationContext;
    }

    public DexClassLoader getDexClassLoader() {
        return mDexClassLoader;
    }

    public PackageInfo getPluginPackageArchiveInfo() {
        return pluginPackageArchiveInfo;
    }
}


