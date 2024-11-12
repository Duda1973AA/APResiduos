package com.getcapacitor.plugin.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import androidx.core.app.NotificationCompat;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class HttpRequestHandler {

    @FunctionalInterface
    public interface ProgressEmitter {
        void emit(Integer num, Integer num2);
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v4 com.getcapacitor.plugin.http.HttpRequestHandler$ResponseType, still in use, count: 1, list:
  (r0v4 com.getcapacitor.plugin.http.HttpRequestHandler$ResponseType) from 0x0042: SPUT (r0v4 com.getcapacitor.plugin.http.HttpRequestHandler$ResponseType) (LINE:50) com.getcapacitor.plugin.http.HttpRequestHandler.ResponseType.DEFAULT com.getcapacitor.plugin.http.HttpRequestHandler$ResponseType
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
    	at java.base/java.util.ArrayList.forEach(Unknown Source)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
    	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class ResponseType {
        ARRAY_BUFFER("arraybuffer"),
        BLOB("blob"),
        DOCUMENT("document"),
        JSON("json"),
        TEXT("text");

        static final ResponseType DEFAULT = new ResponseType("text");
        private final String name;

        public static ResponseType valueOf(String str) {
            return (ResponseType) Enum.valueOf(ResponseType.class, str);
        }

        public static ResponseType[] values() {
            return (ResponseType[]) $VALUES.clone();
        }

        static {
        }

        private ResponseType(String str) {
            this.name = str;
        }

        static ResponseType parse(String str) {
            for (ResponseType responseType : values()) {
                if (responseType.name.equalsIgnoreCase(str)) {
                    return responseType;
                }
            }
            return DEFAULT;
        }
    }

    private static class HttpURLConnectionBuilder {
        private Integer connectTimeout;
        private CapacitorHttpUrlConnection connection;
        private Boolean disableRedirects;
        private JSObject headers;
        private String method;
        private Integer readTimeout;
        private URL url;

        private HttpURLConnectionBuilder() {
        }

        public HttpURLConnectionBuilder setConnectTimeout(Integer num) {
            this.connectTimeout = num;
            return this;
        }

        public HttpURLConnectionBuilder setReadTimeout(Integer num) {
            this.readTimeout = num;
            return this;
        }

        public HttpURLConnectionBuilder setDisableRedirects(Boolean bool) {
            this.disableRedirects = bool;
            return this;
        }

        public HttpURLConnectionBuilder setHeaders(JSObject jSObject) {
            this.headers = jSObject;
            return this;
        }

        public HttpURLConnectionBuilder setMethod(String str) {
            this.method = str;
            return this;
        }

        public HttpURLConnectionBuilder setUrl(URL url) {
            this.url = url;
            return this;
        }

        public HttpURLConnectionBuilder openConnection() throws IOException {
            CapacitorHttpUrlConnection capacitorHttpUrlConnection = new CapacitorHttpUrlConnection((HttpURLConnection) this.url.openConnection());
            this.connection = capacitorHttpUrlConnection;
            capacitorHttpUrlConnection.setAllowUserInteraction(false);
            this.connection.setRequestMethod(this.method);
            Integer num = this.connectTimeout;
            if (num != null) {
                this.connection.setConnectTimeout(num.intValue());
            }
            Integer num2 = this.readTimeout;
            if (num2 != null) {
                this.connection.setReadTimeout(num2.intValue());
            }
            Boolean bool = this.disableRedirects;
            if (bool != null) {
                this.connection.setDisableRedirects(bool.booleanValue());
            }
            this.connection.setRequestHeaders(this.headers);
            return this;
        }

        public HttpURLConnectionBuilder setUrlParams(JSObject jSObject) throws MalformedURLException, URISyntaxException, JSONException {
            return setUrlParams(jSObject, true);
        }

        public HttpURLConnectionBuilder setUrlParams(JSObject jSObject, boolean z) throws URISyntaxException, MalformedURLException {
            String query = this.url.getQuery();
            if (query == null) {
                query = "";
            }
            Iterator<String> keys = jSObject.keys();
            if (!keys.hasNext()) {
                return this;
            }
            StringBuilder sb = new StringBuilder(query);
            while (keys.hasNext()) {
                String next = keys.next();
                try {
                    StringBuilder sb2 = new StringBuilder();
                    JSONArray jSONArray = jSObject.getJSONArray(next);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        sb2.append(next);
                        sb2.append("=");
                        sb2.append(jSONArray.getString(i));
                        if (i != jSONArray.length() - 1) {
                            sb2.append("&");
                        }
                    }
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append((CharSequence) sb2);
                } catch (JSONException unused) {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append(next);
                    sb.append("=");
                    sb.append(jSObject.getString(next));
                }
            }
            String sb3 = sb.toString();
            URI uri = this.url.toURI();
            if (z) {
                this.url = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), sb3, uri.getFragment()).toURL();
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(uri.getScheme());
                sb4.append("://");
                sb4.append(uri.getAuthority());
                sb4.append(uri.getPath());
                sb4.append(sb3.equals("") ? "" : "?" + sb3);
                sb4.append(uri.getFragment() != null ? uri.getFragment() : "");
                this.url = new URL(sb4.toString());
            }
            return this;
        }

        public CapacitorHttpUrlConnection build() {
            return this.connection;
        }
    }

    private static JSObject buildResponse(CapacitorHttpUrlConnection capacitorHttpUrlConnection) throws IOException, JSONException {
        return buildResponse(capacitorHttpUrlConnection, ResponseType.DEFAULT);
    }

    private static JSObject buildResponse(CapacitorHttpUrlConnection capacitorHttpUrlConnection, ResponseType responseType) throws IOException, JSONException {
        int responseCode = capacitorHttpUrlConnection.getResponseCode();
        JSObject jSObject = new JSObject();
        jSObject.put(NotificationCompat.CATEGORY_STATUS, responseCode);
        jSObject.put("headers", (Object) buildResponseHeaders(capacitorHttpUrlConnection));
        jSObject.put("url", (Object) capacitorHttpUrlConnection.getURL());
        jSObject.put("data", readData(capacitorHttpUrlConnection, responseType));
        if (capacitorHttpUrlConnection.getErrorStream() != null) {
            jSObject.put("error", true);
        }
        return jSObject;
    }

    static Object readData(ICapacitorHttpUrlConnection iCapacitorHttpUrlConnection, ResponseType responseType) throws IOException, JSONException {
        InputStream errorStream = iCapacitorHttpUrlConnection.getErrorStream();
        String headerField = iCapacitorHttpUrlConnection.getHeaderField("Content-Type");
        if (errorStream != null) {
            if (isOneOf(headerField, MimeType.APPLICATION_JSON, MimeType.APPLICATION_VND_API_JSON)) {
                return parseJSON(readStreamAsString(errorStream));
            }
            return readStreamAsString(errorStream);
        }
        if (headerField != null && headerField.contains(MimeType.APPLICATION_JSON.getValue())) {
            return parseJSON(readStreamAsString(iCapacitorHttpUrlConnection.getInputStream()));
        }
        InputStream inputStream = iCapacitorHttpUrlConnection.getInputStream();
        int i = AnonymousClass2.$SwitchMap$com$getcapacitor$plugin$http$HttpRequestHandler$ResponseType[responseType.ordinal()];
        if (i == 1 || i == 2) {
            return readStreamAsBase64(inputStream);
        }
        if (i == 3) {
            return parseJSON(readStreamAsString(inputStream));
        }
        return readStreamAsString(inputStream);
    }

    /* renamed from: com.getcapacitor.plugin.http.HttpRequestHandler$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$getcapacitor$plugin$http$HttpRequestHandler$ResponseType;

        static {
            int[] iArr = new int[ResponseType.values().length];
            $SwitchMap$com$getcapacitor$plugin$http$HttpRequestHandler$ResponseType = iArr;
            try {
                iArr[ResponseType.ARRAY_BUFFER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$getcapacitor$plugin$http$HttpRequestHandler$ResponseType[ResponseType.BLOB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$getcapacitor$plugin$http$HttpRequestHandler$ResponseType[ResponseType.JSON.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$getcapacitor$plugin$http$HttpRequestHandler$ResponseType[ResponseType.DOCUMENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$getcapacitor$plugin$http$HttpRequestHandler$ResponseType[ResponseType.TEXT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static boolean isOneOf(String str, MimeType... mimeTypeArr) {
        if (str != null) {
            for (MimeType mimeType : mimeTypeArr) {
                if (str.contains(mimeType.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static JSObject buildResponseHeaders(CapacitorHttpUrlConnection capacitorHttpUrlConnection) {
        JSObject jSObject = new JSObject();
        for (Map.Entry<String, List<String>> entry : capacitorHttpUrlConnection.getHeaderFields().entrySet()) {
            jSObject.put(entry.getKey(), TextUtils.join(", ", entry.getValue()));
        }
        return jSObject;
    }

    private static Object parseJSON(String str) throws JSONException {
        new JSONObject();
        try {
            if ("null".equals(str.trim())) {
                return JSONObject.NULL;
            }
            if ("true".equals(str.trim())) {
                return new JSONObject().put("flag", "true");
            }
            if ("false".equals(str.trim())) {
                return new JSONObject().put("flag", "false");
            }
            try {
                return new JSObject(str);
            } catch (JSONException unused) {
                return new JSArray(str);
            }
        } catch (JSONException unused2) {
            return new JSArray(str);
        }
    }

    private static String readStreamAsBase64(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String encodeToString = Base64.encodeToString(byteArray, 0, byteArray.length, 0);
                    byteArrayOutputStream.close();
                    return encodeToString;
                }
            }
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static String readStreamAsString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            StringBuilder sb = new StringBuilder();
            String readLine = bufferedReader.readLine();
            while (readLine != null) {
                sb.append(readLine);
                readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(System.getProperty("line.separator"));
                }
            }
            String sb2 = sb.toString();
            bufferedReader.close();
            return sb2;
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static JSObject request(PluginCall pluginCall, String str) throws IOException, URISyntaxException, JSONException {
        String string = pluginCall.getString("url", "");
        JSObject object = pluginCall.getObject("headers");
        JSObject object2 = pluginCall.getObject("params");
        Integer num = pluginCall.getInt("connectTimeout");
        Integer num2 = pluginCall.getInt("readTimeout");
        Boolean bool = pluginCall.getBoolean("disableRedirects");
        Boolean bool2 = pluginCall.getBoolean("shouldEncodeUrlParams", true);
        ResponseType parse = ResponseType.parse(pluginCall.getString("responseType"));
        if (str == null) {
            str = pluginCall.getString("method", "");
        }
        String upperCase = str.toUpperCase();
        boolean z = upperCase.equals("DELETE") || upperCase.equals("PATCH") || upperCase.equals("POST") || upperCase.equals("PUT");
        CapacitorHttpUrlConnection build = new HttpURLConnectionBuilder().setUrl(new URL(string)).setMethod(upperCase).setHeaders(object).setUrlParams(object2, bool2.booleanValue()).setConnectTimeout(num).setReadTimeout(num2).setDisableRedirects(bool).openConnection().build();
        if (z) {
            JSValue jSValue = new JSValue(pluginCall, "data");
            if (jSValue.getValue() != null) {
                build.setDoOutput(true);
                build.setRequestBody(pluginCall, jSValue);
            }
        }
        build.connect();
        return buildResponse(build, parse);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x008a A[LOOP:0: B:5:0x0084->B:7:0x008a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x009a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.getcapacitor.JSObject downloadFile(com.getcapacitor.PluginCall r8, android.content.Context r9, com.getcapacitor.plugin.http.HttpRequestHandler.ProgressEmitter r10) throws java.io.IOException, java.net.URISyntaxException, org.json.JSONException {
        /*
            java.lang.String r0 = "url"
            java.lang.String r0 = r8.getString(r0)
            java.lang.String r1 = "method"
            java.lang.String r2 = "GET"
            java.lang.String r1 = r8.getString(r1, r2)
            java.lang.String r1 = r1.toUpperCase()
            java.lang.String r2 = "filePath"
            java.lang.String r2 = r8.getString(r2)
            java.lang.String r3 = "fileDirectory"
            java.lang.String r4 = "DOCUMENTS"
            java.lang.String r3 = r8.getString(r3, r4)
            java.lang.String r4 = "headers"
            com.getcapacitor.JSObject r4 = r8.getObject(r4)
            java.lang.String r5 = "params"
            com.getcapacitor.JSObject r5 = r8.getObject(r5)
            java.lang.String r6 = "connectTimeout"
            java.lang.Integer r6 = r8.getInt(r6)
            java.lang.String r7 = "readTimeout"
            java.lang.Integer r8 = r8.getInt(r7)
            java.net.URL r7 = new java.net.URL
            r7.<init>(r0)
            java.io.File r9 = com.getcapacitor.plugin.http.FilesystemUtils.getFileObject(r9, r2, r3)
            com.getcapacitor.plugin.http.HttpRequestHandler$HttpURLConnectionBuilder r0 = new com.getcapacitor.plugin.http.HttpRequestHandler$HttpURLConnectionBuilder
            r2 = 0
            r0.<init>()
            com.getcapacitor.plugin.http.HttpRequestHandler$HttpURLConnectionBuilder r0 = r0.setUrl(r7)
            com.getcapacitor.plugin.http.HttpRequestHandler$HttpURLConnectionBuilder r0 = r0.setMethod(r1)
            com.getcapacitor.plugin.http.HttpRequestHandler$HttpURLConnectionBuilder r0 = r0.setHeaders(r4)
            com.getcapacitor.plugin.http.HttpRequestHandler$HttpURLConnectionBuilder r0 = r0.setUrlParams(r5)
            com.getcapacitor.plugin.http.HttpRequestHandler$HttpURLConnectionBuilder r0 = r0.setConnectTimeout(r6)
            com.getcapacitor.plugin.http.HttpRequestHandler$HttpURLConnectionBuilder r8 = r0.setReadTimeout(r8)
            com.getcapacitor.plugin.http.HttpRequestHandler$HttpURLConnectionBuilder r8 = r8.openConnection()
            com.getcapacitor.plugin.http.CapacitorHttpUrlConnection r8 = r8.build()
            java.io.InputStream r0 = r8.getInputStream()
            java.io.FileOutputStream r1 = new java.io.FileOutputStream
            r2 = 0
            r1.<init>(r9, r2)
            java.lang.String r3 = "content-length"
            java.lang.String r8 = r8.getHeaderField(r3)
            if (r8 == 0) goto L7e
            int r8 = java.lang.Integer.parseInt(r8)     // Catch: java.lang.NumberFormatException -> L7e
            goto L7f
        L7e:
            r8 = 0
        L7f:
            r3 = 1024(0x400, float:1.435E-42)
            byte[] r3 = new byte[r3]
            r4 = 0
        L84:
            int r5 = r0.read(r3)
            if (r5 <= 0) goto L9a
            r1.write(r3, r2, r5)
            int r4 = r4 + r5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r8)
            r10.emit(r5, r6)
            goto L84
        L9a:
            r0.close()
            r1.close()
            com.getcapacitor.plugin.http.HttpRequestHandler$1 r8 = new com.getcapacitor.plugin.http.HttpRequestHandler$1
            r8.<init>(r9)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.getcapacitor.plugin.http.HttpRequestHandler.downloadFile(com.getcapacitor.PluginCall, android.content.Context, com.getcapacitor.plugin.http.HttpRequestHandler$ProgressEmitter):com.getcapacitor.JSObject");
    }

    public static JSObject uploadFile(PluginCall pluginCall, Context context) throws IOException, URISyntaxException, JSONException {
        String string = pluginCall.getString("url");
        String upperCase = pluginCall.getString("method", "POST").toUpperCase();
        String string2 = pluginCall.getString("filePath");
        String string3 = pluginCall.getString("fileDirectory", FilesystemUtils.DIRECTORY_DOCUMENTS);
        String string4 = pluginCall.getString("name", "file");
        Integer num = pluginCall.getInt("connectTimeout");
        Integer num2 = pluginCall.getInt("readTimeout");
        JSObject object = pluginCall.getObject("headers");
        JSObject object2 = pluginCall.getObject("params");
        JSObject object3 = pluginCall.getObject("data");
        ResponseType parse = ResponseType.parse(pluginCall.getString("responseType"));
        URL url = new URL(string);
        File fileObject = FilesystemUtils.getFileObject(context, string2, string3);
        CapacitorHttpUrlConnection build = new HttpURLConnectionBuilder().setUrl(url).setMethod(upperCase).setHeaders(object).setUrlParams(object2).setConnectTimeout(num).setReadTimeout(num2).openConnection().build();
        build.setDoOutput(true);
        FormUploader formUploader = new FormUploader(build.getHttpConnection());
        formUploader.addFilePart(string4, fileObject, object3);
        formUploader.finish();
        return buildResponse(build, parse);
    }
}