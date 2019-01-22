package com.tyh.java.sortstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/18
 * 描述:
 */
public class SortView extends View {

    int ballNum = 10;//球的最大个数
    float ballDir = 50;//球的直径
    float gapX = 30;//球的间隙


    private Paint mPaint;
    private int width;
    private int height;

    public SortView(Context context) {
        this(context, null);
    }

    public SortView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setDither(false);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = measureWidth(widthMeasureSpec);
        height = measureHeight(width, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int width, int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
            //如果球的边界超出了,要调整球的直径
            if (ballDir > size) {
                ballDir = size;
                gapX = (int) ((width - ballDir * ballNum) * 1.0f / (ballNum - 1));
            }
        } else {
            if (ballDir < size) {
                result = (int) ballDir;
            } else {
                result = size;
                if (ballDir > size) {
                    ballDir = size;
                    gapX = (int) ((width - ballDir * ballNum) * 1.0f / (ballNum - 1));
                }
            }
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
            int ballWidth = getBallWidth();
            if (ballWidth != size) {
                ballDir = size/(1.3f*ballNum-0.3f);
                gapX = ballDir*0.3f;
            }
        } else {
            //计算球要占用的宽度
            result = getBallsWidth(size);
        }
        return result;
    }

    private int getBallsWidth(int size) {
        int width = getBallWidth();
        while (width > size) {
            if (ballDir > 10) {
                ballDir -= 1;
            }

            if (gapX > 1) {
                gapX -= 1;
            }
            width = getBallWidth();
        }
        return width;
    }

    private int getBallWidth() {
        return (int) (ballDir * ballNum + gapX * (ballNum - 1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float centerY = height / 2f;
        float centerX;
        for (int i = 0; i < ballNum; i++) {
            centerX = i * (gapX + ballDir) + ballDir / 2f;
            canvas.drawCircle(centerX, centerY, ballDir / 2f, mPaint);
        }
    }
}
