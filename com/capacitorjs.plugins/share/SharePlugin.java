package com.capacitorjs.plugins.share;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.webkit.MimeTypeMap;
import androidx.activity.result.ActivityResult;
import androidx.core.content.FileProvider;
import androidx.webkit.internal.AssetHelper;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@CapacitorPlugin(name = "Share")
/* loaded from: classes.dex */
public class SharePlugin extends Plugin {
    private BroadcastReceiver broadcastReceiver;
    private ComponentName chosenComponent;
    private boolean stopped = false;
    private boolean isPresenting = false;

    @Override // com.getcapacitor.Plugin
    public void load() {
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.capacitorjs.plugins.share.SharePlugin.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (Build.VERSION.SDK_INT >= 33) {
                    SharePlugin.this.chosenComponent = (ComponentName) intent.getParcelableExtra("android.intent.extra.CHOSEN_COMPONENT", ComponentName.class);
                } else {
                    SharePlugin sharePlugin = SharePlugin.this;
                    sharePlugin.chosenComponent = sharePlugin.getParcelableExtraLegacy(intent, "android.intent.extra.CHOSEN_COMPONENT");
                }
            }
        };
        getActivity().registerReceiver(this.broadcastReceiver, new IntentFilter("android.intent.extra.CHOSEN_COMPONENT"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ComponentName getParcelableExtraLegacy(Intent intent, String str) {
        return (ComponentName) intent.getParcelableExtra(str);
    }

    @ActivityCallback
    private void activityResult(PluginCall pluginCall, ActivityResult activityResult) {
        if (activityResult.getResultCode() == 0 && !this.stopped) {
            pluginCall.reject("Share canceled");
        } else {
            JSObject jSObject = new JSObject();
            ComponentName componentName = this.chosenComponent;
            jSObject.put("activityType", componentName != null ? componentName.getPackageName() : "");
            pluginCall.resolve(jSObject);
        }
        this.isPresenting = false;
    }

    @PluginMethod
    public void canShare(PluginCall pluginCall) {
        JSObject jSObject = new JSObject();
        jSObject.put("value", true);
        pluginCall.resolve(jSObject);
    }

    @PluginMethod
    public void share(PluginCall pluginCall) {
        if (!this.isPresenting) {
            String string = pluginCall.getString("title", "");
            String string2 = pluginCall.getString("text");
            String string3 = pluginCall.getString("url");
            JSArray array = pluginCall.getArray("files");
            String string4 = pluginCall.getString("dialogTitle", "Share");
            if (string2 == null && string3 == null && (array == null || array.length() == 0)) {
                pluginCall.reject("Must provide a URL or Message or files");
                return;
            }
            if (string3 != null && !isFileUrl(string3) && !isHttpUrl(string3)) {
                pluginCall.reject("Unsupported url");
                return;
            }
            Intent intent = new Intent((array == null || array.length() <= 1) ? "android.intent.action.SEND" : "android.intent.action.SEND_MULTIPLE");
            if (string2 != null) {
                if (string3 != null && isHttpUrl(string3)) {
                    string2 = string2 + " " + string3;
                }
                intent.putExtra("android.intent.extra.TEXT", string2);
                intent.setTypeAndNormalize(AssetHelper.DEFAULT_MIME_TYPE);
            }
            if (string3 != null && isHttpUrl(string3) && string2 == null) {
                intent.putExtra("android.intent.extra.TEXT", string3);
                intent.setTypeAndNormalize(AssetHelper.DEFAULT_MIME_TYPE);
            } else if (string3 != null && isFileUrl(string3)) {
                JSArray jSArray = new JSArray();
                jSArray.put(string3);
                shareFiles(jSArray, intent, pluginCall);
            }
            if (string != null) {
                intent.putExtra("android.intent.extra.SUBJECT", string);
            }
            if (array != null && array.length() != 0) {
                shareFiles(array, intent, pluginCall);
            }
            Intent createChooser = Intent.createChooser(intent, string4, PendingIntent.getBroadcast(getContext(), 0, new Intent("android.intent.extra.CHOSEN_COMPONENT"), Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728).getIntentSender());
            this.chosenComponent = null;
            createChooser.addCategory("android.intent.category.DEFAULT");
            this.stopped = false;
            this.isPresenting = true;
            startActivityForResult(pluginCall, createChooser, "activityResult");
            return;
        }
        pluginCall.reject("Can't share while sharing is in progress");
    }

    private void shareFiles(JSArray jSArray, Intent intent, PluginCall pluginCall) {
        ArrayList arrayList = new ArrayList();
        try {
            List list = jSArray.toList();
            for (int i = 0; i < list.size(); i++) {
                String str = (String) list.get(i);
                if (isFileUrl(str)) {
                    String mimeType = getMimeType(str);
                    if (mimeType == null || list.size() > 1) {
                        mimeType = "*/*";
                    }
                    intent.setType(mimeType);
                    arrayList.add(FileProvider.getUriForFile(getActivity(), getContext().getPackageName() + ".fileprovider", new File(Uri.parse(str).getPath())));
                } else {
                    pluginCall.reject("only file urls are supported");
                    return;
                }
            }
            if (arrayList.size() > 1) {
                intent.putExtra("android.intent.extra.STREAM", arrayList);
            } else if (arrayList.size() == 1) {
                if (Build.VERSION.SDK_INT >= 29) {
                    intent.setClipData(ClipData.newRawUri("", (Uri) arrayList.get(0)));
                }
                intent.putExtra("android.intent.extra.STREAM", (Parcelable) arrayList.get(0));
            }
            intent.setFlags(1);
        } catch (Exception e) {
            pluginCall.reject(e.getLocalizedMessage());
        }
    }

    @Override // com.getcapacitor.Plugin
    protected void handleOnDestroy() {
        if (this.broadcastReceiver != null) {
            getActivity().unregisterReceiver(this.broadcastReceiver);
        }
    }

    @Override // com.getcapacitor.Plugin
    protected void handleOnStop() {
        super.handleOnStop();
        this.stopped = true;
    }

    private String getMimeType(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        if (fileExtensionFromUrl != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
        }
        return null;
    }

    private boolean isFileUrl(String str) {
        return str.startsWith("file:");
    }

    private boolean isHttpUrl(String str) {
        return str.startsWith("http");
    }
}