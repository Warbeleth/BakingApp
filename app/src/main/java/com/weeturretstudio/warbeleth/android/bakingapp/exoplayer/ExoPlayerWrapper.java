package com.weeturretstudio.warbeleth.android.bakingapp.exoplayer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;

public class ExoPlayerWrapper implements Parcelable{

    public PlayerView view;
    public SimpleExoPlayer player;
    public MediaSource mediaSource;
    public String url;
    public boolean playWhenReady = false;
    public int currentWindow = 0;
    public long playbackPosition = 0;

    public ExoPlayerWrapper() {}

    protected ExoPlayerWrapper(Parcel in) {
        url = in.readString();
        playWhenReady = in.readByte() != 0;
        currentWindow = in.readInt();
        playbackPosition = in.readLong();
    }

    public static final Creator<ExoPlayerWrapper> CREATOR = new Creator<ExoPlayerWrapper>() {
        @Override
        public ExoPlayerWrapper createFromParcel(Parcel in) {
            return new ExoPlayerWrapper(in);
        }

        @Override
        public ExoPlayerWrapper[] newArray(int size) {
            return new ExoPlayerWrapper[size];
        }
    };

    public void saveState() {
        if(player != null) {
            playWhenReady = false;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeByte((byte) (playWhenReady ? 1 : 0));
        dest.writeInt(currentWindow);
        dest.writeLong(playbackPosition);
    }
}
