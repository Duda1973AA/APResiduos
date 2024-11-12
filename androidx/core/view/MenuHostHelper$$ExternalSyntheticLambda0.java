package androidx.core.view;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class MenuHostHelper$$ExternalSyntheticLambda0 implements LifecycleEventObserver {
    public final /* synthetic */ MenuProvider f$1;

    public /* synthetic */ MenuHostHelper$$ExternalSyntheticLambda0(MenuProvider menuProvider) {
        menuProvider = menuProvider;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        MenuHostHelper.this.m36lambda$addMenuProvider$0$androidxcoreviewMenuHostHelper(menuProvider, lifecycleOwner, event);
    }
}