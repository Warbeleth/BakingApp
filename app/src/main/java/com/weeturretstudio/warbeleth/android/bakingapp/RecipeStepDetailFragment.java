package com.weeturretstudio.warbeleth.android.bakingapp;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weeturretstudio.warbeleth.android.bakingapp.dummy.DummyContent;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Ingredient;
import com.weeturretstudio.warbeleth.android.bakingapp.model.Step;

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

        //TODO: Show view content
        // Show the content as text in a TextView.
        if (ingredients != null) {
            ((TextView) rootView.findViewById(R.id.recipesteplistactivity_detail)).setText("Ingredients: " + ingredients.size());
        }
        if(step != null) {
            ((TextView) rootView.findViewById(R.id.recipesteplistactivity_detail)).setText("Step: " + step.getShortDescription());
        }

        return rootView;
    }
}
