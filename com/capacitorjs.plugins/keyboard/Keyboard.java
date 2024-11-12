package com.capacitorjs.plugins.keyboard;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes.dex */
public class Keyboard {
    static final String EVENT_KB_DID_HIDE = "keyboardDidHide";
    static final String EVENT_KB_DID_SHOW = "keyboardDidShow";
    static final String EVENT_KB_WILL_HIDE = "keyboardWillHide";
    static final String EVENT_KB_WILL_SHOW = "keyboardWillShow";
    private AppCompatActivity activity;
    private FrameLayout.LayoutParams frameLayoutParams;
    private KeyboardEventListener keyboardEventListener;
    private ViewTreeObserver.OnGlobalLayoutListener list;
    private View mChildOfContent;
    private View rootView;
    private int usableHeightPrevious;

    interface KeyboardEventListener {
        void onKeyboardEvent(String str, int i);
    }

    public KeyboardEventListener getKeyboardEventListener() {
        return this.keyboardEventListener;
    }

    public void setKeyboardEventListener(KeyboardEventListener keyboardEventListener) {
        this.keyboardEventListener = keyboardEventListener;
    }

    public Keyboard(final AppCompatActivity appCompatActivity, final boolean z) {
        this.activity = appCompatActivity;
        final float f = appCompatActivity.getResources().getDisplayMetrics().density;
        FrameLayout frameLayout = (FrameLayout) appCompatActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        this.rootView = frameLayout.getRootView();
        this.list = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.capacitorjs.plugins.keyboard.Keyboard.1
            int previousHeightDiff = 0;

            /* JADX WARN: Removed duplicated region for block: B:25:0x00a0  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x00ab  */
            /* JADX WARN: Removed duplicated region for block: B:29:0x00c2  */
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onGlobalLayout() {
                /*
                    r4 = this;
                    android.graphics.Rect r0 = new android.graphics.Rect
                    r0.<init>()
                    com.capacitorjs.plugins.keyboard.Keyboard r1 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    android.view.View r1 = com.capacitorjs.plugins.keyboard.Keyboard.m66$$Nest$fgetrootView(r1)
                    r1.getWindowVisibleDisplayFrame(r0)
                    com.capacitorjs.plugins.keyboard.Keyboard r1 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    android.view.View r1 = com.capacitorjs.plugins.keyboard.Keyboard.m66$$Nest$fgetrootView(r1)
                    android.view.View r1 = r1.getRootView()
                    int r1 = r1.getHeight()
                    int r0 = r0.bottom
                    int r2 = android.os.Build.VERSION.SDK_INT
                    r3 = 30
                    if (r2 < r3) goto L3a
                    com.capacitorjs.plugins.keyboard.Keyboard r2 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    android.view.View r2 = com.capacitorjs.plugins.keyboard.Keyboard.m66$$Nest$fgetrootView(r2)
                    android.view.WindowInsets r2 = r2.getRootWindowInsets()
                    int r3 = android.view.WindowInsets.Type.systemBars()
                    android.graphics.Insets r2 = r2.getInsetsIgnoringVisibility(r3)
                    int r2 = r2.bottom
                L38:
                    int r0 = r0 + r2
                    goto L59
                L3a:
                    int r2 = android.os.Build.VERSION.SDK_INT
                    r3 = 23
                    if (r2 < r3) goto L51
                    com.capacitorjs.plugins.keyboard.Keyboard r2 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    android.view.View r2 = com.capacitorjs.plugins.keyboard.Keyboard.m66$$Nest$fgetrootView(r2)
                    android.view.WindowInsets r2 = r2.getRootWindowInsets()
                    com.capacitorjs.plugins.keyboard.Keyboard r3 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    int r2 = com.capacitorjs.plugins.keyboard.Keyboard.m70$$Nest$mgetLegacyStableInsetBottom(r3, r2)
                    goto L38
                L51:
                    com.capacitorjs.plugins.keyboard.Keyboard r1 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    android.graphics.Point r1 = com.capacitorjs.plugins.keyboard.Keyboard.m69$$Nest$mgetLegacySizePoint(r1)
                    int r1 = r1.y
                L59:
                    int r1 = r1 - r0
                    float r0 = (float) r1
                    float r1 = r2
                    float r0 = r0 / r1
                    int r0 = (int) r0
                    java.lang.String r1 = "Native Keyboard Event Listener not found"
                    r2 = 100
                    if (r0 <= r2) goto L94
                    int r3 = r4.previousHeightDiff
                    if (r0 == r3) goto L94
                    boolean r2 = r3
                    if (r2 == 0) goto L71
                    r2 = 1
                    r4.possiblyResizeChildOfContent(r2)
                L71:
                    com.capacitorjs.plugins.keyboard.Keyboard r2 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    com.capacitorjs.plugins.keyboard.Keyboard$KeyboardEventListener r2 = com.capacitorjs.plugins.keyboard.Keyboard.m64$$Nest$fgetkeyboardEventListener(r2)
                    if (r2 == 0) goto L90
                    com.capacitorjs.plugins.keyboard.Keyboard r1 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    com.capacitorjs.plugins.keyboard.Keyboard$KeyboardEventListener r1 = com.capacitorjs.plugins.keyboard.Keyboard.m64$$Nest$fgetkeyboardEventListener(r1)
                    java.lang.String r2 = "keyboardWillShow"
                    r1.onKeyboardEvent(r2, r0)
                    com.capacitorjs.plugins.keyboard.Keyboard r1 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    com.capacitorjs.plugins.keyboard.Keyboard$KeyboardEventListener r1 = com.capacitorjs.plugins.keyboard.Keyboard.m64$$Nest$fgetkeyboardEventListener(r1)
                    java.lang.String r2 = "keyboardDidShow"
                    r1.onKeyboardEvent(r2, r0)
                    goto Lc5
                L90:
                    com.getcapacitor.Logger.warn(r1)
                    goto Lc5
                L94:
                    int r3 = r4.previousHeightDiff
                    if (r0 == r3) goto Lc5
                    int r3 = r3 - r0
                    if (r3 <= r2) goto Lc5
                    boolean r2 = r3
                    r3 = 0
                    if (r2 == 0) goto La3
                    r4.possiblyResizeChildOfContent(r3)
                La3:
                    com.capacitorjs.plugins.keyboard.Keyboard r2 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    com.capacitorjs.plugins.keyboard.Keyboard$KeyboardEventListener r2 = com.capacitorjs.plugins.keyboard.Keyboard.m64$$Nest$fgetkeyboardEventListener(r2)
                    if (r2 == 0) goto Lc2
                    com.capacitorjs.plugins.keyboard.Keyboard r1 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    com.capacitorjs.plugins.keyboard.Keyboard$KeyboardEventListener r1 = com.capacitorjs.plugins.keyboard.Keyboard.m64$$Nest$fgetkeyboardEventListener(r1)
                    java.lang.String r2 = "keyboardWillHide"
                    r1.onKeyboardEvent(r2, r3)
                    com.capacitorjs.plugins.keyboard.Keyboard r1 = com.capacitorjs.plugins.keyboard.Keyboard.this
                    com.capacitorjs.plugins.keyboard.Keyboard$KeyboardEventListener r1 = com.capacitorjs.plugins.keyboard.Keyboard.m64$$Nest$fgetkeyboardEventListener(r1)
                    java.lang.String r2 = "keyboardDidHide"
                    r1.onKeyboardEvent(r2, r3)
                    goto Lc5
                Lc2:
                    com.getcapacitor.Logger.warn(r1)
                Lc5:
                    r4.previousHeightDiff = r0
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.capacitorjs.plugins.keyboard.Keyboard.AnonymousClass1.onGlobalLayout():void");
            }

            private void possiblyResizeChildOfContent(boolean z2) {
                int computeUsableHeight = z2 ? computeUsableHeight() : -1;
                if (Keyboard.this.usableHeightPrevious != computeUsableHeight) {
                    Keyboard.this.frameLayoutParams.height = computeUsableHeight;
                    Keyboard.this.mChildOfContent.requestLayout();
                    Keyboard.this.usableHeightPrevious = computeUsableHeight;
                }
            }

            private int computeUsableHeight() {
                Rect rect = new Rect();
                Keyboard.this.mChildOfContent.getWindowVisibleDisplayFrame(rect);
                return isOverlays() ? rect.bottom : rect.height();
            }

            private boolean isOverlays() {
                return (appCompatActivity.getWindow().getDecorView().getSystemUiVisibility() & 1024) == 1024;
            }
        };
        this.mChildOfContent = frameLayout.getChildAt(0);
        this.rootView.getViewTreeObserver().addOnGlobalLayoutListener(this.list);
        this.frameLayoutParams = (FrameLayout.LayoutParams) this.mChildOfContent.getLayoutParams();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLegacyStableInsetBottom(WindowInsets windowInsets) {
        return windowInsets.getStableInsetBottom();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Point getLegacySizePoint() {
        Display defaultDisplay = this.activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point;
    }

    public void show() {
        ((InputMethodManager) this.activity.getSystemService("input_method")).showSoftInput(this.activity.getCurrentFocus(), 0);
    }

    public boolean hide() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.activity.getSystemService("input_method");
        View currentFocus = this.activity.getCurrentFocus();
        if (currentFocus == null) {
            return false;
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
        return true;
    }
}