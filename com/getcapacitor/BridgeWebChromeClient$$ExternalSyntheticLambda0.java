package com.getcapacitor;

import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import com.getcapacitor.BridgeWebChromeClient;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda0 implements BridgeWebChromeClient.PermissionListener {
    public final /* synthetic */ ValueCallback f$1;
    public final /* synthetic */ WebChromeClient.FileChooserParams f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda0(ValueCallback valueCallback, WebChromeClient.FileChooserParams fileChooserParams, boolean z) {
        valueCallback = valueCallback;
        fileChooserParams = fileChooserParams;
        z = z;
    }

    @Override // com.getcapacitor.BridgeWebChromeClient.PermissionListener
    public final void onPermissionSelect(Boolean bool) {
        BridgeWebChromeClient.this.lambda$onShowFileChooser$12(valueCallback, fileChooserParams, z, bool);
    }
}