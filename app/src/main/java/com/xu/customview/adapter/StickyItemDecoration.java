package com.xu.customview.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author 言吾許
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 背景画笔
     */
    private Paint bgPaint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    private Paint.FontMetrics mFontMetrics;
    private List<StickyGroupInfo> groupInfos;
    /**
     * 组名的高度
     */
    private int groupTitleHeight = 50;
    /**
     * 普通分割线的高度
     */
    private int dividerHeight = 2;

    public StickyItemDecoration(List<StickyGroupInfo> groupInfos) {
        this.groupInfos = groupInfos;
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.GREEN);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLACK);

        mFontMetrics = textPaint.getFontMetrics();
    }

    //设置 ItemView 之间的间距
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //获取item在list中的位置
        int position = parent.getChildAdapterPosition(view);
        StickyGroupInfo groupInfo = groupInfos.get(position);
        //如果这个item是组内的第一条item，那么将撑开一个矩形，来绘制吸附header
        if (groupInfo != null && groupInfo.isShowTitle()) {
            outRect.top = groupTitleHeight;
        } else {
            //否则矩形的高度为分割线的高度
            outRect.top = dividerHeight;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //获取item的数量
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            //获取item在list中的位置
            int position = parent.getChildAdapterPosition(view);
            StickyGroupInfo stickyGroupInfo = groupInfos.get(position);
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            //不是第一个
            if (i != 0) {
                if (stickyGroupInfo.isShowTitle()) {
                    int top = view.getTop() - groupTitleHeight;
                    int bottom = view.getTop();
                    c.drawRect(left, top, right, bottom, bgPaint);
                    //绘制文字
                    float textX = left;
                    float textY = bottom - mFontMetrics.descent;
                    c.drawText(stickyGroupInfo.getTitle(), textX, textY, textPaint);
                } else {
                    //只画分割线
                    float divideLineLeft = parent.getPaddingLeft();
                    float divideLineRight = parent.getWidth();
                    float divideLineTop = view.getTop() - dividerHeight;
                    float divideLineBottom = view.getTop();
                    c.drawRect(divideLineLeft, divideLineTop, divideLineRight, divideLineBottom, bgPaint);
                }

            } else {
                //是可视屏幕中第一个，那么不管他是不是本小组第一个，都得绘制header
                int top = parent.getPaddingTop();
                int bottom = top + groupTitleHeight;
                c.drawRect(left, top, right, bottom, bgPaint);
                float textX = left;
                float textY = bottom - mFontMetrics.descent;
                c.drawText(stickyGroupInfo.getTitle(), textX, textY, textPaint);
            }
        }
    }
}
