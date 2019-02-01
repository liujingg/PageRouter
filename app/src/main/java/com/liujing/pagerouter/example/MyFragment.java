package com.liujing.pagerouter.example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liujing.pagerouter.RouterArgsx;
import com.liujing.pagerouter.annotation.RouterField;
import com.liujing.pagerouter.annotation.RouterFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

@RouterFragment(value = "myfragment", activityClazz = FragmentContainerActivity.class)
public class MyFragment extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_common, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RouterArgsx.inject(this);
        TextView textview = view.findViewById(R.id.params_text);
        String builder = getClass().getSimpleName() + '\n' +
                "id:" + String.valueOf(id) + '\n' +
                "name:" + String.valueOf(name) + '\n' +
                "isShow:" + String.valueOf(isShow) + '\n' +
                "price:" + String.valueOf(price) + '\n' +
                "time:" + String.valueOf(time) + '\n';
        textview.setText(builder);
    }
}
