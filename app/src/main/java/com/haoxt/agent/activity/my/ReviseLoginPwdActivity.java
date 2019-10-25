package com.haoxt.agent.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.haoxt.agent.R;
import com.haoxt.agent.util.HttpRequest;

import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/** 修改登陆密码 Activity
 * @author baowen
 * @use toActivity(SettingActivity.createIntent(...));
 */
public class ReviseLoginPwdActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SettingActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, ReviseLoginPwdActivity.class);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.revise_login_pwd);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private TextView revise_login_pwd_oldpwd,revise_login_pwd_newpwd,revise_login_pwd_againpwd;
	@Override
	public void initView() {//必须调用

		revise_login_pwd_oldpwd = (TextView)findViewById(R.id.revise_login_pwd_oldpwd);
		revise_login_pwd_newpwd = (TextView)findViewById(R.id.revise_login_pwd_newpwd);
		revise_login_pwd_againpwd = (TextView)findViewById(R.id.revise_login_pwd_againpwd);

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

		findViewById(R.id.revise_pwd_info_commit).setOnClickListener(this);

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.revise_pwd_info_commit:

				this.uploadMsg();

				break;

			default:
				break;
		}

	}

	private void uploadMsg() {

		String oldPwd = revise_login_pwd_oldpwd.getText().toString();
		String pwd = revise_login_pwd_newpwd.getText().toString();
		String pwdAgain = revise_login_pwd_againpwd.getText().toString();

		if (oldPwd.equals("")) {
			Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
			return;
		}

		if (pwd.equals("")) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		}

		if (pwdAgain.equals("")) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		}

		if(!pwd.equals(pwdAgain)){
			Toast.makeText(this, "新密码输入不一致", Toast.LENGTH_SHORT).show();
			return;
		}

		HttpRequest.updatePassWord(oldPwd, pwd,0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
				Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

				if("000000".equals(dataMap.get("rspCd").toString())){

					showShortToast("密码更新成功");

				}else{
					showShortToast("登陆失败");
				}

			}
		});


	}


	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
