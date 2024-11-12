package com.capacitorjs.plugins.filesystem;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import com.capacitorjs.plugins.filesystem.exceptions.CopyFailedException;
import com.capacitorjs.plugins.filesystem.exceptions.DirectoryExistsException;
import com.capacitorjs.plugins.filesystem.exceptions.DirectoryNotFoundException;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class Filesystem {
    private Context context;

    Filesystem(Context context) {
        this.context = context;
    }

    public String readFile(String str, String str2, Charset charset) throws IOException {
        InputStream inputStream = getInputStream(str, str2);
        if (charset != null) {
            return readFileAsString(inputStream, charset.name());
        }
        return readFileAsBase64EncodedData(inputStream);
    }

    public void saveFile(File file, String str, Charset charset, Boolean bool) throws IOException {
        if (charset != null) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, bool.booleanValue()), charset));
            bufferedWriter.write(str);
            bufferedWriter.close();
        } else {
            if (str.contains(",")) {
                str = str.split(",")[1];
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, bool.booleanValue());
            fileOutputStream.write(Base64.decode(str, 2));
            fileOutputStream.close();
        }
    }

    public boolean deleteFile(String str, String str2) throws FileNotFoundException {
        File fileObject = getFileObject(str, str2);
        if (!fileObject.exists()) {
            throw new FileNotFoundException("File does not exist");
        }
        return fileObject.delete();
    }

    public boolean mkdir(String str, String str2, Boolean bool) throws DirectoryExistsException {
        File fileObject = getFileObject(str, str2);
        if (fileObject.exists()) {
            throw new DirectoryExistsException("Directory exists");
        }
        if (bool.booleanValue()) {
            return fileObject.mkdirs();
        }
        return fileObject.mkdir();
    }

    public File[] readdir(String str, String str2) throws DirectoryNotFoundException {
        File fileObject = getFileObject(str, str2);
        if (fileObject != null && fileObject.exists()) {
            return fileObject.listFiles();
        }
        throw new DirectoryNotFoundException("Directory does not exist");
    }

    public File copy(String str, String str2, String str3, String str4, boolean z) throws IOException, CopyFailedException {
        if (str4 == null) {
            str4 = str2;
        }
        File fileObject = getFileObject(str, str2);
        File fileObject2 = getFileObject(str3, str4);
        if (fileObject == null) {
            throw new CopyFailedException("from file is null");
        }
        if (fileObject2 == null) {
            throw new CopyFailedException("to file is null");
        }
        if (fileObject2.equals(fileObject)) {
            return fileObject2;
        }
        if (!fileObject.exists()) {
            throw new CopyFailedException("The source object does not exist");
        }
        if (fileObject2.getParentFile().isFile()) {
            throw new CopyFailedException("The parent object of the destination is a file");
        }
        if (!fileObject2.getParentFile().exists()) {
            throw new CopyFailedException("The parent object of the destination does not exist");
        }
        if (fileObject2.isDirectory()) {
            throw new CopyFailedException("Cannot overwrite a directory");
        }
        fileObject2.delete();
        if (z) {
            if (!fileObject.renameTo(fileObject2)) {
                throw new CopyFailedException("Unable to rename, unknown reason");
            }
        } else {
            copyRecursively(fileObject, fileObject2);
        }
        return fileObject2;
    }

    public InputStream getInputStream(String str, String str2) throws IOException {
        if (str2 == null) {
            Uri parse = Uri.parse(str);
            if (parse.getScheme().equals("content")) {
                return this.context.getContentResolver().openInputStream(parse);
            }
            return new FileInputStream(new File(parse.getPath()));
        }
        File directory = getDirectory(str2);
        if (directory == null) {
            throw new IOException("Directory not found");
        }
        return new FileInputStream(new File(directory, str));
    }

    public String readFileAsString(InputStream inputStream, String str) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                return byteArrayOutputStream.toString(str);
            }
        }
    }

    public String readFileAsBase64EncodedData(InputStream inputStream) throws IOException {
        FileInputStream fileInputStream = (FileInputStream) inputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                fileInputStream.close();
                return Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
            }
        }
    }

    public File getDirectory(String str) {
        Context context;
        context = this.context;
        str.hashCode();
        switch (str) {
            case "EXTERNAL":
                return context.getExternalFilesDir(null);
            case "DOCUMENTS":
                return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            case "DATA":
            case "LIBRARY":
                return context.getFilesDir();
            case "CACHE":
                return context.getCacheDir();
            case "EXTERNAL_STORAGE":
                return Environment.getExternalStorageDirectory();
            default:
                return null;
        }
    }

    public File getFileObject(String str, String str2) {
        if (str2 == null) {
            Uri parse = Uri.parse(str);
            if (parse.getScheme() == null || parse.getScheme().equals("file")) {
                return new File(parse.getPath());
            }
        }
        File directory = getDirectory(str2);
        if (directory == null) {
            return null;
        }
        if (!directory.exists()) {
            directory.mkdir();
        }
        return new File(directory, str);
    }

    public Charset getEncoding(String str) {
        if (str == null) {
            return null;
        }
        str.hashCode();
        switch (str) {
        }
        return null;
    }

    public void deleteRecursively(File file) throws IOException {
        if (file.isFile()) {
            file.delete();
            return;
        }
        for (File file2 : file.listFiles()) {
            deleteRecursively(file2);
        }
        file.delete();
    }

    public void copyRecursively(File file, File file2) throws IOException {
        if (file.isDirectory()) {
            file2.mkdir();
            for (String str : file.list()) {
                copyRecursively(new File(file, str), new File(file2, str));
            }
            return;
        }
        if (!file2.getParentFile().exists()) {
            file2.getParentFile().mkdirs();
        }
        if (!file2.exists()) {
            file2.createNewFile();
        }
        FileChannel channel = new FileInputStream(file).getChannel();
        try {
            FileChannel channel2 = new FileOutputStream(file2).getChannel();
            try {
                channel2.transferFrom(channel, 0L, channel.size());
                if (channel2 != null) {
                    channel2.close();
                }
                if (channel != null) {
                    channel.close();
                }
            } finally {
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00ee A[EDGE_INSN: B:21:0x00ee->B:22:0x00ee BREAK  A[LOOP:0: B:5:0x00c3->B:17:0x00c3], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x00c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.getcapacitor.JSObject downloadFile(com.getcapacitor.PluginCall r20, com.getcapacitor.Bridge r21, com.getcapacitor.plugin.util.HttpRequestHandler.ProgressEmitter r22) throws java.io.IOException, java.net.URISyntaxException, org.json.JSONException {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r22
            java.lang.String r3 = "url"
            java.lang.String r4 = ""
            java.lang.String r3 = r1.getString(r3, r4)
            com.getcapacitor.JSObject r4 = new com.getcapacitor.JSObject
            r4.<init>()
            java.lang.String r5 = "headers"
            com.getcapacitor.JSObject r4 = r1.getObject(r5, r4)
            com.getcapacitor.JSObject r5 = new com.getcapacitor.JSObject
            r5.<init>()
            java.lang.String r6 = "params"
            com.getcapacitor.JSObject r5 = r1.getObject(r6, r5)
            java.lang.String r6 = "connectTimeout"
            java.lang.Integer r6 = r1.getInt(r6)
            java.lang.String r7 = "readTimeout"
            java.lang.Integer r7 = r1.getInt(r7)
            java.lang.String r8 = "disableRedirects"
            java.lang.Boolean r8 = r1.getBoolean(r8)
            r9 = 1
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
            java.lang.String r10 = "shouldEncodeUrlParams"
            java.lang.Boolean r9 = r1.getBoolean(r10, r9)
            r10 = 0
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r10)
            java.lang.String r12 = "progress"
            java.lang.Boolean r11 = r1.getBoolean(r12, r11)
            java.lang.String r12 = "method"
            java.lang.String r13 = "GET"
            java.lang.String r12 = r1.getString(r12, r13)
            java.util.Locale r13 = java.util.Locale.ROOT
            java.lang.String r12 = r12.toUpperCase(r13)
            java.lang.String r13 = "path"
            java.lang.String r13 = r1.getString(r13)
            java.lang.String r14 = "directory"
            java.lang.String r15 = android.os.Environment.DIRECTORY_DOWNLOADS
            java.lang.String r1 = r1.getString(r14, r15)
            java.net.URL r14 = new java.net.URL
            r14.<init>(r3)
            java.io.File r1 = r0.getFileObject(r13, r1)
            com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder r3 = new com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder
            r3.<init>()
            com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder r3 = r3.setUrl(r14)
            com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder r3 = r3.setMethod(r12)
            com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder r3 = r3.setHeaders(r4)
            boolean r4 = r9.booleanValue()
            com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder r3 = r3.setUrlParams(r5, r4)
            com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder r3 = r3.setConnectTimeout(r6)
            com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder r3 = r3.setReadTimeout(r7)
            com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder r3 = r3.setDisableRedirects(r8)
            com.getcapacitor.plugin.util.HttpRequestHandler$HttpURLConnectionBuilder r3 = r3.openConnection()
            com.getcapacitor.plugin.util.CapacitorHttpUrlConnection r3 = r3.build()
            r4 = r21
            r3.setSSLSocketFactory(r4)
            java.io.InputStream r4 = r3.getInputStream()
            java.io.FileOutputStream r5 = new java.io.FileOutputStream
            r5.<init>(r1, r10)
            java.lang.String r6 = "content-length"
            java.lang.String r3 = r3.getHeaderField(r6)
            if (r3 == 0) goto Lb9
            int r3 = java.lang.Integer.parseInt(r3)     // Catch: java.lang.NumberFormatException -> Lb9
            goto Lba
        Lb9:
            r3 = 0
        Lba:
            r6 = 1024(0x400, float:1.435E-42)
            byte[] r6 = new byte[r6]
            long r7 = java.lang.System.currentTimeMillis()
            r9 = 0
        Lc3:
            int r12 = r4.read(r6)
            if (r12 <= 0) goto Lee
            r5.write(r6, r10, r12)
            int r9 = r9 + r12
            boolean r12 = r11.booleanValue()
            if (r12 == 0) goto Lc3
            if (r2 == 0) goto Lc3
            long r12 = java.lang.System.currentTimeMillis()
            long r14 = r12 - r7
            r16 = 100
            int r18 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r18 <= 0) goto Lc3
            java.lang.Integer r7 = java.lang.Integer.valueOf(r9)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)
            r2.emit(r7, r8)
            r7 = r12
            goto Lc3
        Lee:
            boolean r6 = r11.booleanValue()
            if (r6 == 0) goto L101
            if (r2 == 0) goto L101
            java.lang.Integer r6 = java.lang.Integer.valueOf(r9)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2.emit(r6, r3)
        L101:
            r4.close()
            r5.close()
            com.capacitorjs.plugins.filesystem.Filesystem$1 r2 = new com.capacitorjs.plugins.filesystem.Filesystem$1
            r2.<init>(r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.capacitorjs.plugins.filesystem.Filesystem.downloadFile(com.getcapacitor.PluginCall, com.getcapacitor.Bridge, com.getcapacitor.plugin.util.HttpRequestHandler$ProgressEmitter):com.getcapacitor.JSObject");
    }
}