package com.capacitorjs.plugins.keyboard;

import android.os.Handler;
import android.os.Looper;
import com.capacitorjs.plugins.keyboard.Keyboard;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Keyboard")
/* loaded from: classes.dex */
public class KeyboardPlugin extends Plugin {
    private Keyboard implementation;

    @Override // com.getcapacitor.Plugin
    public void load() {
        execute(new Runnable() { // from class: com.capacitorjs.plugins.keyboard.KeyboardPlugin$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardPlugin.this.lambda$load$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$load$0() {
        Keyboard keyboard = new Keyboard(getActivity(), getConfig().getBoolean("resizeOnFullScreen", false));
        this.implementation = keyboard;
        keyboard.setKeyboardEventListener(new Keyboard.KeyboardEventListener() { // from class: com.capacitorjs.plugins.keyboard.KeyboardPlugin$$ExternalSyntheticLambda2
            @Override // com.capacitorjs.plugins.keyboard.Keyboard.KeyboardEventListener
            public final void onKeyboardEvent(String str, int i) {
                KeyboardPlugin.this.onKeyboardEvent(str, i);
            }
        });
    }

    @PluginMethod
    public void show(final PluginCall pluginCall) {
        execute(new Runnable() { // from class: com.capacitorjs.plugins.keyboard.KeyboardPlugin$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardPlugin.this.lambda$show$2(pluginCall);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$2(final PluginCall pluginCall) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.capacitorjs.plugins.keyboard.KeyboardPlugin$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardPlugin.this.lambda$show$1(pluginCall);
            }
        }, 350L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$1(PluginCall pluginCall) {
        this.implementation.show();
        pluginCall.resolve();
    }

    @PluginMethod
    public void hide(final PluginCall pluginCall) {
        execute(new Runnable() { // from class: com.capacitorjs.plugins.keyboard.KeyboardPlugin$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardPlugin.this.lambda$hide$3(pluginCall);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hide$3(PluginCall pluginCall) {
        if (!this.implementation.hide()) {
            pluginCall.reject("Can't close keyboard, not currently focused");
        } else {
            pluginCall.resolve();
        }
    }

    @PluginMethod
    public void setAccessoryBarVisible(PluginCall pluginCall) {
        pluginCall.unimplemented();
    }

    @PluginMethod
    public void setStyle(PluginCall pluginCall) {
        pluginCall.unimplemented();
    }

    @PluginMethod
    public void setResizeMode(PluginCall pluginCall) {
        pluginCall.unimplemented();
    }

    @PluginMethod
    public void getResizeMode(PluginCall pluginCall) {
        pluginCall.unimplemented();
    }

    @PluginMethod
    public void setScroll(PluginCall pluginCall) {
        pluginCall.unimplemented();
    }

    void onKeyboardEvent(String str, int i) {
        JSObject jSObject;
        jSObject = new JSObject();
        str.hashCode();
        switch (str) {
            case "keyboardDidHide":
            case "keyboardWillHide":
                this.bridge.triggerWindowJSEvent(str);
                notifyListeners(str, jSObject);
                break;
            case "keyboardDidShow":
            case "keyboardWillShow":
                this.bridge.triggerWindowJSEvent(str, "{ 'keyboardHeight': " + i + " }");
                jSObject.put("keyboardHeight", i);
                notifyListeners(str, jSObject);
                break;
        }
    }

    @Override // com.getcapacitor.Plugin
    protected void handleOnDestroy() {
        this.implementation.setKeyboardEventListener(null);
    }
}