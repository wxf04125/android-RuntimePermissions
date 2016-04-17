package com.example.android.system.runtimepermissions.permission.core;

public interface PermissionGrantInterface {

    String[] getPermissions();

    void showRationale();

    /**
     * 1.you've got the permissions
     * 2.callback when user grant the requested permissions
     */
    void onGranted();

    /**
     * callback when user deny your request
     */
    void onDenied();
}