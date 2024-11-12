package com.getcapacitor;

import android.content.DialogInterface;
import android.webkit.JsPromptResult;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda9 implements DialogInterface.OnClickListener {
    public final /* synthetic */ JsPromptResult f$0;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda9(JsPromptResult jsPromptResult) {
        jsPromptResult = jsPromptResult;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        BridgeWebChromeClient.lambda$onJsPrompt$9(jsPromptResult, dialogInterface, i);
    }
}