package com.google.android.material.bottomappbar;

import android.view.View;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BottomAppBar$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ View f$0;

    public /* synthetic */ BottomAppBar$$ExternalSyntheticLambda0(View view) {
        findDependentView = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        findDependentView.requestLayout();
    }
}