package com.example.android.common.proxy;

import android.app.Activity;

public abstract class BaseProxy {

    protected Activity mActivity;

    public BaseProxy(Activity activity) {
        mActivity = activity;
    }

    protected abstract boolean isNotValid();

    protected abstract int getRequestCode();

    protected abstract void validate();

    protected abstract void onValidated();

}
