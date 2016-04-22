package com.example.android.system.runtimepermissions.permission.core;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * isAllGranted --> shouldShowRationale --> showRationale --> doRequest --> verify
 */
public abstract class PermissionGroup implements PermissionGrantCallback, Parcelable {

    private static int sBaseCode = 0;

    private PermissionRationale mRationale;

    /**
     * 在fragment中只能使用低八位
     */
    private int mRequestCode;

    private String[] mPermissions;

    private String[] mUnGranted;

    protected Activity mActivity;

    private Fragment mFragment;

    protected PermissionGroup() {
        refreshRequestCode();
    }

    protected PermissionGroup(String permission) {
        mPermissions = new String[]{permission};
        refreshRequestCode();


    }

    protected void requestPermissions(Activity activity) {
        mActivity = activity;
        mFragment = null;
        if (shouldShowRationale()) {
            // 重写该方法时，在适当时候调用doRequest方法，进行权限请求，否则永远不会请求
            showRationale();
        } else {
            requestPermissionFromActivity();
        }
    }

    protected void requestPermissions(Fragment fragment) {
        mActivity = fragment.getActivity();
        mFragment = fragment;
        if (shouldShowRationale()) {
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

    public String[] getPermissions() {
        return mPermissions;
    }

    public void showRationale() {
        if (null == mRationale) {
            doRequest();
        } else {
            mRationale.setPermissionGroup(this);
            mRationale.showRationale();
        }
    }

    public PermissionGroup setupRationale(PermissionRationale rationale) {
        mRationale = rationale;
        return this;
    }

    /**
     * 处理app之前已经获得授权的情况,默认按照授权成功来处理
     */
    public void onChecked() {
        onGranted();
    }

    /**
     * 处理用户拒绝授权的情况,默认不处理
     */
    public void onDenied() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
