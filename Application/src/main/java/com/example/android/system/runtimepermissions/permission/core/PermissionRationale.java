package com.example.android.system.runtimepermissions.permission.core;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by jimsmac on 16/4/17.
 */
public abstract class PermissionRationale implements Serializable{

    protected PermissionGroup mPermissionGroup;

    public void setPermissionGroup(PermissionGroup permissionGroup){
        mPermissionGroup = permissionGroup;
    }

    public abstract void showRationale(Activity activity, Fragment fragment);

}
