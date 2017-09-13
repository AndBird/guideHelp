package com.guidehelp.demo;


import com.guidehelp.lib.GuideHelpShowFinishListener;
import com.guidehelp.lib.OptGuideHelp;
import com.guidehelp.lib.PrintLog;
import com.guidehelp.lib.ScreenTool;
import com.guidehelp.lib.bean.GuideHelpTaskInfo;
import com.guidehelp.lib.bean.ShowPositionType;
import com.helpguide.lib.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TestFragment1 extends Fragment{
	private static final String TAG = TestFragment1.class.getSimpleName();
	
	private Activity activity;
	private View rootView;
	
	//����
    private OptGuideHelp optGuideHelp;
    private boolean isHidden = false;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_guide_help_test, container, false);
		//�Ƴ���ʾ���л�������fragment�Ĳ���
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				showHelpTips();
			}
		}, 5000);
		return rootView;
	}
	
	
	 @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        PrintLog.printLog(TAG, "onHiddenChanged:" + hidden);
        isHidden = hidden;
        //������������Ƴ���ʾ����ҳ���л�ʱ�����������������ʾ������fragment�ϣ����ߵ��л��ر�fragment�У��Ƴ���ʾ��������(��֮ǰȡ����ʾ)��Ҫ��������ʾ
        if(hidden){
            if(optGuideHelp != null){
            	optGuideHelp.hideGuideHelp();
            }
        }else{
            showHelpTips();
        }
	 }
	
	/**
	 *  ˵��:����fragment�����л���ʾ�����Ե�����������Ҫ�Ƴ���ʾ���ߵ��������ݼ��سɹ�����ʾ��
	 *  �Ϳ������ҳ���Ѿ��л�������fragmentҳ�棬Ȼ���������滹����ʾ���������������.������fragment
	 *  ��ҳ���У������������ʾ��Ҫ����һЩ�ж�(��ͨactivity����Ҫ��fragment�е��������治��Ҫ�Ƴ���ʾ�ģ�Ҳ����Ҫ�������)
	 *  
	 *  ����:��fragment�Ѿ�����ʱ��������ʾ����(�Ƴ���ʾ)����ҳ��ָ���ʾʱ�����������������������ʾ
	 *  
	 *  */
	 private void showHelpTips(){
    	try {
    		 PrintLog.printLog(TAG, "getUserVisibleHint()=" + getUserVisibleHint() + ",isHidden=" + isHidden);
    		 PrintLog.printLog(TAG, "getUserVisibleHint()=" + getUserVisibleHint() + ",isHidden()=" + isHidden() + ",isVisible=" + isVisible());
    		 if(optGuideHelp == null){
    			 optGuideHelp = new OptGuideHelp(activity);
             }
             boolean needShow = true;
             if(needShow && !isHidden && !optGuideHelp.isShowing()){
            	 /*������������if�����жϣ��Ƴ���ʾʱ��ҳ���л�������fragment��ʱ������ҳ��Ҳ����ʾ�������ͳ����󵼣���������������жϣ�
            	 ����ͨactivity�л��߲���Ҫ�Ƴ���ʾ��fragment�У����Բ�����������
            	 */
				 optGuideHelp.setGuideHelpShowFinishListener(new GuideHelpShowFinishListener() {
					
					@Override
					public void showEnd() {
						Toast.makeText(activity.getApplicationContext(), "��������", Toast.LENGTH_SHORT).show();	
					}
				});
			
				//���Ƴ���������
				optGuideHelp.removeAllGuideHelpTask();
				//�������������ʾλ�ã����ö���λ��
				
				
				/*�����������*/
				
				GuideHelpTaskInfo guideHelpTaskInfo = new GuideHelpTaskInfo();
				//���þ�����ʾλ�ã����ö�������λ��(�޼�ͷ)
				guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
					.setTopShowY(ScreenTool.convertDpToPx(activity, 50));
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				//���þ�����ʾλ�ã����ö�������λ��(�м�ͷ)
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
					.setShowArrow(true)
					.setShowPositionType(ShowPositionType.Below)
					.setLeftMargin(20)
					.setTopShowY(ScreenTool.convertDpToPx(activity, 50));
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//���������ı���
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setTipText("������������")
				.setLeftMargin(20)
				.setShowArrow(true)
				.setShowPositionType(ShowPositionType.Below)
				.setTopShowY(ScreenTool.convertDpToPx(activity, 50));
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				//���濿����ʾ���޼�ͷ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView1))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Above)
					.setLeftMargin(10);//������ʾ,������ʱ��attachViewˮƽ���ж���
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//������testView2���룬�޼�ͷ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView2))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Below);
					//.setLeftMargin(10);//������ʾ,������ʱ��attachViewˮƽ���ж���
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//���濿����ʾ���м�ͷ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView3))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Above)
					//.setRightMargin(10)//������ʾ,������ʱ��attachViewˮƽ���ж���
					.setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//���濿����ʾ���м�ͷ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView4))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Below)
					//.setRightMargin(10)//������ʾ,������ʱ��attachViewˮƽ���ж���
					.setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//�ص���ʾ
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView5))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Mid);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//�������������ʾλ�ã����õײ�����λ��(�޼�ͷ)
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
					.setBottomShowY(ScreenTool.convertDpToPx(activity, 30)).setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				//�������������ʾλ�ã����õײ�����λ��(�м�ͷ)
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
					.setBottomShowY(ScreenTool.convertDpToPx(activity, 30))
					.setShowArrow(true)
					.setShowPositionType(ShowPositionType.Above);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				/*�ı���ʾ����*/
					    //���濿����ʾ���޼�ͷ
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("������������")
							.setShowPositionType(ShowPositionType.Above)
							.setLeftMargin(10);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
						
						//������testView7���룬�޼�ͷ
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("������������")
							.setShowPositionType(ShowPositionType.Below);
							//.setLeftMargin(10);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
						
						//���濿����ʾ���м�ͷ
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("������������")
							.setShowPositionType(ShowPositionType.Above)
							//.setRightMargin(10) //������ʾ,������ʱ��attachViewˮƽ���ж���
							.setShowArrow(true);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
						
						//���濿����ʾ���м�ͷ
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("������������")
							.setShowPositionType(ShowPositionType.Below)
							//.setRightMargin(10)//������ʾ,������ʱ��attachViewˮƽ���ж���
							.setShowArrow(true);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
						
						//�ص���ʾ
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("������������")
							.setShowPositionType(ShowPositionType.Mid);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				/* end �ı���ʾ����*/
				
				optGuideHelp.showGuideHelp();		
             }else{
            	 PrintLog.printLog(TAG, "����ʾ������������ʾ,needShow=" + needShow);
             }
		} catch (Exception e){
			e.printStackTrace();
		}
    }
}

