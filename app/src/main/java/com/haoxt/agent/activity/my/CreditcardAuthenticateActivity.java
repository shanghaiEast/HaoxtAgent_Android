
package com.haoxt.agent.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haoxt.agent.R;
import com.haoxt.agent.adapter.CardInfoAdapter;
import com.haoxt.agent.model.ItemCardInfo;
import com.haoxt.agent.model.Message;
import com.haoxt.agent.util.HttpRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import tft.mpos.library.base.BaseHttpListActivity;
import tft.mpos.library.interfaces.AdapterCallBack;
import tft.mpos.library.interfaces.OnBottomDragListener;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.JSON;


/**
 *
 * 信用卡认证列表
 *
 * */

/**用户列表界面Activity示例
 * @author baowen
 * @warn 复制到其它工程内使用时务必修改import R文件路径为所在应用包名
 * @use toActivity(DemoHttpListActivity.createIntent(...));
 */
public class CreditcardAuthenticateActivity extends BaseHttpListActivity<ItemCardInfo, ListView, CardInfoAdapter> implements OnBottomDragListener {
		private static final String TAG = "CreditcardAuthenticateActivity";


	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final int RANGE_ALL = HttpRequest.USER_LIST_RANGE_ALL;
	public static final int RANGE_RECOMMEND = HttpRequest.USER_LIST_RANGE_RECOMMEND;
	protected ArrayList<ItemCardInfo> bankArrayList;
	public static final String INTENT_RANGE = "INTENT_RANGE";
	public final int GET_MSG_BANK_OK = 1;


	/**启动这个Activity的Intent
	 * @param context
	 * @param range
	 * @return
	 */
	public static Intent createIntent(Context context, int range) {
		return new Intent(context, CreditcardAuthenticateActivity.class).putExtra(INTENT_RANGE, range);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	private int range = RANGE_ALL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creditcard_authenticate_list, this);

		intent = getIntent();
		range = intent.getIntExtra(INTENT_RANGE, range);


		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

		srlBaseHttpList.autoRefresh();
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private Button bankAuthenticate;
	@Override
	public void initView() {//必须调用
		super.initView();

        bankAuthenticate = findViewById(R.id.tv_bank_authenticate);
        bankAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toActivity(CreditcardVerifedActivity.createIntent(context));
            }
        });

	}

	@Override
	public void setList(final List<ItemCardInfo> list) {
		setList(new AdapterCallBack<CardInfoAdapter>() {

			@Override
			public CardInfoAdapter createAdapter() {
				return new CardInfoAdapter(context);
			}

			@Override
			public void refreshAdapter() {
				adapter.refresh(list);
			}
		});
	}



	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initData() {//必须调用
		super.initData();
		this.getBankList();
	}

	private void getBankList() {

	}

	@Override
	public void getListAsync(final int page) {

		HttpRequest.authlist("1", "5","",0, new OnHttpResponseListener() {

			@Override
			public void onHttpResponse(int requestCode, String resultJson, Exception e) {

				Gson gson = new Gson();
				Type type = new TypeToken<Message<ItemCardInfo>>() {}.getType();
				Message<ItemCardInfo> message = gson.fromJson(resultJson,type);
				bankArrayList = (ArrayList) message.getRspData();

				if("000000".equals(message.getRspCd())){

					android.os.Message msg = new android.os.Message();
					msg.what = GET_MSG_BANK_OK;
					Bundle bundle = new Bundle();
					bundle.putInt("page",page);
					msg.setData(bundle);
					mHandler.sendMessage(msg);

				}else{
					showShortToast("查询失败");
				}

			}
		});

//		new Handler().postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				onHttpResponse(-page, page >= 5 ? null : JSON.toJSONString(TestUtil.getCardInfoList(page, 3)), null);
//			}
//		}, 1000);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
				case GET_MSG_BANK_OK:

					int page = msg.getData().getInt("page");
					onHttpResponse(-page, page >= 5 ? null : JSON.toJSONString(bankArrayList), null);
					break;

			}
		};
	};

	@Override
	public List<ItemCardInfo> parseArray(String json) {
		return JSON.parseArray(json, ItemCardInfo.class);
	}

	@Override
	public void initEvent() {//必须调用
		super.initEvent();
		findViewById(R.id.to_quota_description).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showShortToast("敬请期待");
			}
		});
	}


	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {

			return;
		}

		finish();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (id > 0) {
//			toActivity(UpdateDebitCardActivity.createIntent(context));
		}
	}

}