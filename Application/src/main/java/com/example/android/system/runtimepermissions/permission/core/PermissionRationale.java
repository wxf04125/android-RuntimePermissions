package com.example.android.system.runtimepermissions.permission.core;

import android.app.Activity;

/**
 * Created by jimsmac on 16/4/17.
 */
public abstract class PermissionRationale {

    protected Activity mActivity;

    protected PermissionGroup mPermissionGroup;

    public void setPermissionGroup(PermissionGroup permissionGroup){
        mPermissionGroup = permissionGroup;
        mActivity = mPermissionGroup.mActivity;
    }

    public abstract void showRationale();

}
