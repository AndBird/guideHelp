package com.guidehelp.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

/**
 * 遮罩view
 * */
public class CoverView extends View {
    private static final String TAG = View.class.getSimpleName();

    /**是否可以绘制阴影*/
    private boolean canDraw = false;

    /**待指示view的位置*/
    private int attachViewX, attachViewY;
    /**待指示view的大小*/
    private int attachViewWidth, attachViewHeight;

    private Paint mPaint = new Paint();


    public CoverView(Context context) {
        super(context);
        init();
    }

    public CoverView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        //mPaint.setColor(0x80000000);
        mPaint.setColor(0xCC000000);
        //mPaint.setColor(Color.RED);
        //mPaint.setTextSize(16);
        //mPaint.setTextAlign(Paint.Align.RIGHT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PrintLog.printLog(TAG, "canDraw=" + canDraw);
        if(canDraw) {
            if(attachViewWidth * attachViewHeight <= 0){
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
            }else {
                Path path = new Path();
                int radius = attachViewWidth < attachViewHeight ? attachViewWidth / 2 : attachViewHeight / 2;
                /*if (radius > 60) {
                    radius = 60;
                }*/
                path.addCircle(attachViewX + attachViewWidth / 2, attachViewY + attachViewHeight / 2, radius, Path.Direction.CCW);
                canvas.clipPath(path, Region.Op.DIFFERENCE);

                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
            }
        }
    }

    /**
     * 设置待指示view的位置
     * */
    public void setAttachViewPosition(int x, int y){
       this.attachViewX = x;
       this.attachViewY = y;
    }


    /**设置待指示view的大小*/
    public void setAttachViewSize(int width, int height){
       this.attachViewWidth = width;
       this.attachViewHeight = height;
    }

    /**
     * 重置待指示view的数据
     * */
    public void resetAttachView(){
        this.attachViewX = 0;
        this.attachViewY = 0;
        this.attachViewWidth = 0;
        this.attachViewHeight = 0;
    }

    /**
     * 是否可以显示遮罩
     * */
    public void setCanDraw(final boolean canDraw){
        this.canDraw = canDraw;
    }
}
