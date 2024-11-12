package com.getcapacitor.plugin.http;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
interface ICapacitorHttpUrlConnection {
    InputStream getErrorStream();

    String getHeaderField(String str);

    InputStream getInputStream() throws IOException;
}