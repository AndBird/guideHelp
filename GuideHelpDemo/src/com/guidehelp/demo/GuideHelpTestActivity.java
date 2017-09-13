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

//普通activity的引导
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
				Toast.makeText(getApplicationContext(), "引导结束", Toast.LENGTH_SHORT).show();
			}
		});
		
		/*添加引导任务*/
		
		//设置绝对显示位置，设置顶部绝对位置(无箭头)
		GuideHelpTaskInfo guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setTopShowY(ScreenTool.convertDpToPx(getApplicationContext(), 35));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		//设置绝对显示位置，设置顶部绝对位置(有箭头)
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setShowArrow(true)
			.setShowPositionType(ShowPositionType.Below)
			.setTopShowY(ScreenTool.convertDpToPx(getApplicationContext(), 35));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//设置引导文本，
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setTipText("这是引导帮助")
		.setTopShowY(ScreenTool.convertDpToPx(getApplicationContext(), 35));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		//上面靠左显示，无箭头
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView1))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Above)
			.setLeftMargin(10);//靠左显示,不设置时与attachView水平居中对齐
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//下面与testView2对齐，无箭头
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView2))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Below);
			//.setLeftMargin(10);//靠左显示,不设置时与attachView水平居中对齐
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//上面靠右显示，有箭头
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView3))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Above)
			//.setRightMargin(10)//靠右显示,不设置时与attachView水平居中对齐
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//下面靠右显示，有箭头
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView4))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Below)
			//.setRightMargin(10)//靠右显示,不设置时与attachView水平居中对齐
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//重叠显示
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView5))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Mid);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//设置设置相对显示位置，设置底部绝对位置(无箭头)
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setBottomShowY(ScreenTool.convertDpToPx(getApplicationContext(), 30))
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		//设置设置相对显示位置，设置底部绝对位置(有箭头)
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setBottomShowY(ScreenTool.convertDpToPx(getApplicationContext(), 30))
			.setShowArrow(true)
			.setShowPositionType(ShowPositionType.Above);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		/*文本显示测试*/
			    //上面靠左显示，无箭头
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("这是引导帮助")
					.setShowPositionType(ShowPositionType.Above)
					.setLeftMargin(10);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//下面与testView7对齐，无箭头
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("这是引导帮助")
					.setShowPositionType(ShowPositionType.Below);
					//.setLeftMargin(10);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//上面靠右显示，有箭头
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("这是引导帮助")
					.setShowPositionType(ShowPositionType.Above)
					//.setRightMargin(10) //靠右显示,不设置时与attachView水平居中对齐
					.setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//下面靠右显示，有箭头
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("这是引导帮助")
					.setShowPositionType(ShowPositionType.Below)
					//.setRightMargin(10)//靠右显示,不设置时与attachView水平居中对齐
					.setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//重叠显示
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(findViewById(R.id.testView7))
					.setTipText("这是引导帮助")
					.setShowPositionType(ShowPositionType.Mid);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		
		/* end 文本显示测试*/
		
		
		
		optGuideHelp.showGuideHelp();
	}
	
	
	//相对布局实现(遗弃，想在4个方向上实现箭头，仅搭建了设计初步结构)
	private void showGuideHelp2(){
		OptGuideHelpRelativeLayout optGuideHelp = new OptGuideHelpRelativeLayout(this);
		optGuideHelp.setGuideHelpShowFinishListener(new GuideHelpShowFinishListener() {
			@Override
			public void showEnd() {
				Toast.makeText(getApplicationContext(), "引导结束", Toast.LENGTH_SHORT).show();
			}
		});
		
		/*添加引导任务*/
		
		//设置设置相对显示位置，设置顶部位置
		GuideHelpTaskInfo guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setTopShowY(ScreenTool.convertDpToPx(getApplicationContext(), 35));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//上面靠左显示，无箭头
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView1))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Above)
			.setLeftMargin(10);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//下面靠左显示，无箭头
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView2))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Below)
			.setLeftMargin(10);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//上面靠右显示，有箭头
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView3))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Above)
			.setRightMargin(10)
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//下面靠右显示，有箭头
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView4))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Below)
			.setRightMargin(10)
			.setShowArrow(true);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//重叠显示
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setAttachView(findViewById(R.id.testView5))
			.setImageRes(R.drawable.tip_image)
			.setShowPositionType(ShowPositionType.Mid);
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		//设置设置相对显示位置，设置底部位置
		guideHelpTaskInfo = new GuideHelpTaskInfo();
		guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
			.setBottomShowY(ScreenTool.convertDpToPx(getApplicationContext(), 30));
		optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
		
		optGuideHelp.showGuideHelp();
	}
}
