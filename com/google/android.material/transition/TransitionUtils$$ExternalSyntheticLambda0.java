package com.google.android.material.transition;

import android.graphics.RectF;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class TransitionUtils$$ExternalSyntheticLambda0 implements ShapeAppearanceModel.CornerSizeUnaryOperator {
    public final /* synthetic */ RectF f$0;

    public /* synthetic */ TransitionUtils$$ExternalSyntheticLambda0(RectF rectF) {
        rectF = rectF;
    }

    @Override // com.google.android.material.shape.ShapeAppearanceModel.CornerSizeUnaryOperator
    public final CornerSize apply(CornerSize cornerSize) {
        CornerSize createFromCornerSize;
        createFromCornerSize = RelativeCornerSize.createFromCornerSize(rectF, cornerSize);
        return createFromCornerSize;
    }
}