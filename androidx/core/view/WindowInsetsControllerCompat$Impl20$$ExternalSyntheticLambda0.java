package androidx.core.view;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class WindowInsetsControllerCompat$Impl20$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ View f$0;

    public /* synthetic */ WindowInsetsControllerCompat$Impl20$$ExternalSyntheticLambda0(View view) {
        view = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ((InputMethodManager) r0.getContext().getSystemService("input_method")).showSoftInput(view, 0);
    }
}