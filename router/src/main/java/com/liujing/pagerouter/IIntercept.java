package com.liujing.pagerouter;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

public interface IIntercept {
    void process(@NonNull Context context, @NonNull Uri uri, InterceptorCallback callback);
}
