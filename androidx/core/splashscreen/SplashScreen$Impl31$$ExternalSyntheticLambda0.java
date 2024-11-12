package androidx.core.splashscreen;

import android.window.SplashScreen;
import android.window.SplashScreenView;
import androidx.core.splashscreen.SplashScreen;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class SplashScreen$Impl31$$ExternalSyntheticLambda0 implements SplashScreen.OnExitAnimationListener {
    public final /* synthetic */ SplashScreen.OnExitAnimationListener f$1;

    public /* synthetic */ SplashScreen$Impl31$$ExternalSyntheticLambda0(SplashScreen.OnExitAnimationListener onExitAnimationListener) {
        exitAnimationListener = onExitAnimationListener;
    }

    @Override // android.window.SplashScreen.OnExitAnimationListener
    public final void onSplashScreenExit(SplashScreenView splashScreenView) {
        SplashScreen.Impl31.m35setOnExitAnimationListener$lambda0(SplashScreen.Impl31.this, exitAnimationListener, splashScreenView);
    }
}