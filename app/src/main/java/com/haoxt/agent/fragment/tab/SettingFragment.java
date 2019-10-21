/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.haoxt.agent.fragment.tab;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.agent.R;

import com.haoxt.agent.activity.my.MyDebitCardActivity;
import com.haoxt.agent.activity.my.RealNameAuthenticationActivity;
import com.haoxt.agent.application.AppApplication;
import com.haoxt.agent.util.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import tft.mpos.library.base.BaseFragment;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.ui.AlertDialog;
import tft.mpos.library.ui.AlertDialog.OnDialogButtonClickListener;
import tft.mpos.library.util.StringUtil;

/**设置fragment
 * @author Lemon
 * @use new SettingFragment(),详细使用见.DemoFragmentActivity(initData方法内)
 */
public class SettingFragment extends BaseFragment implements OnClickListener, OnDialogButtonClickListener {

	private HashMap<String, Object> pageData;
//	private static final String TAG = "SettingFragment";

	//与Activity通信<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/**创建一个Fragment实例
	 * @return
	 */
	public static SettingFragment createInstance() {
		return new SettingFragment();
	}

	//与Activity通信>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//类相关初始化，必须使用<<<<<<<<<<<<<<<<
		super.onCreateView(inflater, container, savedInstanceState);
		setContentView(R.layout.setting_fragment);
		//类相关初始化，必须使用>>>>>>>>>>>>>>>>

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		return view;
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private ImageView ivSettingHead;
	private TextView txuserName,txPhone,txmerchantNo,txMerchantNo,txRealnameAuth,txCreditCard,txQuota,txMyPos;
	private LinearLayout llCreditCardAuth,llPOSApply,llCustomerServiceOnline,llMyHelp;

	@Override
	public void initView() {//必须调用


		ivSettingHead = findView(R.id.ivSettingHead);

		txuserName = findView(R.id.tx_myheader_username);
		txPhone = findView(R.id.tx_myheader_phone);
		txmerchantNo = findView(R.id.tv_my_merchant_no);

		txRealnameAuth = findView(R.id.tv_my_realname_auth);
		txCreditCard = findView(R.id.tv_my_credit_card);
		txQuota = findView(R.id.tv_my_quota);
		txMyPos = findView(R.id.tv_my_pos);

		llCreditCardAuth = findView(R.id.llCreditCardAuth);
		llPOSApply = findView(R.id.llPOSApply);
		llCustomerServiceOnline = findView(R.id.llCustomerServiceOnline);
		llMyHelp = findView(R.id.llMyHelp);
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		pageData = ((MainTabActivity) context).getPageData();
//            if(pageData.get("USR_OPR_NM")!=null){
//                username.setText(pageData.get("USR_OPR_NM").toString());
//            }
//            if(pageData.get("USR_LOGIN_MBL")!=null){
//                phone.setText(pageData.get("USR_LOGIN_MBL").toString());
//            }
//

	}





	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用

		txuserName.setText(pageData.get("USR_OPR_NM")==null?"":pageData.get("USR_OPR_NM").toString());
		txPhone.setText(pageData.get("USR_LOGIN_MBL")==null?"":pageData.get("USR_LOGIN_MBL").toString());


		getUserInfo();
	}

	private void getUserInfo() {

		HttpRequest.getUserInfo(0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				if(!StringUtil.isEmpty(resultJson)){
					Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
					Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

					if(dataMap!=null&&"000000".equals(dataMap.get("rspCd").toString())){
						//login
						txmerchantNo.setText(userData.get("MERC_ID").toString());

					}else{
						showShortToast("获取商户信息失败");
					}
				}
			}
		});
	}


	private void logout() {
		context.finish();
	}


	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用

		ivSettingHead.setOnClickListener(this);
		findView(R.id.ll_merchant_info).setOnClickListener(this);

		findView(R.id.tv_my_realname_auth).setOnClickListener(this);
		findView(R.id.tv_my_credit_card).setOnClickListener(this);
		findView(R.id.tv_my_quota).setOnClickListener(this);
		findView(R.id.tv_my_pos).setOnClickListener(this);

		findView(R.id.llCreditCardAuth).setOnClickListener(this);
		findView(R.id.llPOSApply).setOnClickListener(this);
		findView(R.id.llCustomerServiceOnline).setOnClickListener(this);
		findView(R.id.llMyHelp).setOnClickListener(this);

	}




	@Override
	public void onDialogButtonClick(int requestCode, boolean isPositive) {
		if (! isPositive) {
			return;
		}

		switch (requestCode) {
		case 0:
			logout();
			break;
		default:
			break;
		}
	}



	@Override
	public void onClick(View v) {//直接调用不会显示v被点击效果
		switch (v.getId()) {
			case R.id.ivSettingHead:
//				toActivity(MySettingActivity.createIntent(context));
				break;

			case R.id.ll_merchant_info:
//				toActivity(MyUserInfoActivity.createIntent(context));
				break;

			case R.id.llCreditCardAuth:
//				toActivity(CreditcardAuthenticateActivity.createIntent(context,0));
				break;
			case R.id.llPOSApply:
//				toActivity(MyPOSApplyActivity.createIntent(context));
				break;
			case R.id.llCustomerServiceOnline:
//				toActivity(DeviceBindActivity.createIntent(context));
				break;

			case R.id.llMyHelp:

				break;

			case R.id.tv_my_realname_auth:
				toActivity(RealNameAuthenticationActivity.createIntent(context));
				break;

			case R.id.tv_my_credit_card:
				toActivity(MyDebitCardActivity.createIntent(context,0));
				break;

			case R.id.tv_my_quota:
//				toActivity(MyImmediateQuotaActivity.createIntent(context));
				break;

			case R.id.tv_my_pos:
				if(AppApplication.getInstance().getUserTermStatus().equals("0")){
					new AlertDialog(context, "提示", "当前账号未绑定机具，请绑定机具", true, 0, new AlertDialog.OnDialogButtonClickListener(){
						@Override
						public void onDialogButtonClick(int requestCode, boolean isPositive) {

							if (isPositive == true){
//								toActivity(DeviceBindActivity.createIntent(context));
							}else{
								Log.d("状态--------->", "--"+isPositive+"===="+requestCode);
							}

						}
					}).show();
					return;

				}else{
//					toActivity(MyPOSInfoActivity.createIntent(context));
				}

				break;

			default:
				break;
		}
	}




	//生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}