package com.zhangteng.ijkplayer;

import android.app.Application;

import com.kk.taurus.ijkplayer.IjkPlayer;
import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;
import com.kk.taurus.playerbase.entity.DecoderPlan;
import com.kk.taurus.playerbase.record.PlayRecordManager;

/**
 * Created by Swing on 2019/6/12 0012.
 */
public class MainApplication extends Application {
    public static final int PLAN_ID_IJK = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        //如果您想使用默认的网络状态事件生产者，请添加此行配置。
        //并需要添加权限 android.permission.ACCESS_NETWORK_STATE
        PlayerConfig.setUseDefaultNetworkEventProducer(true);
        PlayerConfig.addDecoderPlan(new DecoderPlan(PLAN_ID_IJK, IjkPlayer.class.getName(), "IjkPlayer"));
        PlayerConfig.setDefaultPlanId(PLAN_ID_IJK);
        //初始化库
        PlayerLibrary.init(this);
        //如果添加了'cn.jiajunhui:ijkplayer:xxxx'该依赖
        IjkPlayer.init(this);
        //播放记录的配置
        //开启播放记录
        PlayerConfig.playRecord(true);
        PlayRecordManager.setRecordConfig(
                new PlayRecordManager.RecordConfig.Builder()
                        .setMaxRecordCount(100)
                        //.setRecordKeyProvider()
                        //.setOnRecordCallBack()
                        .build());
    }
}
