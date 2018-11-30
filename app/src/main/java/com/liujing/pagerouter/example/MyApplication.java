package com.liujing.pagerouter.example;

import android.app.Activity;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.liujing.pagerouter.IIntercept;
import com.liujing.pagerouter.InterceptorCallback;
import com.liujing.pagerouter.OtherRouterInitializer;
import com.liujing.pagerouter.RouteCallback;
import com.liujing.pagerouter.Router;
import com.liujing.pagerouter.RouterInitializer;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

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

        Router.setDefaultCallBack(new RouteCallback() {
            @Override
            public void onSuccess(Context context, Uri uri) {
                Toast.makeText(getBaseContext(), "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Context context, String message) {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            }
        });

        Router.setIntercept(new IIntercept() {
            @Override
            public void process(@NonNull Context context, @NonNull Uri uri, InterceptorCallback callback) {
                if ("browser".equals(uri.getHost())) {
                    String webUrl = uri.getQueryParameter("url");
                    if (TextUtils.isEmpty(webUrl)) {
                        callback.onInterrupt(false, "url is empty");
                        return;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(webUrl));
                    try {
                        context.startActivity(intent);
                        callback.onInterrupt(true, null);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        callback.onInterrupt(false, "not support uri");
                    }
                } else {
                    callback.onContinue(uri);
                }
            }
        });
    }
}