package com.example.android.common.permission.group;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 在正常的获取用户授权过程中，这个类的方法是按照顺序来调用的
 * isAllGranted --> shouldShowRationale --> request --> verify
 */
public abstract class PermissionGroup {

    public static final int REQUEST_LOCATION = 0;

    private Activity mActivity;

    private String[] mUnGranted;

    public PermissionGroup(Activity activity) {
        mActivity = activity;
    }

    public Activity getActivity(){
        return mActivity;
    }

    /**
     * 所需要的权限是否都授权了
     */
    public boolean isAllGranted() {
        mUnGranted = getUnGranted(getPermissions());
        return mUnGranted.length == 0;
    }

    /**
     * 获取未授权的权限
     */
    private String[] getUnGranted(String[] permissions) {
        List<String> unGranted = new ArrayList<>();
        if (null != permissions && permissions.length > 0) {
            for (String permission : permissions) {
                if (!checkSelfPermission(permission)) {
                    unGranted.add(permission);
                }
            }
        }
        return unGranted.toArray(new String[unGranted.size()]);
    }

    private boolean checkSelfPermission(String permission) {
        return ContextCompat.checkSelfPermission(mActivity, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 是否需要显示原因，方便用户理解为什么需要这些权限
     */
    public boolean shouldShowRationale() {
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
    public void request() {
        ActivityCompat.requestPermissions(mActivity, mUnGranted, getRequestCode());
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public static boolean verify(int[] grantResults) {
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

    public abstract int getRequestCode();

    public abstract String[] getPermissions();
}
