package com.example.android.system.runtimepermissions.permission.core;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.example.android.common.logger.Log;

import common.activities.SampleActivityBase;

public class PermissionProxyActivity extends SampleActivityBase implements PermissionProxy {

    private PermissionGroupContainer mContainer = new PermissionGroupContainer();

    @Override
    public Activity getActivity() {
        return this;
    }

    public void checkPermissions(PermissionGroup group) {
        group.checkPermissions(this);
    }

    @Override
    public void requestPermissions(PermissionGroup group) {
        mContainer.addPermissions(group);
        ActivityCompat.requestPermissions(getActivity(), group.getUnGranted(), group.getRequestCode());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("PermissionProxyActivity", "onRequestPermissionsResult");
        mContainer.verifyPermissions(requestCode, grantResults);
    }
}