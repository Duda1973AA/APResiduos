package com.capacitorjs.plugins.camera;

/* loaded from: classes.dex */
public enum CameraSource {
    PROMPT("PROMPT"),
    CAMERA("CAMERA"),
    PHOTOS("PHOTOS");

    private String source;

    CameraSource(String str) {
        this.source = str;
    }

    public String getSource() {
        return this.source;
    }
}