package com.capacitorjs.plugins.filesystem;

import com.getcapacitor.PluginCall;
import com.getcapacitor.plugin.util.HttpRequestHandler;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class FilesystemPlugin$$ExternalSyntheticLambda0 implements HttpRequestHandler.ProgressEmitter {
    public final /* synthetic */ PluginCall f$1;

    public /* synthetic */ FilesystemPlugin$$ExternalSyntheticLambda0(PluginCall pluginCall) {
        pluginCall = pluginCall;
    }

    @Override // com.getcapacitor.plugin.util.HttpRequestHandler.ProgressEmitter
    public final void emit(Integer num, Integer num2) {
        FilesystemPlugin.this.lambda$downloadFile$0(pluginCall, num, num2);
    }
}