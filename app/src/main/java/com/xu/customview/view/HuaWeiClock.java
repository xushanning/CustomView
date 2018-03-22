package com.xu.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
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
 * Created by xusn10 on 2018/3/19.
 *
 * @author xusn10
 *         仿华为时钟
 */

public class HuaWeiClock extends View {
    /**
     * 背景颜色
     */
    private int backgroundColor;
    /**
     * 秒针竖线的颜色
     */
    private int secondLineColor;
    /**
     * 秒针红色点的颜色
     */
    private int secondPointColor;
    /**
     * 时间的颜色
     */
    private int timeColor;

    private Paint mPaint;

    /**
     * 背景宽度
     */
    private int widthBg;
    /**
     * 背景高度
     */
    private int heightBg;
    /**
     * 钟表半径
     */
    private int watchRadius;
    /**
     * 秒针红点的半径
     */
    private static final int SECOND_POINT_RADIUS = 20;
    /**
     * 线最长的长度
     */
    private static final int LINE_MAX_LENGTH = 60;
    /**
     * 线最短的长度
     */
    private static final int LINE_MIN_LENGTH = 30;
    /**
     * 秒刻度白线的数量
     */
    private static final int LINE_COUNT = 120;

    /**
     * 每根针占的角度
     */
    private static final int ONE_LINE_ANGLE = 360 / LINE_COUNT;

    /**
     * 秒针的角度
     */
    private float angle;

    private String time = "00:00";

    /**
     * 长于普通秒针刻度的驼峰一侧的条数
     */
    private static final int LONG_LINE_COUNT = 7;
    /**
     * 一侧7根长针的角度
     */
    private static final int LONG_LINES_ANGLE = ONE_LINE_ANGLE * LONG_LINE_COUNT;
    private RectF rectBg;

    public HuaWeiClock(Context context) {
        this(context, null);
    }

    public HuaWeiClock(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HuaWeiClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HuaWeiClock);
        backgroundColor = typedArray.getColor(R.styleable.HuaWeiClock_backgroundColor, Color.parseColor("#003560"));
        secondLineColor = typedArray.getColor(R.styleable.HuaWeiClock_secondLineColor, Color.WHITE);
        secondPointColor = typedArray.getColor(R.styleable.HuaWeiClock_secondPointColor, Color.parseColor("#CE3228"));
        timeColor = typedArray.getColor(R.styleable.HuaWeiClock_timeColor, Color.WHITE);
        typedArray.recycle();
        initPaint();
        startTime();
    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        rectBg = new RectF();
    }

    /**
     * 获取时间，并通过RxJava实现动画
     */
    private void startTime() {
        //延时0秒后每隔100毫秒，刷新一次UI
        Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Calendar calendar = Calendar.getInstance();
                        int second = calendar.get(Calendar.SECOND);
                        int milliSecond = calendar.get(Calendar.MILLISECOND);
                        angle = (float) (second * 1000 + milliSecond) * 6 / 1000f;
                        SimpleDateFormat minuteFormat = new SimpleDateFormat("HH:mm");
                        time = minuteFormat.format(calendar.getTime());
                        postInvalidate();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                    }
                });
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
                width = widthSize * 3 / 4;
                break;
            case MeasureSpec.EXACTLY:
                width = widthSize * 3 / 4;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                height = heightSize / 2;
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize / 2;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        widthBg = width;
        heightBg = height;
        watchRadius = height * 2 / 5;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBg(canvas);
        drawTickMark(canvas);
        drawSecondPoint(canvas);
        drawTime(canvas);
    }

    /**
     * 画背景
     *
     * @param canvas 画板
     */
    private void drawBg(Canvas canvas) {
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        rectBg.set(0, 0, widthBg, heightBg);
        canvas.drawRect(rectBg, mPaint);
    }

    /**
     * 画刻度线
     *
     * @param canvas 画板
     */
    private void drawTickMark(Canvas canvas) {
        mPaint.setColor(secondLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        for (int i = 0; i < LINE_COUNT; i++) {
            //角度差的绝对值
            float absoluteValue = Math.abs(i * ONE_LINE_ANGLE - angle);
            //求  刻度针线 和 秒针 之间的角度（小于180度）
            float differenceAngle = absoluteValue <= 180 ? absoluteValue : 360 - absoluteValue;
            //大于七根长线刻度的线，那么长度固定
            if (differenceAngle > LONG_LINES_ANGLE) {
                canvas.drawLine(widthBg / 2, heightBg / 2 - watchRadius, widthBg / 2, heightBg / 2 - watchRadius + LINE_MIN_LENGTH, mPaint);
                canvas.rotate(3, widthBg / 2, heightBg / 2);
            } else {
                canvas.drawLine(widthBg / 2, heightBg / 2 - watchRadius - (1 - differenceAngle / LONG_LINES_ANGLE) * (LINE_MAX_LENGTH - LINE_MIN_LENGTH), widthBg / 2, heightBg / 2 - watchRadius + LINE_MIN_LENGTH, mPaint);
                canvas.rotate(3, widthBg / 2, heightBg / 2);
            }
        }
    }

    /**
     * 画秒表的红点
     *
     * @param canvas 画布
     */
    private void drawSecondPoint(Canvas canvas) {
        mPaint.setColor(secondPointColor);
        mPaint.setStyle(Paint.Style.FILL);
        int secondPointRadiusBig = watchRadius - LINE_MIN_LENGTH - SECOND_POINT_RADIUS - 20;
        double pointAngle = angle * Math.PI / 180;
        canvas.drawCircle((float) (widthBg / 2 + secondPointRadiusBig * Math.sin(pointAngle)), (float) (heightBg / 2 - secondPointRadiusBig * Math.cos(pointAngle)), SECOND_POINT_RADIUS, mPaint);
    }

    /**
     * 画时间
     *
     * @param canvas 画布
     */
    private void drawTime(Canvas canvas) {
        mPaint.setColor(timeColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(widthBg / 7);
        Rect bounds = new Rect();
        mPaint.getTextBounds(time, 0, time.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(time, getMeasuredWidth() / 2 - bounds.width() / 2, baseline, mPaint);
    }
}
