package com.liujing.pagerouter.example;

import android.app.Activity;
import android.app.Application;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import com.liujing.pagerouter.OtherRouterInitializer;
import com.liujing.pagerouter.Router;
import com.liujing.pagerouter.RouterInitializer;

import java.util.Map;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Router.init("pagerouter");
        Router.register(new OtherRouterInitializer());
        Router.register(new RouterInitializer() {
            @Override
            public void initActivityTable(Map<String, Class<? extends Activity>> router) {
                router.put("second2", SecondActivity.class);
            }

            @Override
            public void initFragmentTable(Map<String, Pair<Class<? extends Activity>, Class<? extends Fragment>>> router) {

            }
        });
    }
}
