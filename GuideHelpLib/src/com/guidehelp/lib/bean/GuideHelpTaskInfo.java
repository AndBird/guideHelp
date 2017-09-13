package com.guidehelp.lib.bean;

import android.view.View;


//��Ҫ��ʾ��ָ������
public class GuideHelpTaskInfo {
	public int imageRes;//����ͼƬ��Դ
	public String tipText;//������ʾ����
	
	public View attachView;//����ָ���view�����λ�ÿ��ƣ���û������ʱ�������ɾ���λ��topShowY��bottomShowY����
	//������ʾλ��topShowY���ȣ�bottomShowY��֮�����Ǿ���λ�ã� ��������attachViewʱ��Ч
	public int topShowY;//���ƴ�ֱ�������ʾλ��
	public int bottomShowY;//���ƴ�ֱ�������ʾλ��
	//guideView�߾����,���ڿ���ˮƽ�����λ��,���ȼ�leftMargin > rightMargin
	public int leftMargin;
	public int rightMargin;
	//guideView�߾����,����΢��λ��,���ȼ�topMargin > bottomMargin
	public int topMargin;
	public int bottomMargin;
	//1.����guideView��attachView�����λ��(Ĭ������)������,�����ص�,ֻ��������attachViewʱ��Ч
	//2.���ü�ͷ�ķ���,ֻ��������ShowPositionType.Top��ShowPositionType.Belowʱ�ſ�����ʾ��ͷ
	public ShowPositionType showPositionType;
	//�Ƿ���Ҫ��ʾ��ͷ��Ĭ�ϲ���ʾ
	public boolean needArrow;
	
	public GuideHelpTaskInfo(){
		this.attachView = null;
		this.imageRes = -1;
		this.showPositionType = ShowPositionType.None;
		this.needArrow = false;
	}
	
	/**
	 * ����: ��Ҫ��ʾ�������ܵ�view�����ڿ�������view��λ��(���λ��)��
	 * @param attachView : ��Ҫ��ʾ������view
	 * 
	 * ��û������attachViewʱ��������TopShowY����BottomShowY��ֱ�ӿ��������������ʾ(����λ��)
	 * 
	 * 
	 * */
	public GuideHelpTaskInfo setAttachView(View attachView) {
		this.attachView = attachView;
		return this;
	}
	
	/**
	 * ����:��������ͼƬ����Դid,��tipText���ߴ�һ
	 * @param imageRes : ����ʾ������ͼƬid
	 * */
	public GuideHelpTaskInfo setImageRes(int imageRes) {
		this.imageRes = imageRes;
		this.tipText = null;
		return this;
	}
	
	
	
	/**
	 * ����:���������ı�,��imageRes���ߴ�һ
	 * @param tipText : ����ʾ�������ı�
	 * */
	public GuideHelpTaskInfo setTipText(String tipText) {
		this.tipText = tipText;
		this.imageRes = -1;
		return this;
	}

	/**
	 * ����:��������������attachView������λ�ù�ϵ(����)���Լ���ͷ�ķ���
	 * 
	 * */
	public GuideHelpTaskInfo setShowPositionType(ShowPositionType positionType) {
		this.showPositionType = positionType;
		return this;
	}
	
	/**
	 * ����:��������ҳ������Ļ����߾�(ˮƽ����)�����ȼ���rightMargin�ߣ��Ҷ���ֻ��һ����Ч��������õ���Ч
	 * @param leftMargin : ������������Ļ����߾�
	 * */
	public GuideHelpTaskInfo setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
		this.rightMargin = 0;
		return this;
	}
	
	/**
	 * ����:��������ҳ������Ļ���ұ߾�(ˮƽ����)�����ȼ���leftMargin�ͣ��Ҷ���ֻ��һ����Ч��������õ���Ч
	 * @param rightMargin : ������������Ļ���ұ߾�
	 * */
	public GuideHelpTaskInfo setRightMargin(int rightMargin) {
		this.rightMargin = rightMargin;
		this.leftMargin = 0;
		return this;
	}
	
	/**
	 * ����:��������ҳ����ϱ߾࣬���ȼ���BottomMargin�ߣ��Ҷ���ֻ��һ����Ч��������õ���Ч
	 * @param topMargin : ����������ϱ߾�(����΢��)
	 * */
	public GuideHelpTaskInfo setTopMargin(int topMargin) {
		this.topMargin = topMargin;
		return this;
	}
	
	/**
	 * ����:��������ҳ����±߾࣬���ȼ���TopMargin�ͣ��Ҷ���ֻ��һ����Ч��������õ���Ч
	 * @param bottomMargin : ����������±߾�(����΢��)
	 * */
	public GuideHelpTaskInfo setBottomMargin(int bottomMargin) {
		this.bottomMargin = bottomMargin;
		return this;
	}
	
	/**
	 * ���ܣ���������ҳ������Ļ���ϱ߾�(����λ��)����attachViewδ����ʱ��Ч
	 * @param topShowY : ����ҳ������Ļ���ϱ߾࣬����λ�ã����ȼ���BottomShowY��
	 * 
	 * */
	public GuideHelpTaskInfo setTopShowY(int topShowY) {
		this.topShowY = topShowY;
		this.bottomShowY = 0;
		return this;
	}

	/**
	 * ���ܣ���������ҳ������Ļ���±߾�(����λ��)����attachViewδ����ʱ��Ч
	 * @param bottomShowY : ����ҳ������Ļ���±߾࣬����λ�ã����ȼ���topShowY��
	 * 
	 * */
	public GuideHelpTaskInfo setBottomShowY(int bottomShowY) {
		this.bottomShowY = bottomShowY;
		this.topShowY = 0;
		return this;
	}
	
	/**
	 * ����: �Ƿ���ʾ��ͷ,���ҽ���������ShowPositionType.Above ����ShowPositionType.Belowʱ����Ч��
	 * 		���û������ShowPositionType����������ʾ��ͷ
	 * ע��:��û������attachViewʱ����ͷ����ʾ������λ�ÿ�����Ҫ΢��
	 * 
	 * @param showArrow : true - ��ʾ��ͷ   false - ����ʾ
	 * */
	public GuideHelpTaskInfo setShowArrow(boolean showArrow){
		this.needArrow = showArrow;
		return this;
	}

	public void build(){
		
	}
	
	//�Ƿ������ʾ��ͷ
	public boolean canShowArrow(){
		//return needArrow && attachView != null && (showPositionType == ShowPositionType.Above || showPositionType == ShowPositionType.Below);
		return needArrow && (showPositionType == ShowPositionType.Above || showPositionType == ShowPositionType.Below);
	}
}
