package com.weeturretstudio.warbeleth.android.bakingapp;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
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

import com.weeturretstudio.warbeleth.android.bakingapp.dummy.DummyContent;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Ingredient;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Recipe;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeStepListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    /**
     * The content this Activity is presenting.
     */
    private Recipe mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipesteplistactivity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.recipesteplistactivity_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        //TODO: Double check if I did this right...?
        Intent invokingIntent = getIntent();
        if(invokingIntent != null) {
            mItem = invokingIntent.getParcelableExtra(RecipeListActivity.ARG_RECIPE_ITEM);

            if(mItem != null)
                Log.v("StepList", mItem.toString());
        }

        View recyclerView = findViewById(R.id.recipesteplistactivity_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        ArrayList<Object> data = new ArrayList<>();
        data.add(mItem.getIngredients());
        data.addAll(mItem.getSteps());
        recyclerView.setAdapter(new ComplexItemRecyclerViewAdapter(this, data, mTwoPane));
    }

    public static class ComplexItemRecyclerViewAdapter
            extends RecyclerView.Adapter<ComplexItemRecyclerViewAdapter.ViewHolder> {

        private final RecipeStepListActivity mParentActivity;
        private final List<Object> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object item = view.getTag();

                if(item == null)
                    return;

                String argumentKey = RecipeStepDetailFragment.getKeyForInstanceType(item);

                if(argumentKey == null)
                    return;

                if (mTwoPane) {
                    Bundle arguments = new Bundle();

                    if(argumentKey.equals(RecipeStepDetailFragment.ARG_INGREDIENT))
                        arguments.putParcelableArrayList(argumentKey, ((ArrayList<Ingredient>)item));
                    else
                        arguments.putParcelable(argumentKey, ((Step)item));

                    RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipesteplistactivity_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeStepDetailActivity.class);

                    if(argumentKey.equals(RecipeStepDetailFragment.ARG_INGREDIENT))
                        intent.putExtra(argumentKey, ((ArrayList<Ingredient>)item));
                    else
                        intent.putExtra(argumentKey, ((Step)item));

                    context.startActivity(intent);
                }
            }
        };

        ComplexItemRecyclerViewAdapter(RecipeStepListActivity parent,
                                      List<Object> items,
                                      boolean twoPane) {
            mValues = new ArrayList<>();
            mValues.addAll(items);
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipesteplistactivity_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Object obj = mValues.get(position);
            String objType = RecipeStepDetailFragment.getKeyForInstanceType(obj);

            if(objType.equals(RecipeStepDetailFragment.ARG_STEP)) {
                holder.mIdView.setText(mParentActivity.getString(R.string.stepPositionQuantity, (position-1)));
                holder.mContentView.setText(((Step) mValues.get(position)).getShortDescription());
            }
            else {
                holder.mIdView.setText("");
                holder.mContentView.setText(R.string.ingredientHeader);
            }

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
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
