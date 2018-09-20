package com.weeturretstudio.warbeleth.android.bakingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weeturretstudio.warbeleth.android.bakingapp.dummy.DummyContent;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Ingredient;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a single RecipeStepListActivity detail screen.
 * This fragment is either contained in a {@link RecipeStepListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepDetailActivity}
 * on handsets.
 */
public class RecipeStepDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_STEP = "arg_step";
    public static final String ARG_INGREDIENT = "arg_ingredient";

    /**
     * The content this fragment is presenting.
     */
    private List<Ingredient> ingredients;
    private Step step;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeStepDetailFragment() {
    }

    public static String getKeyForInstanceType(Object item) {
        if(item == null)
            return null;

        String argumentKey = null;
        if(item instanceof List) {
            argumentKey = RecipeStepDetailFragment.ARG_INGREDIENT;
            Log.v("Type", ((List<Ingredient>) item).toString());
        }
        else if(item instanceof Step) {
            argumentKey = RecipeStepDetailFragment.ARG_STEP;
            Log.v("Type", ((Step) item).toString());
        }

        return argumentKey;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_INGREDIENT)) {
            ingredients = getArguments().getParcelableArrayList(ARG_INGREDIENT);
        }
        else if(getArguments().containsKey(ARG_STEP)) {
            step = getArguments().getParcelable(ARG_STEP);
        }

        if(ingredients != null || step != null) {
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                if(ingredients != null)
                    appBarLayout.setTitle("Ingredients: " + ingredients.size());
                else
                    appBarLayout.setTitle("Step: " + step.getShortDescription());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipesteplistactivity_detail, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.ingredient_list_recycler_view);
        NestedScrollView nestedScrollView = rootView.findViewById(R.id.nestedScrollView_Root);

        //TODO: Show view content
        // Show the content as text in a TextView.
        if (ingredients != null) {
            nestedScrollView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            assert recyclerView != null;
            recyclerView.setAdapter(new IngredientViewAdapter(ingredients));
        }
        if(step != null) {
            //((TextView) rootView.findViewById(R.id.recipesteplistactivity_detail)).setText("Step: " + step.getShortDescription());
            nestedScrollView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            ImageView thumbnail = rootView.findViewById(R.id.imageView_Step_Thumbnail);
            TextView shortDescription = rootView.findViewById(R.id.textView_ShortDescription);
            TextView fullDescription = rootView.findViewById(R.id.textView_FullDescription);
            ImageView exoplayerPlaceholder = rootView.findViewById(R.id.imageView_PlaceHolder);

            if(step.getThumbnailURL() != null) {
                //TODO: thumbnail...?
                Log.v("Thumbnail", "TODO");
            }

            if(step.getShortDescription() != null)
                shortDescription.setText(step.getShortDescription());
            if(step.getDescription() != null)
                fullDescription.setText(step.getDescription());

            if(step.getVideoURL() != null) {
                //TODO: Exoplayer...?
                Log.v("Exoplayer", "TODO");
            }
        }

        return rootView;
    }

    public static class IngredientViewAdapter
            extends RecyclerView.Adapter<IngredientViewAdapter.ViewHolder> {

        private final List<Ingredient> mValues;

        IngredientViewAdapter(List<Ingredient> items) {
            mValues = new ArrayList<>();
            mValues.addAll(items);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredient_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            if(mValues != null) {
                Ingredient obj = mValues.get(position);

                if (obj != null) {
                    holder.mIngredientName.setText(obj.getIngredient());
                    holder.mMeasurementSize.setText(obj.getMeasure());
                    holder.mMeasurementQuantity.setText("" + String.valueOf(obj.getQuantity()));
                }
            }
        }

        @Override
        public int getItemCount() {
            if(mValues == null)
                return 0;
            else
                return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIngredientName;
            final TextView mMeasurementSize;
            final TextView mMeasurementQuantity;

            ViewHolder(View view) {
                super(view);
                mIngredientName = (TextView) view.findViewById(R.id.textview_Ingredient);
                mMeasurementSize = (TextView) view.findViewById(R.id.textview_MeasurementValue);
                mMeasurementQuantity = (TextView) view.findViewById(R.id.textview_MeasureSizeValue);
            }
        }
    }
}
