package com.example.android.common.permission;

public interface PermissionGrantCallback {
    void onGranted();

    void onDenied();

    void showRationale();
}