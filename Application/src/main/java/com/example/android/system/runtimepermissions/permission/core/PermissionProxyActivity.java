package com.example.android.system.runtimepermissions.permission.core;

import android.support.annotation.NonNull;

import common.activities.SampleActivityBase;

public class PermissionProxyActivity extends SampleActivityBase {

    private PermissionGroup mPermissionGroup;

    public void requestPermissions(PermissionGroup group) {
        if (null != group) {
            if (group.isAllGranted(this)) {
                group.onChecked();
            } else if (null == mPermissionGroup){
                mPermissionGroup = group;
                mPermissionGroup.requestPermissions(this);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (null != mPermissionGroup && requestCode == mPermissionGroup.getRequestCode()) {
            if (PermissionGroup.verify(grantResults)) {
                mPermissionGroup.onGranted();
            } else {
                mPermissionGroup.onDenied();
            }
        }

        mPermissionGroup = null;
    }
}