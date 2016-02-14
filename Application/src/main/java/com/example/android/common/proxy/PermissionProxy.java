package com.example.android.common.proxy;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.android.common.permission.PermissionGroup;

public abstract class PermissionProxy extends BaseProxy {

    private PermissionGroup mPermissionGroup;

    public PermissionProxy(Activity activity, PermissionGroup permissionGroup) {
        super(activity);
        mPermissionGroup = permissionGroup;
    }

    @Override
    public void validate() {
        if (isNotValid()) {
            if (mPermissionGroup.shouldShowRationale()) {
                showRationale();
            } else {
                requestPermissions();
            }
        } else {
            onValidated();
        }
    }

    @Override
    public boolean isNotValid() {
        return !mPermissionGroup.isAllGranted();
    }

    @Override
    public int getRequestCode() {
        return mPermissionGroup.getRequestCode();
    }

    public void requestPermissions(){
        mPermissionGroup.request();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == mPermissionGroup.getRequestCode()) {
            if (PermissionGroup.verifyPermissions(grantResults)) {
                onValidated();
            } else {
                onDeny();
            }
        }
    }

    public abstract void showRationale();

    public abstract void onDeny();
}
