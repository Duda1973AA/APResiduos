package com.getcapacitor;

import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import androidx.core.app.NotificationCompat;
import androidx.webkit.JavaScriptReplyProxy;
import androidx.webkit.WebMessageCompat;
import androidx.webkit.WebViewCompat;
import androidx.webkit.WebViewFeature;

/* loaded from: classes.dex */
public class MessageHandler {
    private Bridge bridge;
    private org.apache.cordova.PluginManager cordovaPluginManager;
    private JavaScriptReplyProxy javaScriptReplyProxy;
    private WebView webView;

    public MessageHandler(Bridge bridge, WebView webView, org.apache.cordova.PluginManager pluginManager) {
        this.bridge = bridge;
        this.webView = webView;
        this.cordovaPluginManager = pluginManager;
        if (WebViewFeature.isFeatureSupported("WEB_MESSAGE_LISTENER") && !bridge.getConfig().isUsingLegacyBridge()) {
            try {
                WebViewCompat.addWebMessageListener(webView, "androidBridge", bridge.getAllowedOriginRules(), new WebViewCompat.WebMessageListener() { // from class: com.getcapacitor.MessageHandler$$ExternalSyntheticLambda0
                    public /* synthetic */ MessageHandler$$ExternalSyntheticLambda0() {
                    }

                    @Override // androidx.webkit.WebViewCompat.WebMessageListener
                    public final void onPostMessage(WebView webView2, WebMessageCompat webMessageCompat, Uri uri, boolean z, JavaScriptReplyProxy javaScriptReplyProxy) {
                        MessageHandler.this.lambda$new$0(webView2, webMessageCompat, uri, z, javaScriptReplyProxy);
                    }
                });
                return;
            } catch (Exception unused) {
                webView.addJavascriptInterface(this, "androidBridge");
                return;
            }
        }
        webView.addJavascriptInterface(this, "androidBridge");
    }

    public /* synthetic */ void lambda$new$0(WebView webView, WebMessageCompat webMessageCompat, Uri uri, boolean z, JavaScriptReplyProxy javaScriptReplyProxy) {
        if (z) {
            postMessage(webMessageCompat.getData());
            this.javaScriptReplyProxy = javaScriptReplyProxy;
        } else {
            Logger.warn("Plugin execution is allowed in Main Frame only");
        }
    }

    @JavascriptInterface
    public void postMessage(String str) {
        try {
            JSObject jSObject = new JSObject(str);
            String string = jSObject.getString("type");
            boolean z = true;
            boolean z2 = string != null;
            boolean z3 = z2 && string.equals("cordova");
            if (!z2 || !string.equals("js.error")) {
                z = false;
            }
            String string2 = jSObject.getString("callbackId");
            if (z3) {
                String string3 = jSObject.getString(NotificationCompat.CATEGORY_SERVICE);
                String string4 = jSObject.getString("action");
                String string5 = jSObject.getString("actionArgs");
                Logger.verbose(Logger.tags("Plugin"), "To native (Cordova plugin): callbackId: " + string2 + ", service: " + string3 + ", action: " + string4 + ", actionArgs: " + string5);
                callCordovaPluginMethod(string2, string3, string4, string5);
                return;
            }
            if (z) {
                Logger.error("JavaScript Error: " + str);
                return;
            }
            String string6 = jSObject.getString("pluginId");
            String string7 = jSObject.getString("methodName");
            JSObject jSObject2 = jSObject.getJSObject("options", new JSObject());
            Logger.verbose(Logger.tags("Plugin"), "To native (Capacitor plugin): callbackId: " + string2 + ", pluginId: " + string6 + ", methodName: " + string7);
            callPluginMethod(string2, string6, string7, jSObject2);
        } catch (Exception e) {
            Logger.error("Post message error:", e);
        }
    }

    public void sendResponseMessage(PluginCall pluginCall, PluginResult pluginResult, PluginResult pluginResult2) {
        JavaScriptReplyProxy javaScriptReplyProxy;
        try {
            PluginResult pluginResult3 = new PluginResult();
            pluginResult3.put("save", pluginCall.isKeptAlive());
            pluginResult3.put("callbackId", pluginCall.getCallbackId());
            pluginResult3.put("pluginId", pluginCall.getPluginId());
            pluginResult3.put("methodName", pluginCall.getMethodName());
            if (pluginResult2 != null) {
                pluginResult3.put("success", false);
                pluginResult3.put("error", pluginResult2);
                Logger.debug("Sending plugin error: " + pluginResult3.toString());
            } else {
                pluginResult3.put("success", true);
                if (pluginResult != null) {
                    pluginResult3.put("data", pluginResult);
                }
            }
            if (!pluginCall.getCallbackId().equals(PluginCall.CALLBACK_ID_DANGLING)) {
                if (this.bridge.getConfig().isUsingLegacyBridge()) {
                    legacySendResponseMessage(pluginResult3);
                } else if (WebViewFeature.isFeatureSupported("WEB_MESSAGE_LISTENER") && (javaScriptReplyProxy = this.javaScriptReplyProxy) != null) {
                    javaScriptReplyProxy.postMessage(pluginResult3.toString());
                } else {
                    legacySendResponseMessage(pluginResult3);
                }
            } else {
                this.bridge.getApp().fireRestoredResult(pluginResult3);
            }
        } catch (Exception e) {
            Logger.error("sendResponseMessage: error: " + e);
        }
        if (pluginCall.isKeptAlive()) {
            return;
        }
        pluginCall.release(this.bridge);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.getcapacitor.MessageHandler$$ExternalSyntheticLambda1.<init>(android.webkit.WebView, java.lang.String):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:290)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:193)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:64)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    private void legacySendResponseMessage(com.getcapacitor.PluginResult r3) {
        /*
            r2 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "window.Capacitor.fromNative("
            r0.<init>(r1)
            java.lang.String r3 = r3.toString()
            r0.append(r3)
            java.lang.String r3 = ")"
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            android.webkit.WebView r0 = r2.webView
            com.getcapacitor.MessageHandler$$ExternalSyntheticLambda1 r1 = new com.getcapacitor.MessageHandler$$ExternalSyntheticLambda1
            r1.<init>()
            r0.post(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.getcapacitor.MessageHandler.legacySendResponseMessage(com.getcapacitor.PluginResult):void");
    }

    private void callPluginMethod(String str, String str2, String str3, JSObject jSObject) {
        this.bridge.callPluginMethod(str2, str3, new PluginCall(this, str2, str, str3, jSObject));
    }

    private void callCordovaPluginMethod(String str, String str2, String str3, String str4) {
        this.bridge.execute(new Runnable() { // from class: com.getcapacitor.MessageHandler$$ExternalSyntheticLambda2
            public final /* synthetic */ String f$1;
            public final /* synthetic */ String f$2;
            public final /* synthetic */ String f$3;
            public final /* synthetic */ String f$4;

            public /* synthetic */ MessageHandler$$ExternalSyntheticLambda2(String str22, String str32, String str5, String str42) {
                r2 = str22;
                r3 = str32;
                r4 = str5;
                r5 = str42;
            }

            @Override // java.lang.Runnable
            public final void run() {
                MessageHandler.this.lambda$callCordovaPluginMethod$2(r2, r3, r4, r5);
            }
        });
    }

    public /* synthetic */ void lambda$callCordovaPluginMethod$2(String str, String str2, String str3, String str4) {
        this.cordovaPluginManager.exec(str, str2, str3, str4);
    }
}