package com.dxt2.nativehtml;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button startWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        startWebView = (Button) findViewById(R.id.startWebView);
        startWebView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startWebView:
                Intent intent = new Intent(this,WebViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
