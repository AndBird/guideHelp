package com.guidehelp.demo;

import com.guidehelp.lib.GuideHelpShowFinishListener;
import com.guidehelp.lib.OptGuideHelp;
import com.guidehelp.lib.OptGuideHelpRelativeLayout;
import com.guidehelp.lib.ScreenTool;
import com.guidehelp.lib.bean.GuideHelpTaskInfo;
import com.guidehelp.lib.bean.ShowPositionType;
import com.helpguide.lib.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

//��ͨactivity������
public class GuideHelpTestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide_help_test);
		
		showGuideHelp();
		//showGuideHelp2();
	}

	
	private void showGuideHelp(){
		OptGuideHelp optGuideHelp = new OptGuideHelp(this);
		optGuideHelp.setGuideHelpShowFinishListener(new GuideHelpShowFinishListener() {
			@Override
			public void showEnd() {
				Toast.makeText(getApplicationContext(), "��������", Toast.LENGTH_SHORT).show();
			}
		});
		
		/*�����������*/
		
		//���þ�����ʾλ�ã����ö�������λ��(�޼�ͷ)
		GuideHelpTaskInfo guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setTopShowY(ScreenTool.convertDpToPx(getApplicationContext(), 35));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		//���þ�����ʾλ�ã����ö�������λ��(�м�ͷ)
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setShowArrow(true)
			.setShowPositionType(ShowPositionType.Below)
			.setTopShowY(ScreenTool.convertDpToPx(getApplicationContext(), 35));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//���������ı���
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setTipText("������������")
		.setTopShowY(ScreenTool.convertDpToPx(getApplicationContext(), 35));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		//���濿����ʾ���޼�ͷ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView1))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Above)
			.setLeftMargin(10);//������ʾ,������ʱ��attachViewˮƽ���ж���
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//������testView2���룬�޼�ͷ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView2))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Below);
			//.setLeftMargin(10);//������ʾ,������ʱ��attachViewˮƽ���ж���
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//���濿����ʾ���м�ͷ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView3))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Above)
			//.setRightMargin(10)//������ʾ,������ʱ��attachViewˮƽ���ж���
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//���濿����ʾ���м�ͷ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView4))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Below)
			//.setRightMargin(10)//������ʾ,������ʱ��attachViewˮƽ���ж���
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//�ص���ʾ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView5))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Mid);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//�������������ʾλ�ã����õײ�����λ��(�޼�ͷ)
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setBottomShowY(ScreenTool.convertDpToPx(getApplicationContext(), 30))
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		//�������������ʾλ�ã����õײ�����λ��(�м�ͷ)
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setBottomShowY(ScreenTool.convertDpToPx(getApplicationContext(), 30))
			.setShowArrow(true)
			.setShowPositionType(ShowPositionType.Above);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		/*�ı���ʾ����*/
			    //���濿����ʾ���޼�ͷ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("������������")
					.setShowPositionType(ShowPositionType.Above)
					.setLeftMargin(10);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//������testView7���룬�޼�ͷ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("������������")
					.setShowPositionType(ShowPositionType.Below);
					//.setLeftMargin(10);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//���濿����ʾ���м�ͷ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("������������")
					.setShowPositionType(ShowPositionType.Above)
					//.setRightMargin(10) //������ʾ,������ʱ��attachViewˮƽ���ж���
					.setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//���濿����ʾ���м�ͷ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("������������")
					.setShowPositionType(ShowPositionType.Below)
					//.setRightMargin(10)//������ʾ,������ʱ��attachViewˮƽ���ж���
					.setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//�ص���ʾ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("������������")
					.setShowPositionType(ShowPositionType.Mid);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		/* end �ı���ʾ����*/
		
		
		
		optGuideHelp.showGuideHelp();
	}
	
	
	//��Բ���ʵ��(����������4��������ʵ�ּ�ͷ���������Ƴ����ṹ)
	private void showGuideHelp2(){
		OptGuideHelpRelativeLayout optGuideHelp = new OptGuideHelpRelativeLayout(this);
		optGuideHelp.setGuideHelpShowFinishListener(new GuideHelpShowFinishListener() {
			@Override
			public void showEnd() {
				Toast.makeText(getApplicationContext(), "��������", Toast.LENGTH_SHORT).show();
			}
		});
		
		/*�����������*/
		
		//�������������ʾλ�ã����ö���λ��
		GuideHelpTaskInfo guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setTopShowY(ScreenTool.convertDpToPx(getApplicationContext(), 35));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//���濿����ʾ���޼�ͷ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView1))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Above)
			.setLeftMargin(10);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//���濿����ʾ���޼�ͷ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView2))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Below)
			.setLeftMargin(10);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//���濿����ʾ���м�ͷ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView3))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Above)
			.setRightMargin(10)
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//���濿����ʾ���м�ͷ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView4))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Below)
			.setRightMargin(10)
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//�ص���ʾ
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView5))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Mid);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//�������������ʾλ�ã����õײ�λ��
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setBottomShowY(ScreenTool.convertDpToPx(getApplicationContext(), 30));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		optGuideHelp.showGuideHelp();
	}
}
