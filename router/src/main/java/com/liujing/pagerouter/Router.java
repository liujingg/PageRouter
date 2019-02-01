package com.liujing.pagerouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

public class Router {
    public static final int ERROR_CODE_URI_NULL = 101;
    public static final int ERROR_CODE_NOT_SUPPORT = 102;

    public static String FRAGMENT_CLASS_NAME = "fragmentClassName";
    public static String FRAGMENT_ARGUMENTS = "fragmentArguments";
    public static String FRAGMENT_URI = "fragmentUri";

    @NonNull
    private String uriScheme;
    private static Map<String, Class<? extends Activity>> activityRouterMap = new HashMap<>();
    private static Map<String, Pair<Class<? extends Activity>, Class<? extends Fragment>>> fragmentRouterMap = new HashMap<>();
    private LinkedHashSet<Interceptor> interceptors = new LinkedHashSet<>();

    public Router(@NonNull String uriScheme, @NonNull RouterInitializer initializer) {
        this.uriScheme = uriScheme;
        initializer.initActivityTable(activityRouterMap);
        initializer.initFragmentTable(fragmentRouterMap);
    }

    public void register(@NonNull RouterInitializer initializer) {
        initializer.initActivityTable(activityRouterMap);
        initializer.initFragmentTable(fragmentRouterMap);
    }

    public boolean startActivity(@NonNull final Context context, @Nullable final Uri uri, @Nullable final Callback callback) {
        if (uri == null) {
            if (callback != null) callback.onFailed(context, null, ERROR_CODE_URI_NULL, null);
            return false;
        }
        InterceptResult interceptResult = null;
        for (Interceptor interceptor : interceptors) {
            InterceptResult tempInterceptResult = interceptor.onIntercept(context, uri);
            if (tempInterceptResult != null) {
                interceptResult = tempInterceptResult;
                break;
            }
        }

        if (interceptResult != null) {
            if (interceptResult.isSuccess()) {
                if (callback != null) callback.onSuccess(context, uri);
                return true;
            } else {
                if (callback != null)
                    callback.onFailed(context, uri, interceptResult.getErrorCode(), interceptResult.getErrorMessage());
                return false;
            }
        }

        Intent intent = buildActivityIntent(context, uri);
        if (intent != null) {
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            if (callback != null) callback.onSuccess(context, uri);
            return true;
        } else {
            if (callback != null) callback.onFailed(context, uri, ERROR_CODE_NOT_SUPPORT, null);
            return false;
        }
    }

    private Intent buildActivityIntent(@NonNull Context context, @NonNull Uri uri) {
        Class activityClazz = getActivityClass(uri);
        if (activityClazz != null) {
            Intent intent = new Intent(context, activityClazz);
            intent.setData(uri);
            intent.putExtras(uriParamsToBundle(uri));
            return intent;
        }
        Pair<Class<? extends Activity>, Class<? extends Fragment>> pair = getFragmentActivityPair(uri);
        if (pair != null && pair.first != null && pair.second != null) {
            Intent intent = new Intent(context, pair.first);
            intent.setData(uri);
            intent.putExtra(FRAGMENT_CLASS_NAME, pair.second.getName());
            Bundle bundle = uriParamsToBundle(uri);
            bundle.putParcelable(FRAGMENT_URI, uri);
            intent.putExtra(FRAGMENT_ARGUMENTS, bundle);
            return intent;
        }
        return null;
    }

    private Class<? extends Activity> getActivityClass(@NonNull Uri uri) {
        String url = uri.toString();
        String key;
        int tmp = url.indexOf('?');
        if (tmp > 0) {
            key = url.substring(0, tmp);
        } else {
            key = url;
        }
        Class<? extends Activity> clazz = activityRouterMap.get(key);
        if (clazz != null) {
            return clazz;
        }
        if (uriScheme.equals(uri.getScheme())) {
            key = uri.getHost();
            return activityRouterMap.get(key);
        }
        return null;
    }

    private Pair<Class<? extends Activity>, Class<? extends Fragment>> getFragmentActivityPair(@NonNull Uri uri) {
        String url = uri.toString();
        String key;
        int tmp = url.indexOf('?');
        if (tmp > 0) {
            key = url.substring(0, tmp);
        } else {
            key = url;
        }

        Pair<Class<? extends Activity>, Class<? extends Fragment>> fragmentActivityPair = fragmentRouterMap.get(key);
        if (fragmentActivityPair != null) {
            return fragmentActivityPair;
        }
        if (uriScheme.equals(uri.getScheme())) {
            key = uri.getHost();
            return fragmentRouterMap.get(key);
        }
        return null;
    }

    @NonNull
    private Bundle uriParamsToBundle(@NonNull Uri uri) {
        Bundle bundle = new Bundle();
        Set<String> keys = uri.getQueryParameterNames();
        if (keys == null || keys.isEmpty()) return bundle;
        for (String key : keys) {
            String value = uri.getQueryParameter(key);
            if (TextUtils.isEmpty(value)) continue;
            bundle.putString(key, value);
        }
        return bundle;
    }

    /**
     * Creates a new instance of Fragment by this uri.
     *
     * @param uri uri
     */
    public Fragment buildFragment(@NotNull Uri uri) {
        Pair<Class<? extends Activity>, Class<? extends Fragment>> pair = getFragmentActivityPair(uri);
        if (pair != null && pair.second != null) {
            Fragment fragment;
            try {
                fragment = (Fragment) Class.forName(pair.second.getName()).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Bundle bundle = uriParamsToBundle(uri);
            bundle.putParcelable(FRAGMENT_URI, uri);
            fragment.setArguments(bundle);
            return fragment;
        } else {
            throw new IllegalArgumentException("No matches to Fragment by uri: " + uri.toString());
        }
    }

    /**
     * Adds the specified interceptor to the interceptors set
     *
     * @param interceptor interceptor to be added to the set
     */
    public void addInterceptor(@NonNull Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    /**
     * Removes the specified interceptor from the interceptors set
     *
     * @param interceptor interceptor to be removed from the set
     */
    public void removeInterceptor(@NonNull Interceptor interceptor) {
        interceptors.remove(interceptor);
    }
}
