package com.example.android.system.runtimepermissions.permission.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PermissionProxyFragment extends Fragment {

    public static final String SAVED_PERMISSION_STATE = "saved_permission_state";

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPermissionGroups = (ArrayList<PermissionGroup>) savedInstanceState.getParcelableArrayList(SAVED_PERMISSION_STATE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putputSerializable(SAVED_PERMISSION_STATE, mPermissionGroups);
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