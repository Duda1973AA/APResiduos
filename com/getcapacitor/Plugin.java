package com.getcapacitor;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import com.getcapacitor.util.PermissionHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONException;

/* loaded from: classes.dex */
public class Plugin {
    private static final String BUNDLE_PERSISTED_OPTIONS_JSON_KEY = "_json";
    protected Bridge bridge;
    protected PluginHandle handle;
    private String lastPluginCallId;

    @Deprecated
    protected PluginCall savedLastCall;
    private final Map<String, ActivityResultLauncher<Intent>> activityLaunchers = new HashMap();
    private final Map<String, ActivityResultLauncher<String[]>> permissionLaunchers = new HashMap();
    private final Map<String, List<PluginCall>> eventListeners = new HashMap();
    private final Map<String, List<JSObject>> retainedEventArguments = new HashMap();

    @Deprecated
    protected void handleOnActivityResult(int i, int i2, Intent intent) {
    }

    protected void handleOnConfigurationChanged(Configuration configuration) {
    }

    protected void handleOnDestroy() {
    }

    protected void handleOnNewIntent(Intent intent) {
    }

    protected void handleOnPause() {
    }

    protected void handleOnRestart() {
    }

    protected void handleOnResume() {
    }

    protected void handleOnStart() {
    }

    protected void handleOnStop() {
    }

    public void load() {
    }

    protected void restoreState(Bundle bundle) {
    }

    public Boolean shouldOverrideLoad(Uri uri) {
        return null;
    }

    void initializeActivityLaunchers() {
        ArrayList<Method> arrayList = new ArrayList();
        for (Class<?> cls = getClass(); !cls.getName().equals(Object.class.getName()); cls = cls.getSuperclass()) {
            arrayList.addAll(Arrays.asList(cls.getDeclaredMethods()));
        }
        for (Method method : arrayList) {
            if (method.isAnnotationPresent(ActivityCallback.class)) {
                this.activityLaunchers.put(method.getName(), this.bridge.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.getcapacitor.Plugin$$ExternalSyntheticLambda0
                    public final /* synthetic */ Method f$1;

                    public /* synthetic */ Plugin$$ExternalSyntheticLambda0(Method method2) {
                        r2 = method2;
                    }

                    @Override // androidx.activity.result.ActivityResultCallback
                    public final void onActivityResult(Object obj) {
                        Plugin.this.lambda$initializeActivityLaunchers$0(r2, (ActivityResult) obj);
                    }
                }));
            } else if (method2.isAnnotationPresent(PermissionCallback.class)) {
                this.permissionLaunchers.put(method2.getName(), this.bridge.registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.getcapacitor.Plugin$$ExternalSyntheticLambda1
                    public final /* synthetic */ Method f$1;

                    public /* synthetic */ Plugin$$ExternalSyntheticLambda1(Method method2) {
                        r2 = method2;
                    }

                    @Override // androidx.activity.result.ActivityResultCallback
                    public final void onActivityResult(Object obj) {
                        Plugin.this.lambda$initializeActivityLaunchers$1(r2, (Map) obj);
                    }
                }));
            }
        }
    }

    /* renamed from: triggerPermissionCallback */
    public void lambda$initializeActivityLaunchers$1(Method method, Map<String, Boolean> map) {
        PluginCall permissionCall = this.bridge.getPermissionCall(this.handle.getId());
        if (this.bridge.validatePermissions(this, permissionCall, map)) {
            try {
                method.setAccessible(true);
                method.invoke(this, permissionCall);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: triggerActivityCallback */
    public void lambda$initializeActivityLaunchers$0(Method method, ActivityResult activityResult) {
        PluginCall savedCall = this.bridge.getSavedCall(this.lastPluginCallId);
        if (savedCall == null) {
            savedCall = this.bridge.getPluginCallForLastActivity();
        }
        try {
            method.setAccessible(true);
            method.invoke(this, savedCall, activityResult);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void startActivityForResult(PluginCall pluginCall, Intent intent, String str) {
        ActivityResultLauncher<Intent> activityLauncherOrReject = getActivityLauncherOrReject(pluginCall, str);
        if (activityLauncherOrReject == null) {
            return;
        }
        this.bridge.setPluginCallForLastActivity(pluginCall);
        this.lastPluginCallId = pluginCall.getCallbackId();
        this.bridge.saveCall(pluginCall);
        activityLauncherOrReject.launch(intent);
    }

    private void permissionActivityResult(PluginCall pluginCall, String[] strArr, String str) {
        ActivityResultLauncher<String[]> permissionLauncherOrReject = getPermissionLauncherOrReject(pluginCall, str);
        if (permissionLauncherOrReject == null) {
            return;
        }
        this.bridge.savePermissionCall(pluginCall);
        permissionLauncherOrReject.launch(strArr);
    }

    public Context getContext() {
        return this.bridge.getContext();
    }

    public AppCompatActivity getActivity() {
        return this.bridge.getActivity();
    }

    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }

    public Bridge getBridge() {
        return this.bridge;
    }

    public void setPluginHandle(PluginHandle pluginHandle) {
        this.handle = pluginHandle;
    }

    public PluginHandle getPluginHandle() {
        return this.handle;
    }

    public String getAppId() {
        return getContext().getPackageName();
    }

    @Deprecated
    public void saveCall(PluginCall pluginCall) {
        this.savedLastCall = pluginCall;
    }

    @Deprecated
    public void freeSavedCall() {
        this.savedLastCall.release(this.bridge);
        this.savedLastCall = null;
    }

    @Deprecated
    public PluginCall getSavedCall() {
        return this.savedLastCall;
    }

    public PluginConfig getConfig() {
        return this.bridge.getConfig().getPluginConfiguration(this.handle.getId());
    }

    @Deprecated
    public Object getConfigValue(String str) {
        try {
            return getConfig().getConfigJSON().get(str);
        } catch (JSONException unused) {
            return null;
        }
    }

    @Deprecated
    public boolean hasDefinedPermissions(String[] strArr) {
        for (String str : strArr) {
            if (!PermissionHelper.hasDefinedPermission(getContext(), str)) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public boolean hasDefinedRequiredPermissions() {
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        if (pluginAnnotation == null) {
            return hasDefinedPermissions(this.handle.getLegacyPluginAnnotation().permissions());
        }
        for (Permission permission : pluginAnnotation.permissions()) {
            for (String str : permission.strings()) {
                if (!PermissionHelper.hasDefinedPermission(getContext(), str)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPermissionDeclared(String str) {
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        if (pluginAnnotation != null) {
            for (Permission permission : pluginAnnotation.permissions()) {
                if (str.equalsIgnoreCase(permission.alias())) {
                    boolean z = true;
                    for (String str2 : permission.strings()) {
                        z = z && PermissionHelper.hasDefinedPermission(getContext(), str2);
                    }
                    return z;
                }
            }
        }
        Logger.error(String.format("isPermissionDeclared: No alias defined for %s or missing @CapacitorPlugin annotation.", str));
        return false;
    }

    @Deprecated
    public boolean hasPermission(String str) {
        return ActivityCompat.checkSelfPermission(getContext(), str) == 0;
    }

    @Deprecated
    public boolean hasRequiredPermissions() {
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        if (pluginAnnotation == null) {
            for (String str : this.handle.getLegacyPluginAnnotation().permissions()) {
                if (ActivityCompat.checkSelfPermission(getContext(), str) != 0) {
                    return false;
                }
            }
            return true;
        }
        for (Permission permission : pluginAnnotation.permissions()) {
            for (String str2 : permission.strings()) {
                if (ActivityCompat.checkSelfPermission(getContext(), str2) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void requestAllPermissions(PluginCall pluginCall, String str) {
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        if (pluginAnnotation != null) {
            HashSet hashSet = new HashSet();
            for (Permission permission : pluginAnnotation.permissions()) {
                hashSet.addAll(Arrays.asList(permission.strings()));
            }
            permissionActivityResult(pluginCall, (String[]) hashSet.toArray(new String[0]), str);
        }
    }

    protected void requestPermissionForAlias(String str, PluginCall pluginCall, String str2) {
        requestPermissionForAliases(new String[]{str}, pluginCall, str2);
    }

    protected void requestPermissionForAliases(String[] strArr, PluginCall pluginCall, String str) {
        if (strArr.length == 0) {
            Logger.error("No permission alias was provided");
            return;
        }
        String[] permissionStringsForAliases = getPermissionStringsForAliases(strArr);
        if (permissionStringsForAliases.length > 0) {
            permissionActivityResult(pluginCall, permissionStringsForAliases, str);
        }
    }

    private String[] getPermissionStringsForAliases(String[] strArr) {
        CapacitorPlugin pluginAnnotation = this.handle.getPluginAnnotation();
        HashSet hashSet = new HashSet();
        for (Permission permission : pluginAnnotation.permissions()) {
            if (Arrays.asList(strArr).contains(permission.alias())) {
                hashSet.addAll(Arrays.asList(permission.strings()));
            }
        }
        return (String[]) hashSet.toArray(new String[0]);
    }

    private ActivityResultLauncher<Intent> getActivityLauncherOrReject(PluginCall pluginCall, String str) {
        ActivityResultLauncher<Intent> activityResultLauncher = this.activityLaunchers.get(str);
        if (activityResultLauncher != null) {
            return activityResultLauncher;
        }
        String format = String.format(Locale.US, "There is no ActivityCallback method registered for the name: %s. Please define a callback method annotated with @ActivityCallback that receives arguments: (PluginCall, ActivityResult)", str);
        Logger.error(format);
        pluginCall.reject(format);
        return null;
    }

    private ActivityResultLauncher<String[]> getPermissionLauncherOrReject(PluginCall pluginCall, String str) {
        ActivityResultLauncher<String[]> activityResultLauncher = this.permissionLaunchers.get(str);
        if (activityResultLauncher != null) {
            return activityResultLauncher;
        }
        String format = String.format(Locale.US, "There is no PermissionCallback method registered for the name: %s. Please define a callback method annotated with @PermissionCallback that receives arguments: (PluginCall)", str);
        Logger.error(format);
        pluginCall.reject(format);
        return null;
    }

    @Deprecated
    public void pluginRequestAllPermissions() {
        NativePlugin legacyPluginAnnotation = this.handle.getLegacyPluginAnnotation();
        ActivityCompat.requestPermissions(getActivity(), legacyPluginAnnotation.permissions(), legacyPluginAnnotation.permissionRequestCode());
    }

    @Deprecated
    public void pluginRequestPermission(String str, int i) {
        ActivityCompat.requestPermissions(getActivity(), new String[]{str}, i);
    }

    @Deprecated
    public void pluginRequestPermissions(String[] strArr, int i) {
        ActivityCompat.requestPermissions(getActivity(), strArr, i);
    }

    public PermissionState getPermissionState(String str) {
        return getPermissionStates().get(str);
    }

    public Map<String, PermissionState> getPermissionStates() {
        return this.bridge.getPermissionStates(this);
    }

    private void addEventListener(String str, PluginCall pluginCall) {
        List<PluginCall> list = this.eventListeners.get(str);
        if (list == null || list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            this.eventListeners.put(str, arrayList);
            arrayList.add(pluginCall);
            sendRetainedArgumentsForEvent(str);
            return;
        }
        list.add(pluginCall);
    }

    private void removeEventListener(String str, PluginCall pluginCall) {
        List<PluginCall> list = this.eventListeners.get(str);
        if (list == null) {
            return;
        }
        list.remove(pluginCall);
    }

    public void notifyListeners(String str, JSObject jSObject, boolean z) {
        Logger.verbose(getLogTag(), "Notifying listeners for event " + str);
        List<PluginCall> list = this.eventListeners.get(str);
        if (list == null || list.isEmpty()) {
            Logger.debug(getLogTag(), "No listeners found for event " + str);
            if (z) {
                List<JSObject> list2 = this.retainedEventArguments.get(str);
                if (list2 == null) {
                    list2 = new ArrayList<>();
                }
                list2.add(jSObject);
                this.retainedEventArguments.put(str, list2);
                return;
            }
            return;
        }
        Iterator it = new CopyOnWriteArrayList(list).iterator();
        while (it.hasNext()) {
            ((PluginCall) it.next()).resolve(jSObject);
        }
    }

    public void notifyListeners(String str, JSObject jSObject) {
        notifyListeners(str, jSObject, false);
    }

    public boolean hasListeners(String str) {
        if (this.eventListeners.get(str) == null) {
            return false;
        }
        return !r2.isEmpty();
    }

    private void sendRetainedArgumentsForEvent(String str) {
        List<JSObject> list = this.retainedEventArguments.get(str);
        if (list == null) {
            return;
        }
        this.retainedEventArguments.remove(str);
        Iterator<JSObject> it = list.iterator();
        while (it.hasNext()) {
            notifyListeners(str, it.next());
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void addListener(PluginCall pluginCall) {
        String string = pluginCall.getString("eventName");
        pluginCall.setKeepAlive(true);
        addEventListener(string, pluginCall);
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void removeListener(PluginCall pluginCall) {
        String string = pluginCall.getString("eventName");
        PluginCall savedCall = this.bridge.getSavedCall(pluginCall.getString("callbackId"));
        if (savedCall != null) {
            removeEventListener(string, savedCall);
            this.bridge.releaseCall(savedCall);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_PROMISE)
    public void removeAllListeners(PluginCall pluginCall) {
        this.eventListeners.clear();
        pluginCall.resolve();
    }

    @PluginMethod
    @PermissionCallback
    public void checkPermissions(PluginCall pluginCall) {
        Map<String, PermissionState> permissionStates = getPermissionStates();
        if (permissionStates.size() == 0) {
            pluginCall.resolve();
            return;
        }
        JSObject jSObject = new JSObject();
        for (Map.Entry<String, PermissionState> entry : permissionStates.entrySet()) {
            jSObject.put(entry.getKey(), (Object) entry.getValue());
        }
        pluginCall.resolve(jSObject);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x006d  */
    @com.getcapacitor.PluginMethod
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void requestPermissions(com.getcapacitor.PluginCall r11) {
        /*
            r10 = this;
            com.getcapacitor.PluginHandle r0 = r10.handle
            com.getcapacitor.annotation.CapacitorPlugin r0 = r0.getPluginAnnotation()
            if (r0 != 0) goto Ld
            r10.handleLegacyPermission(r11)
            goto Le7
        Ld:
            java.util.HashSet r1 = new java.util.HashSet
            r1.<init>()
            java.lang.String r2 = "permissions"
            com.getcapacitor.JSArray r2 = r11.getArray(r2)
            r3 = 0
            if (r2 == 0) goto L21
            java.util.List r2 = r2.toList()     // Catch: org.json.JSONException -> L20
            goto L22
        L20:
        L21:
            r2 = r3
        L22:
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            r5 = 0
            if (r2 == 0) goto L65
            boolean r6 = r2.isEmpty()
            if (r6 == 0) goto L31
            goto L65
        L31:
            com.getcapacitor.annotation.Permission[] r0 = r0.permissions()
            int r6 = r0.length
            r7 = 0
        L37:
            if (r7 >= r6) goto L4f
            r8 = r0[r7]
            java.lang.String r9 = r8.alias()
            boolean r9 = r2.contains(r9)
            if (r9 == 0) goto L4c
            java.lang.String r8 = r8.alias()
            r4.add(r8)
        L4c:
            int r7 = r7 + 1
            goto L37
        L4f:
            boolean r0 = r4.isEmpty()
            if (r0 == 0) goto L5b
            java.lang.String r0 = "No valid permission alias was requested of this plugin."
            r11.reject(r0)
            goto Lb0
        L5b:
            java.lang.String[] r0 = new java.lang.String[r5]
            java.lang.Object[] r0 = r4.toArray(r0)
            r3 = r0
            java.lang.String[] r3 = (java.lang.String[]) r3
            goto Lb0
        L65:
            com.getcapacitor.annotation.Permission[] r0 = r0.permissions()
            int r2 = r0.length
            r3 = 0
        L6b:
            if (r3 >= r2) goto La7
            r6 = r0[r3]
            java.lang.String[] r7 = r6.strings()
            int r7 = r7.length
            if (r7 == 0) goto L93
            java.lang.String[] r7 = r6.strings()
            int r7 = r7.length
            r8 = 1
            if (r7 != r8) goto L8b
            java.lang.String[] r7 = r6.strings()
            r7 = r7[r5]
            boolean r7 = r7.isEmpty()
            if (r7 == 0) goto L8b
            goto L93
        L8b:
            java.lang.String r6 = r6.alias()
            r4.add(r6)
            goto La4
        L93:
            java.lang.String r7 = r6.alias()
            boolean r7 = r7.isEmpty()
            if (r7 != 0) goto La4
            java.lang.String r6 = r6.alias()
            r1.add(r6)
        La4:
            int r3 = r3 + 1
            goto L6b
        La7:
            java.lang.String[] r0 = new java.lang.String[r5]
            java.lang.Object[] r0 = r4.toArray(r0)
            r3 = r0
            java.lang.String[] r3 = (java.lang.String[]) r3
        Lb0:
            if (r3 == 0) goto Lbb
            int r0 = r3.length
            if (r0 <= 0) goto Lbb
            java.lang.String r0 = "checkPermissions"
            r10.requestPermissionForAliases(r3, r11, r0)
            goto Le7
        Lbb:
            boolean r0 = r1.isEmpty()
            if (r0 != 0) goto Le4
            com.getcapacitor.JSObject r0 = new com.getcapacitor.JSObject
            r0.<init>()
            java.util.Iterator r1 = r1.iterator()
        Lca:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto Le0
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            com.getcapacitor.PermissionState r3 = com.getcapacitor.PermissionState.GRANTED
            java.lang.String r3 = r3.toString()
            r0.put(r2, r3)
            goto Lca
        Le0:
            r11.resolve(r0)
            goto Le7
        Le4:
            r11.resolve()
        Le7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.getcapacitor.Plugin.requestPermissions(com.getcapacitor.PluginCall):void");
    }

    private void handleLegacyPermission(PluginCall pluginCall) {
        NativePlugin legacyPluginAnnotation = this.handle.getLegacyPluginAnnotation();
        String[] permissions = legacyPluginAnnotation.permissions();
        if (permissions.length > 0) {
            saveCall(pluginCall);
            pluginRequestPermissions(permissions, legacyPluginAnnotation.permissionRequestCode());
        } else {
            pluginCall.resolve();
        }
    }

    @Deprecated
    protected void handleRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (hasDefinedPermissions(strArr)) {
            return;
        }
        StringBuilder sb = new StringBuilder("Missing the following permissions in AndroidManifest.xml:\n");
        for (String str : PermissionHelper.getUndefinedPermissions(getContext(), strArr)) {
            sb.append(str + "\n");
        }
        this.savedLastCall.reject(sb.toString());
        this.savedLastCall = null;
    }

    protected Bundle saveInstanceState() {
        PluginCall savedCall = this.bridge.getSavedCall(this.lastPluginCallId);
        if (savedCall == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        JSObject data = savedCall.getData();
        if (data != null) {
            bundle.putString(BUNDLE_PERSISTED_OPTIONS_JSON_KEY, data.toString());
        }
        return bundle;
    }

    @Deprecated
    protected void startActivityForResult(PluginCall pluginCall, Intent intent, int i) {
        this.bridge.startActivityForPluginWithResult(pluginCall, intent, i);
    }

    public void execute(Runnable runnable) {
        this.bridge.execute(runnable);
    }

    protected String getLogTag(String... strArr) {
        return Logger.tags(strArr);
    }

    protected String getLogTag() {
        return Logger.tags(getClass().getSimpleName());
    }
}