package com.liujing.pagerouter.example;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.liujing.pagerouter.Router;
import com.liujing.pagerouter.annotation.RouterActivity;
import com.liujing.pagerouter.annotation.RouterField;

@RouterActivity({"second"})
public class SecondActivity extends AppCompatActivity {
    @RouterField("id")
    private int id;

    @RouterField("name")
    private String name;

    @RouterField("isShow")
    private boolean isShow;

    @RouterField("price")
    private float price;

    @RouterField("time")
    private long time;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        Router.inject(this);

        TextView textview = findViewById(R.id.params_text);
        String builder = getClass().getSimpleName() + '\n' +
                "id:" + String.valueOf(id) + '\n' +
                "name:" + String.valueOf(name) + '\n' +
                "isShow:" + String.valueOf(isShow) + '\n' +
                "price:" + String.valueOf(price) + '\n' +
                "time:" + String.valueOf(time) + '\n';
        textview.setText(builder);
    }
}
