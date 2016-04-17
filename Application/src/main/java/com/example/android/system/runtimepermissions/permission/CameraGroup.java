package com.example.android.system.runtimepermissions.permission;

import android.Manifest.permission;
import android.widget.Toast;

import com.example.android.system.runtimepermissions.permission.core.PermissionGroup;

public abstract class CameraGroup extends PermissionGroup {

    @Override
    public String[] getPermissions() {
        return new String[]{permission.CAMERA};
    }

    @Override
    public void showRationale() {
        Toast.makeText(mActivity, "rationale_camera", Toast.LENGTH_SHORT).show();
        doRequest();
    }
}
