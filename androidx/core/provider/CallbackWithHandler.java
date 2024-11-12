package androidx.core.provider;

import android.graphics.Typeface;
import android.os.Handler;
import androidx.core.provider.FontRequestWorker;
import androidx.core.provider.FontsContractCompat;

/* loaded from: classes.dex */
class CallbackWithHandler {
    private final FontsContractCompat.FontRequestCallback mCallback;
    private final Handler mCallbackHandler;

    CallbackWithHandler(FontsContractCompat.FontRequestCallback fontRequestCallback, Handler handler) {
        this.mCallback = fontRequestCallback;
        this.mCallbackHandler = handler;
    }

    CallbackWithHandler(FontsContractCompat.FontRequestCallback fontRequestCallback) {
        this.mCallback = fontRequestCallback;
        this.mCallbackHandler = CalleeHandler.create();
    }

    /* renamed from: androidx.core.provider.CallbackWithHandler$1 */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ FontsContractCompat.FontRequestCallback val$callback;
        final /* synthetic */ Typeface val$typeface;

        AnonymousClass1(FontsContractCompat.FontRequestCallback fontRequestCallback, Typeface typeface) {
            r2 = fontRequestCallback;
            r3 = typeface;
        }

        @Override // java.lang.Runnable
        public void run() {
            r2.onTypefaceRetrieved(r3);
        }
    }

    private void onTypefaceRetrieved(Typeface typeface) {
        this.mCallbackHandler.post(new Runnable() { // from class: androidx.core.provider.CallbackWithHandler.1
            final /* synthetic */ FontsContractCompat.FontRequestCallback val$callback;
            final /* synthetic */ Typeface val$typeface;

            AnonymousClass1(FontsContractCompat.FontRequestCallback fontRequestCallback, Typeface typeface2) {
                r2 = fontRequestCallback;
                r3 = typeface2;
            }

            @Override // java.lang.Runnable
            public void run() {
                r2.onTypefaceRetrieved(r3);
            }
        });
    }

    /* renamed from: androidx.core.provider.CallbackWithHandler$2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ FontsContractCompat.FontRequestCallback val$callback;
        final /* synthetic */ int val$reason;

        AnonymousClass2(FontsContractCompat.FontRequestCallback fontRequestCallback, int i) {
            r2 = fontRequestCallback;
            r3 = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            r2.onTypefaceRequestFailed(r3);
        }
    }

    private void onTypefaceRequestFailed(int i) {
        this.mCallbackHandler.post(new Runnable() { // from class: androidx.core.provider.CallbackWithHandler.2
            final /* synthetic */ FontsContractCompat.FontRequestCallback val$callback;
            final /* synthetic */ int val$reason;

            AnonymousClass2(FontsContractCompat.FontRequestCallback fontRequestCallback, int i2) {
                r2 = fontRequestCallback;
                r3 = i2;
            }

            @Override // java.lang.Runnable
            public void run() {
                r2.onTypefaceRequestFailed(r3);
            }
        });
    }

    void onTypefaceResult(FontRequestWorker.TypefaceResult typefaceResult) {
        if (typefaceResult.isSuccess()) {
            onTypefaceRetrieved(typefaceResult.mTypeface);
        } else {
            onTypefaceRequestFailed(typefaceResult.mResult);
        }
    }
}