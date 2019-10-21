package com.haoxt.agent.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haoxt.agent.R;
import com.haoxt.agent.util.HttpRequest;

import java.util.Map;

import cn.cloudwalk.libproject.Bulider;
import cn.cloudwalk.libproject.CloudwalkBankCardOCRActivity;
import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/** 信用卡认证 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class CreditcardVerifedActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "CreditcardVerifedActivity";
	public final int MSG_GET_BANK_OK = 1;

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, CreditcardVerifedActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creditcard_verifed);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private EditText  tvName,tvIdCard,tvPhoneNo;
	private TextView etBankcardNo ;
	private Button btn_submit_certification,  btn_bankcard_no;
	private String bankName;

	@Override
	public void initView() {//必须调用

		etBankcardNo = findViewById(R.id.et_bankcard_no);
		tvName = findViewById(R.id.tv_name);
		tvIdCard =  findViewById(R.id.tv_idCard);
		tvPhoneNo = findViewById(R.id.et_phone_no);

	}

	@Override
	public void initData() {//必须调用
	}

	private void startBankCard() {

		Intent mIntent = new Intent(context, CloudwalkBankCardOCRActivity.class);
		mIntent.putExtra("LICENCE", Bulider.licence);
		mIntent.putExtra("BANKCARD_AUTO_RATIO", false);//是否支持竖版银行卡
		startActivity(mIntent);

		getBankOcrResult();
	}


	private void getBankOcrResult() {
		final Bulider bulider = new Bulider();
		bulider.setBankOCRFinished((bankCardInfo, path) -> {

			String cardNum = bankCardInfo.cardNum;
			bankName = bankCardInfo.bankName;

			Message msg = new Message();
			msg.what = MSG_GET_BANK_OK;
			Bundle bundle = new Bundle();
			bundle.putString("cardNum",cardNum);
			msg.setData(bundle);
			mHandler.sendMessage(msg);

		});
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case MSG_GET_BANK_OK:
					String bankNumber = msg.getData().getString("cardNum");
					etBankcardNo.setText(bankNumber);
					break;

			}
		};
	};


	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用

		findViewById(R.id.btn_submit_certification).setOnClickListener(this);
		findViewById(R.id.btn_bankcard_no).setOnClickListener(this);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.btn_submit_certification:
				this.commitData();
				break;

			case R.id.btn_bankcard_no:
				startBankCard();
				break;


			default:
				break;
		}

	}

	private void commitData() {

		String cardNo,name,idcard,bankphone;

		cardNo = etBankcardNo.getText().toString();
		name = tvName.getText().toString();
		idcard = tvIdCard.getText().toString();
		bankphone = tvPhoneNo.getText().toString();


		if (cardNo.equals("")) {
			Toast.makeText(this, "银行卡号为空", Toast.LENGTH_SHORT).show();
			return;
		}

		if (name.equals("")) {
			Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
			return;
		}
		if (idcard.equals("")) {
			Toast.makeText(this, "请输入身份证号", Toast.LENGTH_SHORT).show();
			return;
		}
		if (bankphone.equals("")) {
			Toast.makeText(this, "请输入银行预留手机号", Toast.LENGTH_SHORT).show();
			return;
		}


		HttpRequest.authPromoteLimit(cardNo, bankName,name,idcard,bankphone,0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
				Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

				if("000000".equals(dataMap.get("rspCd").toString())){

					Intent intent = new Intent(context, RealNameAuthResultActivity.class);
					intent.putExtra("activityfrom", "creditverifed");
					toActivity(intent);

				}else{
					showShortToast("提交失败");
				}

			}
		});


	}

}
