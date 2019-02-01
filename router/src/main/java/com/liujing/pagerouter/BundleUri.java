package com.liujing.pagerouter;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

@SuppressWarnings({"WeakerAccess", "unused"})
public class BundleUri {
    private Bundle bundle;
    private Uri uri;

    public BundleUri(Bundle bundle, Uri uri) {
        this.bundle = bundle;
        this.uri = uri;
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }

    public double getDouble(String key) {
        return getDouble(key, 0.0);
    }

    public float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        Object o = bundle != null ? bundle.get(key) : null;
        if (o == null && uri != null) o = uri.getQueryParameter(key);
        return o != null ? o.toString() : defaultValue;
    }

    public double getDouble(String key, double defaultValue) {
        Object o = bundle != null ? bundle.get(key) : null;
        if (o == null && uri != null) o = uri.getQueryParameter(key);
        if (o == null) return defaultValue;
        try {
            if (o instanceof String) return Double.parseDouble(o.toString());
            return (Double) o;
        } catch (ClassCastException e) {
            printLog(key, o, "Double", defaultValue);
            return defaultValue;
        }
    }

    public long getLong(String key, long defaultValue) {
        Object o = bundle != null ? bundle.get(key) : null;
        if (o == null && uri != null) o = uri.getQueryParameter(key);
        if (o == null) return defaultValue;
        try {
            if (o instanceof String) return Long.parseLong(o.toString());
            return (Long) o;
        } catch (ClassCastException e) {
            printLog(key, o, "Long", defaultValue);
            return defaultValue;
        }
    }

    public int getInt(String key, int defaultValue) {
        Object o = bundle != null ? bundle.get(key) : null;
        if (o == null && uri != null) o = uri.getQueryParameter(key);
        if (o == null) return defaultValue;
        try {
            if (o instanceof String) return Integer.parseInt(o.toString());
            return (Integer) o;
        } catch (ClassCastException e) {
            printLog(key, o, "Integer", defaultValue);
            return defaultValue;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object o = bundle != null ? bundle.get(key) : null;
        if (o == null && uri != null) o = uri.getQueryParameter(key);
        if (o == null) return defaultValue;
        try {
            if (o instanceof String) return Boolean.parseBoolean(o.toString());
            return (Boolean) o;
        } catch (ClassCastException e) {
            printLog(key, o, "Boolean", defaultValue);
            return defaultValue;
        }
    }

    public float getFloat(String key, float defaultValue) {
        Object o = bundle != null ? bundle.get(key) : null;
        if (o == null && uri != null) o = uri.getQueryParameter(key);
        if (o == null) return defaultValue;
        try {
            if (o instanceof String) return Float.parseFloat(o.toString());
            return (Float) o;
        } catch (ClassCastException e) {
            printLog(key, o, "Float", defaultValue);
            return defaultValue;
        }
    }

    public Object get(String key) {
        return bundle.get(key);
    }

    public <T extends Parcelable> T getParcelable(String key) {
        return bundle.getParcelable(key);
    }

    public Serializable getSerializable(String key) {
        return bundle.getSerializable(key);
    }

    public boolean containsKey(String key) {
        return !bundle.containsKey(key) && (uri == null || uri.getQueryParameter(key) == null);
    }

    private void printLog(String key, Object value, String className, Object defaultValue) {
        String msg = "Key : " + key + ". expected a " + className + " class, but value was a " +
                value.getClass().getName() + ".  The default value " + defaultValue + " was returned.";
        Log.w("Router_BundleUri", msg);
    }

}
