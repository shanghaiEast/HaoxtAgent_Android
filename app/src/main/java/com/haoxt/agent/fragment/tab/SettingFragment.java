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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.agent.R;

import com.haoxt.agent.activity.my.MyDebitCardActivity;
import com.haoxt.agent.activity.my.MySettingActivity;
import com.haoxt.agent.activity.my.RealNameAuthenticationActivity;
import com.haoxt.agent.activity.my.profit_detailed.ProfitDetailedAvtivity;
import com.haoxt.agent.util.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import tft.mpos.library.base.BaseFragment;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.ui.AlertDialog.OnDialogButtonClickListener;
import tft.mpos.library.util.StringUtil;

/**我的fragment
 * @author bawen
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

	private ImageView profitDetailed;
	private TextView txuserName,txPhone,transation,activation,cashOut;
	private LinearLayout llRealName,llBankcard,llSettlementDetail,ll_Setting;

	@Override
	public void initView() {//必须调用


//		profitDetailed= findView(R.id.ig_setting_profit_detailed);

		txuserName = findView(R.id.tx_myheader_username);
		txPhone = findView(R.id.tx_myheader_phone);

		transation = findView(R.id.tx_transation_profit);
		activation = findView(R.id.tx_activation_fanxian);
		cashOut = findView(R.id.tx_cash_fanxian);

		llRealName = findView(R.id.ll_realname);
		llBankcard = findView(R.id.ll_mybankcard);
		llSettlementDetail = findView(R.id.ll_settlement_detail);
		ll_Setting = findView(R.id.ll_setting);
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
//						txmerchantNo.setText(userData.get("MERC_ID").toString());

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

		findView(R.id.ig_setting_profit_detailed).setOnClickListener(this);
		findView(R.id.ll_realname).setOnClickListener(this);
		findView(R.id.ll_mybankcard).setOnClickListener(this);
		findView(R.id.ll_settlement_detail).setOnClickListener(this);
		findView(R.id.ll_setting).setOnClickListener(this);
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
			case R.id.ig_setting_profit_detailed:
				toActivity(ProfitDetailedAvtivity.createIntent(context));

				break;

			case R.id.ll_realname:
				toActivity(RealNameAuthenticationActivity.createIntent(context));
				break;

			case R.id.ll_mybankcard:
				toActivity(MyDebitCardActivity.createIntent(context,0));
				break;

			case R.id.ll_settlement_detail:
//				toActivity(MyImmediateQuotaActivity.createIntent(context));
				break;

			case R.id.ll_setting:
				toActivity(MySettingActivity.createIntent(context));
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