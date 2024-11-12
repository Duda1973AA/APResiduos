package com.getcapacitor;

import android.content.DialogInterface;
import android.webkit.JsPromptResult;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda10 implements DialogInterface.OnCancelListener {
    public final /* synthetic */ JsPromptResult f$0;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda10(JsPromptResult jsPromptResult) {
        jsPromptResult = jsPromptResult;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        BridgeWebChromeClient.lambda$onJsPrompt$10(jsPromptResult, dialogInterface);
    }
}