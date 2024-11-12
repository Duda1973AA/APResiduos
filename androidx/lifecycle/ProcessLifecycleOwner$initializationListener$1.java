package androidx.lifecycle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ReportFragment;
import kotlin.Metadata;

/* compiled from: ProcessLifecycleOwner.kt */
@Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0003H\u0016J\b\u0010\u0005\u001a\u00020\u0003H\u0016¨\u0006\u0006"}, d2 = {"androidx/lifecycle/ProcessLifecycleOwner$initializationListener$1", "Landroidx/lifecycle/ReportFragment$ActivityInitializationListener;", "onCreate", "", "onResume", "onStart", "lifecycle-process_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class ProcessLifecycleOwner$initializationListener$1 implements ReportFragment.ActivityInitializationListener {
    @Override // androidx.lifecycle.ReportFragment.ActivityInitializationListener
    public void onCreate() {
    }

    ProcessLifecycleOwner$initializationListener$1() {
    }

    @Override // androidx.lifecycle.ReportFragment.ActivityInitializationListener
    public void onStart() {
        ProcessLifecycleOwner.this.activityStarted$lifecycle_process_release();
    }

    @Override // androidx.lifecycle.ReportFragment.ActivityInitializationListener
    public void onResume() {
        ProcessLifecycleOwner.this.activityResumed$lifecycle_process_release();
    }
}