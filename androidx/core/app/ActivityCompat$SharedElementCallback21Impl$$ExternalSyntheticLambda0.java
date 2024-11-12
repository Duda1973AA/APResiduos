package androidx.core.app;

import android.app.SharedElementCallback;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class ActivityCompat$SharedElementCallback21Impl$$ExternalSyntheticLambda0 implements SharedElementCallback.OnSharedElementsReadyListener {
    public final /* synthetic */ SharedElementCallback.OnSharedElementsReadyListener f$0;

    public /* synthetic */ ActivityCompat$SharedElementCallback21Impl$$ExternalSyntheticLambda0(SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
        onSharedElementsReadyListener = onSharedElementsReadyListener;
    }

    @Override // androidx.core.app.SharedElementCallback.OnSharedElementsReadyListener
    public final void onSharedElementsReady() {
        ActivityCompat.Api23Impl.onSharedElementsReady(onSharedElementsReadyListener);
    }
}