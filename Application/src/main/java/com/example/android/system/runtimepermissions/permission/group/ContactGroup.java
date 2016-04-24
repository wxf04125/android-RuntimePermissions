package com.example.android.system.runtimepermissions.permission.group;

import android.Manifest.permission;

import com.example.android.system.runtimepermissions.permission.core.PermissionGroup;

public abstract class ContactGroup extends PermissionGroup {

    @Override
    public String[] getPermissions() {
        return new String[]{permission.READ_CONTACTS, permission.WRITE_CONTACTS};
    }

}