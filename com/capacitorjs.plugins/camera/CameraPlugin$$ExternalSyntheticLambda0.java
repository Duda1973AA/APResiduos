package com.capacitorjs.plugins.camera;

import com.capacitorjs.plugins.camera.CameraBottomSheetDialogFragment;
import com.getcapacitor.PluginCall;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class CameraPlugin$$ExternalSyntheticLambda0 implements CameraBottomSheetDialogFragment.BottomSheetOnSelectedListener {
    public final /* synthetic */ PluginCall f$1;

    public /* synthetic */ CameraPlugin$$ExternalSyntheticLambda0(PluginCall pluginCall) {
        pluginCall = pluginCall;
    }

    @Override // com.capacitorjs.plugins.camera.CameraBottomSheetDialogFragment.BottomSheetOnSelectedListener
    public final void onSelected(int i) {
        CameraPlugin.this.lambda$showPrompt$0(pluginCall, i);
    }
}