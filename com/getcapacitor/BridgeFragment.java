package com.getcapacitor;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.getcapacitor.Bridge;
import com.getcapacitor.android.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class BridgeFragment extends Fragment {
    private static final String ARG_START_DIR = "startDir";
    protected Bridge bridge;
    protected boolean keepRunning = true;
    private final List<Class<? extends Plugin>> initialPlugins = new ArrayList();
    private CapConfig config = null;
    private final List<WebViewListener> webViewListeners = new ArrayList();

    public static BridgeFragment newInstance(String str) {
        BridgeFragment bridgeFragment = new BridgeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_START_DIR, str);
        bridgeFragment.setArguments(bundle);
        return bridgeFragment;
    }

    public void addPlugin(Class<? extends Plugin> cls) {
        this.initialPlugins.add(cls);
    }

    public void setConfig(CapConfig capConfig) {
        this.config = capConfig;
    }

    public Bridge getBridge() {
        return this.bridge;
    }

    public void addWebViewListener(WebViewListener webViewListener) {
        this.webViewListeners.add(webViewListener);
    }

    protected void load(Bundle bundle) {
        Logger.debug("Loading Bridge with BridgeFragment");
        String string = getArguments() != null ? getArguments().getString(ARG_START_DIR) : null;
        Bridge create = new Bridge.Builder(this).setInstanceState(bundle).setPlugins(this.initialPlugins).setConfig(this.config).addWebViewListeners(this.webViewListeners).create();
        this.bridge = create;
        if (string != null) {
            create.setServerAssetPath(string);
        }
        this.keepRunning = this.bridge.shouldKeepRunning();
    }

    @Override // androidx.fragment.app.Fragment
    public void onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(context, attributeSet, bundle);
        String string = context.obtainStyledAttributes(attributeSet, R.styleable.bridge_fragment).getString(R.styleable.bridge_fragment_start_dir);
        if (string != null) {
            String charSequence = string.toString();
            Bundle bundle2 = new Bundle();
            bundle2.putString(ARG_START_DIR, charSequence);
            setArguments(bundle2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_bridge, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        load(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Bridge bridge = this.bridge;
        if (bridge != null) {
            bridge.onDestroy();
        }
    }
}