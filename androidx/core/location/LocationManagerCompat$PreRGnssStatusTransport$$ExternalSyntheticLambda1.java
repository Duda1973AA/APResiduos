package androidx.core.location;

import android.location.GnssStatus;
import androidx.core.location.LocationManagerCompat;
import java.util.concurrent.Executor;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$PreRGnssStatusTransport$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ Executor f$1;
    public final /* synthetic */ GnssStatus f$2;

    public /* synthetic */ LocationManagerCompat$PreRGnssStatusTransport$$ExternalSyntheticLambda1(Executor executor, GnssStatus gnssStatus) {
        executor = executor;
        gnssStatus = gnssStatus;
    }

    @Override // java.lang.Runnable
    public final void run() {
        LocationManagerCompat.PreRGnssStatusTransport.this.m27xdecf6cdb(executor, gnssStatus);
    }
}