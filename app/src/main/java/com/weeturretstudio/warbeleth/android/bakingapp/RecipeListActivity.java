package com.weeturretstudio.warbeleth.android.bakingapp;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Recipe;
import com.weeturretstudio.warbeleth.android.bakingapp.model.RecipeContent;
import com.weeturretstudio.warbeleth.android.bakingapp.utilities.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecyclerView recyclerView;
    private SimpleItemRecyclerViewAdapter simpleAdapter;
    private Observer observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        //TODO: Remove debug code below
        NetworkUtil.getInstance().getRecipeString(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful())
                    Log.v("NetworkTest", "Result: " + response.body());
                else
                    Log.v("NetworkTest", "Response, Unsuccessful");
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.v("NetworkTest", "Failure: " + t.toString());
            }
        });
        //TODO: Remove debug code above
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if(simpleAdapter == null)
            simpleAdapter = new SimpleItemRecyclerViewAdapter(this, RecipeContent.recipes.getValue(), mTwoPane);

        if(observer == null) {
            observer = new Observer<ArrayList<Recipe>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Recipe> recipes) {
                    simpleAdapter.changeValues(recipes);
                }
            };

            //Add Observer
            RecipeContent.recipes.observe(this, observer);
        }

        recyclerView.setAdapter(simpleAdapter);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final RecipeListActivity mParentActivity;
        private final List<Recipe> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe item = (Recipe) view.getTag();

                if(item == null)
                    return;

                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(RecipeDetailFragment.ARG_ITEM_ID, item);
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, item);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
                                      List<Recipe> items,
                                      boolean twoPane) {
            mValues = new ArrayList<>();
            if(items != null)
                mValues.addAll(items);

            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        public void changeValues(List<Recipe> changedItems) {
            if(changedItems != null) {
                mValues.clear();
                mValues.addAll(changedItems);
                this.notifyDataSetChanged();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if(mValues != null) {

                if(mValues.get(position) != null) {
                    holder.mIdView.setText("ID: " + mValues.get(position).getId());
                    holder.mContentView.setText("Name: " + mValues.get(position).getName());
                }

                holder.itemView.setTag(mValues.get(position));
            }
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            if(mValues != null)
                return mValues.size();
            else
                return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
