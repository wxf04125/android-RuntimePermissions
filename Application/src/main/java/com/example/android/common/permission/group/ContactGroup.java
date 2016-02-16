package com.example.android.common.permission.group;

import android.Manifest.permission;
import android.app.Activity;

import com.example.android.system.runtimepermissions.MainActivity;

public abstract class ContactGroup extends PermissionGroup {

    public ContactGroup(Activity activity) {
        super(activity);
    }

    @Override
    public String[] getPermissions() {
        return new String[]{permission.READ_CONTACTS, permission.WRITE_CONTACTS};
    }

    @Override
    public int getRequestCode() {
        return MainActivity.REQUEST_CONTACTS;
    }

}
