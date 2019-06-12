package com.kk.taurus.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.kk.taurus.cover.CloseCover;
import com.kk.taurus.cover.DataInter;
import com.kk.taurus.cover.LoadingCover;
import com.kk.taurus.ijkplayer.R;
import com.kk.taurus.playerbase.assist.OnVideoViewEventHandler;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.kk.taurus.playerbase.widget.BaseVideoView;

import static com.kk.taurus.cover.DataInter.ReceiverKey.KEY_CLOSE_COVER;
import static com.kk.taurus.cover.DataInter.ReceiverKey.KEY_LOADING_COVER;

public class VideoActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private BaseVideoView baseVideoView;
    private ImageView start;
    private int pointX;
    private int pointY;
    private float pointXValue;
    private float pointYValue;
    private int width;
    private int height;
    private float widthValue;
    private float heightValue;
    private String url;

    /**
     * @param activity       源activity
     * @param url            视频地址
     * @param location       放大动画开始位置(源控件左上角坐标)
     * @param widthAndHeight 控件宽高（用于计算动画开始结束位置）
     *                       int[] location = new int[2];
     *                       int[] widthAndHeight = new int[2];
     *                       v.getLocationOnScreen(location);
     *                       widthAndHeight[0] = v.getMeasuredWidth();
     *                       widthAndHeight[1] = v.getMeasuredHeight();
     *                       VideoActivity.startVideoActivity(getActivity(), location, widthAndHeight);
     */
    public static void startVideoActivity(FragmentActivity activity, String url, int[] location, int[] widthAndHeight) {
        Intent intent = new Intent(activity, VideoActivity.class);
        intent.putExtra("videoUrl", url);
        intent.putExtra("pointX", location[0]);
        intent.putExtra("pointY", location[1]);
        intent.putExtra("width", widthAndHeight[0]);
        intent.putExtra("height", widthAndHeight[1]);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ijkplayer_activity_video);
        constraintLayout = findViewById(R.id.ijkplayer_cl);
        baseVideoView = findViewById(R.id.ijkplayer_superplayerview);
        start = findViewById(R.id.ijkplayer_video_start);

        Intent intent = getIntent();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        if (intent.hasExtra("videoUrl")) {
            url = intent.getStringExtra("videoUrl");
        }
        if (intent.hasExtra("width")) {
            width = intent.getIntExtra("width", 0);
            widthValue = (float) width / metrics.widthPixels;
        }
        if (intent.hasExtra("height")) {
            height = intent.getIntExtra("height", 0);
            heightValue = (float) height / metrics.widthPixels;
        }
        if (intent.hasExtra("pointX")) {
            pointX = intent.getIntExtra("pointX", 0);
            pointX = pointX + width / 2;
            if (pointX == 0) {
                pointXValue = 0.5f;
            } else {
                pointXValue = (float) pointX / metrics.widthPixels;
            }
        }
        if (intent.hasExtra("pointY")) {
            pointY = intent.getIntExtra("pointY", 0);
            pointY = pointY + height / 2;
            if (pointY == 0) {
                pointYValue = 0.5f;
            } else {
                pointYValue = (float) pointY / metrics.heightPixels;
            }
        }

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation(baseVideoView, pointXValue, pointYValue, widthValue, heightValue);
    }

    @Override
    public void onBackPressed() {
        closeAnimation(baseVideoView, pointXValue, pointYValue, widthValue, heightValue);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private void startVideo() {
        ReceiverGroup receiverGroup = new ReceiverGroup();
        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(baseVideoView.getContext()));
        receiverGroup.addReceiver(KEY_CLOSE_COVER, new CloseCover(baseVideoView.getContext()));
        baseVideoView.setReceiverGroup(receiverGroup);

        DataSource dataSource = new DataSource();
        if (url.equals("") || url == null) {
            dataSource.setRawId(R.raw.big_buck_bunny);
        } else {
            dataSource.setData(url);
        }
        baseVideoView.setDataSource(dataSource);
        //设置一个事件处理器
        baseVideoView.setEventHandler(new OnVideoViewEventHandler());
        baseVideoView.setOnReceiverEventListener(new OnReceiverEventListener() {
            @Override
            public void onReceiverEvent(int eventCode, Bundle bundle) {
                if (eventCode == DataInter.Event.EVENT_CODE_REQUEST_CLOSE) {
                    VideoActivity.this.onBackPressed();
                }
            }
        });
//        无缝续播
//        RelationAssist mAssist;
//        mAssist = new RelationAssist(this);
//        mAssist.setEventAssistHandler(new OnAssistPlayEventHandler());
//        mAssist.setReceiverGroup(receiverGroup);
//        mAssist.setDataSource(dataSource);
//        mAssist.attachContainer(constraintLayout);
//        mAssist.play();
        if (!baseVideoView.isPlaying()) {
            baseVideoView.start();
        }
    }

    /**
     * 仿微信大图查看动画
     *
     * @param view        执行动画的控件
     * @param pointXValue 动画开始位置X
     * @param pointYValue 动画开始位置Y
     * @param widthValue  动画开始宽度
     * @param heightValue 动画开始高度
     */
    public void startAnimation(final View view, float pointXValue, float pointYValue, float widthValue, float heightValue) {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

        ScaleAnimation scaleAnimation = new ScaleAnimation(widthValue, 1f, heightValue, 1f, ScaleAnimation.RELATIVE_TO_PARENT,
                pointXValue, ScaleAnimation.RELATIVE_TO_PARENT, pointYValue);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        animationSet.setDuration(200);
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                constraintLayout.setBackgroundColor(Color.parseColor("#ff000000"));
                startVideo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animationSet);
    }

    /**
     * 仿微信大图关闭动画
     *
     * @param view        执行动画的控件
     * @param pointXValue 动画结束位置X
     * @param pointYValue 动画结束位置Y
     * @param widthValue  动画结束宽度
     * @param heightValue 动画结束高度
     */
    public void closeAnimation(final View view, float pointXValue, float pointYValue, float widthValue, float heightValue) {
        AnimationSet animationSet = new AnimationSet(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, widthValue, 1f, heightValue, ScaleAnimation.RELATIVE_TO_PARENT,
                pointXValue, ScaleAnimation.RELATIVE_TO_PARENT, pointYValue);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        animationSet.setDuration(200);
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                constraintLayout.setBackgroundColor(Color.parseColor("#00000000"));
                if (baseVideoView.isPlaying())
                    baseVideoView.stopPlayback();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                VideoActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animationSet);
    }
}
