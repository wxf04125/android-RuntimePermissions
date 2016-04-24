package com.example.android.system.runtimepermissions.permission.core;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * isAllGranted --> shouldShowRationale --> showRationale --> doRequest --> verify
 */
public abstract class PermissionGroup implements PermissionGrantCallback {

    private static int sBaseCode = 0;

    /**
     * 在fragment中只能使用低八位
     */
    private int mRequestCode;

    private PermissionProxy mPermissionProxy;

    private String[] mPermissions;

    private String[] mUnGranted;

    private PermissionRationale mRationale;

    protected PermissionGroup(){
        refreshRequestCode();
    }

    protected PermissionGroup(String... permissions) {
        mPermissions = permissions;
        refreshRequestCode();
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

    public void checkAndRequestPermissions(PermissionProxy proxy) {
        mPermissionProxy = proxy;
        Activity activity = proxy.getActivity();
        if (isAllGranted(activity)) {
            onChecked();
        } else if (shouldShowRationale(activity)) {
            // 重写该方法时，在适当时候调用doRequest方法，进行权限请求，否则永远不会请求
            showRationale(activity);
        } else {
            doRequest();
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
    public String[] getUnGranted(Context context, String[] permissions) {
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

    protected String[] getPermissions() {
        return mPermissions;
    }

    /**
     * 处理app之前已经获得授权的情况,默认按照授权成功来处理
     */
    public void onChecked() {
        onGranted();
    }

    /**
     * 是否需要显示原因，方便用户理解为什么需要这些权限
     */
    private boolean shouldShowRationale(Activity activity) {
        for (String permission : mUnGranted) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    private void showRationale(Activity activity) {
        if (null == mRationale) {
            doRequest();
        } else {
            mRationale.showRationale(activity);
        }
    }

    /**
     * 请求权限
     */
    public void doRequest() {
        mPermissionProxy.requestPermissions(this);
    }

    public PermissionGroup setupRationale(PermissionRationale rationale) {
        mRationale = rationale;
        mRationale.setPermissionGroup(this);
        return this;
    }

    public String[] getUnGranted() {
        return mUnGranted;
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
     * 处理用户拒绝授权的情况,默认不处理
     */
    public void onDenied() {

    }

}
