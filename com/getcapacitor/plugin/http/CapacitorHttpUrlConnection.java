package com.getcapacitor.plugin.http;

import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;

/* loaded from: classes.dex */
public class CapacitorHttpUrlConnection implements ICapacitorHttpUrlConnection {
    private final HttpURLConnection connection;

    public CapacitorHttpUrlConnection(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
        setDefaultRequestProperties();
    }

    public HttpURLConnection getHttpConnection() {
        return this.connection;
    }

    public void setAllowUserInteraction(boolean z) {
        this.connection.setAllowUserInteraction(z);
    }

    public void setRequestMethod(String str) throws ProtocolException {
        this.connection.setRequestMethod(str);
    }

    public void setConnectTimeout(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.connection.setConnectTimeout(i);
    }

    public void setReadTimeout(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        this.connection.setReadTimeout(i);
    }

    public void setDisableRedirects(boolean z) {
        this.connection.setInstanceFollowRedirects(!z);
    }

    public void setRequestHeaders(JSObject jSObject) {
        Iterator<String> keys = jSObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            this.connection.setRequestProperty(next, jSObject.getString(next));
        }
    }

    public void setDoOutput(boolean z) {
        this.connection.setDoOutput(z);
    }

    public void setRequestBody(PluginCall pluginCall, JSValue jSValue) throws JSONException, IOException {
        String str;
        String requestProperty = this.connection.getRequestProperty("Content-Type");
        if (requestProperty == null || requestProperty.isEmpty()) {
            return;
        }
        if (requestProperty.contains("application/json")) {
            JSArray jSArray = null;
            if (jSValue != null) {
                str = jSValue.toString();
            } else {
                jSArray = pluginCall.getArray("data", null);
                str = "";
            }
            if (jSArray != null) {
                str = jSArray.toString();
            } else if (jSValue == null) {
                str = pluginCall.getString("data");
            }
            writeRequestBody(str.toString());
            return;
        }
        if (requestProperty.contains("application/x-www-form-urlencoded")) {
            StringBuilder sb = new StringBuilder();
            JSObject jSObject = jSValue.toJSObject();
            Iterator<String> keys = jSObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                Object obj = jSObject.get(next);
                sb.append(next);
                sb.append("=");
                sb.append(URLEncoder.encode(obj.toString(), "UTF-8"));
                if (keys.hasNext()) {
                    sb.append("&");
                }
            }
            writeRequestBody(sb.toString());
            return;
        }
        if (requestProperty.contains("multipart/form-data")) {
            FormUploader formUploader = new FormUploader(this.connection);
            JSObject jSObject2 = jSValue.toJSObject();
            Iterator<String> keys2 = jSObject2.keys();
            while (keys2.hasNext()) {
                String next2 = keys2.next();
                formUploader.addFormField(next2, jSObject2.get(next2).toString());
            }
            formUploader.finish();
            return;
        }
        writeRequestBody(jSValue.toString());
    }

    private void writeRequestBody(String str) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(this.connection.getOutputStream());
        try {
            dataOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (Throwable th) {
            try {
                dataOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void connect() throws IOException {
        this.connection.connect();
    }

    public int getResponseCode() throws IOException {
        return this.connection.getResponseCode();
    }

    public URL getURL() {
        return this.connection.getURL();
    }

    @Override // com.getcapacitor.plugin.http.ICapacitorHttpUrlConnection
    public InputStream getErrorStream() {
        return this.connection.getErrorStream();
    }

    @Override // com.getcapacitor.plugin.http.ICapacitorHttpUrlConnection
    public String getHeaderField(String str) {
        return this.connection.getHeaderField(str);
    }

    @Override // com.getcapacitor.plugin.http.ICapacitorHttpUrlConnection
    public InputStream getInputStream() throws IOException {
        return this.connection.getInputStream();
    }

    public Map<String, List<String>> getHeaderFields() {
        return this.connection.getHeaderFields();
    }

    private void setDefaultRequestProperties() {
        this.connection.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
        String buildDefaultAcceptLanguageProperty = buildDefaultAcceptLanguageProperty();
        if (TextUtils.isEmpty(buildDefaultAcceptLanguageProperty)) {
            return;
        }
        this.connection.setRequestProperty("Accept-Language", buildDefaultAcceptLanguageProperty);
    }

    private String buildDefaultAcceptLanguageProperty() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= 24) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (TextUtils.isEmpty(language)) {
            return "";
        }
        if (!TextUtils.isEmpty(country)) {
            return String.format("%s-%s,%s;q=0.5", language, country, language);
        }
        return String.format("%s;q=0.5", language);
    }
}