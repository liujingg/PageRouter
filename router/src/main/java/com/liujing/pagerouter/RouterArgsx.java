package com.liujing.pagerouter;

import android.app.Activity;
import android.content.Intent;

import com.liujing.pagerouter.annotation.RouterField;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;

public class RouterArgsx {

    public static void inject(Fragment fragment) {
        BundleUri bundleUri = new BundleUri(fragment.getArguments(), fragment.getActivity() != null ? fragment.getActivity().getIntent().getData() : null);
        List<Field> fieldList = new ArrayList<>(Arrays.asList(fragment.getClass().getDeclaredFields()));
        for (Field field : fieldList) {
            RouterField annotation = field.getAnnotation(RouterField.class);
            if (annotation == null) continue;
            field.setAccessible(true);
            Type type = field.getGenericType();
            try {
                Object defaultValue = field.get(fragment);
                String[] names = annotation.value();
                for (String name : names) {
                    if (bundleUri.containsKey(name)) continue;
                    if (type == String.class) {
                        field.set(fragment, bundleUri.getString(name, (String) defaultValue));
                    } else if (type == Integer.TYPE || type == Integer.class) {
                        field.set(fragment, bundleUri.getInt(name, defaultValue != null ? (Integer) defaultValue : 0));
                    } else if (type == Boolean.TYPE || type == Boolean.class) {
                        field.set(fragment, bundleUri.getBoolean(name, defaultValue != null ? (Boolean) defaultValue : false));
                    } else if (type == Long.TYPE || type == Long.class) {
                        field.set(fragment, bundleUri.getLong(name, defaultValue != null ? (Long) defaultValue : 0));
                    } else if (type == Float.TYPE || type == Float.class) {
                        field.set(fragment, bundleUri.getFloat(name, defaultValue != null ? (Float) defaultValue : 0));
                    } else if (type == Double.TYPE || type == Double.class) {
                        field.set(fragment, bundleUri.getDouble(name, defaultValue != null ? (Double) defaultValue : 0));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void inject(Activity activity) {
        Intent intent = activity.getIntent();
        BundleUri bundleUri = new BundleUri(intent.getExtras(), intent.getData());
        List<Field> fieldList = new ArrayList<>(Arrays.asList(activity.getClass().getDeclaredFields()));
        for (Field field : fieldList) {
            RouterField annotation = field.getAnnotation(RouterField.class);
            if (annotation == null) continue;
            field.setAccessible(true);
            Type type = field.getGenericType();
            try {
                Object defaultValue = field.get(activity);
                String[] names = annotation.value();
                for (String name : names) {
                    if (bundleUri.containsKey(name)) continue;
                    if (type == String.class) {
                        field.set(activity, bundleUri.getString(name, (String) defaultValue));
                    } else if (type == Integer.TYPE || type == Integer.class) {
                        field.set(activity, bundleUri.getInt(name, defaultValue != null ? (Integer) defaultValue : 0));
                    } else if (type == Boolean.TYPE || type == Boolean.class) {
                        field.set(activity, bundleUri.getBoolean(name, defaultValue != null ? (Boolean) defaultValue : false));
                    } else if (type == Long.TYPE || type == Long.class) {
                        field.set(activity, bundleUri.getLong(name, defaultValue != null ? (Long) defaultValue : 0));
                    } else if (type == Float.TYPE || type == Float.class) {
                        field.set(activity, bundleUri.getFloat(name, defaultValue != null ? (Float) defaultValue : 0));
                    } else if (type == Double.TYPE || type == Double.class) {
                        field.set(activity, bundleUri.getDouble(name, defaultValue != null ? (Double) defaultValue : 0));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
