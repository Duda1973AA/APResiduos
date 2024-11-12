package androidx.core.view;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class MenuHostHelper$$ExternalSyntheticLambda1 implements LifecycleEventObserver {
    public final /* synthetic */ Lifecycle.State f$1;
    public final /* synthetic */ MenuProvider f$2;

    public /* synthetic */ MenuHostHelper$$ExternalSyntheticLambda1(Lifecycle.State state, MenuProvider menuProvider) {
        state = state;
        menuProvider = menuProvider;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        MenuHostHelper.this.m37lambda$addMenuProvider$1$androidxcoreviewMenuHostHelper(state, menuProvider, lifecycleOwner, event);
    }
}