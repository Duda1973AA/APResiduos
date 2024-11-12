package com.getcapacitor;

import android.webkit.PermissionRequest;
import com.getcapacitor.BridgeWebChromeClient;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda6 implements BridgeWebChromeClient.PermissionListener {
    public final /* synthetic */ PermissionRequest f$0;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda6(PermissionRequest permissionRequest) {
        permissionRequest = permissionRequest;
    }

    @Override // com.getcapacitor.BridgeWebChromeClient.PermissionListener
    public final void onPermissionSelect(Boolean bool) {
        BridgeWebChromeClient.lambda$onPermissionRequest$2(permissionRequest, bool);
    }
}