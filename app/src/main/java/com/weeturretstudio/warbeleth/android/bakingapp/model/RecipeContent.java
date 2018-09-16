package com.weeturretstudio.warbeleth.android.bakingapp.model;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.weeturretstudio.warbeleth.android.bakingapp.utilities.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeContent {

    public static final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<List<Recipe>>();

    static {
        NetworkUtil.getInstance().getRecipeList(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                if(response.body() != null)
                    addItems(response.body());
                Log.v("NetworkTest_Two", "Recipes: " + recipes.toString());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.v("NetworkTest_Two", "Recipes: " + "Failed to Load");
            }
        });
    }

    private static void addItem(Recipe item) {
        if(item != null) {
            if(recipes.getValue() != null) {
                ArrayList<Recipe> temp = new ArrayList<>(recipes.getValue());
                temp.add(item);
                recipes.setValue(temp);
            }
            else {
                ArrayList<Recipe> temp = new ArrayList<Recipe>();
                temp.add(item);
                recipes.setValue(temp);
            }
        }
    }

    private static void addItems(List<Recipe> items) {
        if(items != null) {
            ArrayList<Recipe> temp;
            if(recipes.getValue() != null) {
                temp = new ArrayList<>(recipes.getValue());
                temp.addAll(items);
            }
            else
                temp = new ArrayList<>(items);

            recipes.setValue(temp);
        }
    }
}
