package com.getcapacitor;

import android.net.Uri;
import android.webkit.ValueCallback;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.BridgeWebChromeClient;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda7 implements BridgeWebChromeClient.ActivityResultListener {
    public final /* synthetic */ Uri f$0;
    public final /* synthetic */ ValueCallback f$1;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda7(Uri uri, ValueCallback valueCallback) {
        createImageFileUri = uri;
        valueCallback = valueCallback;
    }

    @Override // com.getcapacitor.BridgeWebChromeClient.ActivityResultListener
    public final void onActivityResult(ActivityResult activityResult) {
        valueCallback.onReceiveValue(r3.getResultCode() == -1 ? new Uri[]{createImageFileUri} : null);
    }
}