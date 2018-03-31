package com.example.evilj.bakingapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evilj.bakingapp.models.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment implements Player.EventListener {
    private static final String TAG = StepFragment.class.getSimpleName();

    @BindView(R.id.simpleExoPlayerView)
    SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.aspect_ratio_frame)
    AspectRatioFrameLayout mAspectRatioFrameLayout;
    @BindView(R.id.text_fragment)
    TextView mRecipeTextView;
    private Steps mSteps;
    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat sSessionCompat;
    private Context mContext;
    private PlaybackStateCompat.Builder mPlaybackBuilder;
    private StepListener mStepListener;

    private final String SAVE_STEP = "state";



    public StepFragment() {
        // Required empty public constructor
    }

    public interface StepListener {
        void videoCompleted();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_step, container, false);
        mContext = getContext();
        ButterKnife.bind(this,view);

        initializeMediaSession();
        if (savedInstanceState!=null)mSteps = (Steps) savedInstanceState.getParcelable(SAVE_STEP);
        if (mSteps==null)throw new RuntimeException("Steps can´t be null");
        mAspectRatioFrameLayout.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        mExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        initializePlayer(Uri.parse(mSteps.getVideoUrl()));
        mRecipeTextView.setText(mSteps.getDesc());

        return view;
    }

    public void setSteps(Steps steps) {
        mSteps = steps;
    }

    private void initializeMediaSession(){
        sSessionCompat = new MediaSessionCompat(mContext,TAG);

        sSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mPlaybackBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PAUSE |
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        sSessionCompat.setPlaybackState(mPlaybackBuilder.build());
        sSessionCompat.setCallback(new MySessionCallbacks());
        sSessionCompat.setActive(true);
    }

    private void initializePlayer (Uri uri){
        if (mExoPlayer==null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer= ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);

            mExoPlayer.addListener(this);

            String userAgent = Util.getUserAgent(getContext(),getString(R.string.app_name));
            DataSource.Factory dataSource = new DefaultHttpDataSourceFactory(userAgent);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSource).createMediaSource(uri);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState== ExoPlayer.STATE_READY)&& playWhenReady){
            mPlaybackBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(),1f);
        }else if ((playbackState==ExoPlayer.STATE_READY)){
            mPlaybackBuilder.setState(PlaybackStateCompat.STATE_PAUSED,mExoPlayer.getCurrentPosition(),
                    1f);
        }else if ((playbackState==ExoPlayer.STATE_ENDED)){
            mStepListener.videoCompleted();
        }
        sSessionCompat.setPlaybackState(mPlaybackBuilder.build());

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        if (error.type==ExoPlaybackException.TYPE_SOURCE){
            Toast.makeText(mContext, "Error loading video", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }


    private class MySessionCallbacks extends MediaSessionCompat.Callback{
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }


    private void releasePlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer=null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        sSessionCompat.setActive(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mStepListener = (StepListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement StepListener");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVE_STEP,mSteps);
    }
}
