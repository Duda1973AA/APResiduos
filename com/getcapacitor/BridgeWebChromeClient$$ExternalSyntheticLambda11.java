package com.getcapacitor;

import android.webkit.ValueCallback;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.BridgeWebChromeClient;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda11 implements BridgeWebChromeClient.ActivityResultListener {
    public final /* synthetic */ ValueCallback f$0;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda11(ValueCallback valueCallback) {
        valueCallback = valueCallback;
    }

    @Override // com.getcapacitor.BridgeWebChromeClient.ActivityResultListener
    public final void onActivityResult(ActivityResult activityResult) {
        BridgeWebChromeClient.lambda$showFilePicker$15(valueCallback, activityResult);
    }
}