package androidx.core.location;

import android.location.Location;
import androidx.core.util.Consumer;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class LocationManagerCompat$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ Location f$1;

    public /* synthetic */ LocationManagerCompat$$ExternalSyntheticLambda1(Location location) {
        lastKnownLocation = location;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Consumer.this.accept(lastKnownLocation);
    }
}