package com.example.android.system.runtimepermissions.permission.group;

import android.Manifest.permission;

import com.example.android.system.runtimepermissions.permission.core.PermissionGroup;
import com.example.android.system.runtimepermissions.permission.core.PermissionProxy;

public abstract class ContactGroup extends PermissionGroup {

    public ContactGroup(PermissionProxy proxy){
        super(proxy);
    }

    @Override
    public String[] getPermissions() {
        return new String[]{permission.READ_CONTACTS, permission.WRITE_CONTACTS};
    }

}