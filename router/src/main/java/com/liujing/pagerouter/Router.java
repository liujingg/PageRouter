package com.liujing.pagerouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import com.liujing.pagerouter.annotation.RouterField;

import java.lang.reflect.Field;
import java.util.*;

public class Router {
    private static Map<String, Class<? extends Activity>> sActivityRouter = new HashMap<>();
    private static Map<String, Pair<Class<? extends Activity>, Class<? extends Fragment>>> sFragmentRouter = new HashMap<>();
    private static String sScheme = "router";
    private static Filter sFilter;
    private static String sFragmentClassName="fragmentClass";


    private Router(Activity activity) {
        activity.getIntent().getExtras();
    }

    private static List<Field> getDeclaredFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
        }
        return fieldList;
    }

    public static void inject(Fragment fragment) {
        SafeBundle bundle = new SafeBundle(fragment.getArguments(), fragment.getActivity() != null ? fragment.getActivity().getIntent().getData() : null);
        Class clazz = fragment.getClass();
        List<Field> fields = getDeclaredFields(clazz);
        for (Field field : fields) {
            RouterField annotation = field.getAnnotation(RouterField.class);
            if (annotation == null) {
                continue;
            }
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            String[] names = annotation.value();
            try {
                for (String name : names) {
                    if (bundle.containsKey(name)) {
                        continue;
                    }
                    switch (type) {
                        case "int":
                            field.set(fragment, bundle.getInt(name, field.getInt(fragment)));
                            continue;
                        case "boolean":
                            field.set(fragment, bundle.getBoolean(name, field.getBoolean(fragment)));
                            continue;
                        case "long":
                            field.set(fragment, bundle.getLong(name, field.getLong(fragment)));
                            continue;
                        case "float":
                            field.set(fragment, bundle.getFloat(name, field.getFloat(fragment)));
                            continue;
                        case "double":
                            field.set(fragment, bundle.getDouble(name, field.getDouble(fragment)));
                            continue;
                    }
                    Object defaultValue = field.get(fragment);
                    if (field.getGenericType() == String.class) {
                        field.set(fragment, bundle.getString(name, (String) defaultValue));
                    } else if (field.getGenericType() == Integer.class) {
                        field.set(fragment, bundle.getInt(name, defaultValue != null ? (Integer) defaultValue : 0));
                    } else if (field.getGenericType() == Boolean.class) {
                        field.set(fragment, bundle.getBoolean(name, defaultValue != null ? (Boolean) defaultValue : false));
                    } else if (field.getGenericType() == Double.class) {
                        field.set(fragment, bundle.getDouble(name, defaultValue != null ? (Double) defaultValue : 0));
                    } else if (field.getGenericType() == Long.class) {
                        field.set(fragment, bundle.getLong(name, defaultValue != null ? (Long) defaultValue : 0));
                    } else if (field.getGenericType() == Float.class) {
                        field.set(fragment, bundle.getFloat(name, defaultValue != null ? (Float) defaultValue : 0));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void inject(Activity activity) {
        SafeBundle bundle = new SafeBundle(activity.getIntent().getExtras(), activity.getIntent().getData());
        Class clazz = activity.getClass();
        List<Field> fields = getDeclaredFields(clazz);
        for (Field field : fields) {
            RouterField annotation = field.getAnnotation(RouterField.class);
            if (annotation == null) {
                continue;
            }
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            String[] names = annotation.value();
            try {
                for (String name : names) {
                    if (bundle.containsKey(name)) {
                        continue;
                    }
                    switch (type) {
                        case "int":
                            field.set(activity, bundle.getInt(name, field.getInt(activity)));
                            continue;
                        case "boolean":
                            field.set(activity, bundle.getBoolean(name, field.getBoolean(activity)));
                            continue;
                        case "long":
                            field.set(activity, bundle.getLong(name, field.getLong(activity)));
                            continue;
                        case "float":
                            field.set(activity, bundle.getFloat(name, field.getFloat(activity)));
                            continue;
                        case "double":
                            field.set(activity, bundle.getDouble(name, field.getDouble(activity)));
                            continue;
                    }
                    Object defaultValue = field.get(activity);
                    if (field.getGenericType() == String.class) {
                        field.set(activity, bundle.getString(name, (String) defaultValue));
                    } else if (field.getGenericType() == Integer.class) {
                        field.set(activity, bundle.getInt(name, defaultValue != null ? (Integer) defaultValue : 0));
                    } else if (field.getGenericType() == Boolean.class) {
                        field.set(activity, bundle.getBoolean(name, defaultValue != null ? (Boolean) defaultValue : false));
                    } else if (field.getGenericType() == Long.class) {
                        field.set(activity, bundle.getLong(name, defaultValue != null ? (Long) defaultValue : 0));
                    } else if (field.getGenericType() == Float.class) {
                        field.set(activity, bundle.getFloat(name, defaultValue != null ? (Float) defaultValue : 0));
                    } else if (field.getGenericType() == Double.class) {
                        field.set(activity, bundle.getDouble(name, defaultValue != null ? (Double) defaultValue : 0));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void register(RouterInitializer routerInitializer) {
        routerInitializer.initActivityTable(sActivityRouter);
        routerInitializer.initFragmentTable(sFragmentRouter);
    }

    public static boolean startActivity(Context context, String url) {
        if (sFilter != null) {
            url = sFilter.doFilter(url);
            if (sFilter.startActivity(context, url)) {
                return true;
            }
        }
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);

        Class activityClazz = getActivityClass(url, uri);
        if (activityClazz != null) {
            Intent intent = new Intent(context, activityClazz);
            intent.setData(uri);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            return true;
        } else {
            Pair<Class<? extends Activity>, Class<? extends Fragment>> pair = getFragmentActivityPair(url, uri);
            if (pair != null && pair.first != null && pair.second != null) {
                Intent intent = new Intent(context, pair.first);
                intent.setData(uri);
                intent.putExtra(sFragmentClassName, pair.second.getName());
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
                return true;
            } else {
                new Throwable(url + " can not startActivity").printStackTrace();
            }
        }
        return false;
    }

    public static boolean startActivityForResult(Activity activity, String url, int requestCode) {
        if (sFilter != null) {
            url = sFilter.doFilter(url);
            if (sFilter.startActivityForResult(activity, url, requestCode)) {
                return true;
            }
        }
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);

        Class activityClazz = getActivityClass(url, uri);
        if (activityClazz != null) {
            Intent intent = new Intent(activity, activityClazz);
            intent.setData(uri);
            activity.startActivityForResult(intent, requestCode);
            return true;
        } else {
            Pair<Class<? extends Activity>, Class<? extends Fragment>> pair = getFragmentActivityPair(url, uri);
            if (pair != null && pair.first != null && pair.second != null) {
                Intent intent = new Intent(activity, pair.first);
                intent.setData(uri);
                intent.putExtra(sFragmentClassName, pair.second.getName());
                activity.startActivityForResult(intent, requestCode);
                return true;
            } else {
                new Throwable(url + " can not startActivity").printStackTrace();
            }
        }
        return false;
    }

    public static boolean startActivityForResult(Fragment fragment, String url, int requestCode) {
        if (sFilter != null) {
            url = sFilter.doFilter(url);
            if (sFilter.startActivityForResult(fragment, url, requestCode)) {
                return true;
            }
        }
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);

        Class activityClazz = getActivityClass(url, uri);
        if (activityClazz != null) {
            Intent intent = new Intent(fragment.getActivity(), activityClazz);
            intent.setData(uri);
            fragment.startActivityForResult(intent, requestCode);
            return true;
        } else {
            Pair<Class<? extends Activity>, Class<? extends Fragment>> pair = getFragmentActivityPair(url, uri);
            if (pair != null && pair.first != null && pair.second != null) {
                Intent intent = new Intent(fragment.getActivity(), pair.first);
                intent.setData(uri);
                intent.putExtra(sFragmentClassName, pair.second.getName());
                fragment.startActivityForResult(intent, requestCode);
                return true;
            } else {
                new Throwable(url + " can not startActivity").printStackTrace();
            }
        }
        return false;
    }

    private static Class<? extends Activity> getActivityClass(String url, Uri uri) {
        String key;
        int tmp = url.indexOf('?');
        if (tmp > 0) {
            key = url.substring(0, tmp);
        } else {
            key = url;
        }
        Class<? extends Activity> clazz = sActivityRouter.get(key);
        if (clazz != null) {
            return clazz;
        }
        if (sScheme.equals(uri.getScheme())) {
            key = uri.getHost();
            return sActivityRouter.get(key);
        }
        return null;
    }

    private static Pair<Class<? extends Activity>, Class<? extends Fragment>> getFragmentActivityPair(String url, Uri uri) {
        String key;
        int tmp = url.indexOf('?');
        if (tmp > 0) {
            key = url.substring(0, tmp);
        } else {
            key = url;
        }

        Pair<Class<? extends Activity>, Class<? extends Fragment>> fragmentActivityPair = sFragmentRouter.get(key);
        if (fragmentActivityPair != null) {
            return fragmentActivityPair;
        }
        if (sScheme.equals(uri.getScheme())) {
            key = uri.getHost();
            return sFragmentRouter.get(key);
        }
        return null;
    }

    @SuppressWarnings("unused")
    public static String getScheme() {
        return sScheme;
    }

    public static String getFragmentClassName() {
        return sFragmentClassName;
    }

    public static void init(String scheme) {
        Router.sScheme = scheme;
        try {
            Class.forName("com.liujing.pagerouter.AptRouterInitializer");
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
    }

    public static void setFilter(Filter filter) {
        Router.sFilter = filter;
    }


}
