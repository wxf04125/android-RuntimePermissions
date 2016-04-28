package com.example.android.system.runtimepermissions.permission.core;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.android.common.logger.Log;

public class PermissionProxyFragment extends Fragment implements PermissionProxy {

    private PermissionGroupContainer mContainer = new PermissionGroupContainer();

    public void checkPermissions(PermissionGroup group) {
        group.checkPermissions(this);
    }

    @Override
    public void requestPermissions(PermissionGroup group) {
        mContainer.addPermissions(group);
        requestPermissions(group.getUnGranted(), group.getRequestCode());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("PermissionProxyFragment", "onRequestPermissionsResult");
        mContainer.verifyPermissions(requestCode, grantResults);
    }
}