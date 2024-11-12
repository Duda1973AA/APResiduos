package com.capacitorjs.plugins.statusbar;

import com.getcapacitor.PluginCall;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class StatusBarPlugin$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ Boolean f$1;
    public final /* synthetic */ PluginCall f$2;

    public /* synthetic */ StatusBarPlugin$$ExternalSyntheticLambda3(Boolean bool, PluginCall pluginCall) {
        bool = bool;
        pluginCall = pluginCall;
    }

    @Override // java.lang.Runnable
    public final void run() {
        StatusBarPlugin.this.lambda$setOverlaysWebView$4(bool, pluginCall);
    }
}