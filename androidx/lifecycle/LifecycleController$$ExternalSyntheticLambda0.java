package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlinx.coroutines.Job;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class LifecycleController$$ExternalSyntheticLambda0 implements LifecycleEventObserver {
    public final /* synthetic */ Job f$1;

    public /* synthetic */ LifecycleController$$ExternalSyntheticLambda0(Job job) {
        parentJob = job;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        LifecycleController.observer$lambda$0(LifecycleController.this, parentJob, lifecycleOwner, event);
    }
}