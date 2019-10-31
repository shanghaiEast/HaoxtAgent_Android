package com.haoxt.agent.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.haoxt.agent.R;
import com.haoxt.agent.util.HttpRequest;

import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/** 修改手机号 Activity
 * @author baowen
 */
public class UpdatePhoneActivity extends BaseActivity implements OnClickListener {

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, UpdatePhoneActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_phone);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private EditText oldPhone, oldPhoneVerificationCode, newPhone,newPhonVerificationCode;
	private LinearLayout ll1,ll2;
	@Override
	public void initView() {//必须调用

		oldPhone = (EditText) findViewById(R.id.et_update_phone_old);
		oldPhoneVerificationCode = (EditText) findViewById(R.id.et_update_phone_old_verification_code);
		newPhone = (EditText) findViewById(R.id.et_update_phone_new);
		newPhonVerificationCode = (EditText) findViewById(R.id.et_update_phone_new_verification_code);

		ll1 = findViewById(R.id.update_phone_ll1);
		ll2 = findViewById(R.id.update_phone_ll2);

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>






	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	@Override
	public void initData() {//必须调用
	}



	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用

		findViewById(R.id.bt_update_phone_old_verification_code_button1).setOnClickListener(this);
		findViewById(R.id.bt_update_phone_new_verification_code_button2).setOnClickListener(this);
		findViewById(R.id.btn_update_phone1).setOnClickListener(this);
		findViewById(R.id.btn_update_phone2).setOnClickListener(this);

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	/**
	 * 获取验证码
	 */
	private void getVerificationCode(String type,String number) {

		HttpRequest.getVerificationCode(type, number,0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {
				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);

//				verificationCodeBtn.setEnabled(false);
//				verificationCodeBtn.setTextColor(context.getResources().getColor(R.color.gray_bdbdbd));
//
				showShortToast(dataMap.get("rspInf").toString());

			}
		});
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.bt_update_phone_old_verification_code_button1:
				String oldPhoneTx = oldPhone.getText().toString();
				if("".equals(oldPhoneTx)){
					Toast.makeText(this, "原手机号为空", Toast.LENGTH_SHORT).show();
					return;
				}

				getVerificationCode("1",oldPhoneTx);

				break;

			case R.id.bt_update_phone_new_verification_code_button2:
				String newPhoneTx = newPhone.getText().toString();
				if("".equals(newPhoneTx)){
					Toast.makeText(this, "新手机号为空", Toast.LENGTH_SHORT).show();
					return;
				}

				getVerificationCode("2",newPhoneTx);
				break;

			case R.id.btn_update_phone1:
				String oldPhoneTx1 = oldPhone.getText().toString();
				String  code1 = oldPhoneVerificationCode.getText().toString();

				if("".equals(oldPhoneTx1)){
					Toast.makeText(this, "原手机号为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if("".equals(code1)){
					Toast.makeText(this, "验证码为空", Toast.LENGTH_SHORT).show();
					return;
				}

				this.uploadPhone1(oldPhoneTx1,code1);


				break;

			case R.id.btn_update_phone2:

				String oldPhoneTx2 = newPhone.getText().toString();
				String  code2 = newPhonVerificationCode.getText().toString();

				if("".equals(oldPhoneTx2)){
					Toast.makeText(this, "新手机号为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if("".equals(code2)){
					Toast.makeText(this, "验证码为空", Toast.LENGTH_SHORT).show();
					return;
				}

				this.uploadPhone2(oldPhoneTx2,code2);


				break;

			default:
				break;
		}

	}

	/**
	 * 第一步
	 * @param oldPhoneTx
	 * @param code
	 */
	private void uploadPhone1(String oldPhoneTx, String code) {

		HttpRequest.updatePhone1(oldPhoneTx, code,0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
				Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

				if("000000".equals(dataMap.get("rspCd").toString())){

					ll1.setVisibility(View.GONE);
					ll2.setVisibility(View.VISIBLE);
				}else{
					showShortToast("提交失败");
				}

			}
		});
	}

	/**
	 * 第二部
	 * @param newPhoneTx
	 * @param code
	 */
	private void uploadPhone2(String newPhoneTx, String code) {

		HttpRequest.updatePhone1(newPhoneTx, code,0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
				Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

				if("000000".equals(dataMap.get("rspCd").toString())){

					showShortToast("电话更新成功");

				}else{
					showShortToast("提交失败");
				}

			}
		});
	}


	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
