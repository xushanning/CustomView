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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xusn10 on 2018/3/21.
 *
 * @author xu
 */

public class CustomHeader extends View {
    /**
     * view中间正方形的大小（长宽一致，占屏幕宽度的百分之八）
     */
    private float squareSize;
    /**
     * 整个view的宽度
     */
    private float viewWidth;
    /**
     * 整个view的高度
     */
    private float viewHeight;
    /**
     * 长线的长度
     */
    private float longLineLength;
    /**
     * 短线的长度
     */
    private float shortLineLength;
    /**
     * 线和文字的画笔
     */
    private Paint mPaint;
    /**
     * 里面方形的画笔
     */
    private Paint innerRectFPaint;
    /**
     * 外面大框
     */
    private RectF rectOut;
    /**
     * 中间小框
     */
    private RectF rectMiddle;
    /**
     * 里面小框
     */
    private RectF rectInner;

    /**
     * 默认小方块位置
     */
    private int rectPosition = 0;
    /**
     * 四种位置
     */
    private static final int POSITION_COUNT = 4;

    public CustomHeader(Context context) {
        this(context, null);
    }

    public CustomHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        startAnimation();
    }

    private void startAnimation() {
        //延时0秒后每隔100毫秒，刷新一次UI
        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (rectPosition != POSITION_COUNT) {
                            rectPosition++;
                        } else {
                            rectPosition = 1;
                        }
                        //  postInvalidate();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                    }
                });
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorCustomHeaderLine));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);

        innerRectFPaint = new Paint();
        innerRectFPaint.setAntiAlias(true);
        innerRectFPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorCustomHeaderInner));
        innerRectFPaint.setStyle(Paint.Style.FILL);
        rectOut = new RectF();
        rectMiddle = new RectF();
        rectInner = new RectF();
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
        viewHeight = heightSize / 6f;
        squareSize = widthSize * 4 / 25f;
        longLineLength = squareSize * 7 / 10f;
        //短线是长线的五分之二
        shortLineLength = longLineLength * 2 / 5;
        setMeasuredDimension(width, height / 6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rectOut.set(viewWidth / 2 - squareSize / 2f, viewHeight / 2 - squareSize / 2f, viewWidth / 2 + squareSize / 2f, viewHeight / 2 + squareSize / 2f);
        canvas.drawRoundRect(rectOut, 30, 30, mPaint);
        drawLongLines(canvas);
        drawShortLines(canvas);
        drawInnerRect(canvas);

    }

    /**
     * 画三根长线
     *
     * @param canvas 画布
     */
    private void drawLongLines(Canvas canvas) {
        //三条长线
        canvas.drawLine(viewWidth / 2f - longLineLength / 2f, viewHeight / 2f - squareSize * 24 / 78f, viewWidth / 2f + longLineLength / 2f, viewHeight / 2f - squareSize * 24 / 78f, mPaint);
        canvas.drawLine(viewWidth / 2f - longLineLength / 2f, viewHeight / 2f - squareSize * 14 / 78f, viewWidth / 2f + longLineLength / 2f, viewHeight / 2f - squareSize * 14 / 78f, mPaint);
        canvas.drawLine(viewWidth / 2f - longLineLength / 2f, viewHeight / 2f - squareSize * 4 / 78f, viewWidth / 2f + longLineLength / 2f, viewHeight / 2f - squareSize * 4 / 78f, mPaint);
    }

    /**
     * 画三根短线
     *
     * @param canvas 画布
     */
    private void drawShortLines(Canvas canvas) {
        //三条短线
        canvas.drawLine(viewWidth / 2f + longLineLength / 2f - shortLineLength, viewHeight / 2f + squareSize * 6 / 78f, viewWidth / 2f + longLineLength / 2f, viewHeight / 2f + squareSize * 6 / 78f, mPaint);
        canvas.drawLine(viewWidth / 2f + longLineLength / 2f - shortLineLength, viewHeight / 2f + squareSize * 16 / 78f, viewWidth / 2f + longLineLength / 2f, viewHeight / 2f + squareSize * 16 / 78f, mPaint);
        canvas.drawLine(viewWidth / 2f + longLineLength / 2f - shortLineLength, viewHeight / 2f + squareSize * 26 / 78f, viewWidth / 2f + longLineLength / 2f, viewHeight / 2f + squareSize * 26 / 78f, mPaint);

    }

    /**
     * 画里面的矩形
     *
     * @param canvas 画布
     */
    private void drawInnerRect(Canvas canvas) {
        //里面的长方形框
        rectMiddle.set(viewWidth / 2f - longLineLength / 2f, viewHeight / 2f + squareSize * 6 / 78f, viewWidth / 2f - longLineLength / 2f + squareSize / 3, viewHeight / 2f + squareSize * 26 / 78f);
        canvas.drawRect(rectMiddle, mPaint);

        //框里的长方形
        rectInner.set(viewWidth / 2f - longLineLength / 2f + 3f, viewHeight / 2f + squareSize * 6 / 78f + 3f, viewWidth / 2f - longLineLength / 2f + squareSize / 3 - 3, viewHeight / 2f + squareSize * 26 / 78f - 3);
        canvas.drawRect(rectInner, innerRectFPaint);
    }


}
