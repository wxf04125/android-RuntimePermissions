package com.example.android.system.runtimepermissions.permission.core;

import android.app.Activity;


/**
 * Created by jimsmac on 16/4/23.
 *
 */
public interface PermissionProxy {

    Activity getActivity();

    void requestPermissions(PermissionGroup group);

}
