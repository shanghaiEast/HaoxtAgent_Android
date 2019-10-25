package com.haoxt.agent.activity.my.profit_detailed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.agent.R;

import tft.mpos.library.base.BaseActivity;

/** 提现 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class CashWithdrawalActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, CashWithdrawalActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash_withdrawal);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView toDetail,bankName,amount,balanceNow,cashWithdrawalAll,cashWithdrawalPoundage;
	private ImageView bankLogo;
	private Button commit;
	@Override
	public void initView() {//必须调用

//		toDetail = findViewById(R.id.tx_cash_withdrawal_detail);

		bankLogo = findViewById(R.id.tx_cash_withdrawal_bank_logo);
		bankName = findViewById(R.id.tx_cash_withdrawal_bank_name);
		amount = findViewById(R.id.tx_cash_withdrawal_amount);
		balanceNow = findViewById(R.id.tx_cash_balance_now);
		cashWithdrawalPoundage = findViewById(R.id.tx_cash_withdrawal_poundage);

//		cashWithdrawalAll = findViewById(R.id.tx_cash_withdrawal_all);
//		commit = findViewById(R.id.bt_cash_withdrawal_commit);

	}

	public void initData() {//必须调用
	}

	@Override
	public void initEvent() {//必须调用

		findViewById(R.id.tx_cash_withdrawal_detail).setOnClickListener(this);
		findViewById(R.id.tx_cash_withdrawal_all).setOnClickListener(this);
		findViewById(R.id.bt_cash_withdrawal_commit).setOnClickListener(this);
		findViewById(R.id.ll_cash_withdrawal).setOnClickListener(this);


	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onReturnClick(View v) {
		finish();
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.tx_cash_withdrawal_detail:
				toActivity(CashWithdrawalListActivity.createIntent(context));
				break;

			case R.id.tx_cash_withdrawal_all:
//				toActivity(UpdatePhoneActivity.createIntent(context));
				break;

			case R.id.bt_cash_withdrawal_commit:
//				toActivity(UpdatePhoneActivity.createIntent(context));
				break;

			case R.id.ll_cash_withdrawal:
//				toActivity(UpdatePhoneActivity.createIntent(context));
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
