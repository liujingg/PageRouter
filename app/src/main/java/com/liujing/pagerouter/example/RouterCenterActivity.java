package com.liujing.pagerouter.example;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import com.liujing.pagerouter.Router;

public class RouterCenterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        if (data != null) {
            Router.startActivity(this, data.toString());
        }
        this.finish();
    }
}
