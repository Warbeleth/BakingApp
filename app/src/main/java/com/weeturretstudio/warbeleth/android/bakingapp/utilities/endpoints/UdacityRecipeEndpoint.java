package com.weeturretstudio.warbeleth.android.bakingapp.utilities.endpoints;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UdacityRecipeEndpoint {

    @GET("android-baking-app-json/")
    Call<List<JSONObject>> getRecipeString();
}
