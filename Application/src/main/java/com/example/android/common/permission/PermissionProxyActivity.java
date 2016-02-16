package com.example.android.common.permission;

import android.support.annotation.NonNull;

import common.activities.SampleActivityBase;

public class PermissionProxyActivity extends SampleActivityBase {

    private PermissionProxy mPermissionProxy;

    public void requestPermissions(PermissionProxy proxy) {
        mPermissionProxy = proxy;
        if (null != mPermissionProxy) {
            mPermissionProxy.validate();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (null != mPermissionProxy) {
            mPermissionProxy.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}