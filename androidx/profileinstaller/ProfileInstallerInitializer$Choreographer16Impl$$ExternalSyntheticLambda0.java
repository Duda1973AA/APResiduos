package androidx.profileinstaller;

import android.view.Choreographer;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class ProfileInstallerInitializer$Choreographer16Impl$$ExternalSyntheticLambda0 implements Choreographer.FrameCallback {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ ProfileInstallerInitializer$Choreographer16Impl$$ExternalSyntheticLambda0(Runnable runnable) {
        runnable = runnable;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long j) {
        runnable.run();
    }
}