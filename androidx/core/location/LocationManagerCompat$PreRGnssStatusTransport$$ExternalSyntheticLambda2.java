package androidx.core.location;

import androidx.core.location.LocationManagerCompat;
import java.util.concurrent.Executor;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$PreRGnssStatusTransport$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ Executor f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ LocationManagerCompat$PreRGnssStatusTransport$$ExternalSyntheticLambda2(Executor executor, int i) {
        executor = executor;
        i = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        LocationManagerCompat.PreRGnssStatusTransport.this.m26x4191f1e(executor, i);
    }
}