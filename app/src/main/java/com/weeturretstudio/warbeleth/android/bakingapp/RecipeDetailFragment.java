package com.weeturretstudio.warbeleth.android.bakingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weeturretstudio.warbeleth.android.bakingapp.model.Recipe;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The content this fragment is presenting.
     */
    private Recipe mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the content specified by the fragment
            // arguments.
            mItem = getArguments().getParcelable(ARG_ITEM_ID);

            Activity activity = this.getActivity();
            if(activity != null) {
                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle("Title: " + mItem.getName());
                }
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        // Show the content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText("Servings: " + mItem.getServings());
        }

        return rootView;
    }
}
