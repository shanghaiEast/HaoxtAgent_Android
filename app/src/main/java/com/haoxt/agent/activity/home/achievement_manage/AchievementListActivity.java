package com.haoxt.agent.activity.home.achievement_manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.activity.my.profit_detailed.ConditionFilterAvtivity;

import java.util.ArrayList;
import java.util.List;

import tft.mpos.library.base.BaseActivity;

/** 业绩查询 Activity
 * @author baowen
 */
public class AchievementListActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "AchievementListActivity";
	private AchievementSearchFragmentAdapter adapter;

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, AchievementListActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievement_tab);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}

	private TabLayout tabLayout;
	private ViewPager viewPager;
	private TextView title,screen;
	@Override
	public void initView() {//必须调用

		tabLayout = findViewById(R.id.achievement_tab_layout);
		viewPager = findViewById(R.id.achievement_view_pager);

		title = findViewById(R.id.tx_achievement_title);
		screen = findViewById(R.id.tx_achievement_screen);

		screen.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				int posation = tabLayout.getSelectedTabPosition();

				toActivity(ConditionFilterAvtivity.createIntent(getActivity()));
			}
		});

	}

	private List<String> names;
	@Override
	public void initData() {//必须调用

		names = new ArrayList<>();
		names.add("按月查询");
		names.add("按日期查询");

	}

	public void initEvent() {//必须调用

		FragmentManager manager = getSupportFragmentManager();
		adapter = new AchievementSearchFragmentAdapter(manager);
		viewPager.setAdapter(adapter);
		tabLayout.setupWithViewPager(viewPager);

		// 更新适配器数据
		adapter.setList(names);

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tx_achievement_screen:
//				Intent intent = new Intent(context, RealNameAuthResultActivity.class);
//				intent.putExtra("activityfrom", "realnameinfo");
//				toActivity(intent);
				int posation = tabLayout.getSelectedTabPosition();

				toActivity(ConditionFilterAvtivity.createIntent(getActivity()));

				break;

			case R.id.ll_achievement_direct_total:

				break;

			case R.id.ll_achievement_direct_partner:

				break;

			case R.id.rl_salesman_manage_search:

				break;

			default:
				break;
		}

	}

}
