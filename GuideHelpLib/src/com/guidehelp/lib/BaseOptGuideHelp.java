package com.guidehelp.lib;

import com.guidehelp.lib.bean.GuideHelpTaskInfo;


//注buildGuideLay, buildImageView , buildArrowImage 是OptGuideHelp 和OptGuideHelpRelativeLayout的区别所在
public abstract class BaseOptGuideHelp {
	
	/**
	 * 
	 * 初始化引导
	 * 
	 * */
	protected abstract void initGuideHelpWindow();
	
	/**
	 * 显示引导
	 * */
	public abstract void showGuideHelp();
	
	/**
	 * 停止引导
	 * */
	public abstract void hideGuideHelp();
	
	/**
	 * 
	 * 添加引导任务*/
	public abstract void addGuideHelpTask(GuideHelpTaskInfo guideHelpTaskInfo);
	
	/**
	 * 移除所有引导任务
	 * 
	 * */
	public abstract void removeAllGuideHelpTask();
	
	

	
	/**
	 * 设置引导界面的上下位置
	 * 
	 * */
	protected abstract void buildGuideLay(GuideHelpTaskInfo guideHelpTaskInfo);
	
	/**
	 * 设置引导图的位置
	 * 
	 * */
	protected abstract void buildImageView(GuideHelpTaskInfo guideHelpTaskInfo, int[] attachViewPos, int attachViewWidth, int attachViewHeight);
	
	/**
	 * 设置箭头的位置
	 * */
	protected abstract void buildArrowImage(GuideHelpTaskInfo guideHelpTaskInfos, int[] attachViewPos, int attachViewWidth, int attachViewHeight);
	
	/**
	 * 引导界面是否在显示*/
	public abstract boolean isShowing();
	
	/**
	 * 设置引导回调*/
	public abstract void setGuideHelpShowFinishListener(GuideHelpShowFinishListener guideHelpShowFinishListener);
}
