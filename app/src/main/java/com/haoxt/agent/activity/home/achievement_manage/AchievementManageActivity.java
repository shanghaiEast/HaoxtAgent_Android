package com.haoxt.agent.activity.home.achievement_manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.haoxt.agent.R;

import tft.mpos.library.base.BaseActivity;

/** 业绩查询 Activity
 * @author baowen
 */
public class AchievementManageActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "AchievementManageActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, AchievementManageActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievement_manage);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}

	private LinearLayout achievementDirect,total,partner;
	@Override
	public void initView() {//必须调用

//		achievementDirect = findViewById(R.id.ll_achievement_direct_sale);
//		total = findViewById(R.id.ll_achievement_direct_total);
//		partner = findViewById(R.id.ll_achievement_direct_partner);

	}

	@Override
	public void initData() {//必须调用

	}

	public void initEvent() {//必须调用

		findViewById(R.id.ll_achievement_direct_sale).setOnClickListener(this);
		findViewById(R.id.ll_achievement_direct_total).setOnClickListener(this);
		findViewById(R.id.ll_achievement_direct_partner).setOnClickListener(this);

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.ll_achievement_direct_sale:
				toActivity(AchievementListActivity.createIntent(this));
//				Intent intent = new Intent(context, RealNameAuthResultActivity.class);
//				intent.putExtra("activityfrom", "realnameinfo");
//				toActivity(intent);
				break;

			case R.id.ll_achievement_direct_total:
			/*	Intent intent = new Intent(context, RealNameAuthResultActivity.class);
				intent.putExtra("activityfrom", "realnameinfo");
				toActivity(intent);*/
				break;

			case R.id.ll_achievement_direct_partner:
//				Intent intent = new Intent(context, RealNameAuthResultActivity.class);
//				intent.putExtra("activityfrom", "realnameinfo");
//				toActivity(intent);
				break;

			default:
				break;
		}

	}


	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
