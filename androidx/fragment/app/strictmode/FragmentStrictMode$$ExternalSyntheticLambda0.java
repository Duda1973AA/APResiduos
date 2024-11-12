package androidx.fragment.app.strictmode;

import androidx.fragment.app.strictmode.FragmentStrictMode;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class FragmentStrictMode$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Violation f$1;

    public /* synthetic */ FragmentStrictMode$$ExternalSyntheticLambda0(Violation violation) {
        violation = violation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FragmentStrictMode.m53handlePolicyViolation$lambda0(FragmentStrictMode.Policy.this, violation);
    }
}