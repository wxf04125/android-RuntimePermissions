package com.example.android.system.runtimepermissions.permission.rationale;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.example.android.system.runtimepermissions.permission.core.PermissionRationale;

/**
 * Created by jimsmac on 16/4/17.
 */
public class DialogRationale extends PermissionRationale {

    private int mStringId;

    public DialogRationale(int stringId) {
        mStringId = stringId;
    }

    @Override
    public void showRationale(final Activity activity, final Fragment fragment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(mStringId);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPermissionGroup.doRequest(activity, fragment);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }
}
