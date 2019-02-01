package com.liujing.pagerouter;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Callback {
    void onSuccess(@NonNull Context context, @NonNull Uri uri);

    void onFailed(@NonNull Context context, @Nullable Uri uri, int errorCode, @Nullable String message);
}
