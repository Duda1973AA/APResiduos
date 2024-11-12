package com.capacitorjs.plugins.camera;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import androidx.activity.result.ActivityResult;
import androidx.core.content.FileProvider;
import com.capacitorjs.plugins.camera.CameraBottomSheetDialogFragment;
import com.getcapacitor.FileUtils;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import org.json.JSONException;

@CapacitorPlugin(name = "Camera", permissions = {@Permission(alias = CameraPlugin.CAMERA, strings = {"android.permission.CAMERA"}), @Permission(alias = CameraPlugin.PHOTOS, strings = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}), @Permission(alias = CameraPlugin.READ_EXTERNAL_STORAGE, strings = {"android.permission.READ_EXTERNAL_STORAGE"}), @Permission(alias = CameraPlugin.MEDIA, strings = {"android.permission.READ_MEDIA_IMAGES"})})
/* loaded from: classes.dex */
public class CameraPlugin extends Plugin {
    static final String CAMERA = "camera";
    private static final String IMAGE_EDIT_ERROR = "Unable to edit image";
    private static final String IMAGE_FILE_SAVE_ERROR = "Unable to create photo on disk";
    private static final String IMAGE_GALLERY_SAVE_ERROR = "Unable to save the image in the gallery";
    private static final String IMAGE_PROCESS_NO_FILE_ERROR = "Unable to process image, file not found on disk";
    private static final String INVALID_RESULT_TYPE_ERROR = "Invalid resultType option";
    static final String MEDIA = "media";
    private static final String NO_CAMERA_ACTIVITY_ERROR = "Unable to resolve camera activity";
    private static final String NO_CAMERA_ERROR = "Device doesn't have a camera available";
    private static final String NO_PHOTO_ACTIVITY_ERROR = "Unable to resolve photo activity";
    private static final String PERMISSION_DENIED_ERROR_CAMERA = "User denied access to camera";
    private static final String PERMISSION_DENIED_ERROR_PHOTOS = "User denied access to photos";
    static final String PHOTOS = "photos";
    static final String READ_EXTERNAL_STORAGE = "readExternalStorage";
    private static final String UNABLE_TO_PROCESS_IMAGE = "Unable to process image";
    private String imageEditedFileSavePath;
    private String imageFileSavePath;
    private Uri imageFileUri;
    private Uri imagePickedContentUri;
    private boolean isEdited = false;
    private boolean isFirstRequest = true;
    private boolean isSaved = false;
    private CameraSettings settings = new CameraSettings();

    @PluginMethod
    public void getPhoto(PluginCall pluginCall) {
        this.isEdited = false;
        this.settings = getSettings(pluginCall);
        doShow(pluginCall);
    }

    @PluginMethod
    public void pickImages(PluginCall pluginCall) {
        this.settings = getSettings(pluginCall);
        openPhotos(pluginCall, true, false);
    }

    @PluginMethod
    public void pickLimitedLibraryPhotos(PluginCall pluginCall) {
        pluginCall.unimplemented("not supported on android");
    }

    @PluginMethod
    public void getLimitedLibraryPhotos(PluginCall pluginCall) {
        pluginCall.unimplemented("not supported on android");
    }

    /* renamed from: com.capacitorjs.plugins.camera.CameraPlugin$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$capacitorjs$plugins$camera$CameraSource;

        static {
            int[] iArr = new int[CameraSource.values().length];
            $SwitchMap$com$capacitorjs$plugins$camera$CameraSource = iArr;
            try {
                iArr[CameraSource.CAMERA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$capacitorjs$plugins$camera$CameraSource[CameraSource.PHOTOS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void doShow(PluginCall pluginCall) {
        int i = AnonymousClass1.$SwitchMap$com$capacitorjs$plugins$camera$CameraSource[this.settings.getSource().ordinal()];
        if (i == 1) {
            showCamera(pluginCall);
        } else if (i == 2) {
            showPhotos(pluginCall);
        } else {
            showPrompt(pluginCall);
        }
    }

    private void showPrompt(final PluginCall pluginCall) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(pluginCall.getString("promptLabelPhoto", "From Photos"));
        arrayList.add(pluginCall.getString("promptLabelPicture", "Take Picture"));
        CameraBottomSheetDialogFragment cameraBottomSheetDialogFragment = new CameraBottomSheetDialogFragment();
        cameraBottomSheetDialogFragment.setTitle(pluginCall.getString("promptLabelHeader", "Photo"));
        cameraBottomSheetDialogFragment.setOptions(arrayList, new CameraBottomSheetDialogFragment.BottomSheetOnSelectedListener() { // from class: com.capacitorjs.plugins.camera.CameraPlugin$$ExternalSyntheticLambda0
            @Override // com.capacitorjs.plugins.camera.CameraBottomSheetDialogFragment.BottomSheetOnSelectedListener
            public final void onSelected(int i) {
                CameraPlugin.this.lambda$showPrompt$0(pluginCall, i);
            }
        }, new CameraBottomSheetDialogFragment.BottomSheetOnCanceledListener() { // from class: com.capacitorjs.plugins.camera.CameraPlugin$$ExternalSyntheticLambda1
            @Override // com.capacitorjs.plugins.camera.CameraBottomSheetDialogFragment.BottomSheetOnCanceledListener
            public final void onCanceled() {
                PluginCall.this.reject("User cancelled photos app");
            }
        });
        cameraBottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), "capacitorModalsActionSheet");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPrompt$0(PluginCall pluginCall, int i) {
        if (i == 0) {
            this.settings.setSource(CameraSource.PHOTOS);
            openPhotos(pluginCall);
        } else if (i == 1) {
            this.settings.setSource(CameraSource.CAMERA);
            openCamera(pluginCall);
        }
    }

    private void showCamera(PluginCall pluginCall) {
        if (!getContext().getPackageManager().hasSystemFeature("android.hardware.camera.any")) {
            pluginCall.reject(NO_CAMERA_ERROR);
        } else {
            openCamera(pluginCall);
        }
    }

    private void showPhotos(PluginCall pluginCall) {
        openPhotos(pluginCall);
    }

    private boolean checkCameraPermissions(PluginCall pluginCall) {
        String[] strArr;
        boolean isPermissionDeclared = isPermissionDeclared(CAMERA);
        boolean z = !isPermissionDeclared || getPermissionState(CAMERA) == PermissionState.GRANTED;
        boolean z2 = getPermissionState(PHOTOS) == PermissionState.GRANTED;
        if (!this.settings.isSaveToGallery() || ((z && z2) || !this.isFirstRequest)) {
            if (z) {
                return true;
            }
            requestPermissionForAlias(CAMERA, pluginCall, "cameraPermissionsCallback");
            return false;
        }
        this.isFirstRequest = false;
        if (isPermissionDeclared) {
            strArr = new String[]{CAMERA, PHOTOS};
        } else {
            strArr = new String[]{PHOTOS};
        }
        requestPermissionForAliases(strArr, pluginCall, "cameraPermissionsCallback");
        return false;
    }

    private boolean checkPhotosPermissions(PluginCall pluginCall) {
        if (Build.VERSION.SDK_INT < 30) {
            if (getPermissionState(PHOTOS) == PermissionState.GRANTED) {
                return true;
            }
            requestPermissionForAlias(PHOTOS, pluginCall, "cameraPermissionsCallback");
            return false;
        }
        if (Build.VERSION.SDK_INT < 33) {
            if (getPermissionState(READ_EXTERNAL_STORAGE) == PermissionState.GRANTED) {
                return true;
            }
            requestPermissionForAlias(READ_EXTERNAL_STORAGE, pluginCall, "cameraPermissionsCallback");
            return false;
        }
        if (getPermissionState(MEDIA) == PermissionState.GRANTED) {
            return true;
        }
        requestPermissionForAlias(MEDIA, pluginCall, "cameraPermissionsCallback");
        return false;
    }

    @PermissionCallback
    private void cameraPermissionsCallback(PluginCall pluginCall) {
        String str;
        if (pluginCall.getMethodName().equals("pickImages")) {
            openPhotos(pluginCall, true, true);
            return;
        }
        if (this.settings.getSource() == CameraSource.CAMERA && getPermissionState(CAMERA) != PermissionState.GRANTED) {
            Logger.debug(getLogTag(), "User denied camera permission: " + getPermissionState(CAMERA).toString());
            pluginCall.reject(PERMISSION_DENIED_ERROR_CAMERA);
            return;
        }
        if (this.settings.getSource() == CameraSource.PHOTOS) {
            if (Build.VERSION.SDK_INT < 30) {
                str = PHOTOS;
            } else {
                str = Build.VERSION.SDK_INT < 33 ? READ_EXTERNAL_STORAGE : MEDIA;
            }
            PermissionState permissionState = getPermissionState(str);
            if (permissionState != PermissionState.GRANTED) {
                Logger.debug(getLogTag(), "User denied photos permission: " + permissionState.toString());
                pluginCall.reject(PERMISSION_DENIED_ERROR_PHOTOS);
                return;
            }
        }
        doShow(pluginCall);
    }

    @Override // com.getcapacitor.Plugin
    protected void requestPermissionForAliases(String[] strArr, PluginCall pluginCall, String str) {
        int i = 0;
        if (Build.VERSION.SDK_INT >= 33) {
            while (i < strArr.length) {
                if (strArr[i].equals(PHOTOS)) {
                    strArr[i] = MEDIA;
                }
                i++;
            }
        } else if (Build.VERSION.SDK_INT >= 30) {
            while (i < strArr.length) {
                if (strArr[i].equals(PHOTOS)) {
                    strArr[i] = READ_EXTERNAL_STORAGE;
                }
                i++;
            }
        }
        super.requestPermissionForAliases(strArr, pluginCall, str);
    }

    private CameraSettings getSettings(PluginCall pluginCall) {
        CameraSettings cameraSettings = new CameraSettings();
        cameraSettings.setResultType(getResultType(pluginCall.getString("resultType")));
        cameraSettings.setSaveToGallery(pluginCall.getBoolean("saveToGallery", false).booleanValue());
        cameraSettings.setAllowEditing(pluginCall.getBoolean("allowEditing", false).booleanValue());
        cameraSettings.setQuality(pluginCall.getInt("quality", 90).intValue());
        cameraSettings.setWidth(pluginCall.getInt("width", 0).intValue());
        cameraSettings.setHeight(pluginCall.getInt("height", 0).intValue());
        cameraSettings.setShouldResize(cameraSettings.getWidth() > 0 || cameraSettings.getHeight() > 0);
        cameraSettings.setShouldCorrectOrientation(pluginCall.getBoolean("correctOrientation", true).booleanValue());
        try {
            cameraSettings.setSource(CameraSource.valueOf(pluginCall.getString("source", CameraSource.PROMPT.getSource())));
        } catch (IllegalArgumentException unused) {
            cameraSettings.setSource(CameraSource.PROMPT);
        }
        return cameraSettings;
    }

    private CameraResultType getResultType(String str) {
        if (str == null) {
            return null;
        }
        try {
            return CameraResultType.valueOf(str.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException unused) {
            Logger.debug(getLogTag(), "Invalid result type \"" + str + "\", defaulting to base64");
            return CameraResultType.BASE64;
        }
    }

    public void openCamera(PluginCall pluginCall) {
        if (checkCameraPermissions(pluginCall)) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                try {
                    String appId = getAppId();
                    File createImageFile = CameraUtils.createImageFile(getActivity());
                    this.imageFileSavePath = createImageFile.getAbsolutePath();
                    Uri uriForFile = FileProvider.getUriForFile(getActivity(), appId + ".fileprovider", createImageFile);
                    this.imageFileUri = uriForFile;
                    intent.putExtra("output", uriForFile);
                    startActivityForResult(pluginCall, intent, "processCameraImage");
                    return;
                } catch (Exception e) {
                    pluginCall.reject(IMAGE_FILE_SAVE_ERROR, e);
                    return;
                }
            }
            pluginCall.reject(NO_CAMERA_ACTIVITY_ERROR);
        }
    }

    public void openPhotos(PluginCall pluginCall) {
        openPhotos(pluginCall, false, false);
    }

    private void openPhotos(PluginCall pluginCall, boolean z, boolean z2) {
        if (z2 || checkPhotosPermissions(pluginCall)) {
            Intent intent = new Intent("android.intent.action.PICK");
            intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", z);
            intent.setType("image/*");
            try {
                if (z) {
                    intent.putExtra("multi-pick", z);
                    intent.putExtra("android.intent.extra.MIME_TYPES", new String[]{"image/*"});
                    startActivityForResult(pluginCall, intent, "processPickedImages");
                } else {
                    startActivityForResult(pluginCall, intent, "processPickedImage");
                }
            } catch (ActivityNotFoundException unused) {
                pluginCall.reject(NO_PHOTO_ACTIVITY_ERROR);
            }
        }
    }

    @ActivityCallback
    public void processCameraImage(PluginCall pluginCall, ActivityResult activityResult) {
        this.settings = getSettings(pluginCall);
        if (this.imageFileSavePath == null) {
            pluginCall.reject(IMAGE_PROCESS_NO_FILE_ERROR);
            return;
        }
        File file = new File(this.imageFileSavePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Uri fromFile = Uri.fromFile(file);
        Bitmap decodeFile = BitmapFactory.decodeFile(this.imageFileSavePath, options);
        if (decodeFile == null) {
            pluginCall.reject("User cancelled photos app");
        } else {
            returnResult(pluginCall, decodeFile, fromFile);
        }
    }

    @ActivityCallback
    public void processPickedImage(PluginCall pluginCall, ActivityResult activityResult) {
        this.settings = getSettings(pluginCall);
        Intent data = activityResult.getData();
        if (data == null) {
            pluginCall.reject("No image picked");
            return;
        }
        Uri data2 = data.getData();
        this.imagePickedContentUri = data2;
        processPickedImage(data2, pluginCall);
    }

    @ActivityCallback
    public void processPickedImages(final PluginCall pluginCall, ActivityResult activityResult) {
        final Intent data = activityResult.getData();
        if (data != null) {
            Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.capacitorjs.plugins.camera.CameraPlugin$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    CameraPlugin.this.lambda$processPickedImages$2(data, pluginCall);
                }
            });
        } else {
            pluginCall.reject("No images picked");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processPickedImages$2(Intent intent, PluginCall pluginCall) {
        ArrayList<Parcelable> legacyParcelableArrayList;
        JSObject jSObject = new JSObject();
        JSArray jSArray = new JSArray();
        if (intent.getClipData() != null) {
            int itemCount = intent.getClipData().getItemCount();
            for (int i = 0; i < itemCount; i++) {
                JSObject processPickedImages = processPickedImages(intent.getClipData().getItemAt(i).getUri());
                if (processPickedImages.getString("error") != null && !processPickedImages.getString("error").isEmpty()) {
                    pluginCall.reject(processPickedImages.getString("error"));
                    return;
                }
                jSArray.put(processPickedImages);
            }
        } else if (intent.getData() != null) {
            JSObject processPickedImages2 = processPickedImages(intent.getData());
            if (processPickedImages2.getString("error") != null && !processPickedImages2.getString("error").isEmpty()) {
                pluginCall.reject(processPickedImages2.getString("error"));
                return;
            }
            jSArray.put(processPickedImages2);
        } else if (intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            if (extras.keySet().contains("selectedItems")) {
                if (Build.VERSION.SDK_INT >= 33) {
                    legacyParcelableArrayList = extras.getParcelableArrayList("selectedItems", Parcelable.class);
                } else {
                    legacyParcelableArrayList = getLegacyParcelableArrayList(extras, "selectedItems");
                }
                if (legacyParcelableArrayList != null) {
                    Iterator<Parcelable> it = legacyParcelableArrayList.iterator();
                    while (it.hasNext()) {
                        Parcelable next = it.next();
                        if (next instanceof Uri) {
                            try {
                                JSObject processPickedImages3 = processPickedImages((Uri) next);
                                if (processPickedImages3.getString("error") != null && !processPickedImages3.getString("error").isEmpty()) {
                                    pluginCall.reject(processPickedImages3.getString("error"));
                                    return;
                                }
                                jSArray.put(processPickedImages3);
                            } catch (SecurityException unused) {
                                pluginCall.reject("SecurityException");
                            }
                        }
                    }
                }
            }
        }
        jSObject.put(PHOTOS, (Object) jSArray);
        pluginCall.resolve(jSObject);
    }

    private ArrayList<Parcelable> getLegacyParcelableArrayList(Bundle bundle, String str) {
        return bundle.getParcelableArrayList(str);
    }

    private void processPickedImage(Uri uri, PluginCall pluginCall) {
        InputStream openInputStream;
        Bitmap decodeStream;
        InputStream inputStream = null;
        try {
            try {
                try {
                    try {
                        openInputStream = getContext().getContentResolver().openInputStream(uri);
                        decodeStream = BitmapFactory.decodeStream(openInputStream);
                    } catch (OutOfMemoryError unused) {
                        pluginCall.reject("Out of memory");
                        if (0 == 0) {
                            return;
                        } else {
                            inputStream.close();
                        }
                    }
                } catch (FileNotFoundException e) {
                    pluginCall.reject("No such image found", e);
                    if (0 == 0) {
                        return;
                    } else {
                        inputStream.close();
                    }
                }
                if (decodeStream != null) {
                    returnResult(pluginCall, decodeStream, uri);
                    if (openInputStream != null) {
                        openInputStream.close();
                        return;
                    }
                    return;
                }
                pluginCall.reject("Unable to process bitmap");
                if (openInputStream != null) {
                    try {
                        openInputStream.close();
                    } catch (IOException e2) {
                        Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e2);
                    }
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e3);
                    }
                }
                throw th;
            }
        } catch (IOException e4) {
            Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e4);
        }
    }

    private JSObject processPickedImages(Uri uri) {
        JSObject jSObject = new JSObject();
        InputStream inputStream = null;
        try {
            try {
                try {
                    try {
                        InputStream openInputStream = getContext().getContentResolver().openInputStream(uri);
                        Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream);
                        if (decodeStream == null) {
                            jSObject.put("error", "Unable to process bitmap");
                            if (openInputStream != null) {
                                try {
                                    openInputStream.close();
                                } catch (IOException e) {
                                    Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e);
                                }
                            }
                            return jSObject;
                        }
                        ExifWrapper exifData = ImageUtils.getExifData(getContext(), decodeStream, uri);
                        try {
                            Bitmap prepareBitmap = prepareBitmap(decodeStream, uri, exifData);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            prepareBitmap.compress(Bitmap.CompressFormat.JPEG, this.settings.getQuality(), byteArrayOutputStream);
                            Uri tempImage = getTempImage(uri, byteArrayOutputStream);
                            exifData.copyExif(tempImage.getPath());
                            if (tempImage != null) {
                                jSObject.put("format", "jpeg");
                                jSObject.put("exif", (Object) exifData.toJson());
                                jSObject.put("path", tempImage.toString());
                                jSObject.put("webPath", FileUtils.getPortablePath(getContext(), this.bridge.getLocalUrl(), tempImage));
                            } else {
                                jSObject.put("error", UNABLE_TO_PROCESS_IMAGE);
                            }
                            if (openInputStream != null) {
                                try {
                                    openInputStream.close();
                                } catch (IOException e2) {
                                    Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e2);
                                }
                            }
                            return jSObject;
                        } catch (IOException unused) {
                            jSObject.put("error", UNABLE_TO_PROCESS_IMAGE);
                            if (openInputStream != null) {
                                try {
                                    openInputStream.close();
                                } catch (IOException e3) {
                                    Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e3);
                                }
                            }
                            return jSObject;
                        }
                    } catch (OutOfMemoryError unused2) {
                        jSObject.put("error", "Out of memory");
                        if (0 != 0) {
                            inputStream.close();
                        }
                        return jSObject;
                    }
                } catch (FileNotFoundException e4) {
                    jSObject.put("error", "No such image found");
                    Logger.error(getLogTag(), "No such image found", e4);
                    if (0 != 0) {
                        inputStream.close();
                    }
                    return jSObject;
                }
            } catch (IOException e5) {
                Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e5);
                return jSObject;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    inputStream.close();
                } catch (IOException e6) {
                    Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e6);
                }
            }
            throw th;
        }
    }

    @ActivityCallback
    private void processEditedImage(PluginCall pluginCall, ActivityResult activityResult) {
        this.isEdited = true;
        this.settings = getSettings(pluginCall);
        if (activityResult.getResultCode() == 0) {
            Uri uri = this.imagePickedContentUri;
            if (uri != null) {
                processPickedImage(uri, pluginCall);
                return;
            } else {
                processCameraImage(pluginCall, activityResult);
                return;
            }
        }
        processPickedImage(pluginCall, activityResult);
    }

    private Uri saveImage(Uri uri, InputStream inputStream) throws IOException {
        File file;
        if (uri.getScheme().equals("content")) {
            file = getTempFile(uri);
        } else {
            file = new File(uri.getPath());
        }
        try {
            writePhoto(file, inputStream);
        } catch (FileNotFoundException unused) {
            file = getTempFile(uri);
            writePhoto(file, inputStream);
        }
        return Uri.fromFile(file);
    }

    private void writePhoto(File file, InputStream inputStream) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.close();
                return;
            }
        }
    }

    private File getTempFile(Uri uri) {
        String lastPathSegment = Uri.parse(Uri.decode(uri.toString())).getLastPathSegment();
        if (!lastPathSegment.contains(".jpg") && !lastPathSegment.contains(".jpeg")) {
            lastPathSegment = lastPathSegment + "." + new Date().getTime() + ".jpeg";
        }
        return new File(getContext().getCacheDir(), lastPathSegment);
    }

    private void returnResult(PluginCall pluginCall, Bitmap bitmap, Uri uri) {
        String str;
        ExifWrapper exifData = ImageUtils.getExifData(getContext(), bitmap, uri);
        try {
            Bitmap prepareBitmap = prepareBitmap(bitmap, uri, exifData);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            prepareBitmap.compress(Bitmap.CompressFormat.JPEG, this.settings.getQuality(), byteArrayOutputStream);
            if (this.settings.isAllowEditing() && !this.isEdited) {
                editImage(pluginCall, uri, byteArrayOutputStream);
                return;
            }
            if (pluginCall.getBoolean("saveToGallery", false).booleanValue() && ((str = this.imageEditedFileSavePath) != null || this.imageFileSavePath != null)) {
                this.isSaved = true;
                if (str == null) {
                    try {
                        str = this.imageFileSavePath;
                    } catch (FileNotFoundException e) {
                        this.isSaved = false;
                        Logger.error(getLogTag(), IMAGE_GALLERY_SAVE_ERROR, e);
                    } catch (IOException e2) {
                        this.isSaved = false;
                        Logger.error(getLogTag(), IMAGE_GALLERY_SAVE_ERROR, e2);
                    }
                }
                File file = new File(str);
                if (Build.VERSION.SDK_INT >= 29) {
                    ContentResolver contentResolver = getContext().getContentResolver();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_display_name", file.getName());
                    contentValues.put("mime_type", "image/jpeg");
                    contentValues.put("relative_path", Environment.DIRECTORY_DCIM);
                    Uri insert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    if (insert == null) {
                        throw new IOException("Failed to create new MediaStore record.");
                    }
                    OutputStream openOutputStream = contentResolver.openOutputStream(insert);
                    if (openOutputStream == null) {
                        throw new IOException("Failed to open output stream.");
                    }
                    if (!Boolean.valueOf(prepareBitmap.compress(Bitmap.CompressFormat.JPEG, this.settings.getQuality(), openOutputStream)).booleanValue()) {
                        this.isSaved = false;
                    }
                } else if (MediaStore.Images.Media.insertImage(getContext().getContentResolver(), str, file.getName(), "") == null) {
                    this.isSaved = false;
                }
            }
            if (this.settings.getResultType() == CameraResultType.BASE64) {
                returnBase64(pluginCall, exifData, byteArrayOutputStream);
            } else if (this.settings.getResultType() == CameraResultType.URI) {
                returnFileURI(pluginCall, exifData, prepareBitmap, uri, byteArrayOutputStream);
            } else if (this.settings.getResultType() == CameraResultType.DATAURL) {
                returnDataUrl(pluginCall, exifData, byteArrayOutputStream);
            } else {
                pluginCall.reject(INVALID_RESULT_TYPE_ERROR);
            }
            if (this.settings.getResultType() != CameraResultType.URI) {
                deleteImageFile();
            }
            this.imageFileSavePath = null;
            this.imageFileUri = null;
            this.imagePickedContentUri = null;
            this.imageEditedFileSavePath = null;
        } catch (IOException unused) {
            pluginCall.reject(UNABLE_TO_PROCESS_IMAGE);
        }
    }

    private void deleteImageFile() {
        if (this.imageFileSavePath == null || this.settings.isSaveToGallery()) {
            return;
        }
        File file = new File(this.imageFileSavePath);
        if (file.exists()) {
            file.delete();
        }
    }

    private void returnFileURI(PluginCall pluginCall, ExifWrapper exifWrapper, Bitmap bitmap, Uri uri, ByteArrayOutputStream byteArrayOutputStream) {
        Uri tempImage = getTempImage(uri, byteArrayOutputStream);
        exifWrapper.copyExif(tempImage.getPath());
        if (tempImage != null) {
            JSObject jSObject = new JSObject();
            jSObject.put("format", "jpeg");
            jSObject.put("exif", (Object) exifWrapper.toJson());
            jSObject.put("path", tempImage.toString());
            jSObject.put("webPath", FileUtils.getPortablePath(getContext(), this.bridge.getLocalUrl(), tempImage));
            jSObject.put("saved", this.isSaved);
            pluginCall.resolve(jSObject);
            return;
        }
        pluginCall.reject(UNABLE_TO_PROCESS_IMAGE);
    }

    private Uri getTempImage(Uri uri, ByteArrayOutputStream byteArrayOutputStream) {
        ByteArrayInputStream byteArrayInputStream;
        Uri uri2 = null;
        uri2 = null;
        uri2 = null;
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            try {
                byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                try {
                    uri2 = saveImage(uri, byteArrayInputStream);
                    byteArrayInputStream.close();
                } catch (IOException unused) {
                    if (byteArrayInputStream != null) {
                        byteArrayInputStream.close();
                    }
                    return uri2;
                } catch (Throwable th) {
                    th = th;
                    byteArrayInputStream2 = byteArrayInputStream;
                    if (byteArrayInputStream2 != null) {
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e) {
                            Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e);
                        }
                    }
                    throw th;
                }
            } catch (IOException unused2) {
                byteArrayInputStream = null;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            Logger.error(getLogTag(), UNABLE_TO_PROCESS_IMAGE, e2);
        }
        return uri2;
    }

    private Bitmap prepareBitmap(Bitmap bitmap, Uri uri, ExifWrapper exifWrapper) throws IOException {
        if (this.settings.isShouldCorrectOrientation()) {
            bitmap = replaceBitmap(bitmap, ImageUtils.correctOrientation(getContext(), bitmap, uri, exifWrapper));
        }
        return this.settings.isShouldResize() ? replaceBitmap(bitmap, ImageUtils.resize(bitmap, this.settings.getWidth(), this.settings.getHeight())) : bitmap;
    }

    private Bitmap replaceBitmap(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap != bitmap2) {
            bitmap.recycle();
        }
        return bitmap2;
    }

    private void returnDataUrl(PluginCall pluginCall, ExifWrapper exifWrapper, ByteArrayOutputStream byteArrayOutputStream) {
        String encodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
        JSObject jSObject = new JSObject();
        jSObject.put("format", "jpeg");
        jSObject.put("dataUrl", "data:image/jpeg;base64," + encodeToString);
        jSObject.put("exif", (Object) exifWrapper.toJson());
        pluginCall.resolve(jSObject);
    }

    private void returnBase64(PluginCall pluginCall, ExifWrapper exifWrapper, ByteArrayOutputStream byteArrayOutputStream) {
        String encodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
        JSObject jSObject = new JSObject();
        jSObject.put("format", "jpeg");
        jSObject.put("base64String", encodeToString);
        jSObject.put("exif", (Object) exifWrapper.toJson());
        pluginCall.resolve(jSObject);
    }

    @Override // com.getcapacitor.Plugin
    @PluginMethod
    public void requestPermissions(PluginCall pluginCall) {
        List list;
        if (isPermissionDeclared(CAMERA)) {
            super.requestPermissions(pluginCall);
            return;
        }
        JSArray array = pluginCall.getArray("permissions");
        if (array != null) {
            try {
                list = array.toList();
            } catch (JSONException unused) {
            }
            if (list == null && list.size() == 1 && list.contains(CAMERA)) {
                checkPermissions(pluginCall);
                return;
            } else {
                requestPermissionForAlias(PHOTOS, pluginCall, "checkPermissions");
            }
        }
        list = null;
        if (list == null) {
        }
        requestPermissionForAlias(PHOTOS, pluginCall, "checkPermissions");
    }

    @Override // com.getcapacitor.Plugin
    public Map<String, PermissionState> getPermissionStates() {
        Map<String, PermissionState> permissionStates = super.getPermissionStates();
        if (!isPermissionDeclared(CAMERA)) {
            permissionStates.put(CAMERA, PermissionState.GRANTED);
        }
        if (Build.VERSION.SDK_INT >= 30) {
            String str = Build.VERSION.SDK_INT >= 33 ? MEDIA : READ_EXTERNAL_STORAGE;
            if (permissionStates.containsKey(str)) {
                permissionStates.put(PHOTOS, permissionStates.get(str));
            }
        }
        return permissionStates;
    }

    private void editImage(PluginCall pluginCall, Uri uri, ByteArrayOutputStream byteArrayOutputStream) {
        try {
            Intent createEditIntent = createEditIntent(getTempImage(uri, byteArrayOutputStream));
            if (createEditIntent != null) {
                startActivityForResult(pluginCall, createEditIntent, "processEditedImage");
            } else {
                pluginCall.reject(IMAGE_EDIT_ERROR);
            }
        } catch (Exception e) {
            pluginCall.reject(IMAGE_EDIT_ERROR, e);
        }
    }

    private Intent createEditIntent(Uri uri) {
        List<ResolveInfo> legacyQueryIntentActivities;
        try {
            File file = new File(uri.getPath());
            Uri uriForFile = FileProvider.getUriForFile(getActivity(), getContext().getPackageName() + ".fileprovider", file);
            Intent intent = new Intent("android.intent.action.EDIT");
            intent.setDataAndType(uriForFile, "image/*");
            this.imageEditedFileSavePath = file.getAbsolutePath();
            intent.addFlags(3);
            intent.putExtra("output", uriForFile);
            if (Build.VERSION.SDK_INT >= 33) {
                legacyQueryIntentActivities = getContext().getPackageManager().queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(65536L));
            } else {
                legacyQueryIntentActivities = legacyQueryIntentActivities(intent);
            }
            Iterator<ResolveInfo> it = legacyQueryIntentActivities.iterator();
            while (it.hasNext()) {
                getContext().grantUriPermission(it.next().activityInfo.packageName, uriForFile, 3);
            }
            return intent;
        } catch (Exception unused) {
            return null;
        }
    }

    private List<ResolveInfo> legacyQueryIntentActivities(Intent intent) {
        return getContext().getPackageManager().queryIntentActivities(intent, 65536);
    }

    @Override // com.getcapacitor.Plugin
    protected Bundle saveInstanceState() {
        Bundle saveInstanceState = super.saveInstanceState();
        if (saveInstanceState != null) {
            saveInstanceState.putString("cameraImageFileSavePath", this.imageFileSavePath);
        }
        return saveInstanceState;
    }

    @Override // com.getcapacitor.Plugin
    protected void restoreState(Bundle bundle) {
        String string = bundle.getString("cameraImageFileSavePath");
        if (string != null) {
            this.imageFileSavePath = string;
        }
    }
}