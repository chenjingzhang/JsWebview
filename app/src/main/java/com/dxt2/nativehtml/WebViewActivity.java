package com.dxt2.nativehtml;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    WebView mWebView;
    Button bt1, bt2, bt3;
    EditText inputvalue;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        inputvalue = (EditText) findViewById(R.id.inputvalue);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        mWebView = (WebView) findViewById(R.id.webview);
        // 默认情况下在WebVIew中是不能使用JavaScript的，
        // 如果想使用需要在WebView中添加一个设置
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");

        mWebView.addJavascriptInterface(new JSCallJava(), "jscalljava");

//     通过 WebChromeClient的onJsAlert()方法回调拦截JS对话框alert()、confirm()、prompt（） 消息
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final android.webkit.JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(
                        WebViewActivity.this);
                b.setTitle("dialog");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                result.confirm();
                            }
                        });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });
        mWebView.loadUrl("file:///android_asset/test.html");
    }

    //Js调用java中的方法
    class JSCallJava {
        /*1 无参 无返回值*/
        @JavascriptInterface
        public void CallJavaMethod() {
            Toast.makeText(WebViewActivity.this, "java方法被js调用1", Toast.LENGTH_SHORT).show();
        }
        /*2有参 有返回值*/
        @JavascriptInterface
        public String CallJavaMethodParam(String param) {
            Toast.makeText(WebViewActivity.this, "java方法被js调用2", Toast.LENGTH_LONG).show();
            return "java方法被js调用2带参数参数为" + param;
        }
      /*3 */
       @JavascriptInterface
        public void CallBackJavaMethodParam(String str){
           Toast.makeText(WebViewActivity.this,"java方法被js调用3调用返回的数据为"+str,Toast.LENGTH_LONG).show();
       }

    }


    //Java调用Js
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                mWebView.loadUrl("javascript:CallJsMethod()");
                break;
            case R.id.bt2:
                String script = String.format("javascript:CallJSRretunStrMethod()");
                mWebView.evaluateJavascript(script, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(WebViewActivity.this, "js返回[" + s + "]", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.bt3:
                String params = inputvalue.getText().toString();
                mWebView.loadUrl("javascript:CallJsMethodParams('" + params + "')");
                break;
        }
    }
}





























