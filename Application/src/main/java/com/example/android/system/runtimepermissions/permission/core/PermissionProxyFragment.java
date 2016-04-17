package com.example.android.system.runtimepermissions.permission.core;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class PermissionProxyFragment extends Fragment {

    private PermissionGroup mPermissionGroup;

    public void requestPermissions(PermissionGroup group) {
        if (null != group) {
            mPermissionGroup = group;
            mPermissionGroup.requestPermissions(this);
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
    }
}