package com.xu.customview.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author 言吾許
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 分割线的高度
     */
    private float divideLineHeight;
    /**
     * 画笔
     */
    private Paint paint;

    public StickyItemDecoration() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
    }

    //设置 ItemView 之间的间距
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //设定底部边距为2
        outRect.bottom = 2;
        //要绘制的区域的颜色要和Rect的高度一样
        divideLineHeight = 2;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //获取item的数量
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            float divideLineLeft = parent.getPaddingLeft();
            float divideLineRight = parent.getWidth();
            float divideLineTop = view.getTop() - divideLineHeight;
            float divideLineBottom = view.getTop();
            c.drawRect(divideLineLeft, divideLineTop, divideLineRight, divideLineBottom, paint);
        }
    }
}
