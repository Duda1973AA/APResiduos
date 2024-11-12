package com.getcapacitor.plugin.http;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class CapacitorCookieManager extends CookieManager {
    private final android.webkit.CookieManager webkitCookieManager;

    public CapacitorCookieManager() {
        this(null, null);
    }

    public CapacitorCookieManager(CookieStore cookieStore, CookiePolicy cookiePolicy) {
        super(cookieStore, cookiePolicy);
        this.webkitCookieManager = android.webkit.CookieManager.getInstance();
    }

    public String getCookieString(String str) {
        return this.webkitCookieManager.getCookie(str);
    }

    public HttpCookie getCookie(String str, String str2) {
        for (HttpCookie httpCookie : getCookies(str)) {
            if (httpCookie.getName().equals(str2)) {
                return httpCookie;
            }
        }
        return null;
    }

    public HttpCookie[] getCookies(String str) {
        try {
            ArrayList arrayList = new ArrayList();
            String cookieString = getCookieString(str);
            if (cookieString != null) {
                for (String str2 : cookieString.split(";")) {
                    HttpCookie httpCookie = HttpCookie.parse(str2).get(0);
                    httpCookie.setValue(httpCookie.getValue());
                    arrayList.add(httpCookie);
                }
            }
            return (HttpCookie[]) arrayList.toArray(new HttpCookie[arrayList.size()]);
        } catch (Exception unused) {
            return new HttpCookie[0];
        }
    }

    public void setCookie(String str, String str2) {
        this.webkitCookieManager.setCookie(str, str2);
        flush();
    }

    public void setCookie(String str, String str2, String str3) {
        setCookie(str, str2 + "=" + str3);
    }

    public void removeAllCookies() {
        this.webkitCookieManager.removeAllCookies(null);
        flush();
    }

    public void flush() {
        this.webkitCookieManager.flush();
    }

    @Override // java.net.CookieManager, java.net.CookieHandler
    public void put(URI uri, Map<String, List<String>> map) {
        if (uri == null || map == null) {
            return;
        }
        String uri2 = uri.toString();
        for (String str : map.keySet()) {
            if (str != null && (str.equalsIgnoreCase("Set-Cookie2") || str.equalsIgnoreCase("Set-Cookie"))) {
                Iterator it = ((List) Objects.requireNonNull(map.get(str))).iterator();
                while (it.hasNext()) {
                    setCookie(uri2, (String) it.next());
                }
            }
        }
    }

    @Override // java.net.CookieManager, java.net.CookieHandler
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> map) {
        if (uri == null || map == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        String uri2 = uri.toString();
        HashMap hashMap = new HashMap();
        String cookieString = getCookieString(uri2);
        if (cookieString != null) {
            hashMap.put("Cookie", Collections.singletonList(cookieString));
        }
        return hashMap;
    }

    @Override // java.net.CookieManager
    public CookieStore getCookieStore() {
        throw new UnsupportedOperationException();
    }
}