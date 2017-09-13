package com.guidehelp.lib;

import com.guidehelp.lib.bean.GuideHelpTaskInfo;


//עbuildGuideLay, buildImageView , buildArrowImage ��OptGuideHelp ��OptGuideHelpRelativeLayout����������
public abstract class BaseOptGuideHelp {
	
	/**
	 * 
	 * ��ʼ������
	 * 
	 * */
	protected abstract void initGuideHelpWindow();
	
	/**
	 * ��ʾ����
	 * */
	public abstract void showGuideHelp();
	
	/**
	 * ֹͣ����
	 * */
	public abstract void hideGuideHelp();
	
	/**
	 * 
	 * �����������*/
	public abstract void addGuideHelpTask(GuideHelpTaskInfo guideHelpTaskInfo);
	
	/**
	 * �Ƴ�������������
	 * 
	 * */
	public abstract void removeAllGuideHelpTask();
	
	

	
	/**
	 * �����������������λ��
	 * 
	 * */
	protected abstract void buildGuideLay(GuideHelpTaskInfo guideHelpTaskInfo);
	
	/**
	 * ��������ͼ��λ��
	 * 
	 * */
	protected abstract void buildImageView(GuideHelpTaskInfo guideHelpTaskInfo, int[] attachViewPos, int attachViewWidth, int attachViewHeight);
	
	/**
	 * ���ü�ͷ��λ��
	 * */
	protected abstract void buildArrowImage(GuideHelpTaskInfo guideHelpTaskInfos, int[] attachViewPos, int attachViewWidth, int attachViewHeight);
	
	/**
	 * ���������Ƿ�����ʾ*/
	public abstract boolean isShowing();
	
	/**
	 * ���������ص�*/
	public abstract void setGuideHelpShowFinishListener(GuideHelpShowFinishListener guideHelpShowFinishListener);
}
