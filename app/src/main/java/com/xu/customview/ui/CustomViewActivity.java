package com.xu.customview.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.xu.customview.R;
import com.xu.customview.adapter.StickyAdapter;
import com.xu.customview.adapter.StickyGroupInfo;
import com.xu.customview.adapter.StickyItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xusn10 on 2018/3/21.
 *
 * @author xusn10
 */

public class CustomViewActivity extends Activity {
    /**
     * 时钟类型
     */
    public static final int TYPE_CLOCK = 1;
    /**
     * 今日头条 header
     */
    public static final int TYPE_HEADER = 2;

    /**
     * 仿脉脉匿名区吸附效果
     */
    public static final int TYPE_STICKY = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        Intent intent = getIntent();
        int type = intent.getIntExtra("viewType", 0);
        switch (type) {
            case TYPE_CLOCK:
                findViewById(R.id.huaWeiClock).setVisibility(View.VISIBLE);
                break;
            case TYPE_HEADER:
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setVisibility(View.VISIBLE);
                final AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) ContextCompat.getDrawable(this, R.drawable.refresh_vector);
                imageView.setImageDrawable(animatedVectorDrawable);
                animatedVectorDrawable.start();
                final Handler mainHandler = new Handler(Looper.getMainLooper());
                animatedVectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                animatedVectorDrawable.start();
                            }
                        });
                    }
                });
                break;
            case TYPE_STICKY:
                RecyclerView recyclerView = findViewById(R.id.rv_sticky);
                recyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                List<StickyGroupInfo> groupInfos = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    StickyGroupInfo groupInfo = new StickyGroupInfo();
                    if (i % 5 == 0) {
                        groupInfo.setShowTitle(true);
                        groupInfo.setTitle("标题" + i);
                    } else {
                        groupInfo.setShowTitle(false);
                        groupInfo.setTitle("标题" + i);
                    }
                    groupInfos.add(groupInfo);

                }
                StickyItemDecoration stickyItemDecoration = new StickyItemDecoration(groupInfos);
                recyclerView.addItemDecoration(stickyItemDecoration);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(new StickyAdapter());

                break;
            default:
                break;
        }
    }


}
