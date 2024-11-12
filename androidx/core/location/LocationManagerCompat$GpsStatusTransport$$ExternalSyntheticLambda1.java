package androidx.core.location;

import androidx.core.location.LocationManagerCompat;
import java.util.concurrent.Executor;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ Executor f$1;

    public /* synthetic */ LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda1(Executor executor) {
        executor = executor;
    }

    @Override // java.lang.Runnable
    public final void run() {
        LocationManagerCompat.GpsStatusTransport.this.m17xc3a89a22(executor);
    }
}