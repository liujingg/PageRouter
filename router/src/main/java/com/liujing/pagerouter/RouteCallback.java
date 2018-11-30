package com.liujing.pagerouter;

import android.content.Context;
import android.net.Uri;

public interface RouteCallback {
    void onSuccess(Context context, Uri uri);

    void onFailed(Context context, String message);
}
