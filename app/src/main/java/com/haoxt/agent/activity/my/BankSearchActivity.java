
package com.haoxt.agent.activity.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haoxt.agent.R;
import com.haoxt.agent.adapter.BaseInfoAdapter;
import com.haoxt.agent.adapter.SubBankInfoAdapter;
import com.haoxt.agent.model.BankInfo;
import com.haoxt.agent.model.Message;
import com.haoxt.agent.model.SubBankInfoSet;
import com.haoxt.agent.util.HttpRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;


/**
 *
 * 银行搜索 Activity
 *
 * */
public class BankSearchActivity extends BaseActivity implements View.OnClickListener {

	public static final int RANGE_ALL = HttpRequest.USER_LIST_RANGE_ALL;
	public static final int RANGE_RECOMMEND = HttpRequest.USER_LIST_RANGE_RECOMMEND;

	public static final String INTENT_RANGE = "INTENT_RANGE";
	private BaseInfoAdapter baseInfoAdapter;
	private SubBankInfoAdapter subBankInfoAdapter;
//	protected ArrayList<BankInfo> topArrayList,branchArrayList;

	protected ArrayList<BankInfo> topArrayList;
	protected ArrayList<SubBankInfoSet> branchArrayList;
	private String nameMsg,topBankNo,province,city;


	/**启动这个Activity的Intent
	 * @param context
	 * @return
	 */
	public static Intent createIntent(Context context) {
		return new Intent(context, BankSearchActivity.class);
	}

	private int range = RANGE_ALL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TODO demo_http_list_activity改为你所需要的layout文件；传this是为了底部左右滑动手势
		setContentView(R.layout.activity_search_bank);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>
	}

 	private ListView listView;
	private EditText name;
	@Override
	public void initView() {//必须调用

		name = findViewById(R.id.bank_searc_name);
		listView = findViewById(R.id.bank_name_lv);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				Intent resultIntent = new Intent();

				if("0".equals(searchPageFrom)){
					resultIntent.putExtra("searchPageFrom",searchPageFrom);
					resultIntent.putExtra("bankName",topArrayList.get(position).getFldExp());
					resultIntent.putExtra("bankNo",topArrayList.get(position).getFldVal());
					setResult(1002, resultIntent);
					topArrayList.clear();
					finish();
				}else{
					resultIntent.putExtra("searchPageFrom",searchPageFrom);
					resultIntent.putExtra("bankName",branchArrayList.get(position).getSubBankInfo().getLbnkNm());
					resultIntent.putExtra("bankNo",branchArrayList.get(position).getSubBankInfo().getLbnkNo());
					setResult(1002, resultIntent);
					branchArrayList.clear();
					finish();
				}


			}
		});

	}

	private String searchPageFrom;
	@Override
	public void initData() {

		searchPageFrom = getIntent().getStringExtra("searchPageFrom");

        if (searchPageFrom.equals("1")){
            topBankNo = getIntent().getStringExtra("topBankNo");
            province = getIntent().getStringExtra("province");
            city = getIntent().getStringExtra("city");
        }

        getBankInfo(searchPageFrom);

	}


	private void getBankInfo(String searchPageFrom) {

        if (searchPageFrom.equals("0")){

            HttpRequest.getTopBankList(nameMsg,0, new OnHttpResponseListener() {

                @Override
                public void onHttpResponse(int requestCode, String resultJson, Exception e) {

					Gson gson = new Gson();
					Type type = new TypeToken<Message<BankInfo>>() {}.getType();
					Message<BankInfo> message = gson.fromJson(resultJson,type);

					topArrayList = (ArrayList) message.getRspData();

                    if("000000".equals(message.getRspCd())){

                        if(baseInfoAdapter == null){
                            baseInfoAdapter = new BaseInfoAdapter(topArrayList,context);
                            listView.setAdapter(baseInfoAdapter);
                        }
                        baseInfoAdapter.notifyDataSetChanged();

                    }else{
                        showShortToast("获取数据失败");
                    }

                }
            });
        }else{

            /*topBankNo = intent.getStringExtra("topBankNo");
            province = intent.getStringExtra("province");
            city = intent.getStringExtra("city");*/

            HttpRequest.getBranchBankList(nameMsg,topBankNo,province,city,0, new OnHttpResponseListener() {

                @Override
                public void onHttpResponse(int requestCode, String resultJson, Exception e) {

					Gson gson = new Gson();
					Type type = new TypeToken<Message<SubBankInfoSet>>() {}.getType();
					Message<SubBankInfoSet> message = gson.fromJson(resultJson,type);

					branchArrayList = (ArrayList) message.getRspData();

                    if("000000".equals(message.getRspCd())){

                    	if(subBankInfoAdapter == null){
							subBankInfoAdapter = new SubBankInfoAdapter(branchArrayList,context);
							listView.setAdapter(subBankInfoAdapter);
						}
						subBankInfoAdapter.notifyDataSetChanged();
                    }else{
                        showShortToast("获取数据失败");
                    }

                }
            });

        }

	}



	@Override
	public void initEvent() {//必须调用
        findViewById(R.id.bank_search_btn).setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
			case R.id.bank_search_btn:

				nameMsg = name.getText().toString();
				if (nameMsg.equals("")) {
					Toast.makeText(this, "输入值为空", Toast.LENGTH_SHORT).show();
					return;
				}
				getBankInfo(searchPageFrom);

				break;

			default:
				break;
		}
	}

	@Override
	public void onReturnClick(View v) {
		finish();
	}


}