package com.getcapacitor;

import android.content.DialogInterface;
import android.webkit.JsResult;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BridgeWebChromeClient$$ExternalSyntheticLambda13 implements DialogInterface.OnClickListener {
    public final /* synthetic */ JsResult f$0;

    public /* synthetic */ BridgeWebChromeClient$$ExternalSyntheticLambda13(JsResult jsResult) {
        jsResult = jsResult;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        BridgeWebChromeClient.lambda$onJsConfirm$6(jsResult, dialogInterface, i);
    }
}