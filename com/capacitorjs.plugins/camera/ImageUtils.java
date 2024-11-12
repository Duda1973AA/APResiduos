package com.capacitorjs.plugins.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class ImageUtils {
    public static Bitmap resize(Bitmap bitmap, int i, int i2) {
        return resizePreservingAspectRatio(bitmap, i, i2);
    }

    private static Bitmap resizePreservingAspectRatio(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (i2 == 0) {
            i2 = height;
        }
        if (i == 0) {
            i = width;
        }
        float min = Math.min(width, i);
        float f = (height * min) / width;
        float f2 = i2;
        if (f > f2) {
            min = (width * i2) / height;
            f = f2;
        }
        return Bitmap.createScaledBitmap(bitmap, Math.round(min), Math.round(f), false);
    }

    private static Bitmap transform(Bitmap bitmap, Matrix matrix) {
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap correctOrientation(Context context, Bitmap bitmap, Uri uri, ExifWrapper exifWrapper) throws IOException {
        int orientation = getOrientation(context, uri);
        if (orientation == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(orientation);
        exifWrapper.resetOrientation();
        return transform(bitmap, matrix);
    }

    private static int getOrientation(Context context, Uri uri) throws IOException {
        InputStream openInputStream = context.getContentResolver().openInputStream(uri);
        try {
            int attributeInt = new ExifInterface(openInputStream).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            int i = attributeInt == 6 ? 90 : attributeInt == 3 ? 180 : attributeInt == 8 ? 270 : 0;
            if (openInputStream != null) {
                openInputStream.close();
            }
            return i;
        } catch (Throwable th) {
            if (openInputStream != null) {
                try {
                    openInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0035 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.capacitorjs.plugins.camera.ExifWrapper getExifData(android.content.Context r2, android.graphics.Bitmap r3, android.net.Uri r4) {
        /*
            r3 = 0
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch: java.lang.Throwable -> L1b java.io.IOException -> L20
            java.io.InputStream r2 = r2.openInputStream(r4)     // Catch: java.lang.Throwable -> L1b java.io.IOException -> L20
            androidx.exifinterface.media.ExifInterface r4 = new androidx.exifinterface.media.ExifInterface     // Catch: java.io.IOException -> L19 java.lang.Throwable -> L32
            r4.<init>(r2)     // Catch: java.io.IOException -> L19 java.lang.Throwable -> L32
            com.capacitorjs.plugins.camera.ExifWrapper r0 = new com.capacitorjs.plugins.camera.ExifWrapper     // Catch: java.io.IOException -> L19 java.lang.Throwable -> L32
            r0.<init>(r4)     // Catch: java.io.IOException -> L19 java.lang.Throwable -> L32
            if (r2 == 0) goto L18
            r2.close()     // Catch: java.io.IOException -> L18
        L18:
            return r0
        L19:
            r4 = move-exception
            goto L22
        L1b:
            r2 = move-exception
            r1 = r3
            r3 = r2
            r2 = r1
            goto L33
        L20:
            r4 = move-exception
            r2 = r3
        L22:
            java.lang.String r0 = "Error loading exif data from image"
            com.getcapacitor.Logger.error(r0, r4)     // Catch: java.lang.Throwable -> L32
            if (r2 == 0) goto L2c
            r2.close()     // Catch: java.io.IOException -> L2c
        L2c:
            com.capacitorjs.plugins.camera.ExifWrapper r2 = new com.capacitorjs.plugins.camera.ExifWrapper
            r2.<init>(r3)
            return r2
        L32:
            r3 = move-exception
        L33:
            if (r2 == 0) goto L38
            r2.close()     // Catch: java.io.IOException -> L38
        L38:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.capacitorjs.plugins.camera.ImageUtils.getExifData(android.content.Context, android.graphics.Bitmap, android.net.Uri):com.capacitorjs.plugins.camera.ExifWrapper");
    }
}