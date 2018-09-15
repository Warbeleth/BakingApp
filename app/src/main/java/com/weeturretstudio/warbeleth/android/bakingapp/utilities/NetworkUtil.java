package com.weeturretstudio.warbeleth.android.bakingapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.weeturretstudio.warbeleth.android.bakingapp.utilities.endpoints.UdacityRecipeEndpoint;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class NetworkUtil {
    private static final String TAG = NetworkUtil.class.getSimpleName();
    private static final String UDACITY_RECIPE_LIST_URL = "http://go.udacity.com/";
    private static NetworkUtil INSTANCE = null;
    private static UdacityRecipeEndpoint udacityEndpoint = null;
    private static Retrofit retrofit = null;

    private NetworkUtil() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(UDACITY_RECIPE_LIST_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    synchronized public static NetworkUtil getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new NetworkUtil();
        }

        return INSTANCE;
    }

    public UdacityRecipeEndpoint getUdacityEndpoint() {
        if(udacityEndpoint == null) {
            udacityEndpoint = retrofit.create(UdacityRecipeEndpoint.class);
        }

        return udacityEndpoint;
    }

    public void getRecipeString(Callback<List<JSONObject>> callback) {
        try {
            Call<List<JSONObject>> getRecipeCall = getUdacityEndpoint().getRecipeString();
            getRecipeCall.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
