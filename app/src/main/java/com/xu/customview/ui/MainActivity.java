package com.xu.customview.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xu.customview.R;

/**
 * @author xusn10
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent(MainActivity.this, CustomViewActivity.class);
        //仿华为时钟
        findViewById(R.id.bt_clock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("viewType", CustomViewActivity.TYPE_CLOCK);
                startActivity(intent);
            }
        });
        //仿头条刷新header
        findViewById(R.id.bt_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("viewType", CustomViewActivity.TYPE_HEADER);
                startActivity(intent);
            }
        });

    }
}
