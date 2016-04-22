package com.example.android.system.runtimepermissions.permission.rationale;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.android.system.runtimepermissions.permission.core.PermissionRationale;

/**
 * Created by jimsmac on 16/4/17.
 */
public class ToastRationale extends PermissionRationale {

    private static final long serialVersionUID = 564899352713897655L;

    private int mStringId;

    public ToastRationale(int stringId) {
        mStringId = stringId;
    }

    @Override
    public void showRationale(final Activity activity, final Fragment fragment) {
        Toast.makeText(activity, mStringId, Toast.LENGTH_LONG).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mPermissionGroup.doRequest(activity, fragment);
            }
        }, 1000);

    }
}
