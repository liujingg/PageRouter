package com.liujing.pagerouter.example;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.liujing.pagerouter.InterceptResult;
import com.liujing.pagerouter.Interceptor;

import androidx.annotation.NonNull;

/**
 * interceptor example
 */
public class BrowserIntercept implements Interceptor {
    private static final int ERROR_CODE_MISSING_WEB_URL = 201;
    private static final int ERROR_CODE_MISSING_WEB_BROWSER = 202;

    @Override
    public InterceptResult onIntercept(@NonNull Context context, @NonNull Uri uri) {
        if (!"browser".equals(uri.getHost())) return null;

        String webUrl = uri.getQueryParameter("url");

        if (TextUtils.isEmpty(webUrl))
            return InterceptResult.failed(ERROR_CODE_MISSING_WEB_URL, "missing web url");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webUrl));
        try {
            context.startActivity(intent);
            return InterceptResult.success();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return InterceptResult.failed(ERROR_CODE_MISSING_WEB_BROWSER, "missing web browser");
        }
    }
}