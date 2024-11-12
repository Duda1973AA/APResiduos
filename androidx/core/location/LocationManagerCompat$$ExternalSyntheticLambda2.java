package androidx.core.location;

import androidx.core.location.LocationManagerCompat;
import androidx.core.os.CancellationSignal;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$$ExternalSyntheticLambda2 implements CancellationSignal.OnCancelListener {
    public /* synthetic */ LocationManagerCompat$$ExternalSyntheticLambda2() {
    }

    @Override // androidx.core.os.CancellationSignal.OnCancelListener
    public final void onCancel() {
        LocationManagerCompat.CancellableLocationListener.this.cancel();
    }
}