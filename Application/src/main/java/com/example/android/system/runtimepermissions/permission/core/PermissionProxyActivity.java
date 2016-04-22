package com.example.android.system.runtimepermissions.permission.core;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import common.activities.SampleActivityBase;

public class PermissionProxyActivity extends SampleActivityBase {

    private List<PermissionGroup> mPermissionGroups = new ArrayList<>();

    public void requestPermissions(PermissionGroup group) {
        if (null != group) {
            if (!mPermissionGroups.contains(group)) {
                mPermissionGroups.add(group);
            }
            group.requestPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (null != mPermissionGroups && mPermissionGroups.size() > 0) {
            for (PermissionGroup permissionGroup : mPermissionGroups) {
                if (permissionGroup.getRequestCode() == requestCode)
                    if (PermissionGroup.verify(grantResults)) {
                        permissionGroup.onGranted();
                    } else {
                        permissionGroup.onDenied();
                    }
            }
        }
    }
}