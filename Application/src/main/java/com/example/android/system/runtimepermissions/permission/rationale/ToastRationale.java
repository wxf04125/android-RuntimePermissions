package com.example.android.system.runtimepermissions.permission.rationale;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.android.system.runtimepermissions.permission.core.PermissionRationale;

/**
 * Created by jimsmac on 16/4/17.
 */
public class ToastRationale extends PermissionRationale {

    private int mStringId;

    public ToastRationale(int stringId) {
        mStringId = stringId;
    }

    @Override
    public void showRationale(Activity activity) {
        Toast.makeText(activity, mStringId, Toast.LENGTH_LONG).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mPermissionGroup.requestPermissions();
            }
        }, 1000);

    }
}
