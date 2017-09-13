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
 * ����:����������ʾ
 * ʵ��:ͨ��RelativeLayout����
 * 
 * ˵��:����ֻ����������tip��4�������϶�������ʾ��ͷ����������RelativeLayoutʵ����
 * 		����ļ�ͷ(���Բ�����4����������ʾ��ͷ�������ö�̬��ӿؼ�ʵ�֣�Ҳ�����ò�����
 * 		����ֻ�ǲ����ļ������RelativeLayout���ԣ��ϸ���)��ֻ��˼·��û�����,�������Ƴ����ṹ�������ο�
 * */
public class OptGuideHelpRelativeLayout extends BaseOptGuideHelp{
	private final String TAG = OptGuideHelpRelativeLayout.class.getSimpleName();
	
	private Activity activity;
	
	//��ʾ��
	private PopupWindow tipsWindow = null;
	private View tipsWindowRootView;
	private int statusBarHeights = 0; //״̬���߶�
	private int screenHeight = 0; //״̬���߶�
	private int screenWidth = 0;
	
	
	//��ʾ����
	private RelativeLayout tipLayout;
	private ImageView imageView;
	private ImageView arrowImageView;
	
	//��ʾ����
	private List<GuideHelpTaskInfo> helpList;
	private int curShowIndex = 0;//��ǰ��ʾindex
	
	
	private GuideHelpShowFinishListener guideHelpShowFinishListener;
	
	
	public OptGuideHelpRelativeLayout(Activity activity) {
		this.activity = activity;
		this.helpList = new ArrayList<>();
	}

	 @Override
	protected void initGuideHelpWindow() {
		try {
			if(statusBarHeights <= 0 || screenHeight <= 0 || screenWidth <= 0){
				// ��ȡ״̬���߶�  
			    statusBarHeights = ScreenTool.getStatusBarHeight(activity); 
			    PrintLog.printLog(TAG, "initGuideHelpWindow statusBarHeight=" + statusBarHeights);
			    if(statusBarHeights <= 0){
			    	statusBarHeights = ScreenTool.getStatusBarHeight(activity.getApplicationContext());
			    }
		    
			    DisplayMetrics dm = new DisplayMetrics(); // ��ȡ��Ļ�ֱ���
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

	
	//����tipLayout��λ��,��attachView������margin����Ч
	@Override
	protected void buildGuideLay(GuideHelpTaskInfo guideHelpTaskInfo) {
		 PrintLog.printLog(TAG, "buildTipLayoutParams statusHeight=" + statusBarHeights);
	 	//���ô�ֱ�����ϵ�λ��
        LayoutParams lp = (LayoutParams) tipLayout.getLayoutParams();
        int[] pos = null;
        int width = 0;
        int height = 0;
        if(guideHelpTaskInfo.attachView != null){
        	lp.gravity = Gravity.LEFT | Gravity.TOP;
        	//����attach��λ��
        	pos = new int[2];
        	guideHelpTaskInfo.attachView.getLocationInWindow(pos);
        	PrintLog.printLog(TAG, "attachView pos=" + pos[0] + "," + pos[1]);
        	
        	//����attach�Ĵ�С
        	int intw = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
        	int inth= View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED); 
        	guideHelpTaskInfo.attachView.measure(intw, inth); 
        	width = guideHelpTaskInfo.attachView.getMeasuredWidth(); 
        	height = guideHelpTaskInfo.attachView.getMeasuredHeight();
        	PrintLog.printLog(TAG, "attachView size=" + width + "," + height);
        	
        	if(guideHelpTaskInfo.showPositionType == ShowPositionType.Above){
        		//�ϲ���ʾ
        		int imgResHeight = ScreenTool.getImageResHeightInDevice(activity.getResources(), guideHelpTaskInfo.imageRes);
        		lp.topMargin = pos[1] - imgResHeight - statusBarHeights;
        	}else{ 
        		if(guideHelpTaskInfo.showPositionType == ShowPositionType.Below){
		        	lp.topMargin = pos[1] + height - statusBarHeights;
        		}else{
        			//�����ص�
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
        //��Բ���ʱ����ִ��һ������������һ�����õĻ�Ӱ�쵽��һ��view����ʾ
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
            	//����ʾ��ͷʱ������
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
		        //��Բ���ʱ����ִ��һ������������һ�����õĻ�Ӱ�쵽��һ��view����ʾ
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
	
	//���������Ƿ�����ʾ
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
	 
	//��˳����ʾ
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
