package com.example.android.system.runtimepermissions.permission.rationale;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.android.system.runtimepermissions.permission.core.PermissionRationale;

/**
 * Created by jimsmac on 16/4/17.
 */
public class SnackBarRationale extends PermissionRationale {

    private int mViewId;

    private int mStringId;

    public SnackBarRationale(int viewId, int stringId) {
        mViewId = viewId;
        mStringId = stringId;
    }

    @Override
    public void showRationale(Activity activity) {
        Snackbar.make(activity.findViewById(mViewId), mStringId, Snackbar.LENGTH_INDEFINITE).
                setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPermissionGroup.doRequest();
                    }
                }).show();
    }
}
