package androidx.lifecycle;

import androidx.arch.core.util.Function;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Transformations.kt */
@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0015\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000bR\"\u0010\u0002\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\f"}, d2 = {"androidx/lifecycle/Transformations$switchMap$2", "Landroidx/lifecycle/Observer;", "liveData", "Landroidx/lifecycle/LiveData;", "getLiveData", "()Landroidx/lifecycle/LiveData;", "setLiveData", "(Landroidx/lifecycle/LiveData;)V", "onChanged", "", "value", "(Ljava/lang/Object;)V", "lifecycle-livedata_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class Transformations$switchMap$2<X> implements Observer<X> {
    final /* synthetic */ MediatorLiveData<Y> $result;
    final /* synthetic */ Function<X, LiveData<Y>> $switchMapFunction;
    private LiveData<Y> liveData;

    Transformations$switchMap$2(Function<X, LiveData<Y>> function, MediatorLiveData<Y> mediatorLiveData) {
        switchMapFunction = function;
        mediatorLiveData = mediatorLiveData;
    }

    public final LiveData<Y> getLiveData() {
        return this.liveData;
    }

    public final void setLiveData(LiveData<Y> liveData) {
        this.liveData = liveData;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.lifecycle.Observer
    public void onChanged(X value) {
        LiveData<Y> liveData = (LiveData) switchMapFunction.apply(value);
        Object obj = this.liveData;
        if (obj == liveData) {
            return;
        }
        if (obj != null) {
            MediatorLiveData<Y> mediatorLiveData = mediatorLiveData;
            Intrinsics.checkNotNull(obj);
            mediatorLiveData.removeSource(obj);
        }
        this.liveData = liveData;
        if (liveData != 0) {
            MediatorLiveData<Y> mediatorLiveData2 = mediatorLiveData;
            Intrinsics.checkNotNull(liveData);
            mediatorLiveData2.addSource(liveData, new Transformations$sam$androidx_lifecycle_Observer$0(new Function1<Y, Unit>() { // from class: androidx.lifecycle.Transformations$switchMap$2$onChanged$1
                final /* synthetic */ MediatorLiveData<Y> $result;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                Transformations$switchMap$2$onChanged$1(MediatorLiveData<Y> mediatorLiveData3) {
                    super(1);
                    mediatorLiveData4 = mediatorLiveData3;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj2) {
                    invoke2((Transformations$switchMap$2$onChanged$1<Y>) obj2);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke */
                public final void invoke2(Y y) {
                    mediatorLiveData4.setValue(y);
                }
            }));
        }
    }
}