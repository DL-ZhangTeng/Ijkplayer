package com.kk.taurus.cover;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kk.taurus.ijkplayer.R;
import com.kk.taurus.playerbase.receiver.BaseCover;

public class CloseCover extends BaseCover {

    ImageView mCloseIcon;
    RelativeLayout relativeLayout;

    public CloseCover(Context context) {
        super(context);
//        mCloseIcon = findViewById(R.id.iv_close);
//        mCloseIcon.setOnClickListener(v -> notifyReceiverEvent(DataInter.Event.EVENT_CODE_REQUEST_CLOSE, null));
        relativeLayout = findViewById(R.id.iv_close_rl);
        relativeLayout.setOnClickListener(view -> notifyReceiverEvent(DataInter.Event.EVENT_CODE_REQUEST_CLOSE, null));
    }

    @Override
    public void onReceiverBind() {
        super.onReceiverBind();
    }

    @Override
    public void onReceiverUnBind() {
        super.onReceiverUnBind();

    }

    @Override
    public View onCreateCoverView(Context context) {
        return View.inflate(context, R.layout.layout_close_cover, null);
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onErrorEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public int getCoverLevel() {
        return levelMedium(10);
    }
}
