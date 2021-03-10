package com.guidehelp.lib;

import java.util.ArrayList;
import java.util.List;

import com.guidehelp.lib.bean.GuideHelpTaskInfo;
import com.guidehelp.lib.bean.ShowPositionType;
import com.helpguide.lib.R;
import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;


/**
 * 
 * 功能:帮助引导提示
 * 实现:通过LinearLayout控制
 * 
 * 
 * */
public final class OptGuideHelp extends BaseOptGuideHelp{
	private final String TAG = OptGuideHelp.class.getSimpleName();
	
	private boolean isFullScreen;//界面是否全屏显示，默认非全屏
	private Activity activity;
	
	//提示窗
	private PopupWindow tipsWindow = null;
	private View tipsWindowRootView;
	private int statusBarHeights = 0; //状态栏高度
	private int screenHeight = 0; //状态栏高度
	private int screenWidth = 0;
	
	//提示内容
	private LinearLayout tipLayout;
	private ImageView imageView;
	private TextView textView;
	private ImageView arrowImageViewTop, arrowImageViewBottom;
	
	private ImageView showArrowImageView;//可以显示的箭头view
	
	//显示队列
	private List<GuideHelpTaskInfo> helpList;
	private int curShowIndex = 0;//当前显示index
	
	private GuideHelpShowFinishListener guideHelpShowFinishListener;
	
	
	
	public OptGuideHelp(Activity activity) {
		init(activity, false, null);
	}
	
	public OptGuideHelp(Activity activity, boolean activityFullScreen) {
		init(activity, activityFullScreen, null);
	}
	
	public OptGuideHelp(Activity activity, boolean activityFullScreen, GuideHelpShowFinishListener guideHelpShowFinishListener) {
		init(activity, activityFullScreen, guideHelpShowFinishListener);
	}
	
	private void init(Activity activity, boolean activityFullScreen, GuideHelpShowFinishListener guideHelpShowFinishListener){
		this.activity = activity;
		this.isFullScreen = activityFullScreen;
		this.guideHelpShowFinishListener = guideHelpShowFinishListener;
		this.helpList = new ArrayList<>();
	}
	
	/**
	 * 设置引导状态监听
	 * @param guideHelpShowFinishListener : 监听回调对象
	 * */
	@Override
	public void setGuideHelpShowFinishListener(GuideHelpShowFinishListener guideHelpShowFinishListener) {
		this.guideHelpShowFinishListener = guideHelpShowFinishListener;
	}

	@Override
	protected void initGuideHelpWindow() {
		try {
			// 获取状态栏高度  
			if(!isFullScreen && statusBarHeights <= 0){
				//当不上全屏时，需要计算状态栏的高度
			    statusBarHeights = ScreenTool.getStatusBarHeight(activity); 
			    PrintLog.printLog(TAG, "initGuideHelpWindow statusBarHeight=" + statusBarHeights);
			    if(statusBarHeights <= 0){
			    	statusBarHeights = ScreenTool.getStatusBarHeight(activity.getApplicationContext());
			    }
			}
	    
			//获取屏幕分辨率
			if(screenHeight <= 0 || screenWidth <= 0){
			    DisplayMetrics dm = new DisplayMetrics(); 
			    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			    screenHeight = dm.heightPixels;
			    screenWidth = dm.widthPixels;
			    PrintLog.printLog(TAG, "screenHeight=" + screenHeight + " ,screenWidth=" + screenWidth + ",statusBarHeights=" + statusBarHeights);
			}
			
			tipsWindowRootView =  LayoutInflater.from(activity).inflate(R.layout.user_opt_guide_help, null);
			tipLayout = (LinearLayout) tipsWindowRootView.findViewById(R.id.optHelpLay);
			imageView = (ImageView) tipsWindowRootView.findViewById(R.id.imageView1);
			textView = (TextView) tipsWindowRootView.findViewById(R.id.textView);
			arrowImageViewTop = (ImageView) tipsWindowRootView.findViewById(R.id.arrowImageTop);
			arrowImageViewBottom = (ImageView) tipsWindowRootView.findViewById(R.id.arrowImageBottom);
			
			tipsWindowRootView.setOnClickListener(new OnClickListener(){
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
					/*  if (keyCode == KeyEvent.KEYCODE_BACK){
						 return true;
					 }
					 return false; */
					 
					 if (keyCode == KeyEvent.KEYCODE_BACK){
						 if(event.getAction() == KeyEvent.ACTION_UP){
							 //避免一次做2次
							 doClickEvent();
						 }
						 return true;
					 }
					 return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**开始显示引导界面*/
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
	
	private void doClickEvent(){
		try {
			curShowIndex++;
			if(curShowIndex < helpList.size()){
				showByOrder();
			}else{
				hideGuideHelp();
				showFinish(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**隐藏引导界面*/
	@Override
	public void hideGuideHelp() {
		if(tipsWindow != null && tipsWindow.isShowing()){
			tipsWindow.dismiss();
			tipsWindow = null;
		}
		removeAllGuideHelpTask();
	}

	/**
	 * 功能: 添加引导任务,任务将按加入的先后顺序显示
	 * 
	 * */
	@Override
	public void addGuideHelpTask(GuideHelpTaskInfo guideHelpTaskInfo) {
		if(guideHelpTaskInfo == null || guideHelpTaskInfo.imageRes == -1 && TextUtils.isEmpty(guideHelpTaskInfo.tipText)){
			//当没设置内容时，任务无效
			return ;
		}
		this.helpList.add(guideHelpTaskInfo);
	}

	/**
	 * 功能: 移除所有引导任务
	 * */
	@Override
	public void removeAllGuideHelpTask() {
		if(helpList != null){
			helpList.clear();
		}
	}

	/**
	 * 功能: 设置tip垂直方向上的Y位置
	 * Y位置 = 由attachView的Y位置(或者绝对位置Y) 、 tipView 的高度、箭头的高度以及微调topMargin(bottomMargin)共同决定
	 * 
	 * */
	@Override
	protected void buildGuideLay(GuideHelpTaskInfo guideHelpTaskInfo) {
		    PrintLog.printLog(TAG, "buildTipLayoutParams statusHeight=" + statusBarHeights);
	        LayoutParams lp = (LayoutParams) tipLayout.getLayoutParams();
	        //PrintLog.printLog(TAG, "margin=" + lp.bottomMargin + "," + lp.topMargin + "," + lp.leftMargin + "," + lp.rightMargin);
	        initFrameLayoutParams(lp);
	        int[] pos = null;
	        int width = 0;
	        int height = 0;
	        if(guideHelpTaskInfo.attachView != null){
	        	//当attachView存在时，通过lp.topMargin来设置Y位置
	        	lp.gravity = Gravity.LEFT | Gravity.TOP;
	        	//测量attach的位置
	        	pos = new int[2];
	        	guideHelpTaskInfo.attachView.getLocationInWindow(pos);
	        	PrintLog.printLog(TAG, "attachView pos=" + pos[0] + "," + pos[1]);
	        	
	        	//测量attachView的大小
	        	int intw = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED); 
	        	int inth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED); 
//	        	ViewGroup.LayoutParams attachViewLp = guideHelpTaskInfo.attachView.getLayoutParams();
//	        	if(attachViewLp != null){
//		        	if(attachViewLp.width == ViewGroup.LayoutParams.WRAP_CONTENT){
//		        		intw =  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST); 
//		        	}else{
//		        		intw =  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY); 
//		        	}
//		        	
//		        	if(attachViewLp.height == ViewGroup.LayoutParams.WRAP_CONTENT){
//		        		inth =  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST); 
//		        	}else{
//		        		inth =  View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY); 
//		        	}
//	        	}
	        	guideHelpTaskInfo.attachView.measure(intw, inth); 
	        	width = guideHelpTaskInfo.attachView.getMeasuredWidth(); 
	        	height = guideHelpTaskInfo.attachView.getMeasuredHeight();
	        	PrintLog.printLog(TAG, "attachView size=" + width + "," + height);
	        	
	        	//计算tip的Y位置
	        	if(guideHelpTaskInfo.showPositionType == ShowPositionType.Above){
	        		//在上面显示，需要减去imageView或者textView的高度
	        		if(canShowImage(guideHelpTaskInfo)){
		        		int imgResHeight = ScreenTool.getImageResHeightInDevice(activity.getResources(), guideHelpTaskInfo.imageRes);
		        		lp.topMargin = pos[1] - imgResHeight - statusBarHeights;
	        		}else{
	        			int textHeight = ScreenTool.getTextViewHeight(activity, textView, guideHelpTaskInfo.tipText);
		        		lp.topMargin = pos[1] - textHeight - statusBarHeights;
	        		}
	        	}else{ 
	        		if(guideHelpTaskInfo.showPositionType == ShowPositionType.Below){
			        	lp.topMargin = pos[1] + height - statusBarHeights;
	        		}else{
	        			//当showPositionType是ShowPositionType.None或者ShowPositionType.Mid时，居中重叠
	        			if(canShowImage(guideHelpTaskInfo)){
	        				//垂直方向上重叠显示并垂直居中，需要计算imageView的高度
	        				int imgResHeight =  ScreenTool.getImageResHeightInDevice(activity.getResources(), guideHelpTaskInfo.imageRes);
		        			lp.topMargin = pos[1] + (height - imgResHeight) / 2 - statusBarHeights;
		        		}else{
		        			//垂直方向上重叠显示并垂直居中，需要计算textView的高度
		        			int textHeight = ScreenTool.getTextViewHeight(activity, textView, guideHelpTaskInfo.tipText);
		        			lp.topMargin = pos[1] + (height - textHeight) / 2 - statusBarHeights;
		        		}
	        		}
	        	}
	        }else{
	        	//当attachView不存在时，通过lp.topMargin或者lp.bottomMargin设置Y位置
	        	if(guideHelpTaskInfo.topShowY != 0){
	        		lp.gravity = Gravity.TOP;
	        		lp.topMargin = guideHelpTaskInfo.topShowY;
	        	}else{
	        		lp.gravity = Gravity.BOTTOM;
	        		lp.bottomMargin = guideHelpTaskInfo.bottomShowY;
	        	}
	        }
	        PrintLog.printLog(TAG, "guideLay topMargin =" + lp.topMargin);
	        
	        //将箭头的高度包含到tip的Y位置上
	        if(guideHelpTaskInfo.canShowArrow()){
	        	showArrowImageView = null;
		        if(guideHelpTaskInfo.showPositionType == ShowPositionType.Above){
		        	//计算箭头的高度，并包含到Y位置上，当箭头在上面时，lp.topMargin需要减去箭头图片高度
		        	int imgResHeight = ScreenTool.getImageResHeightInDevice(activity.getResources(), R.drawable.arrow_down);
		        	lp.topMargin -= imgResHeight;
		        	showArrowImageView = arrowImageViewBottom;
		        }else if(guideHelpTaskInfo.showPositionType == ShowPositionType.Below){
		        	//当箭头在下面时，lp.topMargin与箭头高度无关
		        	showArrowImageView = arrowImageViewTop;
		        }
	        }
	        
	        //将topMargin和bottomMargin的微调距离包含进去
	        if(guideHelpTaskInfo.topMargin != 0){
	        	if(guideHelpTaskInfo.attachView != null || guideHelpTaskInfo.topShowY != 0){
	        		//通过lp.topMargin设置Y方向
	        		lp.topMargin += guideHelpTaskInfo.topMargin;
	        	}else{
	        		//当attachView为null,通过bottomShowY来设置Y时，将margin计算到lp.bottomMargin中
	        		lp.bottomMargin -= guideHelpTaskInfo.topMargin;
	        	}
	        }else if(guideHelpTaskInfo.bottomMargin != 0){
	        	if(guideHelpTaskInfo.attachView != null || guideHelpTaskInfo.topShowY != 0){
	        		//通过lp.topMargin设置Y方向
	        		lp.topMargin -= guideHelpTaskInfo.bottomMargin;
	        	}else{
	        		//当attachView为null,通过bottomShowY来设置Y时，将margin计算到lp.bottomMargin中
	        		lp.bottomMargin += guideHelpTaskInfo.bottomMargin;
	        	}
	        	
	            if(!guideHelpTaskInfo.canShowArrow()){
	            	lp.topMargin -= guideHelpTaskInfo.bottomMargin;
	            }
	        }
	        
	      
	        tipLayout.setLayoutParams(lp);
	        
	        //设置image或者tipText的内容和X方向位置
	        if(canShowImage(guideHelpTaskInfo)){
	        	textView.setVisibility(View.GONE);
	        	imageView.setVisibility(View.VISIBLE);
	        	buildImageView(guideHelpTaskInfo, pos, width, height);
	        }else{
	        	imageView.setVisibility(View.GONE);
	        	textView.setVisibility(View.VISIBLE);
	        	buildTextView(guideHelpTaskInfo, pos, width, height);
	        }
	        
	        //设置箭头的显示和箭头的X位置
	        buildArrowImage(guideHelpTaskInfo, pos, width, height);
	}
	
	//是否显示图片
	private boolean canShowImage(GuideHelpTaskInfo guideHelpTaskInfo){
		return guideHelpTaskInfo.imageRes != -1;
	}

	//设置tip内容和tip的X位置
	@Override
	protected void buildImageView(GuideHelpTaskInfo guideHelpTaskInfo, int[] attachViewPos, int attachViewWidth, int attachViewHeight) {
		 	PrintLog.printLog(TAG, "buildImageView");
			LinearLayout.LayoutParams imageParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
			initLinearLayoutParams(imageParams);
			if(guideHelpTaskInfo.leftMargin != 0){
	        	imageParams.gravity = Gravity.LEFT;
	        	imageParams.leftMargin = guideHelpTaskInfo.leftMargin;
	        }else if(guideHelpTaskInfo.rightMargin != 0){
	        	imageParams.gravity = Gravity.RIGHT;
	        	imageParams.rightMargin = guideHelpTaskInfo.rightMargin;
	        }else{
	        	if(guideHelpTaskInfo.showPositionType == ShowPositionType.Mid){
	        		//在重叠模式下，当没有设置左右边距时，水平居中显示
	        		imageParams.gravity = Gravity.CENTER_HORIZONTAL;
	        	}else{
		        	//其他模式下， 当没有设置左右边距时，优先与attachView水平居中对齐，如果attachView也没有，那就使用屏幕水平居中
		        	if(guideHelpTaskInfo.attachView == null || attachViewPos == null || attachViewWidth <= 0){
		        		imageParams.gravity = Gravity.CENTER_HORIZONTAL;
		        	}else{
		        		imageParams.gravity = Gravity.LEFT;
			        	imageParams.leftMargin = attachViewPos[0];
		        		//计算图片的宽度
		        		int tipImageWidth = ScreenTool.getImageResWidthInDevice(activity.getResources(), guideHelpTaskInfo.imageRes);	
		        		PrintLog.printLog(TAG, "tipImageView width=" + tipImageWidth);
		        		imageParams.leftMargin += (attachViewWidth - tipImageWidth) / 2;
		        	}
	        	}
	        }

	    	PrintLog.printLog(TAG, "buildImageView 1111");
	        imageView.setLayoutParams(imageParams);
	        imageView.setImageResource(guideHelpTaskInfo.imageRes);
	}
	
	
	//设置tip内容和tip的X位置
	protected void buildTextView(GuideHelpTaskInfo guideHelpTaskInfo, int[] attachViewPos, int attachViewWidth, int attachViewHeight) {
	 	PrintLog.printLog(TAG, "buildTextView");
		LinearLayout.LayoutParams textParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
		initLinearLayoutParams(textParams);
		if(guideHelpTaskInfo.leftMargin != 0){
        	textParams.gravity = Gravity.LEFT;
        	textParams.leftMargin = guideHelpTaskInfo.leftMargin;
        }else if(guideHelpTaskInfo.rightMargin != 0){
        	textParams.gravity = Gravity.RIGHT;
        	textParams.rightMargin = guideHelpTaskInfo.rightMargin;
        }else{
        	if(guideHelpTaskInfo.showPositionType == ShowPositionType.Mid){
        		//在重叠模式下，当没有设置左右边距时，水平居中显示
        		textParams.gravity = Gravity.CENTER_HORIZONTAL;
        	}else{
	        	//其他模式下， 当没有设置左右边距时，优先与attachView水平居中对齐，如果attachView也没有，那就使用屏幕水平居中
	        	if(guideHelpTaskInfo.attachView == null || attachViewPos == null || attachViewWidth <= 0){
	        		textParams.gravity = Gravity.CENTER_HORIZONTAL;
	        	}else{
	        		textParams.gravity = Gravity.LEFT;
		        	textParams.leftMargin = attachViewPos[0];
	        		//计算textView的宽度
	        		int tipTextWidth = ScreenTool.getTextViewWidth(activity, textView, guideHelpTaskInfo.tipText);	
	        		PrintLog.printLog(TAG, "tipTextView width=" + tipTextWidth);
	        		textParams.leftMargin += (attachViewWidth - tipTextWidth) / 2;
	        	}
        	}
        }

    	PrintLog.printLog(TAG, "buildImageView 1111");
        textView.setLayoutParams(textParams);
        textView.setText(guideHelpTaskInfo.tipText);
	}

	//设置箭头和箭头的X位置
	@Override
	protected void buildArrowImage(GuideHelpTaskInfo guideHelpTaskInfo, int[] attachViewPos, int attachViewWidth, int attachViewHeight) {
		//先隐藏箭头view
		arrowImageViewTop.setVisibility(View.GONE);
		arrowImageViewBottom.setVisibility(View.GONE);
		if(guideHelpTaskInfo.canShowArrow() && showArrowImageView != null){
		        LinearLayout.LayoutParams imageParams = (LinearLayout.LayoutParams) showArrowImageView.getLayoutParams();
		        initLinearLayoutParams(imageParams);
		        if(attachViewPos == null){
		        	//当没有设置attachView时，箭头的位置和imageView一样由leftMargin决定
		        	if(guideHelpTaskInfo.leftMargin != 0){
		            	imageParams.gravity = Gravity.LEFT;
		            	imageParams.leftMargin = guideHelpTaskInfo.leftMargin;
		            }else if(guideHelpTaskInfo.rightMargin != 0){
		            	imageParams.gravity = Gravity.RIGHT;
		            	imageParams.rightMargin = guideHelpTaskInfo.rightMargin;
		            }else{
		            	imageParams.gravity = Gravity.CENTER_HORIZONTAL;
		            }
		        }else{
		        	imageParams.gravity = Gravity.LEFT;
		        	imageParams.leftMargin = attachViewPos[0];
		        	if(attachViewWidth > 0){
		        		int arrowWidth = ScreenTool.getImageResWidthInDevice(activity.getResources(), R.drawable.arrow_down);	
		        		imageParams.leftMargin += (attachViewWidth - arrowWidth) / 2;
		        	}else{
		        		imageParams.leftMargin += ScreenTool.convertDpToPx(activity, 6);
		        	}
		        }
		        showArrowImageView.setLayoutParams(imageParams);
		        showArrowImageView.setVisibility(View.VISIBLE);
		 }else{
			 arrowImageViewTop.setVisibility(View.GONE);
			 arrowImageViewBottom.setVisibility(View.GONE);
		 }
	}
	
	/**
	 *  引导界面是否在显示
	 *  */
	 public boolean isShowing(){
		 if(tipsWindow != null && tipsWindow.isShowing()){
			 return true;
		 }
		 return false;
	 }
	 
	 /**
	  * 引导显示结束
	  * @param normalEnd : true - 正常结束， false - 非正常，有可能就没显示
	  * */
	 private void showFinish(boolean normalEnd){
		 if(guideHelpShowFinishListener != null){
			guideHelpShowFinishListener.showEnd();
		 }
	 }
	 
	//按顺序显示
	private void showByOrder(){
		PrintLog.printLog(TAG, "showByOrder curShowIndex=" + curShowIndex);
		GuideHelpTaskInfo guideHelpTaskInfo = helpList.get(curShowIndex);
		//设置显示内容
		//imageView.setImageResource(guideHelpTaskInfo.imageRes);
		//更新显示位置
		buildGuideLay(guideHelpTaskInfo);
	}

	//重置LayoutParams
	private void initFrameLayoutParams(FrameLayout.LayoutParams lp){
		if(lp != null){
			lp.topMargin = 0;
			lp.bottomMargin = 0;
			lp.leftMargin = 0;
			lp.rightMargin = 0;
			lp.gravity = Gravity.NO_GRAVITY;
		}
	}
	
	//重置LayoutParams
	private void initLinearLayoutParams(LinearLayout.LayoutParams lp){
		if(lp != null){
			lp.topMargin = 0;
			lp.bottomMargin = 0;
			lp.leftMargin = 0;
			lp.rightMargin = 0;
			lp.gravity = Gravity.NO_GRAVITY;
		}
	}
}
