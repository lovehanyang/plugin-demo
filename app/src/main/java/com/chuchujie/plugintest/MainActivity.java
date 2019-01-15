package com.chuchujie.plugintest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chuchujie.pluginlib.PluginManager;
import com.chuchujie.pluginlib.ProxyActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadApk(View view) {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    public void startApk(View view) {
        Intent intent = new Intent(this, ProxyActivity.class);
        String otherApkMainActivityName = PluginManager.getInstance().getPluginPackageArchiveInfo().activities[0].name;
        intent.putExtra("className", otherApkMainActivityName);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PluginManager.getInstance().setApplicationContext(this.getApplicationContext());
        PluginManager.getInstance().loadApk(this.getExternalFilesDir("")
                + File.separator
                + "plugin"
                +  File.separator
                + "pluginapk-debug.apk");
    }
}
