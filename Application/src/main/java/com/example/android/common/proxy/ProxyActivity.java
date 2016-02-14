package com.example.android.common.proxy;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import common.activities.SampleActivityBase;

public class ProxyActivity extends SampleActivityBase {

    private List<BaseProxy> mProxyList = new ArrayList<>();

    protected void addProxy(BaseProxy... proxies) {
        if (proxies != null && proxies.length > 0) {
            for (BaseProxy proxy : proxies) {
                mProxyList.add(proxy);
            }
        }
    }

    public void validatePermission() {
        for (BaseProxy proxy : mProxyList) {
            if (proxy instanceof PermissionProxy) {
                proxy.validate();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        for (BaseProxy proxy : mProxyList) {
            if (proxy instanceof PermissionProxy) {
                ((PermissionProxy) proxy).onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}