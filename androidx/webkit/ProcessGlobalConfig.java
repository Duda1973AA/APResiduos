package androidx.webkit;

import android.content.Context;
import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import androidx.webkit.internal.ApiHelperForP;
import androidx.webkit.internal.WebViewFeatureInternal;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.chromium.support_lib_boundary.ProcessGlobalConfigConstants;

/* loaded from: classes.dex */
public class ProcessGlobalConfig {
    String mDataDirectorySuffix;
    private static final AtomicReference<HashMap<String, Object>> sProcessGlobalConfig = new AtomicReference<>();
    private static final Object sLock = new Object();
    private static boolean sApplyCalled = false;

    public ProcessGlobalConfig setDataDirectorySuffix(Context context, String str) {
        if (!WebViewFeatureInternal.STARTUP_FEATURE_SET_DATA_DIRECTORY_SUFFIX.isSupported(context)) {
            throw WebViewFeatureInternal.getUnsupportedOperationException();
        }
        if (str.equals("")) {
            throw new IllegalArgumentException("Suffix cannot be an empty string");
        }
        if (str.indexOf(File.separatorChar) >= 0) {
            throw new IllegalArgumentException("Suffix " + str + " contains a path separator");
        }
        this.mDataDirectorySuffix = str;
        return this;
    }

    public static void apply(ProcessGlobalConfig processGlobalConfig) {
        synchronized (sLock) {
            if (sApplyCalled) {
                throw new IllegalStateException("ProcessGlobalConfig#apply was called more than once, which is an illegal operation. The configuration settings provided by ProcessGlobalConfig take effect only once, when WebView is first loaded into the current process. Every process should only ever create a single instance of ProcessGlobalConfig and apply it once, before any calls to android.webkit APIs, such as during early app startup.");
            }
            sApplyCalled = true;
        }
        HashMap hashMap = new HashMap();
        if (webViewCurrentlyLoaded()) {
            throw new IllegalStateException("WebView has already been loaded in the current process, so any attempt to apply the settings in ProcessGlobalConfig will have no effect. ProcessGlobalConfig#apply needs to be called before any calls to android.webkit APIs, such as during early app startup.");
        }
        if (WebViewFeatureInternal.STARTUP_FEATURE_SET_DATA_DIRECTORY_SUFFIX.isSupportedByFramework()) {
            ApiHelperForP.setDataDirectorySuffix(processGlobalConfig.mDataDirectorySuffix);
        } else {
            hashMap.put(ProcessGlobalConfigConstants.DATA_DIRECTORY_SUFFIX, processGlobalConfig.mDataDirectorySuffix);
        }
        if (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(sProcessGlobalConfig, null, hashMap)) {
            throw new RuntimeException("Attempting to set ProcessGlobalConfig#sProcessGlobalConfig when it was already set");
        }
    }

    private static boolean webViewCurrentlyLoaded() {
        try {
            Field declaredField = Class.forName("android.webkit.WebViewFactory").getDeclaredField("sProviderInstance");
            declaredField.setAccessible(true);
            return declaredField.get(null) != null;
        } catch (Exception unused) {
            return false;
        }
    }
}