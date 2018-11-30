package com.liujing.pagerouter;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface InterceptorCallback {
    void onContinue(@NonNull Uri newUri);

    void onInterrupt(boolean isSuccess, @Nullable String message);
}
