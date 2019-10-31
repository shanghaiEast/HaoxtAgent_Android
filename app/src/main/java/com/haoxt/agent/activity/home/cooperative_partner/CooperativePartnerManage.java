package com.haoxt.agent.activity.home.cooperative_partner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.util.DateUtil;
import com.haoxt.agent.util.HttpRequest;

import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/** 商户信息 Activity
 * @author baowen
 */
public class CooperativePartnerManage extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, CooperativePartnerManage.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cooperative_partner_manage);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private RelativeLayout ll_partner_manage,rl_partner_manage_search,ll_partner_salesman,rl_salesman_manage_search;
//	private TextView merchantName,merchantAddress,merchantNo,merchantContacts,merchantNumber,merchantOpenTime,merchantStatus,merchantDebitRate,merchantCreditRate,merchantQrcodeRate;
	@Override
	public void initView() {//必须调用

//		ll_partner_manage = (TextView)findViewById(R.id.tx_my_merchant_name);
//		merchantAddress = (TextView)findViewById(R.id.tv_my_merchant_address);
//
//		merchantNo = (TextView)findViewById(R.id.tv_myuser_merchant_no);
//		merchantContacts = (TextView)findViewById(R.id.tv_myuser_merchant_contacts);
//		merchantNumber = (TextView)findViewById(R.id.tv_myuser_merchant_number);
//		merchantOpenTime = (TextView)findViewById(R.id.tv_myuser_merchant_opentime);
//		merchantStatus = (TextView)findViewById(R.id.tv_myuser_merchant_status);
//		merchantDebitRate = (TextView)findViewById(R.id.tv_myuser_merchant_debit_rate);
//		merchantCreditRate = (TextView)findViewById(R.id.tv_myuser_merchant_credit_rate);
//		merchantQrcodeRate = (TextView)findViewById(R.id.tv_myuser_merchant_qrcode_rate);


	}

	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initData() {//必须调用

	}

	public void initEvent() {//必须调用

		findViewById(R.id.ll_partner_manage).setOnClickListener(this);
		findViewById(R.id.rl_partner_manage_search).setOnClickListener(this);
		findViewById(R.id.ll_partner_salesman).setOnClickListener(this);
		findViewById(R.id.rl_salesman_manage_search).setOnClickListener(this);

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.ll_partner_manage:
//				Intent intent = new Intent(context, RealNameAuthResultActivity.class);
//				intent.putExtra("activityfrom", "realnameinfo");
//				toActivity(intent);
				break;

			case R.id.rl_partner_manage_search:
			/*	Intent intent = new Intent(context, RealNameAuthResultActivity.class);
				intent.putExtra("activityfrom", "realnameinfo");
				toActivity(intent);*/
				break;

			case R.id.ll_partner_salesman:
//				Intent intent = new Intent(context, RealNameAuthResultActivity.class);
//				intent.putExtra("activityfrom", "realnameinfo");
//				toActivity(intent);
				break;

			case R.id.rl_salesman_manage_search:
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
