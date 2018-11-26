package com.liujing.pagerouter;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;

public interface Filter {
    String doFilter(String url);

    boolean startActivity(Context context, String url);

    boolean startActivityForResult(Activity activity, String url, int requestCode);

    boolean startActivityForResult(Fragment fragment, String url, int requestCode);
}
