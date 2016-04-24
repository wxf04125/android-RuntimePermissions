package com.example.android.system.runtimepermissions.permission.core;

import android.app.Activity;

/**
 * Created by jimsmac on 16/4/17.
 */
public abstract class PermissionRationale {

    protected PermissionGroup mPermissionGroup;

    public void setPermissionGroup(PermissionGroup permissionGroup){
        mPermissionGroup = permissionGroup;
    }

    public abstract void showRationale(Activity activity);

}
