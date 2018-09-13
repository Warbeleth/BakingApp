package com.weeturretstudio.warbeleth.android.bakingapp.utilities;

import com.google.gson.Gson;

public class JSONUtil {
    private static final String TAG = JSONUtil.class.getSimpleName();

    private static JSONUtil INSTANCE = null;
    private static Gson gsonParser = null;

    private JSONUtil() {
        gsonParser = new Gson();
    }

    synchronized public static JSONUtil getInstance() {
        if(INSTANCE == null)
            INSTANCE = new JSONUtil();

        return INSTANCE;
    }
}
