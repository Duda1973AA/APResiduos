package androidx.activity.result;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IntentSenderRequest.kt */
@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u001d\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"androidx/activity/result/IntentSenderRequest$Companion$CREATOR$1", "Landroid/os/Parcelable$Creator;", "Landroidx/activity/result/IntentSenderRequest;", "createFromParcel", "inParcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Landroidx/activity/result/IntentSenderRequest;", "activity_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class IntentSenderRequest$Companion$CREATOR$1 implements Parcelable.Creator<IntentSenderRequest> {
    IntentSenderRequest$Companion$CREATOR$1() {
    }

    @Override // android.os.Parcelable.Creator
    public IntentSenderRequest createFromParcel(Parcel inParcel) {
        Intrinsics.checkNotNullParameter(inParcel, "inParcel");
        return new IntentSenderRequest(inParcel);
    }

    @Override // android.os.Parcelable.Creator
    public IntentSenderRequest[] newArray(int size) {
        return new IntentSenderRequest[size];
    }
}