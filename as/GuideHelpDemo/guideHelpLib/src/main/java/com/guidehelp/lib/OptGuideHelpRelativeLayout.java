package com.guidehelp.lib;

import java.util.ArrayList;
import java.util.List;

import com.guidehelp.lib.bean.GuideHelpTaskInfo;
import com.guidehelp.lib.bean.ShowPositionType;
import com.helpguide.lib.R;
import android.app.Activity;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * 
 * 功能:帮助引导提示
 * 实现:通过RelativeLayout控制
 * 
 * 说明:本类只是用于想在tip的4个方向上都可以显示箭头而创建，用RelativeLayout实现了
 * 		纵向的箭头(线性布局在4个方向上显示箭头，可以用动态添加控件实现，也可以用布局文
 * 		件，只是布局文件相对于RelativeLayout而言，较复杂)，只是思路还没有完成,仅搭建了设计初步结构，仅供参考
 * */
public class OptGuideHelpRelativeLayout extends BaseOptGuideHelp{
	private final String TAG = OptGuideHelpRelativeLayout.class.getSimpleName();
	
	private Activity activity;
	
	//提示窗
	private PopupWindow tipsWindow = null;
	private View tipsWindowRootView;
	private int statusBarHeights = 0; //状态栏高度
	private int screenHeight = 0; //状态栏高度
	private int screenWidth = 0;
	
	
	//提示内容
	private RelativeLayout tipLayout;
	private ImageView imageView;
	private ImageView arrowImageView;
	
	//显示队列
	private List<GuideHelpTaskInfo> helpList;
	private int curShowIndex = 0;//当前显示index
	
	
	private GuideHelpShowFinishListener guideHelpShowFinishListener;
	
	
	public OptGuideHelpRelativeLayout(Activity activity) {
		this.activity = activity;
		this.helpList = new ArrayList<GuideHelpTaskInfo>();
	}

	 @Override
	protected void initGuideHelpWindow() {
		try {
			if(statusBarHeights <= 0 || screenHeight <= 0 || screenWidth <= 0){
				// 获取状态栏高度  
			    statusBarHeights = ScreenTool.getStatusBarHeight(activity); 
			    PrintLog.printLog(TAG, "initGuideHelpWindow statusBarHeight=" + statusBarHeights);
			    if(statusBarHeights <= 0){
			    	statusBarHeights = ScreenTool.getStatusBarHeight(activity.getApplicationContext());
			    }
		    
			    DisplayMetrics dm = new DisplayMetrics(); // 获取屏幕分辨率
			    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			    screenHeight = dm.heightPixels;
			    screenWidth = dm.widthPixels;
			    PrintLog.printLog(TAG, "screenHeight=" + screenHeight + " ,screenWidth=" + screenWidth + ",statusBarHeights=" + statusBarHeights);
			}
			
			tipsWindowRootView =  LayoutInflater.from(activity).inflate(R.layout.user_opt_guide_help_relative, null);
			tipLayout = (RelativeLayout) tipsWindowRootView.findViewById(R.id.optHelpLay);
			imageView = (ImageView) tipsWindowRootView.findViewById(R.id.imageView1);
			arrowImageView = (ImageView) tipsWindowRootView.findViewById(R.id.arrowImage);
			
			tipsWindowRootView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					curShowIndex++;
					if(curShowIndex < helpList.size()){
						showByOrder();
					}else{
						hideGuideHelp();
						showFinish(true);
					}
				}
			});
			
			int height = screenHeight - statusBarHeights;
		    if(height > 0){
		    	tipsWindow = new PopupWindow(tipsWindowRootView, LinearLayout.LayoutParams.MATCH_PARENT, height);
		    }else{
		    	tipsWindow = new PopupWindow(tipsWindowRootView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		    }
		    
			tipsWindow.setFocusable(true);
			//不可设置背景，背景设置后将不会监听到返回键
	        //ColorDrawable dw = new ColorDrawable(0x00000000);//去除背景黑色
	        //tipsWindow.setBackgroundDrawable(dw);
	        tipsWindow.setOutsideTouchable(false);
	        
	        tipsWindowRootView.setFocusableInTouchMode(true);
	        tipsWindowRootView.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					 //PrintLog.printLog(TAG, "key event");
					 //禁止返回退出引导界面
					 if (keyCode == KeyEvent.KEYCODE_BACK){
						 return true;
					 }
					 return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void showGuideHelp() {
		//非点击事件中调用， 直接显示popupwindow会出问题
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					if(helpList == null || helpList.size() <= 0){
						PrintLog.printLog(TAG, "没有设置帮助提示内容");
						showFinish(false);
						return ;
					}
					if(tipsWindow == null){
						initGuideHelpWindow();
					}
					//tipsWindow.setAnimationStyle(R.style.Video_Filter_PopupAnimation);
					tipsWindow.showAtLocation(tipsWindowRootView, Gravity.NO_GRAVITY, 0, statusBarHeights);
				
					curShowIndex = 0;
					showByOrder();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 100);
	}

	@Override
	public void hideGuideHelp() {
		if(tipsWindow != null && tipsWindow.isShowing()){
			tipsWindow.dismiss();
			tipsWindow = null;
		}
		removeAllGuideHelpTask();
	}

	@Override
	public void addGuideHelpTask(GuideHelpTaskInfo guideHelpTaskInfo) {
		if(guideHelpTaskInfo == null){
			return ;
		}
		this.helpList.add(guideHelpTaskInfo);
	}

	@Override
	public void removeAllGuideHelpTask() {
		if(helpList != null){
			helpList.clear();
		}
	}

	
	//设置tipLayout的位置,由attachView决定，margin不生效
	@Override
	protected void buildGuideLay(GuideHelpTaskInfo guideHelpTaskInfo) {
		 PrintLog.printLog(TAG, "buildTipLayoutParams statusHeight=" + statusBarHeights);
	 	//设置垂直方向上的位置
        LayoutParams lp = (LayoutParams) tipLayout.getLayoutParams();
        int[] pos = null;
        int width = 0;
        int height = 0;
        if(guideHelpTaskInfo.attachView != null){
        	lp.gravity = Gravity.LEFT | Gravity.TOP;
        	//测量attach的位置
        	pos = new int[2];
        	guideHelpTaskInfo.attachView.getLocationInWindow(pos);
        	PrintLog.printLog(TAG, "attachView pos=" + pos[0] + "," + pos[1]);
        	
        	//测量attach的大小
        	int intw = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
        	int inth= View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
        	guideHelpTaskInfo.attachView.measure(intw, inth); 
        	width = guideHelpTaskInfo.attachView.getMeasuredWidth(); 
        	height = guideHelpTaskInfo.attachView.getMeasuredHeight();
        	PrintLog.printLog(TAG, "attachView size=" + width + "," + height);
        	
        	if(guideHelpTaskInfo.showPositionType == ShowPositionType.Above){
        		//上部显示
        		int imgResHeight = ScreenTool.getImageResHeightInDevice(activity.getResources(), guideHelpTaskInfo.imageRes);
        		lp.topMargin = pos[1] - imgResHeight - statusBarHeights;
        	}else{ 
        		if(guideHelpTaskInfo.showPositionType == ShowPositionType.Below){
		        	lp.topMargin = pos[1] + height - statusBarHeights;
        		}else{
        			//居中重叠
        			int imgResHeight = ScreenTool.getImageResHeightInDevice(activity.getResources(), guideHelpTaskInfo.imageRes);
        			lp.topMargin = pos[1] + (height - imgResHeight) / 2 - statusBarHeights;
        		}
        	}
        }else{
        	if(guideHelpTaskInfo.topShowY != 0){
        		lp.gravity = Gravity.TOP;
        		lp.topMargin = guideHelpTaskInfo.topShowY;
        	}else{
        		lp.gravity = Gravity.BOTTOM;
        		lp.bottomMargin = guideHelpTaskInfo.bottomShowY;
        	}
        }
        tipLayout.setLayoutParams(lp);
        
        buildImageView(guideHelpTaskInfo, pos, width, height);
        buildArrowImage(guideHelpTaskInfo, pos, width, height);
	}

	@Override
	protected void buildImageView(GuideHelpTaskInfo guideHelpTaskInfo, int[] attachViewPos, int attachViewWidth, int attachViewHeight) {
		//LinearLayout.LayoutParams imageParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams imageParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        //相对布局时必须执行一次清理，否则上一次设置的会影响到下一个view的显示
        removeRelativeRule(imageParams);
        
        if(guideHelpTaskInfo.leftMargin != 0){
        	//imageParams.gravity = Gravity.LEFT;
        	PrintLog.printLog(TAG, "left");
        	imageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        	imageParams.leftMargin = guideHelpTaskInfo.leftMargin;
        }else if(guideHelpTaskInfo.rightMargin != 0){
        	//imageParams.gravity = Gravity.RIGHT;
        	PrintLog.printLog(TAG, "right");
        	imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        	imageParams.rightMargin = guideHelpTaskInfo.rightMargin;
        }else{
        	//imageParams.gravity = Gravity.CENTER_HORIZONTAL;
        	PrintLog.printLog(TAG, "CENTER_HORIZONTAL");
        	imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }

        if(guideHelpTaskInfo.topMargin != 0){
            //imageParams.gravity |= Gravity.TOP;
        	imageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            imageParams.topMargin = guideHelpTaskInfo.topMargin;
        }else if(guideHelpTaskInfo.bottomMargin != 0){
            if(!guideHelpTaskInfo.needArrow){
            	//imageParams.gravity |= Gravity.BOTTOM;
            	imageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            	//不显示箭头时才设置
            	imageParams.bottomMargin = guideHelpTaskInfo.bottomMargin;
            }
        }else{
        	//imageParams.gravity |= Gravity.CENTER_VERTICAL;
        	imageParams.addRule(RelativeLayout.CENTER_VERTICAL);
        }
        imageView.setLayoutParams(imageParams);
        imageView.setImageResource(guideHelpTaskInfo.imageRes);
	}

	@Override
	protected void buildArrowImage(GuideHelpTaskInfo guideHelpTaskInfo, int[] attachViewPos, int attachViewWidth, int attachViewHeight) {
		 if(guideHelpTaskInfo.needArrow){
		        //LinearLayout.LayoutParams imageParams = (LinearLayout.LayoutParams) arrowImageView.getLayoutParams();
		        RelativeLayout.LayoutParams imageParams = (RelativeLayout.LayoutParams) arrowImageView.getLayoutParams();
		        //相对布局时必须执行一次清理，否则上一次设置的会影响到下一个view的显示
		        removeRelativeRule(imageParams);
		        
		        if(attachViewPos == null){
		        	//imageParams.gravity = Gravity.CENTER_HORIZONTAL;
		        	imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		        }else{
		        	//imageParams.gravity = Gravity.LEFT;
		        	imageParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		        	imageParams.leftMargin = attachViewPos[0];
		        	if(attachViewWidth > 0){
		        		int imageHeight = ScreenTool.getImageResWidthInDevice(activity.getResources(), R.drawable.arrow_down);		        	
		        		imageParams.leftMargin += (attachViewWidth - imageHeight) / 2;
		        	}else{
		        		imageParams.leftMargin += ScreenTool.convertDpToPx(activity, 6);
		        	}
		        }
		        arrowImageView.setLayoutParams(imageParams);
		        arrowImageView.setVisibility(View.VISIBLE);
		 }else{
			 arrowImageView.setVisibility(View.GONE);
		 }
	}
	
	//引导界面是否在显示
	 public boolean isShowing(){
		 if(tipsWindow != null && tipsWindow.isShowing()){
			 return true;
		 }
		 return false;
	 }
	 
	 private void showFinish(boolean norEnd){
		 if(guideHelpShowFinishListener != null){
			guideHelpShowFinishListener.showEnd();
		 }
	 }
	 
	//按顺序显示
	private void showByOrder(){
		PrintLog.printLog(TAG, "showByOrder curShowIndex=" + curShowIndex);
		GuideHelpTaskInfo guideHelpTaskInfo = helpList.get(curShowIndex);
		buildGuideLay(guideHelpTaskInfo);
	}
	
	private void removeRelativeRule(RelativeLayout.LayoutParams lp){
		lp.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.removeRule(RelativeLayout.ALIGN_TOP);
        lp.removeRule(RelativeLayout.ALIGN_BOTTOM);
        lp.removeRule(RelativeLayout.CENTER_VERTICAL);
	}

	@Override
	public void setGuideHelpShowFinishListener(GuideHelpShowFinishListener guideHelpShowFinishListener) {
		this.guideHelpShowFinishListener = guideHelpShowFinishListener;
	}
}
