package com.example.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

/**
 * Created by john.wick on 2024/2/1 11:21.
 */
public class RunWayView extends View {

    private static final String FIXED_TEXT_SLOPE = "坡度";

    private static final String FIXED_TEXT_LAPS = "圈数";

    private static final String FIXED_TEXT_SPEED = "速度";

    private static final float OUTER_RING_OFFSET = 5F;

    private Paint mOuterRingPaint;

    private Paint mInnerRingPaint;

    private Paint mProgressPaint;

    private Paint mCircleIndicatorPaint;

    private Paint mAxisPaint;

    private TextPaint mValueTextPaint;

    private TextPaint mFixedTextPaint;

    private final PathEffect mPathEffect = new CornerPathEffect(10);

    private final Path mOuterRingPath = new Path();

    private final RectF mOuterRingLeftArcRectF = new RectF();

    private final RectF mOuterRingRightArcRectF = new RectF();

    private final Path mInnerRingPath = new Path();

    private final RectF mInnerRingLeftArcRectF = new RectF();

    private final RectF mInnerRingRightArcRectF = new RectF();

    private final Path mProgressPath = new Path();

    private final RectF mProgressPathBounds = new RectF();

    private final Path mCurrentProgressPath = new Path();

    private final PathMeasure mPathMeasure = new PathMeasure();

    private final RectF mProgressLeftArcRectF = new RectF();

    private final RectF mProgressRightArcRectF = new RectF();

    private int mOuterRingColor = Color.parseColor("#1aFFFFFF");

    private int mInnerRingColor = Color.parseColor("#1aFFFFFF");

    private float mOuterRingWidth = 1.0F;

    private float mInnerRingWidth = 1.0F;

    private float mProgressRingWidth = 1.0F;

    private int mProgressStartColor = Color.parseColor("#ffEE6767");

    private int mProgressCenterColor = Color.parseColor("#ffF03072");

    private int mProgressEndColor = Color.parseColor("#FFD03D3D");

    private int mProgressIndicatorShadowColor = Color.WHITE;

    private int mProgressIndicatorColor = Color.parseColor("#ffEE6767");

    private float mProgressIndicatorRadius = 3;

    private int mValueTextColor = Color.WHITE;

    private float mValueTextSize = 20F;

    private int mValueTextStyle = 0;

    private float mValueTextBaseline;

    private int mFixedTextColor = Color.parseColor("#ffEE6767");

    private float mFixedTextSize = 8F;

    private int mFixedTextStyle = 0;

    /**
     * 坡度
     */
    private float mSlope = 0.0F;

    /**
     * 圈数
     */
    private int mLaps = 0;

    /**
     * 速度
     */
    private float mSpeed = 0.0F;

    private int mProgress = 0;

    private int mMaxProgress = 100;

    public RunWayView(Context context) {
        this(context, null);
    }

    public RunWayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RunWayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RunWayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mOuterRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOuterRingPaint.setStrokeWidth(mOuterRingWidth);
        mOuterRingPaint.setColor(mOuterRingColor);
        mOuterRingPaint.setStyle(Paint.Style.STROKE);
        mOuterRingPaint.setPathEffect(mPathEffect);

        mInnerRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerRingPaint.setStrokeWidth(mInnerRingWidth);
        mInnerRingPaint.setColor(mInnerRingColor);
        mInnerRingPaint.setStyle(Paint.Style.STROKE);
        mInnerRingPaint.setPathEffect(mPathEffect);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setStrokeWidth(mProgressRingWidth);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setPathEffect(mPathEffect);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        mCircleIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleIndicatorPaint.setStyle(Paint.Style.FILL);

        mAxisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAxisPaint.setStyle(Paint.Style.STROKE);

        mValueTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mValueTextPaint.setStyle(Paint.Style.FILL);
        mValueTextPaint.setColor(mValueTextColor);
        mValueTextPaint.setTextSize(mValueTextSize);
        mValueTextPaint.setTypeface(Typeface.create((Typeface) null, mValueTextStyle));
        mValueTextBaseline = (mValueTextPaint.ascent() + mValueTextPaint.descent()) / 2;

        mFixedTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mFixedTextPaint.setStyle(Paint.Style.FILL);
        mFixedTextPaint.setColor(mFixedTextColor);
        mFixedTextPaint.setTextSize(mFixedTextSize);
        mFixedTextPaint.setTypeface(Typeface.create((Typeface) null, mFixedTextStyle));
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RunWayView);

        mOuterRingColor = ta.getColor(R.styleable.RunWayView_rw_outerRingColor, mOuterRingColor);
        mOuterRingWidth = ta.getDimension(R.styleable.RunWayView_rw_outerRingWidth, mOuterRingWidth);

        mInnerRingColor = ta.getColor(R.styleable.RunWayView_rw_innerRingColor, mInnerRingColor);
        mInnerRingWidth = ta.getDimension(R.styleable.RunWayView_rw_innerRingWidth, mInnerRingWidth);

        mProgressStartColor = ta.getColor(R.styleable.RunWayView_rw_progressRingStartColor, mProgressStartColor);
        mProgressCenterColor = ta.getColor(R.styleable.RunWayView_rw_progressRingCenterColor, mProgressCenterColor);
        mProgressEndColor = ta.getColor(R.styleable.RunWayView_rw_progressRingEndColor, mProgressEndColor);
        mProgressRingWidth = ta.getDimension(R.styleable.RunWayView_rw_progressRingWidth, mProgressRingWidth);

        mProgressIndicatorShadowColor = ta.getColor(R.styleable.RunWayView_rw_progressIndicatorShadowColor, mProgressIndicatorShadowColor);
        mProgressIndicatorColor = ta.getColor(R.styleable.RunWayView_rw_progressIndicatorColor, mProgressIndicatorColor);
        mProgressIndicatorRadius = ta.getDimension(R.styleable.RunWayView_rw_progressIndicatorRadius, mProgressIndicatorRadius);

        mProgress = ta.getInt(R.styleable.RunWayView_rw_progress, mProgress);
        mMaxProgress = ta.getInt(R.styleable.RunWayView_rw_max_progress, mMaxProgress);

        mSlope = ta.getFloat(R.styleable.RunWayView_rw_slope, 0.0F);
        mLaps = ta.getInt(R.styleable.RunWayView_rw_laps, 0);
        mSpeed = ta.getFloat(R.styleable.RunWayView_rw_speed, 0.0F);

        mValueTextColor = ta.getColor(R.styleable.RunWayView_rw_valueTextColor, mValueTextColor);
        mValueTextSize = ta.getDimension(R.styleable.RunWayView_rw_valueTextSize, mValueTextSize);
        mValueTextStyle = ta.getInt(R.styleable.RunWayView_rw_valueTextStyle, mValueTextStyle);

        mFixedTextColor = ta.getColor(R.styleable.RunWayView_rw_fixedTextColor, mFixedTextColor);
        mFixedTextSize = ta.getDimension(R.styleable.RunWayView_rw_fixedTextSize, mFixedTextSize);
        mFixedTextStyle = ta.getInt(R.styleable.RunWayView_rw_fixedTextStyle, mFixedTextStyle);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightSize > widthSize) {
            throw new IllegalArgumentException("height must be not greater than width.");
        }

        if (isInEditMode()) {
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(500, MeasureSpec.EXACTLY);
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mOuterRingPath.reset();
        mInnerRingPath.reset();
        mProgressPath.reset();

        computeOuterRing(w, h);
        computeInnerRing(h);
        computeProgressRing();
    }

    private void computeProgressRing() {
        mProgressLeftArcRectF.setEmpty();
        mProgressRightArcRectF.setEmpty();

        // 左边的圆弧
        float leftArcLeft = (mOuterRingLeftArcRectF.left + mInnerRingLeftArcRectF.left) / 2;
        float leftArcTop = (mOuterRingLeftArcRectF.top + mInnerRingLeftArcRectF.top) / 2;
        float leftArcRight = (mOuterRingLeftArcRectF.right + mInnerRingLeftArcRectF.right) / 2;
        float leftArcBottom = (mOuterRingLeftArcRectF.bottom + mInnerRingLeftArcRectF.bottom) / 2;
        mProgressLeftArcRectF.set(leftArcLeft, leftArcTop, leftArcRight, leftArcBottom);

        // 右边的圆弧
        float rightArcLeft = (mOuterRingRightArcRectF.left + mInnerRingRightArcRectF.left) / 2;
        float rightArcTop = (mOuterRingRightArcRectF.top + mInnerRingRightArcRectF.top) / 2;
        float rightArcRight = (mOuterRingRightArcRectF.right + mInnerRingRightArcRectF.right) / 2;
        float rightArcBottom = (mOuterRingRightArcRectF.bottom + mInnerRingRightArcRectF.bottom) / 2;
        mProgressRightArcRectF.set(rightArcLeft, rightArcTop, rightArcRight, rightArcBottom);

        // 顶部的线的起始坐标
        mProgressPath.moveTo(mProgressLeftArcRectF.centerX(), mProgressLeftArcRectF.top);

        // 顶部的线与右边的圆弧
        mProgressPath.arcTo(mProgressRightArcRectF, -90F, 180F);

        // 底部的线与左边的圆弧
        mProgressPath.arcTo(mProgressLeftArcRectF, 90F, 180F);

        mPathMeasure.setPath(mProgressPath, false);

        mProgressPathBounds.setEmpty();
        mProgressPath.computeBounds(mProgressPathBounds, false);

        Shader progressShader = new SweepGradient(
                mProgressPathBounds.centerX(),
                mProgressPathBounds.centerY(),
                new int[]{mProgressStartColor, mProgressCenterColor, mProgressEndColor},
                new float[]{0F, 0.6F, 1F});

        Matrix matrix = new Matrix();
        // 计算起始点与路径框中心点的夹角角度
        double theta = Math.atan2(mProgressLeftArcRectF.top - mProgressPathBounds.centerY(), mProgressLeftArcRectF.centerX() - mProgressPathBounds.centerX());
        float angle = (float) (theta * (180 / Math.PI));
        matrix.setRotate(angle - 0.5F, mProgressPathBounds.centerX(), mProgressPathBounds.centerY());
        progressShader.setLocalMatrix(matrix);

        mProgressPaint.setShader(progressShader);
    }

    private void computeInnerRing(int height) {
        mInnerRingLeftArcRectF.setEmpty();
        mInnerRingRightArcRectF.setEmpty();

        // 半径
        float radius = (height - getPaddingTop() - getPaddingBottom()) * 1.0F / 4;

        // 左边的圆弧
        float leftArcLeft = mOuterRingLeftArcRectF.centerX() - radius;
        float leftArcTop = mOuterRingLeftArcRectF.centerY() - radius;
        float leftArcRight = mOuterRingLeftArcRectF.centerX() + radius;
        float leftArcBottom = mOuterRingLeftArcRectF.centerY() + radius;
        mInnerRingLeftArcRectF.set(leftArcLeft, leftArcTop, leftArcRight, leftArcBottom);

        // 右边的圆弧
        float rightArcLeft = mOuterRingRightArcRectF.centerX() - radius;
        float rightArcTop = mOuterRingRightArcRectF.centerY() - radius;
        float rightArcRight = mOuterRingRightArcRectF.centerX() + radius;
        float rightArcBottom = mOuterRingRightArcRectF.centerY() + radius;
        mInnerRingRightArcRectF.set(rightArcLeft, rightArcTop, rightArcRight, rightArcBottom);

        // 顶部的线的起始坐标
        mInnerRingPath.moveTo(mInnerRingLeftArcRectF.centerX(), mInnerRingLeftArcRectF.top);

        // 顶部的线与右边的圆弧
        mInnerRingPath.arcTo(mInnerRingRightArcRectF, -90F, 180F);

        // 底部的线与左边的圆弧
        mInnerRingPath.arcTo(mInnerRingLeftArcRectF, 90F, 180F);
    }

    private void computeOuterRing(int width, int height) {
        mOuterRingLeftArcRectF.setEmpty();
        mOuterRingRightArcRectF.setEmpty();

        // 直径
        int diameter = height - getPaddingTop() - getPaddingBottom();

        // 左边的圆弧
        float leftArcLeft = getPaddingStart() + OUTER_RING_OFFSET;
        float leftArcTop = getPaddingTop() + OUTER_RING_OFFSET;
        float leftArcRight = leftArcLeft + diameter;
        float leftArcBottom = height - getPaddingBottom() - OUTER_RING_OFFSET;
        mOuterRingLeftArcRectF.set(leftArcLeft, leftArcTop, leftArcRight, leftArcBottom);

        // 右边的圆弧
        float rightArcLeft = width - getPaddingEnd() - diameter - OUTER_RING_OFFSET;
        float rightArcTop = getPaddingTop() + OUTER_RING_OFFSET;
        float rightArcRight = rightArcLeft + diameter;
        float rightArcBottom = height - getPaddingBottom() - OUTER_RING_OFFSET;
        mOuterRingRightArcRectF.set(rightArcLeft, rightArcTop, rightArcRight, rightArcBottom);

        // 顶部的线的起始坐标
        mOuterRingPath.moveTo(mOuterRingLeftArcRectF.centerX(), mOuterRingLeftArcRectF.top);

        // 顶部的线与右边的圆弧
        mOuterRingPath.arcTo(mOuterRingRightArcRectF, -90F, 180F);

        // 底部的线与左边的圆弧
        mOuterRingPath.arcTo(mOuterRingLeftArcRectF, 90F, 180F);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if (isInEditMode()) {
//            canvas.drawColor(Color.parseColor("#ff132855"));
        }

        int width = getWidth();
        int height = getHeight();

        drawOuterRing(canvas);
        drawInnerRing(canvas);
        drawProgressRing(canvas);
        drawProgressIndicator(canvas);
        drawText(canvas, width, height);

//        drawAxis(canvas, width, height);
    }

    private void drawAxis(Canvas canvas, int width, int height) {
        mAxisPaint.setColor(Color.RED);
        DashPathEffect effect = new DashPathEffect(new float[]{5F, 2F}, 0);

        float offset = 5F;
        float centerX = mProgressPathBounds.centerX();
        float centerY = mProgressPathBounds.centerY();

        // X轴
        mAxisPaint.setStrokeWidth(2F);
        mAxisPaint.setPathEffect(effect);
        canvas.drawLine(0F, centerY, width, centerY, mAxisPaint);

        mAxisPaint.setPathEffect(null);
        canvas.drawLine(width, centerY, width - offset, centerY - offset, mAxisPaint);
        canvas.drawLine(width, centerY, width - offset, centerY + offset, mAxisPaint);

        // Y轴
        mAxisPaint.setPathEffect(effect);
        canvas.drawLine(centerX, 0F, centerX, height, mAxisPaint);

        mAxisPaint.setPathEffect(null);
        canvas.drawLine(centerX, height, centerX - offset, height - offset, mAxisPaint);
        canvas.drawLine(centerX, height, centerX + offset, height - offset, mAxisPaint);

        // 中心点与起始点连线
        mAxisPaint.setColor(Color.YELLOW);
        mAxisPaint.setPathEffect(effect);
        Path linePath = new Path();
        linePath.moveTo(mProgressPathBounds.centerX(), mProgressPathBounds.centerY());
        linePath.lineTo(mProgressLeftArcRectF.centerX(), mProgressLeftArcRectF.top);
        canvas.drawPath(linePath, mAxisPaint);

        // 夹角线
        PathMeasure measure = new PathMeasure(linePath, false);
        float[] pos = new float[2];
        measure.getPosTan(measure.getLength() * 0.15F, pos, null);

        Path angularPath = new Path();
        float offsetX = 90F;
        angularPath.moveTo(mProgressPathBounds.centerX() + offsetX, mProgressPathBounds.centerY());
        angularPath.quadTo((mProgressPathBounds.centerX() + offsetX + pos[0]) / 2, (mProgressPathBounds.centerY() + pos[1]) / 4, pos[0], pos[1]);
        canvas.drawPath(angularPath, mAxisPaint);
    }

    private void drawText(Canvas canvas, int width, int height) {

        float baseline = getPaddingTop() + (height - getPaddingTop() - getPaddingBottom()) * 1.0F / 2;

        // 坡度数据
        String slopeText = format(mSlope);
        float slopeTextWidth = mValueTextPaint.measureText(slopeText);
        canvas.drawText(slopeText, mInnerRingLeftArcRectF.right - slopeTextWidth / 2, baseline - mValueTextBaseline, mValueTextPaint);

        // 坡度文本
        canvas.drawText(FIXED_TEXT_SLOPE, mInnerRingLeftArcRectF.right + slopeTextWidth / 2 + 5, baseline - mValueTextBaseline, mFixedTextPaint);

        // 圈数数据
        String lapsText = String.valueOf(mLaps);
        float lapsTextWidth = mValueTextPaint.measureText(lapsText);
        canvas.drawText(lapsText, width * 1.0F / 2 - lapsTextWidth / 2, baseline, mValueTextPaint);

        // 圈数文本
        float fixedLapsTextWidth = mFixedTextPaint.measureText(FIXED_TEXT_LAPS);
        canvas.drawText(FIXED_TEXT_LAPS, width * 1.0F / 2 - fixedLapsTextWidth / 2, baseline - mFixedTextPaint.ascent() + 5, mFixedTextPaint);

        // 速度数据
        String speedText = format(mSpeed);
        float speedTextWidth = mValueTextPaint.measureText(speedText);
        canvas.drawText(speedText, mInnerRingRightArcRectF.left - speedTextWidth, baseline - mValueTextBaseline, mValueTextPaint);

        // 速度文本
        canvas.drawText(FIXED_TEXT_SPEED, mInnerRingRightArcRectF.left + 5, baseline - mValueTextBaseline, mFixedTextPaint);
    }

    private void drawProgressRing(Canvas canvas) {
        mCurrentProgressPath.reset();
        float progress = mProgress * 1.0F / mMaxProgress;
        float distance = mPathMeasure.getLength() * progress;
        if (mPathMeasure.getSegment(0F, distance, mCurrentProgressPath, true)) {
            canvas.drawPath(mCurrentProgressPath, mProgressPaint);
        }
    }

    private void drawProgressIndicator(Canvas canvas) {
        float distance = mPathMeasure.getLength() * (mProgress * 1.0F / mMaxProgress);
        float[] pos = new float[2];
        mPathMeasure.getPosTan(distance, pos, null);

        float cx = pos[0];
        float cy = pos[1];

        mCircleIndicatorPaint.setColor(mProgressIndicatorShadowColor);
        canvas.drawCircle(cx, cy, mProgressIndicatorRadius, mCircleIndicatorPaint);

        mCircleIndicatorPaint.setColor(mProgressIndicatorColor);
        canvas.drawCircle(cx, cy, mProgressIndicatorRadius - 1F, mCircleIndicatorPaint);
    }

    private void drawInnerRing(Canvas canvas) {
        canvas.drawPath(mInnerRingPath, mInnerRingPaint);
    }

    private void drawOuterRing(Canvas canvas) {
        canvas.drawPath(mOuterRingPath, mOuterRingPaint);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        if (mProgress >= mMaxProgress) {
            aLapCompleted();
        }
        invalidate();
    }

    public int getProgress() {
        return mProgress;
    }

    public void setSlope(float slope) {
        mSlope = slope;
        invalidate();
    }

    public float getSlope() {
        return mSlope;
    }

    public void setLaps(int laps) {
        mLaps = laps;
        invalidate();
    }

    public int getLaps() {
        return mLaps;
    }

    public void setSpeed(float speed) {
        mSpeed = speed;
        invalidate();
    }

    public float getSpeed() {
        return mSpeed;
    }

    public void reset() {
        mSlope = 0.0F;
        mLaps = 0;
        mSpeed = 0.0F;
        mProgress = 0;
        invalidate();
    }

    private void aLapCompleted() {
        mProgress = 0;
        mLaps++;
    }

    private String format(float value) {
        return String.format(Locale.CHINESE, "%.1f", value);
    }
}
