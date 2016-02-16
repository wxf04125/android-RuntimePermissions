package com.example.android.common.permission;

import android.support.annotation.NonNull;

import com.example.android.common.permission.group.PermissionGroup;

import common.activities.SampleActivityBase;

public abstract class PermissionProxyActivity extends SampleActivityBase {

    private PermissionGroup mPermissionGroup;

    public void requestPermissions(PermissionGroup group) {
        mPermissionGroup = group;
        if (null != group) {
            validate();
        }
    }

    public void validate() {
        if (mPermissionGroup.isAllGranted()) {
            mPermissionGroup.onGranted();
        } else {
            if (mPermissionGroup.shouldShowRationale()) {
                mPermissionGroup.showRationale();
            } else {
                mPermissionGroup.request();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == mPermissionGroup.getRequestCode()) {
            if (PermissionGroup.verify(grantResults)) {
                mPermissionGroup.onGranted();
            } else {
                mPermissionGroup.onDenied();
            }
        }
    }
}