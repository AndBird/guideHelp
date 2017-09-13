package com.guidehelp.lib.bean;

import android.view.View;


//需要显示的指引任务
public class GuideHelpTaskInfo {
	public int imageRes;//引导图片资源
	public String tipText;//引导提示内容
	
	public View attachView;//引导指向的view，相对位置控制，当没有设置时，纵向由绝对位置topShowY和bottomShowY控制
	//纵向显示位置topShowY优先，bottomShowY次之，这是绝对位置， 当不设置attachView时生效
	public int topShowY;//控制垂直方向的显示位置
	public int bottomShowY;//控制垂直方向的显示位置
	//guideView边距控制,用于控制水平方向的位置,优先级leftMargin > rightMargin
	public int leftMargin;
	public int rightMargin;
	//guideView边距控制,用于微调位置,优先级topMargin > bottomMargin
	public int topMargin;
	public int bottomMargin;
	//1.设置guideView与attachView的相对位置(默认上面)，上下,或者重叠,只在设置了attachView时生效
	//2.设置箭头的方向,只有设置了ShowPositionType.Top和ShowPositionType.Below时才可以显示箭头
	public ShowPositionType showPositionType;
	//是否需要显示箭头，默认不显示
	public boolean needArrow;
	
	public GuideHelpTaskInfo(){
		this.attachView = null;
		this.imageRes = -1;
		this.showPositionType = ShowPositionType.None;
		this.needArrow = false;
	}
	
	/**
	 * 功能: 需要显示引导功能的view，用于控制引导view的位置(相对位置)等
	 * @param attachView : 需要显示引导的view
	 * 
	 * 当没有设置attachView时，请设置TopShowY或者BottomShowY来直接控制引导界面的显示(绝对位置)
	 * 
	 * 
	 * */
	public GuideHelpTaskInfo setAttachView(View attachView) {
		this.attachView = attachView;
		return this;
	}
	
	/**
	 * 功能:设置引导图片的资源id,与tipText二者存一
	 * @param imageRes : 待显示的引导图片id
	 * */
	public GuideHelpTaskInfo setImageRes(int imageRes) {
		this.imageRes = imageRes;
		this.tipText = null;
		return this;
	}
	
	
	
	/**
	 * 功能:设置引导文本,与imageRes二者存一
	 * @param tipText : 待显示的引导文本
	 * */
	public GuideHelpTaskInfo setTipText(String tipText) {
		this.tipText = tipText;
		this.imageRes = -1;
		return this;
	}

	/**
	 * 功能:设置引导界面与attachView的上下位置关系(纵向)，以及箭头的方向
	 * 
	 * */
	public GuideHelpTaskInfo setShowPositionType(ShowPositionType positionType) {
		this.showPositionType = positionType;
		return this;
	}
	
	/**
	 * 功能:设置引导页面与屏幕的左边距(水平方向)，优先级比rightMargin高，且二者只有一个生效，最后设置的生效
	 * @param leftMargin : 引导界面与屏幕的左边距
	 * */
	public GuideHelpTaskInfo setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
		this.rightMargin = 0;
		return this;
	}
	
	/**
	 * 功能:设置引导页面与屏幕的右边距(水平方向)，优先级比leftMargin低，且二者只有一个生效，最后设置的生效
	 * @param rightMargin : 引导界面与屏幕的右边距
	 * */
	public GuideHelpTaskInfo setRightMargin(int rightMargin) {
		this.rightMargin = rightMargin;
		this.leftMargin = 0;
		return this;
	}
	
	/**
	 * 功能:设置引导页面的上边距，优先级比BottomMargin高，且二者只有一个生效，最后设置的生效
	 * @param topMargin : 引导界面的上边距(用于微调)
	 * */
	public GuideHelpTaskInfo setTopMargin(int topMargin) {
		this.topMargin = topMargin;
		return this;
	}
	
	/**
	 * 功能:设置引导页面的下边距，优先级比TopMargin低，且二者只有一个生效，最后设置的生效
	 * @param bottomMargin : 引导界面的下边距(用于微调)
	 * */
	public GuideHelpTaskInfo setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
		return this;
	}
	
	/**
	 * 功能：控制引导页面与屏幕的上边距(绝对位置)，当attachView未设置时生效
	 * @param topShowY : 引导页面与屏幕的上边距，绝对位置，优先级比BottomShowY高
	 * 
	 * */
	public GuideHelpTaskInfo setTopShowY(int topShowY) {
		this.topShowY = topShowY;
		this.bottomShowY = 0;
		return this;
	}

	/**
	 * 功能：控制引导页面与屏幕的下边距(绝对位置)，当attachView未设置时生效
	 * @param bottomShowY : 引导页面与屏幕的下边距，绝对位置，优先级比topShowY低
	 * 
	 * */
	public GuideHelpTaskInfo setBottomShowY(int bottomShowY) {
		this.bottomShowY = bottomShowY;
		this.topShowY = 0;
		return this;
	}
	
	/**
	 * 功能: 是否显示箭头,当且仅当设置了ShowPositionType.Above 或者ShowPositionType.Below时才有效，
	 * 		如果没有设置ShowPositionType，将不会显示箭头
	 * 注意:当没有设置attachView时，箭头的显示，纵向位置可能需要微调
	 * 
	 * @param showArrow : true - 显示箭头   false - 不显示
	 * */
	public GuideHelpTaskInfo setShowArrow(boolean showArrow){
		this.needArrow = showArrow;
		return this;
	}

	public void build(){
		
	}
	
	//是否可以显示箭头
	public boolean canShowArrow(){
		//return needArrow && attachView != null && (showPositionType == ShowPositionType.Above || showPositionType == ShowPositionType.Below);
		return needArrow && (showPositionType == ShowPositionType.Above || showPositionType == ShowPositionType.Below);
	}
}
