package com.guidehelp.demo;


import com.guidehelp.lib.GuideHelpShowFinishListener;
import com.guidehelp.lib.OptGuideHelp;
import com.guidehelp.lib.PrintLog;
import com.guidehelp.lib.ScreenTool;
import com.guidehelp.lib.bean.GuideHelpTaskInfo;
import com.guidehelp.lib.bean.ShowPositionType;
import com.helpguide.demo.R;

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
	
	//引导
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
		//推迟显示，切换到其他fragment的测试
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
        //如果引导界面推迟显示，当页面切换时可能造成引导界面显示在其他fragment上，或者当切换回本fragment中，推迟显示引导界面(被之前取消显示)需要被触发显示
        if(hidden){
            if(optGuideHelp != null){
            	optGuideHelp.hideGuideHelp();
            }
        }else{
            showHelpTips();
        }
	 }
	
	/**
	 *  说明:由于fragment可以切换显示，所以当引导界面需要推迟显示或者当网络数据加载成功后显示，
	 *  就可能造成页面已经切换到其他fragment页面，然后引导界面还能显示出来，出现误导情况.故在有fragment
	 *  的页面中，引导界面的显示需要加入一些判断(普通activity不需要，fragment中的引导界面不需要推迟显示的，也不需要加入控制)
	 *  
	 *  处理:当fragment已经隐藏时，不再显示引导(推迟显示)，当页面恢复显示时，则立即触发引导界面的显示
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
            	 /*如果不加上这个if条件判断，推迟显示时当页面切换到其他fragment中时，引导页面也会显示，这样就出现误导，这里必须做条件判断，
            	 在普通activity中或者不需要推迟显示的fragment中，可以不做条件限制
            	 */
				 optGuideHelp.setGuideHelpShowFinishListener(new GuideHelpShowFinishListener() {
					
					@Override
					public void showEnd() {
						Toast.makeText(activity.getApplicationContext(), "引导结束", Toast.LENGTH_SHORT).show();	
					}
				});
			
				//先移除所有任务
				optGuideHelp.removeAllGuideHelpTask();
				//设置设置相对显示位置，设置顶部位置
				
				
				/*添加引导任务*/
				
				GuideHelpTaskInfo guideHelpTaskInfo = new GuideHelpTaskInfo();
				//设置绝对显示位置，设置顶部绝对位置(无箭头)
				guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
					.setTopShowY(ScreenTool.convertDpToPx(activity, 50));
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				//设置绝对显示位置，设置顶部绝对位置(有箭头)
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
					.setShowArrow(true)
					.setShowPositionType(ShowPositionType.Below)
					.setLeftMargin(20)
					.setTopShowY(ScreenTool.convertDpToPx(activity, 50));
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//设置引导文本，
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setTipText("这是引导帮助")
				.setLeftMargin(20)
				.setShowArrow(true)
				.setShowPositionType(ShowPositionType.Below)
				.setTopShowY(ScreenTool.convertDpToPx(activity, 50));
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				//上面靠左显示，无箭头
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView1))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Above)
					.setLeftMargin(10);//靠左显示,不设置时与attachView水平居中对齐
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//下面与testView2对齐，无箭头
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView2))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Below);
					//.setLeftMargin(10);//靠左显示,不设置时与attachView水平居中对齐
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//上面靠右显示，有箭头
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView3))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Above)
					//.setRightMargin(10)//靠右显示,不设置时与attachView水平居中对齐
					.setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//下面靠右显示，有箭头
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView4))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Below)
					//.setRightMargin(10)//靠右显示,不设置时与attachView水平居中对齐
					.setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//重叠显示
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView5))
					.setImageRes(R.drawable.tip_image)
					.setShowPositionType(ShowPositionType.Mid);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				//设置设置相对显示位置，设置底部绝对位置(无箭头)
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
					.setBottomShowY(ScreenTool.convertDpToPx(activity, 30)).setShowArrow(true);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				//设置设置相对显示位置，设置底部绝对位置(有箭头)
				guideHelpTaskInfo = new GuideHelpTaskInfo();
				guideHelpTaskInfo.setImageRes(R.drawable.tip_image)
					.setBottomShowY(ScreenTool.convertDpToPx(activity, 30))
					.setShowArrow(true)
					.setShowPositionType(ShowPositionType.Above);
				optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				/*文本显示测试*/
					    //上面靠左显示，无箭头
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("这是引导帮助")
							.setShowPositionType(ShowPositionType.Above)
							.setLeftMargin(10);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
						
						//下面与testView7对齐，无箭头
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("这是引导帮助")
							.setShowPositionType(ShowPositionType.Below);
							//.setLeftMargin(10);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
						
						//上面靠右显示，有箭头
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("这是引导帮助")
							.setShowPositionType(ShowPositionType.Above)
							//.setRightMargin(10) //靠右显示,不设置时与attachView水平居中对齐
							.setShowArrow(true);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
						
						//下面靠右显示，有箭头
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("这是引导帮助")
							.setShowPositionType(ShowPositionType.Below)
							//.setRightMargin(10)//靠右显示,不设置时与attachView水平居中对齐
							.setShowArrow(true);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
						
						//重叠显示
						guideHelpTaskInfo = new GuideHelpTaskInfo();
						guideHelpTaskInfo.setAttachView(rootView.findViewById(R.id.testView7))
							.setTipText("这是引导帮助")
							.setShowPositionType(ShowPositionType.Mid);
						optGuideHelp.addGuideHelpTask(guideHelpTaskInfo);
				
				
				/* end 文本显示测试*/
				
				optGuideHelp.showGuideHelp(activity);
             }else{
            	 PrintLog.printLog(TAG, "已显示过或者正在显示,needShow=" + needShow);
             }
		} catch (Exception e){
			e.printStackTrace();
		}
    }
}

