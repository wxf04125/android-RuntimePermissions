package com.example.android.common.permission.group;

import android.Manifest.permission;
import android.app.Activity;

public class LocationGroup extends PermissionGroup {

    public LocationGroup(Activity activity) {
        super(activity);
    }

    @Override
    public String[] getPermissions() {
        return new String[]{permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION};
    }

    @Override
    public int getRequestCode() {
        return REQUEST_LOCATION;
    }

}
