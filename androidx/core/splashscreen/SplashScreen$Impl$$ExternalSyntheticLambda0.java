package androidx.core.splashscreen;

import androidx.core.splashscreen.SplashScreen;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class SplashScreen$Impl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SplashScreen.OnExitAnimationListener f$1;

    public /* synthetic */ SplashScreen$Impl$$ExternalSyntheticLambda0(SplashScreen.OnExitAnimationListener onExitAnimationListener) {
        onExitAnimationListener = onExitAnimationListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SplashScreen.Impl.m32dispatchOnExitAnimation$lambda3(SplashScreenViewProvider.this, onExitAnimationListener);
    }
}