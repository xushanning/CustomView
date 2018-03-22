package com.xu.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.xu.customview.R;

/**
 * Created by xusn10 on 2018/3/21.
 *
 * @author xu
 */

public class CustomHeader extends View {
    /**
     * view中间正方形的大小（长宽一致，占屏幕宽度的百分之八）
     */
    private int squareSize;
    /**
     * 整个view的宽度
     */
    private int viewWidth;
    /**
     * 整个view的高度
     */
    private int viewHeight;
    /**
     * 长线的长度
     */
    private int longLineLength;
    /**
     * 短线的长度
     */
    private int shortLineLength;
    private Paint mPaint;
    private RectF rectF;


    public CustomHeader(Context context) {
        this(context, null);
    }

    public CustomHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorCustomHeader));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                width = widthSize;
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:

                break;
            default:
                break;
        }
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                width = widthSize;
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:

                break;
            default:
                break;
        }

        viewWidth = widthSize;
        viewHeight = heightSize / 6;
        squareSize = widthSize * 2 / 25;
        longLineLength = squareSize * 7 / 10;
        //短线是长线的五分之二
        shortLineLength = longLineLength * 2 / 5;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rectF.set(viewWidth / 2 - squareSize, viewHeight / 2 - squareSize, viewWidth / 2 + squareSize, viewHeight / 2 + squareSize);
        canvas.drawRoundRect(rectF, 40, 40, mPaint);

        canvas.drawLine(viewWidth / 2 - longLineLength / 2, viewHeight / 2, viewWidth / 2 + longLineLength / 2, viewHeight / 2, mPaint);
    }


}
