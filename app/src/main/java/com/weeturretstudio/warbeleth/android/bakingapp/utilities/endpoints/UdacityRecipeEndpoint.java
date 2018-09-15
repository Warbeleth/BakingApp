package com.weeturretstudio.warbeleth.android.bakingapp.utilities.endpoints;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UdacityRecipeEndpoint {

    @GET("android-baking-app-json/")
    Call<JsonArray> getRecipeString();
}
