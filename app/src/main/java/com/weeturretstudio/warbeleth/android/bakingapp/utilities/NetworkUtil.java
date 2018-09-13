package com.weeturretstudio.warbeleth.android.bakingapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    private static final String TAG = NetworkUtil.class.getSimpleName();

    /**
     * isNetworkOperable: Helper method to determine if the network is connected (or connecting).
     * @param context - Invoking context
     * @return - True if (Connected || Connecting), otherwise False.
     */
    public static boolean isNetworkOperable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm == null)
            return false;

        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info == null || !info.isConnectedOrConnecting())
            return false;

        return true;
    }
}
