package com.example.android.system.runtimepermissions.permission.core;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimsmac on 16/4/23.
 */
public class PermissionGroupContainer {

    private List<PermissionGroup> mPermissionGroups = new ArrayList<>();

    public void addPermissions(PermissionGroup group) {
        if (!mPermissionGroups.contains(group)) {
            mPermissionGroups.add(group);
        }
    }

    public void verifyPermissions(int requestCode, @NonNull int[] grantResults) {
        if (null != mPermissionGroups && mPermissionGroups.size() > 0) {
            for (PermissionGroup permissionGroup : mPermissionGroups) {
                if (permissionGroup.getRequestCode() == requestCode) {
                    if (PermissionGroup.verify(grantResults)) {
                        permissionGroup.onGranted();
                    } else {
                        permissionGroup.onDenied();
                    }
                    mPermissionGroups.remove(permissionGroup);
                }
            }
        }
    }
}
