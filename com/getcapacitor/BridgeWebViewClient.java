package com.getcapacitor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Iterator;

/* loaded from: classes.dex */
public class BridgeWebViewClient extends WebViewClient {
    private Bridge bridge;

    public BridgeWebViewClient(Bridge bridge) {
        this.bridge = bridge;
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        return this.bridge.getLocalServer().shouldInterceptRequest(webResourceRequest);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        return this.bridge.launchIntent(webResourceRequest.getUrl());
    }

    @Override // android.webkit.WebViewClient
    @Deprecated
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return this.bridge.launchIntent(Uri.parse(str));
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        if (this.bridge.getWebViewListeners() == null || webView.getProgress() != 100) {
            return;
        }
        Iterator<WebViewListener> it = this.bridge.getWebViewListeners().iterator();
        while (it.hasNext()) {
            it.next().onPageLoaded(webView);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError);
        if (this.bridge.getWebViewListeners() != null) {
            Iterator<WebViewListener> it = this.bridge.getWebViewListeners().iterator();
            while (it.hasNext()) {
                it.next().onReceivedError(webView);
            }
        }
        String errorUrl = this.bridge.getErrorUrl();
        if (errorUrl == null || !webResourceRequest.isForMainFrame()) {
            return;
        }
        webView.loadUrl(errorUrl);
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        this.bridge.reset();
        if (this.bridge.getWebViewListeners() != null) {
            Iterator<WebViewListener> it = this.bridge.getWebViewListeners().iterator();
            while (it.hasNext()) {
                it.next().onPageStarted(webView);
            }
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        if (this.bridge.getWebViewListeners() != null) {
            Iterator<WebViewListener> it = this.bridge.getWebViewListeners().iterator();
            while (it.hasNext()) {
                it.next().onReceivedHttpError(webView);
            }
        }
        String errorUrl = this.bridge.getErrorUrl();
        if (errorUrl == null || !webResourceRequest.isForMainFrame()) {
            return;
        }
        webView.loadUrl(errorUrl);
    }

    @Override // android.webkit.WebViewClient
    public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail renderProcessGoneDetail) {
        boolean z;
        super.onRenderProcessGone(webView, renderProcessGoneDetail);
        if (this.bridge.getWebViewListeners() == null) {
            return false;
        }
        Iterator<WebViewListener> it = this.bridge.getWebViewListeners().iterator();
        while (true) {
            while (it.hasNext()) {
                z = it.next().onRenderProcessGone(webView, renderProcessGoneDetail) || z;
            }
            return z;
        }
    }
}