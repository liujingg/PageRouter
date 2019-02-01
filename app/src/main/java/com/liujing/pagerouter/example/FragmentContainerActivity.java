package com.liujing.pagerouter.example;

import android.os.Bundle;
import android.text.TextUtils;

import com.liujing.pagerouter.Router;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class FragmentContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle fragmentParams = getIntent().getBundleExtra(Router.FRAGMENT_ARGUMENTS);
        String fragmentClassName = getIntent().getStringExtra(Router.FRAGMENT_CLASS_NAME);

        if (TextUtils.isEmpty(fragmentClassName)) finish();

        setContentView(R.layout.activity_fragments);

        Fragment fragment;
        try {
            fragment = (Fragment) Class.forName(fragmentClassName).newInstance();
            fragment.setArguments(fragmentParams);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
            return;
        }

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_fragments_content, fragment)
                .commit();
    }
}
