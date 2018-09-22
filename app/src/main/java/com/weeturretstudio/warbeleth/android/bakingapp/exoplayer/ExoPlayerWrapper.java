package com.weeturretstudio.warbeleth.android.bakingapp.exoplayer;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;

public class ExoPlayerWrapper {

    public PlayerView view;
    public SimpleExoPlayer player;
    public MediaSource mediaSource;
    public String url;
    public boolean playWhenReady = true;
    public int currentWindow = 0;
    public long playbackPosition = 0;

    public void saveState() {
        if(player != null) {
            playWhenReady = player.getPlayWhenReady();
            currentWindow = player.getCurrentWindowIndex();
            playbackPosition = player.getCurrentPosition();
        }
    }

    public void releasePlayer() {
        if(player != null) {
            saveState();
            player.release();
            player = null;
        }

        if(mediaSource != null)
            mediaSource = null;
    }
}
