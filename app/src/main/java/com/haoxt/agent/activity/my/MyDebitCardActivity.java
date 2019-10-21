
package com.haoxt.agent.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.util.HttpRequest;

import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;


/**
 *
 * 我的结算卡
 *
 * */

/**用户列表界面Activity示例
 * @author baowen
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use toActivity(DemoHttpListActivity.createIntent(...));
 */
public class MyDebitCardActivity extends BaseActivity implements View.OnClickListener {
	//	private static final String TAG = "DemoHttpListActivity";


	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final int RANGE_ALL = HttpRequest.USER_LIST_RANGE_ALL;
	public static final int RANGE_RECOMMEND = HttpRequest.USER_LIST_RANGE_RECOMMEND;

	public static final String INTENT_RANGE = "INTENT_RANGE";


	/**启动这个Activity的Intent
	 * @param context
	 * @param range
	 * @return
	 */
	public static Intent createIntent(Context context, int range) {
		return new Intent(context, MyDebitCardActivity.class).putExtra(INTENT_RANGE, range);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	private int range = RANGE_ALL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_debit_card);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {


		switch (view.getId()) {

			case R.id.to_update_debit_card:
				toActivity(UpdateDebitCardActivity.createIntent(context));

				break;

			default:
				break;
		}
	}

	private TextView tvBankName,tvBankCardType,tvBankNo,toUpdateDebitCard;
	private ImageView logo;
	@Override
	public void initView() {
		logo = findViewById(R.id.bank_logo);

		tvBankName = (TextView)findViewById(R.id.tvBankName);
		tvBankCardType = (TextView)findViewById(R.id.tvBankCardType);
		tvBankNo = (TextView)findViewById(R.id.tvBankNo);
		toUpdateDebitCard = findViewById(R.id.to_update_debit_card);

	}

	@Override
	public void initData() {
		this.getCardMsg();
	}

	private void getCardMsg() {
		HttpRequest.getDebitCard(0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
				Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

				if("000000".equals(dataMap.get("rspCd").toString())){

					tvBankName.setText(userData.get("STL_BNK_NM")==null?"":userData.get("STL_BNK_NM").toString());
					tvBankNo.setText(userData.get("STL_ACO_NO")==null?"":userData.get("STL_ACO_NO").toString());
					tvBankCardType.setText(userData.get("STL_TYP")==null?"":userData.get("STL_TYP").toString());

				}else{
					showShortToast("查询失败");
				}

			}
		});
	}

	@Override
	public void initEvent() {

		findViewById(R.id.to_update_debit_card).setOnClickListener(this);

	}


	//生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}