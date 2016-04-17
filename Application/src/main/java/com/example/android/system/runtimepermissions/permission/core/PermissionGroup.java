package com.example.android.system.runtimepermissions.permission.core;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * isAllGranted --> shouldShowRationale --> showRationale --> doRequest --> verify
 */
public abstract class PermissionGroup implements PermissionGrantInterface {

    private static int sBaseCode = 0;

    /**
     * 在fragment中只能使用低八位
     */
    private int mRequestCode;

    private String[] mUnGranted;

    protected Activity mActivity;

    private Fragment mFragment;

    protected PermissionGroup() {
        refreshRequestCode();
    }

    protected void requestPermissions(Activity activity) {
        mActivity = activity;
        mFragment = null;
        if (isAllGranted(activity)) {
            onGranted();
        } else if (shouldShowRationale()) {
            // 重写该方法时，在适当时候调用doRequest方法，进行权限请求，否则永远不会请求
            showRationale();
        } else {
            requestPermissionFromActivity();
        }
    }

    protected void requestPermissions(Fragment fragment) {
        mActivity = fragment.getActivity();
        mFragment = fragment;
        if (isAllGranted(mActivity)) {
            onGranted();
        } else if (shouldShowRationale()) {
            // 重写该方法时，在适当时候调用doRequest方法，进行权限请求，否则永远不会请求
            showRationale();
        } else {
            requestPermissionFromFragment();
        }
    }

    /**
     * 所需要的权限是否都授权了
     */
    public boolean isAllGranted(Context context) {
        mUnGranted = getUnGranted(context, getPermissions());
        return mUnGranted.length == 0;
    }

    /**
     * 获取未授权的权限
     */
    private String[] getUnGranted(Context context, String[] permissions) {
        List<String> unGranted = new ArrayList<>();
        if (null != permissions && permissions.length > 0) {
            for (String permission : permissions) {
                if (!checkSelfPermission(context, permission)) {
                    unGranted.add(permission);
                }
            }
        }
        return unGranted.toArray(new String[unGranted.size()]);
    }

    private boolean checkSelfPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 是否需要显示原因，方便用户理解为什么需要这些权限
     */
    boolean shouldShowRationale() {
        for (String permission : mUnGranted) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 请求权限
     */
    public void doRequest() {
        if (null != mFragment) {
            requestPermissionFromFragment();
        } else {
            requestPermissionFromActivity();
        }
    }

    private void requestPermissionFromActivity() {
        ActivityCompat.requestPermissions(mActivity, mUnGranted, getRequestCode());
    }

    private void requestPermissionFromFragment() {
        mFragment.requestPermissions(mUnGranted, getRequestCode());
    }

    /**
     * 保证requestCode的值在0-255之间
     */
    private void refreshRequestCode() {
        if (sBaseCode > 0xff) {
            sBaseCode = 0;
        }

        mRequestCode = sBaseCode++;
    }

    int getRequestCode() {
        return mRequestCode;
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    static boolean verify(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在这里处理用户拒绝授权的情况，可以在这里提供默认的实现，也可以由子类自己实现
     */
    @Override
    public void onDenied() {

    }
}
