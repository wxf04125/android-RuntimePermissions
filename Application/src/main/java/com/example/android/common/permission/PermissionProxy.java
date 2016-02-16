package com.example.android.common.permission;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.android.common.permission.group.PermissionGroup;


public abstract class PermissionProxy {

    protected Activity mActivity;

    private PermissionGroup mPermissionGroup;

    public PermissionProxy(PermissionGroup permissionGroup) {
        mActivity = permissionGroup.getActivity();
        mPermissionGroup = permissionGroup;
    }

    public void validate() {
        if (isGranted()) {
            onGranted();
        } else {
            if (mPermissionGroup.shouldShowRationale()) {
                showRationale();
            } else {
                requestPermissions();
            }
        }
    }

    protected boolean isGranted(){
        return mPermissionGroup.isAllGranted();
    }

    public void requestPermissions() {
        mPermissionGroup.request();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == mPermissionGroup.getRequestCode()) {
            if (PermissionGroup.verify(grantResults)) {
                onGranted();
            } else {
                onDenied();
            }
        }
    }

    protected abstract void onGranted();

    public abstract void onDenied();

    public abstract void showRationale();
}
