package com.weeturretstudio.warbeleth.android.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
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
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.weeturretstudio.warbeleth.android.bakingapp.exoplayer.ExoPlayerWrapper;
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
    private static final String ARG_THUMBNAIL = "arg_thumbnail";
    private static final String ARG_VIDEO = "arg_video";

    /**
     * The content this fragment is presenting.
     */
    private List<Ingredient> ingredients;
    private Step step;

    ExoPlayerWrapper exoplayerThumbnailWrapper;
    ExoPlayerWrapper exoplayerVideoWrapper;

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

        if(savedInstanceState != null) {
            if (savedInstanceState.containsKey(ARG_THUMBNAIL)) {
                exoplayerThumbnailWrapper = savedInstanceState.getParcelable(ARG_THUMBNAIL);
            }

            if (savedInstanceState.containsKey(ARG_VIDEO)) {
                exoplayerVideoWrapper = savedInstanceState.getParcelable(ARG_VIDEO);
            }
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

            TextView shortDescription = rootView.findViewById(R.id.textView_ShortDescription);
            TextView fullDescription = rootView.findViewById(R.id.textView_FullDescription);

            if(step.getShortDescription() != null)
                shortDescription.setText(step.getShortDescription());
            if(step.getDescription() != null)
                fullDescription.setText(step.getDescription());

            if(step.getThumbnailURL() != null && step.getThumbnailURL().length() > 0) {
                Log.v("Thumbnail", "TODO");
                if(exoplayerThumbnailWrapper == null) {
                    exoplayerThumbnailWrapper = new ExoPlayerWrapper();
                    exoplayerThumbnailWrapper.url = step.getThumbnailURL();
                }

                exoplayerThumbnailWrapper.view = rootView.findViewById(R.id.exoplayer_Step_Thumbnail);
            }
            else {
                PlayerView exoview = rootView.findViewById(R.id.exoplayer_Step_Thumbnail);
                exoview.setVisibility(View.GONE);
            }

            if(step.getVideoURL() != null && step.getVideoURL().length() > 0) {
                Log.v("Exoplayer", "TODO");

                if(exoplayerVideoWrapper == null) {
                    exoplayerVideoWrapper = new ExoPlayerWrapper();
                    exoplayerVideoWrapper.url = step.getVideoURL();
                }

                exoplayerVideoWrapper.view = rootView.findViewById(R.id.exoplayer_Video);
            }
            else {
                PlayerView exoview = rootView.findViewById(R.id.exoplayer_Video);
                exoview.setVisibility(View.GONE);
            }
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("onAttach", "Orientation: " + context.getResources().getConfiguration().orientation +
                "\nHeight-DP: " + context.getResources().getConfiguration().screenHeightDp +
                "\nWidth-DP: " + context.getResources().getConfiguration().screenWidthDp);
    }

    /*
        Reference Code: https://codelabs.developers.google.com/codelabs/exoplayer-intro/#2
         */
    private void InitializePlayer(ExoPlayerWrapper wrapper, String url) {
        if(wrapper != null && url != null && url.length() > 0) {

            if(wrapper.player == null) {
                wrapper.player = ExoPlayerFactory.newSimpleInstance(
                        new DefaultRenderersFactory(this.getContext()),
                        new DefaultTrackSelector(), new DefaultLoadControl());

                wrapper.view.setVisibility(View.VISIBLE);
                wrapper.view.setPlayer(wrapper.player);
            }

            wrapper.url = url;

            wrapper.player.setPlayWhenReady(wrapper.playWhenReady);
            wrapper.player.seekTo(wrapper.currentWindow, wrapper.playbackPosition);

            if(wrapper.mediaSource == null) {
                wrapper.mediaSource = buildMediaSource(url);
                wrapper.player.prepare(wrapper.mediaSource, false, false);
            }
        }
    }

    private void InitializePlayer(ExoPlayerWrapper wrapper) {
        if(wrapper != null && wrapper.url != null && wrapper.url.length() > 0) {
            InitializePlayer(wrapper, wrapper.url);
        }
    }

    private MediaSource buildMediaSource(String url) {
        Uri uri = Uri.parse(url);

        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-bakingapp")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            InitializePlayer(exoplayerThumbnailWrapper);
            InitializePlayer(exoplayerVideoWrapper);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Util.SDK_INT <= 23) {
            InitializePlayer(exoplayerThumbnailWrapper);
            InitializePlayer(exoplayerVideoWrapper);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if(exoplayerThumbnailWrapper != null) {
            exoplayerThumbnailWrapper.releasePlayer();
        }

        if(exoplayerVideoWrapper != null) {
            exoplayerVideoWrapper.releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(exoplayerThumbnailWrapper != null) {
            exoplayerThumbnailWrapper.saveState();
            outState.putParcelable(ARG_THUMBNAIL, exoplayerThumbnailWrapper);
        }

        if(exoplayerVideoWrapper != null) {
            exoplayerVideoWrapper.saveState();
            outState.putParcelable(ARG_VIDEO, exoplayerVideoWrapper);
        }
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
                    holder.mMeasurementQuantity.setText(holder.itemView.getContext().getString(R.string.emptyString, String.valueOf(obj.getQuantity())));
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
