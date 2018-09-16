package com.weeturretstudio.warbeleth.android.bakingapp.utilities.endpoints;

import com.google.gson.JsonArray;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UdacityRecipeEndpoint {

    @GET("android-baking-app-json/")
    Call<JsonArray> getRecipeString();

    @GET("android-baking-app-json/")
    Call<List<Recipe>> getRecipeList();
}
