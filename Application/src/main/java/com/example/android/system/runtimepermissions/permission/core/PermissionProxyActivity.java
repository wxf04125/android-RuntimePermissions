package com.example.android.system.runtimepermissions.permission.core;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import common.activities.SampleActivityBase;

public class PermissionProxyActivity extends SampleActivityBase implements PermissionProxy {

    private PermissionGroupContainer mContainer = new PermissionGroupContainer();

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void requestPermissions(PermissionGroup group) {
        mContainer.addPermissions(group);
        ActivityCompat.requestPermissions(this, group.getUnGranted(), group.getRequestCode());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mContainer.checkAndRemovePermissions(requestCode, grantResults);
    }
}