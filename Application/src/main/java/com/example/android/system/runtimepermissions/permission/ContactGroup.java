package com.example.android.system.runtimepermissions.permission;

import android.Manifest.permission;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.android.system.runtimepermissions.permission.core.PermissionGroup;

public abstract class ContactGroup extends PermissionGroup {

    @Override
    public String[] getPermissions() {
        return new String[]{permission.READ_CONTACTS, permission.WRITE_CONTACTS};
    }

    protected abstract int[] getSnackbarResources();

    @Override
    public void showRationale() {
        Snackbar.make(mActivity.findViewById(getSnackbarResources()[0]), getSnackbarResources()[1], Snackbar.LENGTH_INDEFINITE).
                setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doRequest();
                    }
                }).show();
    }
}