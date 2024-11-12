package androidx.profileinstaller;

import android.content.Context;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class ProfileInstallerInitializer$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ Context f$0;

    public /* synthetic */ ProfileInstallerInitializer$$ExternalSyntheticLambda0(Context context) {
        context = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ProfileInstallerInitializer.writeInBackground(context);
    }
}