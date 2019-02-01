package com.liujing.pagerouter;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Interceptor {
    @Nullable
    InterceptResult onIntercept(@NonNull Context context, @NonNull Uri uri);
}
