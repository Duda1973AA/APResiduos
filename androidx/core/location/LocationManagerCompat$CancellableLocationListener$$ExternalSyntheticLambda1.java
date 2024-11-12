package androidx.core.location;

import android.location.Location;
import androidx.core.util.Consumer;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$CancellableLocationListener$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ Location f$1;

    public /* synthetic */ LocationManagerCompat$CancellableLocationListener$$ExternalSyntheticLambda1(Location location) {
        location = location;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Consumer.this.accept(location);
    }
}