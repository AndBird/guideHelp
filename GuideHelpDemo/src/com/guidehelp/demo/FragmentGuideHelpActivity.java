package com.guidehelp.demo;


import com.helpguide.lib.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;

/**FragmentµÄÊ¹ÓÃ*/
public class FragmentGuideHelpActivity extends FragmentActivity implements OnClickListener{
	private TestFragment1 testFragment1;
	private TestFragment2 testFragment2;
	private FragmentManager fm;
	private Fragment mContent;
	
	private TextView t1, t2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		initView();
	}
	
	private void initView(){
		t1 = (TextView) findViewById(R.id.page1);
		t2 = (TextView) findViewById(R.id.page2);
		
		t1.setOnClickListener(this);
		t2.setOnClickListener(this);
		
		testFragment1 = new TestFragment1();
		fm = this.getSupportFragmentManager();
		testFragment2 = new TestFragment2();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.lay, testFragment1);
		ft.commit();
		mContent = testFragment1;
		t1.setBackgroundColor(Color.RED);
	}

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.page1:
				switchPage(testFragment1);
				t1.setBackgroundColor(Color.RED);
				t2.setBackgroundColor(Color.WHITE);
				break;
			case R.id.page2:
				switchPage(testFragment2);
				t2.setBackgroundColor(Color.RED);
				t1.setBackgroundColor(Color.WHITE);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void switchPage(Fragment to){
		try {
			 if(mContent != to){
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				if(!to.isAdded()){
					transaction.hide(mContent).add(R.id.lay, to).commit();
				}else{
					transaction.hide(mContent).show(to).commit();
				}
				mContent = to;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
