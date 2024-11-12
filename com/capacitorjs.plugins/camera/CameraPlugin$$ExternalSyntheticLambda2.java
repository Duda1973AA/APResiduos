package com.capacitorjs.plugins.camera;

import android.content.Intent;
import com.getcapacitor.PluginCall;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class CameraPlugin$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ Intent f$1;
    public final /* synthetic */ PluginCall f$2;

    public /* synthetic */ CameraPlugin$$ExternalSyntheticLambda2(Intent intent, PluginCall pluginCall) {
        data = intent;
        pluginCall = pluginCall;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CameraPlugin.this.lambda$processPickedImages$2(data, pluginCall);
    }
}