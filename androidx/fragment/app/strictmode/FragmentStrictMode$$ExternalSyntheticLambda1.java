package androidx.fragment.app.strictmode;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class FragmentStrictMode$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Violation f$1;

    public /* synthetic */ FragmentStrictMode$$ExternalSyntheticLambda1(String str, Violation violation) {
        name = str;
        violation = violation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FragmentStrictMode.m54handlePolicyViolation$lambda1(name, violation);
    }
}