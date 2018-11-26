package com.liujing.pagerouter;

import android.app.Activity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import java.util.Map;

public interface RouterInitializer {
    void initActivityTable(Map<String, Class<? extends Activity>> router);

    void initFragmentTable(Map<String, Pair<Class<? extends Activity>, Class<? extends Fragment>>> router);
}
