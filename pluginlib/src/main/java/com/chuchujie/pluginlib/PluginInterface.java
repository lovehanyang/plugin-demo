package com.chuchujie.pluginlib;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public interface PluginInterface {

    void onCreate(Bundle saveInstance);

    void attachContext(FragmentActivity fragmentActivity);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}


