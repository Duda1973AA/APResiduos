package com.getcapacitor;

import android.net.Uri;
import android.webkit.ValueCallback;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.BridgeWebChromeClient;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda15 implements BridgeWebChromeClient.ActivityResultListener {
    public final /* synthetic */ ValueCallback f$0;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda15(ValueCallback valueCallback) {
        valueCallback = valueCallback;
    }

    @Override // com.getcapacitor.BridgeWebChromeClient.ActivityResultListener
    public final void onActivityResult(ActivityResult activityResult) {
        valueCallback.onReceiveValue(r3.getResultCode() == -1 ? new Uri[]{activityResult.getData().getData()} : null);
    }
}