package com.example.android.system.runtimepermissions.permission.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import common.activities.SampleActivityBase;

public class PermissionProxyActivity extends SampleActivityBase {

    private ArrayList<PermissionGroup> mPermissionGroups = new ArrayList<>();

    public void requestPermissions(PermissionGroup group) {
        if (null != group) {
            if (!mPermissionGroups.contains(group)) {
                mPermissionGroups.add(group);
            }
            group.requestPermissions(this);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPermissionGroups = (ArrayList<PermissionGroup>) savedInstanceState.getSerializable(
                    PermissionGroup.SAVED_PERMISSION_STATE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(PermissionGroup.SAVED_PERMISSION_STATE, mPermissionGroups);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (null != mPermissionGroups && mPermissionGroups.size() > 0) {
            for (PermissionGroup permissionGroup : mPermissionGroups) {
                if (permissionGroup.getRequestCode() == requestCode) {
                    if (PermissionGroup.verify(grantResults)) {
                        permissionGroup.onGranted();
                    } else {
                        permissionGroup.onDenied();
                    }
                }
            }
        }
    }
}