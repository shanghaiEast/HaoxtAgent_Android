package com.haoxt.agent.activity.my.profit_detailed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.adapter.CashWithdrawalDetailListAdapter;
import com.haoxt.agent.entity.ListProfitTransaction;
import com.haoxt.agent.util.DateUtil;
import com.haoxt.agent.widget.MyDialog;

import java.util.ArrayList;

import tft.mpos.library.base.BaseActivity;

/** 提现明细列表 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class CashWithdrawalListActivity extends BaseActivity  implements View.OnClickListener ,CashWithdrawalDetailListAdapter.OnItemClickListener {
	private static final String TAG = "CashWithdrawalDetailActivity";
	private static final int CASHWITHDRAWALLIST = 1000;
	private ArrayList<ListProfitTransaction> mList;
	private MyDialog mConditionPopup;


	public CashWithdrawalListActivity() {
	}

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, CashWithdrawalListActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash_withdrawal_list);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView screen,noData;
	private RecyclerView datalist;
	@Override
	public void initView() {//必须调用

		screen = findViewById(R.id.tx_cash_withdrawal_screen);
		noData = findViewById(R.id.no_data_tv);
		datalist = findViewById(R.id.data_list);
		datalist.setLayoutManager(new LinearLayoutManager(getActivity()));

	}

	public void initData() {//必须调用

		mList = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			ListProfitTransaction listTransaction = new ListProfitTransaction();
			listTransaction.setType("收益分润");
			listTransaction.setStatus("待审核");
			listTransaction.setAmount(String.valueOf(12+i));
			listTransaction.setTime(DateUtil.getTime());

			mList.add(listTransaction);
		}


		if (mList == null || mList.size() < 1) {
			datalist.setVisibility(View.GONE);
			noData.setVisibility(View.VISIBLE);
		} else {
			noData.setVisibility(View.GONE);
			datalist.setVisibility(View.VISIBLE);
			CashWithdrawalDetailListAdapter adapter = new CashWithdrawalDetailListAdapter(context, mList);
			datalist.setAdapter(adapter);
			adapter.setOnItemClickListener(this);
		}
	}

	@Override
	public void initEvent() {//必须调用

		findViewById(R.id.tx_cash_withdrawal_screen).setOnClickListener(this);
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
			case R.id.tx_cash_withdrawal_screen:
				startActivityForResult(ConditionFilterAvtivity.createIntent(context),CASHWITHDRAWALLIST);

				break;

			default:
				break;
		}

	}

	@Override
	public void onItemClick(int position) {
		toActivity(CashWithdrawalDetailActivity.createIntent(context));

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode){
			case CASHWITHDRAWALLIST:
				ArrayList<String> list=data.getStringArrayListExtra("checkboxlist");
				String startime = data.getStringExtra("startime");
				String endtime = data.getStringExtra("endtime");

				break;

			default:
				break;
		}
	}
}
