package com.getcapacitor;

import android.content.DialogInterface;
import android.webkit.JsPromptResult;
import android.widget.EditText;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda8 implements DialogInterface.OnClickListener {
    public final /* synthetic */ EditText f$0;
    public final /* synthetic */ JsPromptResult f$1;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda8(EditText editText, JsPromptResult jsPromptResult) {
        editText = editText;
        jsPromptResult = jsPromptResult;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        BridgeWebChromeClient.lambda$onJsPrompt$8(editText, jsPromptResult, dialogInterface, i);
    }
}