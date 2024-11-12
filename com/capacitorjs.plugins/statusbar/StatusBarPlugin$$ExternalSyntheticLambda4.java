package com.capacitorjs.plugins.statusbar;

import com.getcapacitor.PluginCall;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class StatusBarPlugin$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ String f$1;
    public final /* synthetic */ PluginCall f$2;

    public /* synthetic */ StatusBarPlugin$$ExternalSyntheticLambda4(String str, PluginCall pluginCall) {
        string = str;
        pluginCall = pluginCall;
    }

    @Override // java.lang.Runnable
    public final void run() {
        StatusBarPlugin.this.lambda$setStyle$0(string, pluginCall);
    }
}