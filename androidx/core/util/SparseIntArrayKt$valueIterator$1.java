package androidx.core.util;

import android.util.SparseIntArray;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.collections.IntIterator;

/* compiled from: SparseIntArray.kt */
@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\t\u0010\b\u001a\u00020\tH\u0096\u0002J\b\u0010\n\u001a\u00020\u0003H\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"androidx/core/util/SparseIntArrayKt$valueIterator$1", "Lkotlin/collections/IntIterator;", "index", "", "getIndex", "()I", "setIndex", "(I)V", "hasNext", "", "nextInt", "core-ktx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class SparseIntArrayKt$valueIterator$1 extends IntIterator {
    final /* synthetic */ SparseIntArray $this_valueIterator;
    private int index;

    SparseIntArrayKt$valueIterator$1(SparseIntArray sparseIntArray) {
        sparseIntArray = sparseIntArray;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int i) {
        this.index = i;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.index < sparseIntArray.size();
    }

    @Override // kotlin.collections.IntIterator
    public int nextInt() {
        SparseIntArray sparseIntArray = sparseIntArray;
        int i = this.index;
        this.index = i + 1;
        return sparseIntArray.valueAt(i);
    }
}