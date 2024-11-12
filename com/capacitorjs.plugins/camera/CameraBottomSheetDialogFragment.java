package com.capacitorjs.plugins.camera;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.List;

/* loaded from: classes.dex */
public class CameraBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private BottomSheetOnCanceledListener canceledListener;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.capacitorjs.plugins.camera.CameraBottomSheetDialogFragment.1
        @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public void onSlide(View view, float f) {
        }

        @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public void onStateChanged(View view, int i) {
            if (i == 5) {
                CameraBottomSheetDialogFragment.this.dismiss();
            }
        }
    };
    private List<String> options;
    private BottomSheetOnSelectedListener selectedListener;
    private String title;

    interface BottomSheetOnCanceledListener {
        void onCanceled();
    }

    interface BottomSheetOnSelectedListener {
        void onSelected(int i);
    }

    void setTitle(String str) {
        this.title = str;
    }

    void setOptions(List<String> list, BottomSheetOnSelectedListener bottomSheetOnSelectedListener, BottomSheetOnCanceledListener bottomSheetOnCanceledListener) {
        this.options = list;
        this.selectedListener = bottomSheetOnSelectedListener;
        this.canceledListener = bottomSheetOnCanceledListener;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        super.onCancel(dialogInterface);
        BottomSheetOnCanceledListener bottomSheetOnCanceledListener = this.canceledListener;
        if (bottomSheetOnCanceledListener != null) {
            bottomSheetOnCanceledListener.onCanceled();
        }
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        List<String> list = this.options;
        if (list == null || list.size() == 0) {
            return;
        }
        dialog.getWindow();
        float f = getResources().getDisplayMetrics().density;
        int i2 = (int) ((16.0f * f) + 0.5f);
        int i3 = (int) ((12.0f * f) + 0.5f);
        int i4 = (int) ((f * 8.0f) + 0.5f);
        CoordinatorLayout coordinatorLayout = new CoordinatorLayout(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        linearLayout.setPadding(i2, i2, i2, i2);
        TextView textView = new TextView(getContext());
        textView.setTextColor(Color.parseColor("#757575"));
        textView.setPadding(i4, i4, i4, i4);
        textView.setText(this.title);
        linearLayout.addView(textView);
        for (final int i5 = 0; i5 < this.options.size(); i5++) {
            TextView textView2 = new TextView(getContext());
            textView2.setTextColor(Color.parseColor("#000000"));
            textView2.setPadding(i3, i3, i3, i3);
            textView2.setText(this.options.get(i5));
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.capacitorjs.plugins.camera.CameraBottomSheetDialogFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CameraBottomSheetDialogFragment.this.lambda$setupDialog$0(i5, view);
                }
            });
            linearLayout.addView(textView2);
        }
        coordinatorLayout.addView(linearLayout.getRootView());
        dialog.setContentView(coordinatorLayout.getRootView());
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) ((View) coordinatorLayout.getParent()).getLayoutParams()).getBehavior();
        if (behavior == null || !(behavior instanceof BottomSheetBehavior)) {
            return;
        }
        ((BottomSheetBehavior) behavior).addBottomSheetCallback(this.mBottomSheetBehaviorCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupDialog$0(int i, View view) {
        BottomSheetOnSelectedListener bottomSheetOnSelectedListener = this.selectedListener;
        if (bottomSheetOnSelectedListener != null) {
            bottomSheetOnSelectedListener.onSelected(i);
        }
        dismiss();
    }
}