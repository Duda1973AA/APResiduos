package com.getcapacitor;

import android.webkit.GeolocationPermissions;
import com.getcapacitor.BridgeWebChromeClient;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda3 implements BridgeWebChromeClient.PermissionListener {
    public final /* synthetic */ GeolocationPermissions.Callback f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda3(GeolocationPermissions.Callback callback, String str) {
        callback = callback;
        str = str;
    }

    @Override // com.getcapacitor.BridgeWebChromeClient.PermissionListener
    public final void onPermissionSelect(Boolean bool) {
        BridgeWebChromeClient.this.lambda$onGeolocationPermissionsShowPrompt$11(callback, str, bool);
    }
}