package com.mjs.com.mjsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

public class MainActivity extends PermissionActivity {
    WebView webView;
    Dialog dialog_web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission(phone_state_permissions, 2);
    }
    public static String[] phone_state_permissions = new String[]{//电话权限
            Manifest.permission.READ_PHONE_STATE,//电话状态
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 2:
                webView = (WebView) findViewById(R.id.webview);
                webView.setVerticalScrollBarEnabled(false);
                webView.setHorizontalScrollBarEnabled(false);

                WebSettings settings = webView.getSettings();
                settings.setDomStorageEnabled(true);
                settings.setJavaScriptEnabled(true);
                settings.setBuiltInZoomControls(true);
                settings.setDisplayZoomControls(false);
                settings.setAllowFileAccess(true);
                settings.setAppCacheEnabled(true);
                settings.setSupportZoom(true);
                settings.setDefaultTextEncodingName("UTF-8");
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }

                if (Build.VERSION.SDK_INT >= 19) {
                    settings.setLoadsImagesAutomatically(true);
                } else {
                    settings.setLoadsImagesAutomatically(false);
                }

                webView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {//用作拨打电话
                        view.loadUrl(url);
                        return true;
                    }
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        if (dialog_web == null)
                            dialog_web = loadingDialog();
                    }
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        dialog_web.dismiss();
                    }
                });
                Log.i("getdeviceid","设备号为="+getdeviceid(this));
                webView.setWebChromeClient(new WebChromeViewClient());
                webView.loadUrl("http://47.244.46.198/View/login.html?deviceid="+getdeviceid(this));
                break;
        }
    }
    //大 及 个
    @SuppressLint("MissingPermission")
    public static String getdeviceid(Context c) {//获取设备ID
        String uniqueId = "";
        try {
            final TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            final String tmDevice, tmSerial, tmPhone, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(c.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            uniqueId = deviceUuid.toString();
        } catch (Exception e) {
        }
        return uniqueId;
    }
    public Dialog loadingDialog() {
        try {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View layout = inflater.inflate(R.layout.dialog_loading_shuju, null);
            ImageView img = (ImageView) layout.findViewById(R.id.img_loading);
            TextView text = (TextView) layout.findViewById(R.id.msg);
            Animation ani = AnimationUtils.loadAnimation(MainActivity.this,
                    R.anim.anim_loading);
            img.setAnimation(ani);
            Dialog dialog;
            dialog = new Dialog(MainActivity.this, R.style.CustomDialog);
            dialog.setContentView(layout);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            return dialog;
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }
}
