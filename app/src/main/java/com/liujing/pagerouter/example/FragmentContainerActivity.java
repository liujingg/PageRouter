package com.liujing.pagerouter.example;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.liujing.pagerouter.Router;

public class FragmentContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fragmentClassName = getIntent().getStringExtra(Router.getFragmentClassName());
        if (TextUtils.isEmpty(fragmentClassName)) {
            finish();
        }
        setContentView(R.layout.activity_fragments);


        Fragment fragment;
        try {
            fragment = (Fragment) Class.forName(fragmentClassName).newInstance();
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
