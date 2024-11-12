package com.getcapacitor.plugin.http;

import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.getcapacitor.CapConfig;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.plugin.http.HttpRequestHandler;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URI;

@CapacitorPlugin(name = "Http", permissions = {@Permission(alias = "HttpWrite", strings = {"android.permission.WRITE_EXTERNAL_STORAGE"}), @Permission(alias = "HttpRead", strings = {"android.permission.WRITE_EXTERNAL_STORAGE"})})
/* loaded from: classes.dex */
public class Http extends Plugin {
    public static final int HTTP_REQUEST_DOWNLOAD_WRITE_PERMISSIONS = 9022;
    public static final int HTTP_REQUEST_UPLOAD_READ_PERMISSIONS = 9023;
    CapConfig capConfig;
    CapacitorCookieManager cookieManager;

    private String getServerUrl(PluginCall pluginCall) {
        String string = pluginCall.getString("url", "");
        if (getUri(string) != null) {
            return string;
        }
        pluginCall.reject("Invalid URL. Check that \"server\" is passed in correctly");
        return "";
    }

    private URI getUri(String str) {
        try {
            return new URI(str);
        } catch (Exception unused) {
            return null;
        }
    }

    private boolean isStoragePermissionGranted(PluginCall pluginCall, String str) {
        if (hasPermission(str)) {
            Log.v(getLogTag(), "Permission '" + str + "' is granted");
            return true;
        }
        Log.v(getLogTag(), "Permission '" + str + "' denied. Asking user for it.");
        requestPermissions(pluginCall);
        return false;
    }

    private void http(final PluginCall pluginCall, final String str) {
        new Thread(new Runnable() { // from class: com.getcapacitor.plugin.http.Http.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    pluginCall.resolve(HttpRequestHandler.request(pluginCall, str));
                } catch (Exception e) {
                    System.out.println(e.toString());
                    pluginCall.reject(e.getClass().getSimpleName(), e);
                }
            }
        }).start();
    }

    @Override // com.getcapacitor.Plugin
    public void load() {
        CapacitorCookieManager capacitorCookieManager = new CapacitorCookieManager(null, CookiePolicy.ACCEPT_ALL);
        this.cookieManager = capacitorCookieManager;
        CookieHandler.setDefault(capacitorCookieManager);
        this.capConfig = getBridge().getConfig();
    }

    @PluginMethod
    public void request(PluginCall pluginCall) {
        http(pluginCall, null);
    }

    @PluginMethod
    public void get(PluginCall pluginCall) {
        http(pluginCall, "GET");
    }

    @PluginMethod
    public void post(PluginCall pluginCall) {
        http(pluginCall, "POST");
    }

    @PluginMethod
    public void put(PluginCall pluginCall) {
        http(pluginCall, "PUT");
    }

    @PluginMethod
    public void patch(PluginCall pluginCall) {
        http(pluginCall, "PATCH");
    }

    @PluginMethod
    public void del(PluginCall pluginCall) {
        http(pluginCall, "DELETE");
    }

    @PluginMethod
    public void downloadFile(final PluginCall pluginCall) {
        try {
            this.bridge.saveCall(pluginCall);
            if (!FilesystemUtils.isPublicDirectory(pluginCall.getString("fileDirectory", FilesystemUtils.DIRECTORY_DOCUMENTS)) || isStoragePermissionGranted(pluginCall, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                pluginCall.release(this.bridge);
                HttpRequestHandler.ProgressEmitter progressEmitter = new HttpRequestHandler.ProgressEmitter() { // from class: com.getcapacitor.plugin.http.Http.2
                    @Override // com.getcapacitor.plugin.http.HttpRequestHandler.ProgressEmitter
                    public void emit(Integer num, Integer num2) {
                    }
                };
                if (pluginCall.getBoolean(NotificationCompat.CATEGORY_PROGRESS, false).booleanValue()) {
                    progressEmitter = new HttpRequestHandler.ProgressEmitter() { // from class: com.getcapacitor.plugin.http.Http.3
                        @Override // com.getcapacitor.plugin.http.HttpRequestHandler.ProgressEmitter
                        public void emit(Integer num, Integer num2) {
                            JSObject jSObject = new JSObject();
                            jSObject.put("type", "DOWNLOAD");
                            jSObject.put("url", pluginCall.getString("url"));
                            jSObject.put("bytes", (Object) num);
                            jSObject.put("contentLength", (Object) num2);
                            Http.this.notifyListeners(NotificationCompat.CATEGORY_PROGRESS, jSObject);
                        }
                    };
                }
                pluginCall.resolve(HttpRequestHandler.downloadFile(pluginCall, getContext(), progressEmitter));
            }
        } catch (MalformedURLException e) {
            pluginCall.reject("Invalid URL", e);
        } catch (IOException e2) {
            pluginCall.reject("IO Error", e2);
        } catch (Exception e3) {
            pluginCall.reject("Error", e3);
        }
    }

    @PluginMethod
    public void uploadFile(PluginCall pluginCall) {
        try {
            String string = pluginCall.getString("fileDirectory", FilesystemUtils.DIRECTORY_DOCUMENTS);
            this.bridge.saveCall(pluginCall);
            if (!FilesystemUtils.isPublicDirectory(string) || isStoragePermissionGranted(pluginCall, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                pluginCall.release(this.bridge);
                pluginCall.resolve(HttpRequestHandler.uploadFile(pluginCall, getContext()));
            }
        } catch (Exception e) {
            pluginCall.reject("Error", e);
        }
    }

    @PluginMethod
    public void setCookie(PluginCall pluginCall) {
        String string = pluginCall.getString("key");
        String string2 = pluginCall.getString("value");
        String serverUrl = getServerUrl(pluginCall);
        if (serverUrl.isEmpty()) {
            return;
        }
        this.cookieManager.setCookie(serverUrl, string, string2);
        pluginCall.resolve();
    }

    @PluginMethod
    public void getCookiesMap(PluginCall pluginCall) {
        String serverUrl = getServerUrl(pluginCall);
        if (serverUrl.isEmpty()) {
            return;
        }
        HttpCookie[] cookies = this.cookieManager.getCookies(serverUrl);
        JSObject jSObject = new JSObject();
        for (HttpCookie httpCookie : cookies) {
            jSObject.put(httpCookie.getName(), httpCookie.getValue());
        }
        pluginCall.resolve(jSObject);
    }

    @PluginMethod
    public void getCookies(PluginCall pluginCall) {
        String serverUrl = getServerUrl(pluginCall);
        if (serverUrl.isEmpty()) {
            return;
        }
        HttpCookie[] cookies = this.cookieManager.getCookies(serverUrl);
        JSArray jSArray = new JSArray();
        for (HttpCookie httpCookie : cookies) {
            JSObject jSObject = new JSObject();
            jSObject.put("key", httpCookie.getName());
            jSObject.put("value", httpCookie.getValue());
            jSArray.put(jSObject);
        }
        JSObject jSObject2 = new JSObject();
        jSObject2.put("cookies", (Object) jSArray);
        pluginCall.resolve(jSObject2);
    }

    @PluginMethod
    public void getCookie(PluginCall pluginCall) {
        String string = pluginCall.getString("key");
        String serverUrl = getServerUrl(pluginCall);
        if (serverUrl.isEmpty()) {
            return;
        }
        HttpCookie cookie = this.cookieManager.getCookie(serverUrl, string);
        JSObject jSObject = new JSObject();
        jSObject.put("key", string);
        if (cookie != null) {
            jSObject.put("value", cookie.getValue());
        } else {
            jSObject.put("value", "");
        }
        pluginCall.resolve(jSObject);
    }

    @PluginMethod
    public void deleteCookie(PluginCall pluginCall) {
        String string = pluginCall.getString("key");
        String serverUrl = getServerUrl(pluginCall);
        if (serverUrl.isEmpty()) {
            return;
        }
        this.cookieManager.setCookie(serverUrl, string + "=; Expires=Wed, 31 Dec 2000 23:59:59 GMT");
        pluginCall.resolve();
    }

    @PluginMethod
    public void clearCookies(PluginCall pluginCall) {
        String serverUrl = getServerUrl(pluginCall);
        if (serverUrl.isEmpty()) {
            return;
        }
        for (HttpCookie httpCookie : this.cookieManager.getCookies(serverUrl)) {
            this.cookieManager.setCookie(serverUrl, httpCookie.getName() + "=; Expires=Wed, 31 Dec 2000 23:59:59 GMT");
        }
        pluginCall.resolve();
    }

    @PluginMethod
    public void clearAllCookies(PluginCall pluginCall) {
        this.cookieManager.removeAllCookies();
        pluginCall.resolve();
    }
}