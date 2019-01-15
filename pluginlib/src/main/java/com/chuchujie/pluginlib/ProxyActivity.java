package com.chuchujie.pluginlib;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

public class ProxyActivity extends FragmentActivity {

    private PluginInterface mPluginInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //拿到要启动的Activity
        String className = getIntent().getStringExtra("className");

        try {
            Class<?> pluginClass = PluginManager.getInstance().getDexClassLoader().loadClass(className);
            Object newInstance = pluginClass.newInstance();
            if (newInstance instanceof PluginInterface) {
                mPluginInterface = (PluginInterface) newInstance;
                //将代理Activity的实例传递给三方Activity
                mPluginInterface.attachContext(this);
                Bundle bundle = new Bundle();
                mPluginInterface.onCreate(bundle);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        Intent newIntent = new Intent(this, ProxyActivity.class);
        newIntent.putExtra("className", intent.getComponent().getClassName());
        super.startActivity(newIntent);
    }

    @Override
    protected void onStart() {
        mPluginInterface.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        mPluginInterface.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mPluginInterface.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mPluginInterface.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPluginInterface.onDestroy();
        super.onDestroy();
    }


    /**
     * 注意：三方调用拿到对应加载的三方Resources
     */
    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getPluginResource();
    }


}
