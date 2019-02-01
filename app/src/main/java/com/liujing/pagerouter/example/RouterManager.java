package com.liujing.pagerouter.example;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.liujing.pagerouter.AptRouterInitializer;
import com.liujing.pagerouter.Callback;
import com.liujing.pagerouter.OtherRouterInitializer;
import com.liujing.pagerouter.Router;
import com.liujing.pagerouter.RouterInitializer;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

public final class RouterManager {
    public static final String SCHEME = "pagerouter";
    public static final Router ROUTER;

    static {
        Router router = new Router(RouterManager.SCHEME, new AptRouterInitializer());
        router.register(new OtherRouterInitializer());
        router.register(new RouterInitializer() {
            @Override
            public void initActivityTable(Map<String, Class<? extends Activity>> router) {
                router.put("second2", SecondActivity.class);
            }

            @Override
            public void initFragmentTable(Map<String, Pair<Class<? extends Activity>, Class<? extends Fragment>>> router) {

            }
        });
        router.addInterceptor(new BrowserIntercept());
        ROUTER = router;
    }

    @NonNull
    private Uri uri;

    private RouterManager(@NonNull Uri uri) {
        this.uri = uri;
    }

    @NonNull
    public static RouterManager instance(@NonNull Uri uri) {
        return new RouterManager(uri);
    }

    public boolean start(@NonNull Context context) {
        return start(context, null);
    }

    public boolean start(@NonNull Context context, @Nullable final Callback callback) {
        return ROUTER.startActivity(context, uri, new Callback() {
            @Override
            public void onSuccess(@NonNull Context context, @NonNull Uri uri) {
                if (callback != null) callback.onSuccess(context, uri);
            }

            @Override
            public void onFailed(@NonNull Context context, @Nullable Uri uri, int errorCode, @Nullable String message) {
                if (errorCode == Router.ERROR_CODE_URI_NULL) {
                    Toast.makeText(context, "router failed , uri is null", Toast.LENGTH_LONG).show();
                } else if (errorCode == Router.ERROR_CODE_NOT_SUPPORT) {
                    Toast.makeText(context, "router failed , not support uri", Toast.LENGTH_LONG).show();
                } else if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
                if (callback != null) callback.onFailed(context, uri, errorCode, message);
            }
        });
    }

}
