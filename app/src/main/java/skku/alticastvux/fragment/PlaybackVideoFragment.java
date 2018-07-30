/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package skku.alticastvux.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v17.leanback.app.VideoSupportFragment;
import android.support.v17.leanback.app.VideoSupportFragmentGlueHost;
import android.support.v17.leanback.media.MediaPlayerAdapter;
import android.support.v17.leanback.media.MediaPlayerGlue;
import android.support.v17.leanback.media.PlaybackGlue;
import android.util.Log;

import com.alticast.mmuxclient.ClientAPI;

import java.util.ArrayList;

import skku.alticastvux.activity.DetailsActivity;
import skku.alticastvux.activity.PlaybackActivity;
import skku.alticastvux.activity.base.BaseFragmentActivity;
import skku.alticastvux.media.PlaybackSeekDiskDataProvider;
import skku.alticastvux.media.VideoMediaPlayerGlue;
import skku.alticastvux.model.VideoInfo;
import skku.alticastvux.voiceable.CommandListener;
import skku.alticastvux.voiceable.pattern.FindSongPattern;
import skku.alticastvux.voiceable.pattern.MovePattern;
import skku.alticastvux.voiceable.pattern.VoiceablePattern;

/**
 * Handles video playback with media controls.
 */
public class PlaybackVideoFragment extends VideoSupportFragment {
    private static final String TAG = "PlaybackVideoFragment";

    private VideoMediaPlayerGlue<MediaPlayerAdapter> mMediaPlayerGlue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final VideoInfo videoInfo = (VideoInfo) getActivity()
                .getIntent().getSerializableExtra(DetailsActivity.VIDEO_INFO);

        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(PlaybackVideoFragment.this);

        mMediaPlayerGlue = new VideoMediaPlayerGlue(getActivity(), new MediaPlayerAdapter(getActivity()));
        mMediaPlayerGlue.setHost(glueHost);
        mMediaPlayerGlue.setMode(MediaPlayerGlue.NO_REPEAT);
        mMediaPlayerGlue.setPlayerCallback(new PlaybackGlue.PlayerCallback() {
            @Override
            public void onReadyForPlayback() {
                mMediaPlayerGlue.play();

            }
        });
        mMediaPlayerGlue.setTitle(videoInfo.getTitle());
        mMediaPlayerGlue.setSubtitle(videoInfo.getPath());
        mMediaPlayerGlue.getPlayerAdapter().setDataSource(Uri.parse(videoInfo.getPath()));
        PlaybackSeekDiskDataProvider.setDemoSeekProvider(mMediaPlayerGlue, videoInfo.getPath());

        ((BaseFragmentActivity) getActivity()).setCommandListener(new CommandListener() {
            @Override
            public boolean receiveCommand(VoiceablePattern pattern) {
                if (pattern instanceof MovePattern) {
                    int seconds = ((MovePattern) pattern).getSeconds();
                    if (seconds != 0) {
                        mMediaPlayerGlue.seekTo(mMediaPlayerGlue.getCurrentPosition() + seconds * 1000);
                    }
                } else if (pattern instanceof FindSongPattern) {
                    String filename = videoInfo.getPath();
                    long position = mMediaPlayerGlue.getCurrentPosition();
                    Log.e(getClass().getSimpleName(), "find song");
                    ((PlaybackActivity) getActivity()).findSong(filename, position);
                }
                return true;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayerGlue != null) {
            mMediaPlayerGlue.pause();
        }
    }
}