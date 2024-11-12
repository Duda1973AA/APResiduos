package com.capacitorjs.plugins.camera;

import com.capacitorjs.plugins.camera.CameraBottomSheetDialogFragment;
import com.getcapacitor.PluginCall;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class CameraPlugin$$ExternalSyntheticLambda1 implements CameraBottomSheetDialogFragment.BottomSheetOnCanceledListener {
    public /* synthetic */ CameraPlugin$$ExternalSyntheticLambda1() {
    }

    @Override // com.capacitorjs.plugins.camera.CameraBottomSheetDialogFragment.BottomSheetOnCanceledListener
    public final void onCanceled() {
        PluginCall.this.reject("User cancelled photos app");
    }
}