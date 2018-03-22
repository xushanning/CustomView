package com.xu.customview.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xu.customview.R;
import com.xu.customview.view.HuaWeiClock;

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
                findViewById(R.id.customHeader).setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


}
