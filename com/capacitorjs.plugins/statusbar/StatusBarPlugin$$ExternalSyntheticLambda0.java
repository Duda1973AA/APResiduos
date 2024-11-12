package com.capacitorjs.plugins.statusbar;

import com.getcapacitor.PluginCall;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class StatusBarPlugin$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ String f$1;
    public final /* synthetic */ PluginCall f$2;

    public /* synthetic */ StatusBarPlugin$$ExternalSyntheticLambda0(String str, PluginCall pluginCall) {
        string = str;
        pluginCall = pluginCall;
    }

    @Override // java.lang.Runnable
    public final void run() {
        StatusBarPlugin.this.lambda$setBackgroundColor$1(string, pluginCall);
    }
}