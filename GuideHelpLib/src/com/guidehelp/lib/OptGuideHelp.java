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
 * ����:����������ʾ
 * ʵ��:ͨ��LinearLayout����
 * 
 * 
 * */
public final class OptGuideHelp extends BaseOptGuideHelp{
	private final String TAG = OptGuideHelp.class.getSimpleName();
	
	private boolean isFullScreen;//�����Ƿ�ȫ����ʾ��Ĭ�Ϸ�ȫ��
	private Activity activity;
	
	//��ʾ��
	private PopupWindow tipsWindow = null;
	private View tipsWindowRootView;
	private int statusBarHeights = 0; //״̬���߶�
	private int screenHeight = 0; //״̬���߶�
	private int screenWidth = 0;
	
	//��ʾ����
	private LinearLayout tipLayout;
	private ImageView imageView;
	private TextView textView;
	private ImageView arrowImageViewTop, arrowImageViewBottom;
	
	private ImageView showArrowImageView;//������ʾ�ļ�ͷview
	
	//��ʾ����
	private List<GuideHelpTaskInfo> helpList;
	private int curShowIndex = 0;//��ǰ��ʾindex
	
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
	 * ��������״̬����
	 * @param guideHelpShowFinishListener : �����ص�����
	 * */
	@Override
	public void setGuideHelpShowFinishListener(GuideHelpShowFinishListener guideHelpShowFinishListener) {
		this.guideHelpShowFinishListener = guideHelpShowFinishListener;
	}

	@Override
	protected void initGuideHelpWindow() {
		try {
			// ��ȡ״̬���߶�  
			if(!isFullScreen && statusBarHeights <= 0){
				//������ȫ��ʱ����Ҫ����״̬���ĸ߶�
			    statusBarHeights = ScreenTool.getStatusBarHeight(activity); 
			    PrintLog.printLog(TAG, "initGuideHelpWindow statusBarHeight=" + statusBarHeights);
			    if(statusBarHeights <= 0){
			    	statusBarHeights = ScreenTool.getStatusBarHeight(activity.getApplicationContext());
			    }
			}
	    
			//��ȡ��Ļ�ֱ���
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
			//�������ñ������������ú󽫲�����������ؼ�
	        //ColorDrawable dw = new ColorDrawable(0x00000000);//ȥ��������ɫ
	        //tipsWindow.setBackgroundDrawable(dw);
	        tipsWindow.setOutsideTouchable(false);
	        
	        tipsWindowRootView.setFocusableInTouchMode(true);
	        tipsWindowRootView.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					 //PrintLog.printLog(TAG, "key event");
					 //��ֹ�����˳���������
					/*  if (keyCode == KeyEvent.KEYCODE_BACK){
						 return true;
					 }
					 return false; */
					 
					 if (keyCode == KeyEvent.KEYCODE_BACK){
						 if(event.getAction() == KeyEvent.ACTION_UP){
							 //����һ����2��
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

	/**��ʼ��ʾ��������*/
	@Override
	public void showGuideHelp() {
		//�ǵ���¼��е��ã� ֱ����ʾpopupwindow�������
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					if(helpList == null || helpList.size() <= 0){
						PrintLog.printLog(TAG, "û�����ð�����ʾ����");
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
	
	/**������������*/
	@Override
	public void hideGuideHelp() {
		if(tipsWindow != null && tipsWindow.isShowing()){
			tipsWindow.dismiss();
			tipsWindow = null;
		}
		removeAllGuideHelpTask();
	}

	/**
	 * ����: �����������,���񽫰�������Ⱥ�˳����ʾ
	 * 
	 * */
	@Override
	public void addGuideHelpTask(GuideHelpTaskInfo guideHelpTaskInfo) {
		if(guideHelpTaskInfo == null || guideHelpTaskInfo.imageRes == -1 && TextUtils.isEmpty(guideHelpTaskInfo.tipText)){
			//��û��������ʱ��������Ч
			return ;
		}
		this.helpList.add(guideHelpTaskInfo);
	}

	/**
	 * ����: �Ƴ�������������
	 * */
	@Override
	public void removeAllGuideHelpTask() {
		if(helpList != null){
			helpList.clear();
		}
	}

	/**
	 * ����: ����tip��ֱ�����ϵ�Yλ��
	 * Yλ�� = ��attachView��Yλ��(���߾���λ��Y) �� tipView �ĸ߶ȡ���ͷ�ĸ߶��Լ�΢��topMargin(bottomMargin)��ͬ����
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
	        	//��attachView����ʱ��ͨ��lp.topMargin������Yλ��
	        	lp.gravity = Gravity.LEFT | Gravity.TOP;
	        	//����attach��λ��
	        	pos = new int[2];
	        	guideHelpTaskInfo.attachView.getLocationInWindow(pos);
	        	PrintLog.printLog(TAG, "attachView pos=" + pos[0] + "," + pos[1]);
	        	
	        	//����attachView�Ĵ�С
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
	        	
	        	//����tip��Yλ��
	        	if(guideHelpTaskInfo.showPositionType == ShowPositionType.Above){
	        		//��������ʾ����Ҫ��ȥimageView����textView�ĸ߶�
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
	        			//��showPositionType��ShowPositionType.None����ShowPositionType.Midʱ�������ص�
	        			if(canShowImage(guideHelpTaskInfo)){
	        				//��ֱ�������ص���ʾ����ֱ���У���Ҫ����imageView�ĸ߶�
	        				int imgResHeight =  ScreenTool.getImageResHeightInDevice(activity.getResources(), guideHelpTaskInfo.imageRes);
		        			lp.topMargin = pos[1] + (height - imgResHeight) / 2 - statusBarHeights;
		        		}else{
		        			//��ֱ�������ص���ʾ����ֱ���У���Ҫ����textView�ĸ߶�
		        			int textHeight = ScreenTool.getTextViewHeight(activity, textView, guideHelpTaskInfo.tipText);
		        			lp.topMargin = pos[1] + (height - textHeight) / 2 - statusBarHeights;
		        		}
	        		}
	        	}
	        }else{
	        	//��attachView������ʱ��ͨ��lp.topMargin����lp.bottomMargin����Yλ��
	        	if(guideHelpTaskInfo.topShowY != 0){
	        		lp.gravity = Gravity.TOP;
	        		lp.topMargin = guideHelpTaskInfo.topShowY;
	        	}else{
	        		lp.gravity = Gravity.BOTTOM;
	        		lp.bottomMargin = guideHelpTaskInfo.bottomShowY;
	        	}
	        }
	        PrintLog.printLog(TAG, "guideLay topMargin =" + lp.topMargin);
	        
	        //����ͷ�ĸ߶Ȱ�����tip��Yλ����
	        if(guideHelpTaskInfo.canShowArrow()){
	        	showArrowImageView = null;
		        if(guideHelpTaskInfo.showPositionType == ShowPositionType.Above){
		        	//�����ͷ�ĸ߶ȣ���������Yλ���ϣ�����ͷ������ʱ��lp.topMargin��Ҫ��ȥ��ͷͼƬ�߶�
		        	int imgResHeight = ScreenTool.getImageResHeightInDevice(activity.getResources(), R.drawable.arrow_down);
		        	lp.topMargin -= imgResHeight;
		        	showArrowImageView = arrowImageViewBottom;
		        }else if(guideHelpTaskInfo.showPositionType == ShowPositionType.Below){
		        	//����ͷ������ʱ��lp.topMargin���ͷ�߶��޹�
		        	showArrowImageView = arrowImageViewTop;
		        }
	        }
	        
	        //��topMargin��bottomMargin��΢�����������ȥ
	        if(guideHelpTaskInfo.topMargin != 0){
	        	if(guideHelpTaskInfo.attachView != null || guideHelpTaskInfo.topShowY != 0){
	        		//ͨ��lp.topMargin����Y����
	        		lp.topMargin += guideHelpTaskInfo.topMargin;
	        	}else{
	        		//��attachViewΪnull,ͨ��bottomShowY������Yʱ����margin���㵽lp.bottomMargin��
	        		lp.bottomMargin -= guideHelpTaskInfo.topMargin;
	        	}
	        }else if(guideHelpTaskInfo.bottomMargin != 0){
	        	if(guideHelpTaskInfo.attachView != null || guideHelpTaskInfo.topShowY != 0){
	        		//ͨ��lp.topMargin����Y����
	        		lp.topMargin -= guideHelpTaskInfo.bottomMargin;
	        	}else{
	        		//��attachViewΪnull,ͨ��bottomShowY������Yʱ����margin���㵽lp.bottomMargin��
	        		lp.bottomMargin += guideHelpTaskInfo.bottomMargin;
	        	}
	        	
	            if(!guideHelpTaskInfo.canShowArrow()){
	            	lp.topMargin -= guideHelpTaskInfo.bottomMargin;
	            }
	        }
	        
	      
	        tipLayout.setLayoutParams(lp);
	        
	        //����image����tipText�����ݺ�X����λ��
	        if(canShowImage(guideHelpTaskInfo)){
	        	textView.setVisibility(View.GONE);
	        	imageView.setVisibility(View.VISIBLE);
	        	buildImageView(guideHelpTaskInfo, pos, width, height);
	        }else{
	        	imageView.setVisibility(View.GONE);
	        	textView.setVisibility(View.VISIBLE);
	        	buildTextView(guideHelpTaskInfo, pos, width, height);
	        }
	        
	        //���ü�ͷ����ʾ�ͼ�ͷ��Xλ��
	        buildArrowImage(guideHelpTaskInfo, pos, width, height);
	}
	
	//�Ƿ���ʾͼƬ
	private boolean canShowImage(GuideHelpTaskInfo guideHelpTaskInfo){
		return guideHelpTaskInfo.imageRes != -1;
	}

	//����tip���ݺ�tip��Xλ��
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
	        		//���ص�ģʽ�£���û���������ұ߾�ʱ��ˮƽ������ʾ
	        		imageParams.gravity = Gravity.CENTER_HORIZONTAL;
	        	}else{
		        	//����ģʽ�£� ��û���������ұ߾�ʱ��������attachViewˮƽ���ж��룬���attachViewҲû�У��Ǿ�ʹ����Ļˮƽ����
		        	if(guideHelpTaskInfo.attachView == null || attachViewPos == null || attachViewWidth <= 0){
		        		imageParams.gravity = Gravity.CENTER_HORIZONTAL;
		        	}else{
		        		imageParams.gravity = Gravity.LEFT;
			        	imageParams.leftMargin = attachViewPos[0];
		        		//����ͼƬ�Ŀ��
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
	
	
	//����tip���ݺ�tip��Xλ��
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
        		//���ص�ģʽ�£���û���������ұ߾�ʱ��ˮƽ������ʾ
        		textParams.gravity = Gravity.CENTER_HORIZONTAL;
        	}else{
	        	//����ģʽ�£� ��û���������ұ߾�ʱ��������attachViewˮƽ���ж��룬���attachViewҲû�У��Ǿ�ʹ����Ļˮƽ����
	        	if(guideHelpTaskInfo.attachView == null || attachViewPos == null || attachViewWidth <= 0){
	        		textParams.gravity = Gravity.CENTER_HORIZONTAL;
	        	}else{
	        		textParams.gravity = Gravity.LEFT;
		        	textParams.leftMargin = attachViewPos[0];
	        		//����textView�Ŀ��
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

	//���ü�ͷ�ͼ�ͷ��Xλ��
	@Override
	protected void buildArrowImage(GuideHelpTaskInfo guideHelpTaskInfo, int[] attachViewPos, int attachViewWidth, int attachViewHeight) {
		//�����ؼ�ͷview
		arrowImageViewTop.setVisibility(View.GONE);
		arrowImageViewBottom.setVisibility(View.GONE);
		if(guideHelpTaskInfo.canShowArrow() && showArrowImageView != null){
		        LinearLayout.LayoutParams imageParams = (LinearLayout.LayoutParams) showArrowImageView.getLayoutParams();
		        initLinearLayoutParams(imageParams);
		        if(attachViewPos == null){
		        	//��û������attachViewʱ����ͷ��λ�ú�imageViewһ����leftMargin����
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
	 *  ���������Ƿ�����ʾ
	 *  */
	 public boolean isShowing(){
		 if(tipsWindow != null && tipsWindow.isShowing()){
			 return true;
		 }
		 return false;
	 }
	 
	 /**
	  * ������ʾ����
	  * @param normalEnd : true - ���������� false - ���������п��ܾ�û��ʾ
	  * */
	 private void showFinish(boolean normalEnd){
		 if(guideHelpShowFinishListener != null){
			guideHelpShowFinishListener.showEnd();
		 }
	 }
	 
	//��˳����ʾ
	private void showByOrder(){
		PrintLog.printLog(TAG, "showByOrder curShowIndex=" + curShowIndex);
		GuideHelpTaskInfo guideHelpTaskInfo = helpList.get(curShowIndex);
		//������ʾ����
		//imageView.setImageResource(guideHelpTaskInfo.imageRes);
		//������ʾλ��
		buildGuideLay(guideHelpTaskInfo);
	}

	//����LayoutParams
	private void initFrameLayoutParams(FrameLayout.LayoutParams lp){
		if(lp != null){
			lp.topMargin = 0;
			lp.bottomMargin = 0;
			lp.leftMargin = 0;
			lp.rightMargin = 0;
			lp.gravity = Gravity.NO_GRAVITY;
		}
	}
	
	//����LayoutParams
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
