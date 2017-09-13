package com.guidehelp.lib;

import com.helpguide.lib.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class ScreenTool {
	private static final String TAG = ScreenTool.class.getSimpleName();
	
    /**
     * pxתdp
     * */
    public static int convertPxToDp(Context context, int px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float logicalDensity = metrics.density;
        int dp = Math.round(px / logicalDensity);
        return dp;
    }

    /**
     * dpתpx
     * */
    public static int convertDpToPx(Context context, int dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }
    
    //��ȡ״̬���߶�
    public static int getStatusBarHeight(Context context) {  
        int result = 0;  
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");  
        if (resourceId > 0) {  
            result = context.getResources().getDimensionPixelSize(resourceId);  
        }  
        return result;  
	 }
    
    //��ȡ״̬���߶�(activity��������Ҫ�Ƴ�һ��ʱ����ܻ�ȡ��)
    public static int getStatusBarHeight(Activity activity){
	    Rect frames = new Rect();  
	    View views =  activity.getWindow().getDecorView();  
	    views.buildDrawingCache();  
	    views.getWindowVisibleDisplayFrame(frames);  
	    int statusBarHeights = frames.top;  
	    return statusBarHeights;
    }
    
    //��ȡͼƬ��ԭʼ�ߴ�
	 public static int getImageResOriginalHeight(Resources resources, int imageRes){
		 if(imageRes <= 0 || resources == null){
				return -1;
			}
		 BitmapFactory.Options opts = new BitmapFactory.Options();  
         opts.inJustDecodeBounds = true;  
        // opts.inSampleSize = 1;    
        BitmapFactory.decodeResource(resources, imageRes, opts);  
        int width = opts.outWidth;  
        int height = opts.outHeight; 
        PrintLog.printLog(TAG, "image bitmap original size:" + width + "," + height);
        return height;
	 }
	 
	//��ȡͼƬ��ԭʼ�ߴ�
	public static int getImageResOriginalWidth(Resources resources, int imageRes){
		if(imageRes <= 0 || resources == null){
			return -1;
		}
	    BitmapFactory.Options opts = new BitmapFactory.Options();  
        opts.inJustDecodeBounds = true;  
        // opts.inSampleSize = 1;    
        BitmapFactory.decodeResource(resources, imageRes, opts);  
        int width = opts.outWidth;  
        int height = opts.outHeight; 
        PrintLog.printLog(TAG, "image bitmap original size:" + width + "," + height);
        return width;
	 }
	 
	 //�ڲ�ͬ�ֻ��ϣ��ߴ粻һ��
	 public static int getImageResHeightInDevice(Resources resources, int imageRes){
			 if(imageRes <= 0 || resources == null){
					return -1;
			 }
			 int width = 0;
			 int height = 0;
			 final BitmapFactory.Options options = new BitmapFactory.Options();
	        // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
	        options.inJustDecodeBounds = false;
	        options.inPreferredConfig = Bitmap.Config.RGB_565; //�����ڴ�����
	
	       Bitmap mBitmap =BitmapFactory.decodeResource(resources, imageRes, options);  
	       width = mBitmap.getWidth();  
	       height = mBitmap.getHeight(); 
	       PrintLog.printLog(TAG, "image bitmap2 :" + width + "," + height);
	
		    /*DisplayMetrics dm = new DisplayMetrics(); // ��ȡ��Ļ�ֱ���
		    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	       width = mBitmap.getScaledHeight(dm);
	       height = mBitmap.getScaledHeight(dm);
	       PrintLog.printLog(TAG, "image bitmap3 :" + width + "," + height);*/
	       if(mBitmap != null){
		       	mBitmap.recycle();
		       	mBitmap = null;
	       }
	       return height;
	}
	 
	 //�ڲ�ͬ�ֻ��ϣ��ߴ粻һ��
	 public static int getImageResWidthInDevice(Resources resources, int imageRes){
			 if(imageRes <= 0 || resources == null){
					return -1;
			 }
			 int width = 0;
			 int height = 0;
			 final BitmapFactory.Options options = new BitmapFactory.Options();
	        // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
	        options.inJustDecodeBounds = false;
	        options.inPreferredConfig = Bitmap.Config.RGB_565; //�����ڴ�����
	
	       Bitmap mBitmap =BitmapFactory.decodeResource(resources, imageRes, options);  
	       width = mBitmap.getWidth();  
	       height = mBitmap.getHeight(); 
	       PrintLog.printLog(TAG, "image bitmap2 :" + width + "," + height);
	
		  
	       /*DisplayMetrics dm = new DisplayMetrics(); // ��ȡ��Ļ�ֱ���
		    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	       width = mBitmap.getScaledHeight(dm);
	       height = mBitmap.getScaledHeight(dm);
	       PrintLog.printLog(TAG, "image bitmap3 :" + width + "," + height);*/
	       if(mBitmap != null){
		       	mBitmap.recycle();
		       	mBitmap = null;
	       }
	       return width;
	}
	
	//��ȡtextview�ĳߴ�
	public static int getTextViewWidth(final Context context, final TextView textView, String content){
		//int strWidth = (int) Math.ceil(textView.getPaint().measureText(content)) + getRconvertDpToPx(context, 3) * 2;//textView������padding
		int strWidth = (int) Math.ceil(textView.getPaint().measureText(content)) + (int)context.getResources().getDimension(R.dimen.textview_padding) * 2;//textView������padding
		PrintLog.printLog(TAG, "textView width =" + strWidth);
		return strWidth;
	}
	
	//��ȡTextview���и�
	public static int getTextViewHeight(final Context context, final TextView textView, String content){
		FontMetrics fm = textView.getPaint().getFontMetrics();
		//int mFontHeight = (int) Math.ceil(fm.bottom - fm.top)  + convertDpToPx(context, 3) * 2;//����textView������padding
		int mFontHeight = (int) Math.ceil(fm.bottom - fm.top)  + (int)context.getResources().getDimension(R.dimen.textview_padding) * 2;//����textView������padding
		PrintLog.printLog(TAG, "textView height =" + mFontHeight);
		return mFontHeight;
	}
}
