package com.haoxt.agent.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haoxt.agent.R;
import com.haoxt.agent.util.HttpRequest;

import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/** 更改结算卡信息 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class UpdateDebitCardActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, UpdateDebitCardActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_debit_card);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private EditText update_debit_card_number,update_debit_card_top_bank,update_debit_card_open_branch,update_debit_card_open_area,update_debit_card_open_bank;
	private TextView merchan_name,merchan_card_no;

	@Override
	public void initView() {//必须调用

		update_debit_card_number = (EditText)findViewById(R.id.update_debit_card_number);
		update_debit_card_top_bank = findViewById(R.id.update_debit_card_open_bank);

		update_debit_card_open_branch = (EditText)findViewById(R.id.update_debit_card_open_branch);
		update_debit_card_open_area = (EditText)findViewById(R.id.update_debit_card_open_area);

		merchan_name =  findViewById(R.id.update_merchan_name);
		merchan_card_no = findViewById(R.id.update_merchan_card_no);


	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initData() {//必须调用
		this.getUserInfo();
	}



	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void getUserInfo() {

		HttpRequest.getUserInfo(0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				if(!StringUtil.isEmpty(resultJson)){
					Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
					Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

					if(dataMap!=null&&"000000".equals(dataMap.get("rspCd").toString())){
						//login
						merchan_name.setText(userData.get("MERC_NM")==null?"":userData.get("MERC_NM").toString());
						merchan_card_no.setText(userData.get("MERC_ADDR")==null?"":userData.get("MERC_ADDR").toString());

					}else{
						showShortToast("获取商户信息失败");
					}
				}
			}
		});
	}







	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用

		findViewById(R.id.btn_updatedebit_no).setOnClickListener(this);
		findViewById(R.id.btn_updatedebit_commit).setOnClickListener(this);

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.btn_updatedebit_no:
//				Intent intent = new Intent(context, RealNameAuthResultActivity.class);
//				intent.putExtra("activityfrom", "realnameinfo");
//				toActivity(intent);
				break;

			case R.id.btn_updatedebit_commit:
				this.uploadData();

				break;

			default:
				break;
		}

	}

	private void uploadData() {
		String cardNo,bank,province,city,brachBank;


		cardNo = update_debit_card_number.getText().toString();
		bank = update_debit_card_top_bank.getText().toString();
		province = update_debit_card_open_area.getText().toString();
		city = update_debit_card_open_area.getText().toString();
		brachBank = update_debit_card_open_branch.getText().toString();



		if("".equals(cardNo)){
			Toast.makeText(this, "银行卡号为空", Toast.LENGTH_SHORT).show();
			return;
		}

		if (bank.equals("")) {
			Toast.makeText(this, "开户行为空", Toast.LENGTH_SHORT).show();
			return;
		}

		if (province.equals("")) {
			Toast.makeText(this, "省份为空", Toast.LENGTH_SHORT).show();
			return;
		}

		if (city.equals("")) {
			Toast.makeText(this, "城市为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (brachBank.equals("")) {
			Toast.makeText(this, "分行为空", Toast.LENGTH_SHORT).show();
			return;
		}

		HttpRequest.updStlBankInfo(province, city,"","","","","",0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);

				if("000000".equals(dataMap.get("rspCd").toString())){
					showShortToast("修改成功");
				}else{
					showShortToast("修改失败");
				}
			}
		});
	}


	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
