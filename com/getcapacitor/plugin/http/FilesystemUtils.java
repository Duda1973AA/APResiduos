package com.getcapacitor.plugin.http;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import java.io.File;

/* loaded from: classes.dex */
public class FilesystemUtils {
    public static final String DIRECTORY_APPLICATION = "APPLICATION";
    public static final String DIRECTORY_CACHE = "CACHE";
    public static final String DIRECTORY_DATA = "DATA";
    public static final String DIRECTORY_DOCUMENTS = "DOCUMENTS";
    public static final String DIRECTORY_DOWNLOADS = "DOWNLOADS";
    public static final String DIRECTORY_EXTERNAL = "EXTERNAL";
    public static final String DIRECTORY_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";

    public static File getFileObject(Context context, String str, String str2) {
        if (str2 == null || str.startsWith("file://")) {
            Uri parse = Uri.parse(str);
            if (parse.getScheme() == null || parse.getScheme().equals("file")) {
                return new File(parse.getPath());
            }
        }
        File directory = getDirectory(context, str2);
        if (directory == null) {
            return null;
        }
        if (!directory.exists()) {
            directory.mkdir();
        }
        return new File(directory, str);
    }

    public static File getDirectory(Context context, String str) {
        str.hashCode();
        switch (str) {
            case "EXTERNAL":
                return context.getExternalFilesDir(null);
            case "APPLICATION":
            case "DATA":
                return context.getFilesDir();
            case "DOCUMENTS":
                return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            case "DOWNLOADS":
                return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            case "CACHE":
                return context.getCacheDir();
            case "EXTERNAL_STORAGE":
                return Environment.getExternalStorageDirectory();
            default:
                return null;
        }
    }

    public static boolean isPublicDirectory(String str) {
        return DIRECTORY_DOCUMENTS.equals(str) || DIRECTORY_DOWNLOADS.equals(str) || DIRECTORY_EXTERNAL_STORAGE.equals(str);
    }
}