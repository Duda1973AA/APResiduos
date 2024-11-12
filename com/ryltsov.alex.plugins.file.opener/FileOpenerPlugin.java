package com.ryltsov.alex.plugins.file.opener;

import android.webkit.MimeTypeMap;
import com.getcapacitor.Plugin;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FileOpener")
/* loaded from: classes.dex */
public class FileOpenerPlugin extends Plugin {
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003c, code lost:
    
        if (r1.trim().equals("") != false) goto L11;
     */
    @com.getcapacitor.PluginMethod
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void open(com.getcapacitor.PluginCall r9) {
        /*
            r8 = this;
            java.lang.String r0 = "filePath"
            java.lang.String r0 = r9.getString(r0)
            java.lang.String r1 = "contentType"
            java.lang.String r1 = r9.getString(r1)
            r2 = 1
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r2)
            java.lang.String r4 = "openWithDefault"
            java.lang.Boolean r3 = r9.getBoolean(r4, r3)
            boolean r3 = r3.booleanValue()
            android.net.Uri r4 = android.net.Uri.parse(r0)     // Catch: java.lang.Exception -> L24
            java.lang.String r0 = r4.getPath()     // Catch: java.lang.Exception -> L24
            goto L25
        L24:
        L25:
            java.io.File r4 = new java.io.File
            r4.<init>(r0)
            boolean r5 = r4.exists()
            if (r5 == 0) goto Lb3
            if (r1 == 0) goto L3e
            java.lang.String r5 = r1.trim()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            java.lang.String r6 = ""
            boolean r5 = r5.equals(r6)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            if (r5 == 0) goto L42
        L3e:
            java.lang.String r1 = r8.getMimeType(r0)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
        L42:
            android.content.Intent r0 = new android.content.Intent     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            java.lang.String r5 = "android.intent.action.VIEW"
            r0.<init>(r5)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            androidx.appcompat.app.AppCompatActivity r5 = r8.getActivity()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            android.content.Context r5 = r5.getApplicationContext()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            r6.<init>()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            androidx.appcompat.app.AppCompatActivity r7 = r8.getActivity()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            java.lang.String r7 = r7.getPackageName()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            r6.append(r7)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            java.lang.String r7 = ".file.opener.provider"
            r6.append(r7)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            android.net.Uri r4 = androidx.core.content.FileProvider.getUriForFile(r5, r6, r4)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            r0.setDataAndType(r4, r1)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            r0.setFlags(r2)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            if (r3 == 0) goto L7e
            androidx.appcompat.app.AppCompatActivity r1 = r8.getActivity()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            r1.startActivity(r0)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            goto L8b
        L7e:
            androidx.appcompat.app.AppCompatActivity r1 = r8.getActivity()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            java.lang.String r2 = "Open File in..."
            android.content.Intent r0 = android.content.Intent.createChooser(r0, r2)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            r1.startActivity(r0)     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
        L8b:
            r9.resolve()     // Catch: java.lang.Exception -> L8f android.content.ActivityNotFoundException -> L9a
            goto Lba
        L8f:
            r0 = move-exception
            java.lang.String r1 = r0.getLocalizedMessage()
            java.lang.String r2 = "1"
            r9.reject(r1, r2, r0)
            goto Lba
        L9a:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Activity not found: "
            r1.<init>(r2)
            java.lang.String r2 = r0.getMessage()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "8"
            r9.reject(r1, r2, r0)
            goto Lba
        Lb3:
            java.lang.String r0 = "File not found"
            java.lang.String r1 = "9"
            r9.reject(r0, r1)
        Lba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ryltsov.alex.plugins.file.opener.FileOpenerPlugin.open(com.getcapacitor.PluginCall):void");
    }

    private String getMimeType(String str) {
        String mimeTypeFromExtension;
        int lastIndexOf = str.lastIndexOf(46);
        return (lastIndexOf <= 0 || (mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(lastIndexOf + 1))) == null) ? "*/*" : mimeTypeFromExtension;
    }
}