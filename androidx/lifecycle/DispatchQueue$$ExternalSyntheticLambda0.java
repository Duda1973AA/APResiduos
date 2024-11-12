package androidx.lifecycle;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class DispatchQueue$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ DispatchQueue$$ExternalSyntheticLambda0(Runnable runnable) {
        runnable = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DispatchQueue.dispatchAndEnqueue$lambda$2$lambda$1(DispatchQueue.this, runnable);
    }
}