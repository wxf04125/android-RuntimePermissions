package com.example.android.system.runtimepermissions.permission.group;

import android.Manifest.permission;

import com.example.android.system.runtimepermissions.permission.core.PermissionGroup;
import com.example.android.system.runtimepermissions.permission.core.PermissionProxyActivity;
import com.example.android.system.runtimepermissions.permission.core.PermissionProxyFragment;

public abstract class ContactGroup extends PermissionGroup {

    public ContactGroup(PermissionProxyActivity activity){
        super(activity);
    }

    public ContactGroup(PermissionProxyFragment fragment){
        super(fragment);
    }

    @Override
    public String[] getPermissions() {
        return new String[]{permission.READ_CONTACTS, permission.WRITE_CONTACTS};
    }

}