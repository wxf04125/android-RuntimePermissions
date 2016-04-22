package com.example.android.system.runtimepermissions.permission.core;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PermissionProxyFragment extends Fragment {

    private List<PermissionGroup> mPermissionGroups = new ArrayList<>();

    public void requestPermissions(PermissionGroup group) {
        if (null != group) {
            if (group.isAllGranted(getActivity())) {
                group.onChecked();
            } else {
                if (!mPermissionGroups.contains(group)) {
                    mPermissionGroups.add(group);
                }
                group.requestPermissions(this);
            }
        }
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
                    mPermissionGroups.remove(permissionGroup);
                }
            }
        }
    }
}