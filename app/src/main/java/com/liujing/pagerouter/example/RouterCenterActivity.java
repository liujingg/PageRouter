package com.liujing.pagerouter.example;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

public class RouterCenterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        if (uri != null) {
            RouterManager.instance(uri).start(this);
        }
        this.finish();
    }
}
