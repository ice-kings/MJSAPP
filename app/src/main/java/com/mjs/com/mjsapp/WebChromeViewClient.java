package com.mjs.com.mjsapp;

import android.graphics.Bitmap;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by sczgy on 2018/8/28.
 */

public class WebChromeViewClient extends WebChromeClient {
    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {
        return true;
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message,
                                    JsResult result) {
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message,
                               JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsTimeout() {
        return super.onJsTimeout();
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    @Override
    public void onRequestFocus(WebView view) {
        super.onRequestFocus(view);
    }

}