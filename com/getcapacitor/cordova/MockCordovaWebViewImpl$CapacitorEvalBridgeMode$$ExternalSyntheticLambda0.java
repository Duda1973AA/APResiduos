package com.getcapacitor.cordova;

import com.getcapacitor.cordova.MockCordovaWebViewImpl;
import org.apache.cordova.NativeToJsMessageQueue;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class MockCordovaWebViewImpl$CapacitorEvalBridgeMode$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ NativeToJsMessageQueue f$1;

    public /* synthetic */ MockCordovaWebViewImpl$CapacitorEvalBridgeMode$$ExternalSyntheticLambda0(NativeToJsMessageQueue nativeToJsMessageQueue) {
        nativeToJsMessageQueue = nativeToJsMessageQueue;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MockCordovaWebViewImpl.CapacitorEvalBridgeMode.this.lambda$onNativeToJsMessageAvailable$0(nativeToJsMessageQueue);
    }
}