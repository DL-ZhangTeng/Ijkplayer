/*
 * Copyright 2017 jiajunhui<junhui_jia@163.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.kk.taurus.playerbase.assist;

import android.os.Bundle;

import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.EventKey;
import com.kk.taurus.playerbase.log.PLog;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.widget.BaseVideoView;

/**
 * Created by Taurus on 2018/5/25.
 */
public class OnVideoViewEventHandler extends BaseEventAssistHandler<BaseVideoView> {

    @Override
    public void requestPause(BaseVideoView videoView, Bundle bundle) {
        if (isInPlaybackState(videoView)) {
            videoView.pause();
        } else {
            videoView.stop();
        }
    }

    @Override
    public void requestResume(BaseVideoView videoView, Bundle bundle) {
        if (isInPlaybackState(videoView)) {
            videoView.resume();
        } else {
            requestRetry(videoView, bundle);
        }
    }

    @Override
    public void requestSeek(BaseVideoView videoView, Bundle bundle) {
        int pos = 0;
        if (bundle != null) {
            pos = bundle.getInt(EventKey.INT_DATA);
        }
        videoView.seekTo(pos);
    }

    @Override
    public void requestStop(BaseVideoView videoView, Bundle bundle) {
        videoView.stop();
    }

    @Override
    public void requestReset(BaseVideoView videoView, Bundle bundle) {
        videoView.stop();
    }

    @Override
    public void requestRetry(BaseVideoView videoView, Bundle bundle) {
        int pos = 0;
        if (bundle != null) {
            pos = bundle.getInt(EventKey.INT_DATA);
        }
        videoView.rePlay(pos);
    }

    @Override
    public void requestReplay(BaseVideoView videoView, Bundle bundle) {
        videoView.rePlay(0);
    }

    @Override
    public void requestPlayDataSource(BaseVideoView assist, Bundle bundle) {
        if (bundle != null) {
            DataSource data = (DataSource) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
            if (data == null) {
                PLog.e("OnVideoViewEventHandler", "requestPlayDataSource need legal data source");
                return;
            }
            assist.stop();
            assist.setDataSource(data);
            assist.start();
        }
    }

    private boolean isInPlaybackState(BaseVideoView videoView) {
        int state = videoView.getState();
        return state != IPlayer.STATE_END
                && state != IPlayer.STATE_ERROR
                && state != IPlayer.STATE_IDLE
                && state != IPlayer.STATE_INITIALIZED
                && state != IPlayer.STATE_STOPPED;
    }

}
