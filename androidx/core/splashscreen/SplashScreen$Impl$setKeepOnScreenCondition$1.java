package androidx.core.splashscreen;

import android.view.View;
import android.view.ViewTreeObserver;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.splashscreen.SplashScreen;
import kotlin.Metadata;

/* compiled from: SplashScreen.kt */
@Metadata(d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"androidx/core/splashscreen/SplashScreen$Impl$setKeepOnScreenCondition$1", "Landroid/view/ViewTreeObserver$OnPreDrawListener;", "onPreDraw", "", "core-splashscreen_release"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class SplashScreen$Impl$setKeepOnScreenCondition$1 implements ViewTreeObserver.OnPreDrawListener {
    final /* synthetic */ View $contentView;

    SplashScreen$Impl$setKeepOnScreenCondition$1(View view) {
        findViewById = view;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        SplashScreenViewProvider splashScreenViewProvider;
        if (SplashScreen.Impl.this.getSplashScreenWaitPredicate().shouldKeepOnScreen()) {
            return false;
        }
        findViewById.getViewTreeObserver().removeOnPreDrawListener(this);
        splashScreenViewProvider = SplashScreen.Impl.this.mSplashScreenViewProvider;
        if (splashScreenViewProvider == null) {
            return true;
        }
        SplashScreen.Impl.this.dispatchOnExitAnimation(splashScreenViewProvider);
        return true;
    }
}