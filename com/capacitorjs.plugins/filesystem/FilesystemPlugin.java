package com.capacitorjs.plugins.filesystem;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.NotificationCompat;
import com.capacitorjs.plugins.filesystem.exceptions.CopyFailedException;
import com.capacitorjs.plugins.filesystem.exceptions.DirectoryExistsException;
import com.capacitorjs.plugins.filesystem.exceptions.DirectoryNotFoundException;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.getcapacitor.plugin.http.FilesystemUtils;
import com.getcapacitor.plugin.util.HttpRequestHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import org.json.JSONException;

@CapacitorPlugin(name = "Filesystem", permissions = {@Permission(alias = FilesystemPlugin.PUBLIC_STORAGE, strings = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"})})
/* loaded from: classes.dex */
public class FilesystemPlugin extends Plugin {
    private static final String PERMISSION_DENIED_ERROR = "Unable to do file operation, user denied permission request";
    static final String PUBLIC_STORAGE = "publicStorage";
    private Filesystem implementation;

    @Override // com.getcapacitor.Plugin
    public void load() {
        this.implementation = new Filesystem(getContext());
    }

    @PluginMethod
    public void readFile(PluginCall pluginCall) {
        String string = pluginCall.getString("path");
        String directoryParameter = getDirectoryParameter(pluginCall);
        String string2 = pluginCall.getString("encoding");
        Charset encoding = this.implementation.getEncoding(string2);
        if (string2 != null && encoding == null) {
            pluginCall.reject("Unsupported encoding provided: " + string2);
            return;
        }
        if (isPublicDirectory(directoryParameter) && !isStoragePermissionGranted()) {
            requestAllPermissions(pluginCall, "permissionCallback");
            return;
        }
        try {
            String readFile = this.implementation.readFile(string, directoryParameter, encoding);
            JSObject jSObject = new JSObject();
            jSObject.putOpt("data", readFile);
            pluginCall.resolve(jSObject);
        } catch (FileNotFoundException e) {
            pluginCall.reject("File does not exist", e);
        } catch (IOException e2) {
            pluginCall.reject("Unable to read file", e2);
        } catch (JSONException e3) {
            pluginCall.reject("Unable to return value for reading file", e3);
        }
    }

    @PluginMethod
    public void writeFile(PluginCall pluginCall) {
        String string = pluginCall.getString("path");
        String string2 = pluginCall.getString("data");
        Boolean bool = pluginCall.getBoolean("recursive", false);
        if (string == null) {
            Logger.error(getLogTag(), "No path or filename retrieved from call", null);
            pluginCall.reject("NO_PATH");
            return;
        }
        if (string2 == null) {
            Logger.error(getLogTag(), "No data retrieved from call", null);
            pluginCall.reject("NO_DATA");
            return;
        }
        String directoryParameter = getDirectoryParameter(pluginCall);
        if (directoryParameter != null) {
            if (isPublicDirectory(directoryParameter) && !isStoragePermissionGranted()) {
                requestAllPermissions(pluginCall, "permissionCallback");
                return;
            }
            File directory = this.implementation.getDirectory(directoryParameter);
            if (directory != null) {
                if (directory.exists() || directory.mkdirs()) {
                    File file = new File(directory, string);
                    if (file.getParentFile().exists() || (bool.booleanValue() && file.getParentFile().mkdirs())) {
                        saveFile(pluginCall, file, string2);
                        return;
                    } else {
                        pluginCall.reject("Parent folder doesn't exist");
                        return;
                    }
                }
                Logger.error(getLogTag(), "Not able to create '" + directoryParameter + "'!", null);
                pluginCall.reject("NOT_CREATED_DIR");
                return;
            }
            Logger.error(getLogTag(), "Directory ID '" + directoryParameter + "' is not supported by plugin", null);
            pluginCall.reject("INVALID_DIR");
            return;
        }
        Uri parse = Uri.parse(string);
        if (parse.getScheme() == null || parse.getScheme().equals("file")) {
            File file2 = new File(parse.getPath());
            if (!isStoragePermissionGranted()) {
                requestAllPermissions(pluginCall, "permissionCallback");
                return;
            }
            if (file2.getParentFile() == null || file2.getParentFile().exists() || (bool.booleanValue() && file2.getParentFile().mkdirs())) {
                saveFile(pluginCall, file2, string2);
                return;
            } else {
                pluginCall.reject("Parent folder doesn't exist");
                return;
            }
        }
        pluginCall.reject(parse.getScheme() + " scheme not supported");
    }

    private void saveFile(PluginCall pluginCall, File file, String str) {
        String string = pluginCall.getString("encoding");
        boolean booleanValue = pluginCall.getBoolean("append", false).booleanValue();
        Charset encoding = this.implementation.getEncoding(string);
        if (string != null && encoding == null) {
            pluginCall.reject("Unsupported encoding provided: " + string);
            return;
        }
        try {
            this.implementation.saveFile(file, str, encoding, Boolean.valueOf(booleanValue));
            if (isPublicDirectory(getDirectoryParameter(pluginCall))) {
                MediaScannerConnection.scanFile(getContext(), new String[]{file.getAbsolutePath()}, null, null);
            }
            Logger.debug(getLogTag(), "File '" + file.getAbsolutePath() + "' saved!");
            JSObject jSObject = new JSObject();
            jSObject.put("uri", Uri.fromFile(file).toString());
            pluginCall.resolve(jSObject);
        } catch (IOException e) {
            Logger.error(getLogTag(), "Creating file '" + file.getPath() + "' with charset '" + encoding + "' failed. Error: " + e.getMessage(), e);
            pluginCall.reject("FILE_NOTCREATED");
        } catch (IllegalArgumentException unused) {
            pluginCall.reject("The supplied data is not valid base64 content.");
        }
    }

    @PluginMethod
    public void appendFile(PluginCall pluginCall) {
        try {
            pluginCall.getData().putOpt("append", true);
        } catch (JSONException unused) {
        }
        writeFile(pluginCall);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x002f -> B:12:0x0036). Please report as a decompilation issue!!! */
    @PluginMethod
    public void deleteFile(PluginCall pluginCall) {
        String string = pluginCall.getString("path");
        String directoryParameter = getDirectoryParameter(pluginCall);
        if (isPublicDirectory(directoryParameter) && !isStoragePermissionGranted()) {
            requestAllPermissions(pluginCall, "permissionCallback");
            return;
        }
        try {
            if (!this.implementation.deleteFile(string, directoryParameter)) {
                pluginCall.reject("Unable to delete file");
            } else {
                pluginCall.resolve();
            }
        } catch (FileNotFoundException e) {
            pluginCall.reject(e.getMessage());
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0042 -> B:12:0x0049). Please report as a decompilation issue!!! */
    @PluginMethod
    public void mkdir(PluginCall pluginCall) {
        String string = pluginCall.getString("path");
        String directoryParameter = getDirectoryParameter(pluginCall);
        boolean booleanValue = pluginCall.getBoolean("recursive", false).booleanValue();
        if (isPublicDirectory(directoryParameter) && !isStoragePermissionGranted()) {
            requestAllPermissions(pluginCall, "permissionCallback");
            return;
        }
        try {
            if (!this.implementation.mkdir(string, directoryParameter, Boolean.valueOf(booleanValue))) {
                pluginCall.reject("Unable to create directory, unknown reason");
            } else {
                pluginCall.resolve();
            }
        } catch (DirectoryExistsException e) {
            pluginCall.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void rmdir(PluginCall pluginCall) {
        String string = pluginCall.getString("path");
        String directoryParameter = getDirectoryParameter(pluginCall);
        boolean z = false;
        Boolean bool = pluginCall.getBoolean("recursive", false);
        File fileObject = this.implementation.getFileObject(string, directoryParameter);
        if (isPublicDirectory(directoryParameter) && !isStoragePermissionGranted()) {
            requestAllPermissions(pluginCall, "permissionCallback");
            return;
        }
        if (!fileObject.exists()) {
            pluginCall.reject("Directory does not exist");
            return;
        }
        if (fileObject.isDirectory() && fileObject.listFiles().length != 0 && !bool.booleanValue()) {
            pluginCall.reject("Directory is not empty");
            return;
        }
        try {
            this.implementation.deleteRecursively(fileObject);
            z = true;
        } catch (IOException unused) {
        }
        if (!z) {
            pluginCall.reject("Unable to delete directory, unknown reason");
        } else {
            pluginCall.resolve();
        }
    }

    @PluginMethod
    public void readdir(PluginCall pluginCall) {
        String string = pluginCall.getString("path");
        String directoryParameter = getDirectoryParameter(pluginCall);
        if (isPublicDirectory(directoryParameter) && !isStoragePermissionGranted()) {
            requestAllPermissions(pluginCall, "permissionCallback");
            return;
        }
        try {
            File[] readdir = this.implementation.readdir(string, directoryParameter);
            JSArray jSArray = new JSArray();
            if (readdir != null) {
                for (File file : readdir) {
                    JSObject jSObject = new JSObject();
                    jSObject.put("name", file.getName());
                    jSObject.put("type", file.isDirectory() ? "directory" : "file");
                    jSObject.put("size", file.length());
                    jSObject.put("mtime", file.lastModified());
                    jSObject.put("uri", Uri.fromFile(file).toString());
                    if (Build.VERSION.SDK_INT >= 26) {
                        try {
                            BasicFileAttributes readAttributes = Files.readAttributes(file.toPath(), (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
                            if (readAttributes.creationTime().toMillis() < readAttributes.lastAccessTime().toMillis()) {
                                jSObject.put("ctime", readAttributes.creationTime().toMillis());
                            } else {
                                jSObject.put("ctime", readAttributes.lastAccessTime().toMillis());
                            }
                        } catch (Exception unused) {
                        }
                    } else {
                        jSObject.put("ctime", (String) null);
                    }
                    jSArray.put(jSObject);
                }
                JSObject jSObject2 = new JSObject();
                jSObject2.put("files", (Object) jSArray);
                pluginCall.resolve(jSObject2);
                return;
            }
            pluginCall.reject("Unable to read directory");
        } catch (DirectoryNotFoundException e) {
            pluginCall.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void getUri(PluginCall pluginCall) {
        String string = pluginCall.getString("path");
        String directoryParameter = getDirectoryParameter(pluginCall);
        File fileObject = this.implementation.getFileObject(string, directoryParameter);
        if (isPublicDirectory(directoryParameter) && !isStoragePermissionGranted()) {
            requestAllPermissions(pluginCall, "permissionCallback");
            return;
        }
        JSObject jSObject = new JSObject();
        jSObject.put("uri", Uri.fromFile(fileObject).toString());
        pluginCall.resolve(jSObject);
    }

    @PluginMethod
    public void stat(PluginCall pluginCall) {
        String string = pluginCall.getString("path");
        String directoryParameter = getDirectoryParameter(pluginCall);
        File fileObject = this.implementation.getFileObject(string, directoryParameter);
        if (isPublicDirectory(directoryParameter) && !isStoragePermissionGranted()) {
            requestAllPermissions(pluginCall, "permissionCallback");
            return;
        }
        if (!fileObject.exists()) {
            pluginCall.reject("File does not exist");
            return;
        }
        JSObject jSObject = new JSObject();
        jSObject.put("type", fileObject.isDirectory() ? "directory" : "file");
        jSObject.put("size", fileObject.length());
        jSObject.put("mtime", fileObject.lastModified());
        jSObject.put("uri", Uri.fromFile(fileObject).toString());
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                BasicFileAttributes readAttributes = Files.readAttributes(fileObject.toPath(), (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
                if (readAttributes.creationTime().toMillis() < readAttributes.lastAccessTime().toMillis()) {
                    jSObject.put("ctime", readAttributes.creationTime().toMillis());
                } else {
                    jSObject.put("ctime", readAttributes.lastAccessTime().toMillis());
                }
            } catch (Exception unused) {
            }
        } else {
            jSObject.put("ctime", (String) null);
        }
        pluginCall.resolve(jSObject);
    }

    @PluginMethod
    public void rename(PluginCall pluginCall) {
        _copy(pluginCall, true);
    }

    @PluginMethod
    public void copy(PluginCall pluginCall) {
        _copy(pluginCall, false);
    }

    @PluginMethod
    public void downloadFile(final PluginCall pluginCall) {
        try {
            String string = pluginCall.getString("directory", Environment.DIRECTORY_DOWNLOADS);
            if (isPublicDirectory(string) && !isStoragePermissionGranted()) {
                requestAllPermissions(pluginCall, "permissionCallback");
                return;
            }
            JSObject downloadFile = this.implementation.downloadFile(pluginCall, this.bridge, new HttpRequestHandler.ProgressEmitter() { // from class: com.capacitorjs.plugins.filesystem.FilesystemPlugin$$ExternalSyntheticLambda0
                @Override // com.getcapacitor.plugin.util.HttpRequestHandler.ProgressEmitter
                public final void emit(Integer num, Integer num2) {
                    FilesystemPlugin.this.lambda$downloadFile$0(pluginCall, num, num2);
                }
            });
            if (isPublicDirectory(string)) {
                MediaScannerConnection.scanFile(getContext(), new String[]{downloadFile.getString("path")}, null, null);
            }
            pluginCall.resolve(downloadFile);
        } catch (Exception e) {
            pluginCall.reject("Error downloading file: " + e.getLocalizedMessage(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadFile$0(PluginCall pluginCall, Integer num, Integer num2) {
        JSObject jSObject = new JSObject();
        jSObject.put("url", pluginCall.getString("url"));
        jSObject.put("bytes", (Object) num);
        jSObject.put("contentLength", (Object) num2);
        notifyListeners(NotificationCompat.CATEGORY_PROGRESS, jSObject);
    }

    private void _copy(PluginCall pluginCall, Boolean bool) {
        String string = pluginCall.getString("from");
        String string2 = pluginCall.getString("to");
        String string3 = pluginCall.getString("directory");
        String string4 = pluginCall.getString("toDirectory");
        if (string == null || string.isEmpty() || string2 == null || string2.isEmpty()) {
            pluginCall.reject("Both to and from must be provided");
            return;
        }
        if ((isPublicDirectory(string3) || isPublicDirectory(string4)) && !isStoragePermissionGranted()) {
            requestAllPermissions(pluginCall, "permissionCallback");
            return;
        }
        try {
            File copy = this.implementation.copy(string, string3, string2, string4, bool.booleanValue());
            if (!bool.booleanValue()) {
                JSObject jSObject = new JSObject();
                jSObject.put("uri", Uri.fromFile(copy).toString());
                pluginCall.resolve(jSObject);
                return;
            }
            pluginCall.resolve();
        } catch (CopyFailedException e) {
            pluginCall.reject(e.getMessage());
        } catch (IOException e2) {
            pluginCall.reject("Unable to perform action: " + e2.getLocalizedMessage());
        }
    }

    @Override // com.getcapacitor.Plugin
    @PluginMethod
    public void checkPermissions(PluginCall pluginCall) {
        if (isStoragePermissionGranted()) {
            JSObject jSObject = new JSObject();
            jSObject.put(PUBLIC_STORAGE, "granted");
            pluginCall.resolve(jSObject);
            return;
        }
        super.checkPermissions(pluginCall);
    }

    @Override // com.getcapacitor.Plugin
    @PluginMethod
    public void requestPermissions(PluginCall pluginCall) {
        if (isStoragePermissionGranted()) {
            JSObject jSObject = new JSObject();
            jSObject.put(PUBLIC_STORAGE, "granted");
            pluginCall.resolve(jSObject);
            return;
        }
        requestPermissionForAlias(PUBLIC_STORAGE, pluginCall, "permissionCallback");
    }

    @PermissionCallback
    private void permissionCallback(PluginCall pluginCall) {
        if (!isStoragePermissionGranted()) {
            Logger.debug(getLogTag(), "User denied storage permission");
            pluginCall.reject(PERMISSION_DENIED_ERROR);
        }
        String methodName = pluginCall.getMethodName();
        methodName.hashCode();
        switch (methodName) {
            case "appendFile":
            case "writeFile":
                writeFile(pluginCall);
                break;
            case "getUri":
                getUri(pluginCall);
                break;
            case "rename":
                rename(pluginCall);
                break;
            case "readFile":
                readFile(pluginCall);
                break;
            case "copy":
                copy(pluginCall);
                break;
            case "stat":
                stat(pluginCall);
                break;
            case "mkdir":
                mkdir(pluginCall);
                break;
            case "rmdir":
                rmdir(pluginCall);
                break;
            case "readdir":
                readdir(pluginCall);
                break;
            case "downloadFile":
                downloadFile(pluginCall);
                break;
            case "deleteFile":
                deleteFile(pluginCall);
                break;
        }
    }

    private boolean isStoragePermissionGranted() {
        return Build.VERSION.SDK_INT >= 30 || getPermissionState(PUBLIC_STORAGE) == PermissionState.GRANTED;
    }

    private String getDirectoryParameter(PluginCall pluginCall) {
        return pluginCall.getString("directory");
    }

    private boolean isPublicDirectory(String str) {
        return FilesystemUtils.DIRECTORY_DOCUMENTS.equals(str) || FilesystemUtils.DIRECTORY_EXTERNAL_STORAGE.equals(str);
    }
}