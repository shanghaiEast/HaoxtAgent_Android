package com.haoxt.agent.activity.home.transaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haoxt.agent.R;
import com.haoxt.agent.adapter.TransactionListAdapter;
import com.haoxt.agent.entity.ListTransaction;
import com.haoxt.agent.model.Message;
import com.haoxt.agent.util.HttpRequest;
import com.haoxt.agent.widget.MyDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.base.BaseFragment;
import tft.mpos.library.interfaces.OnHttpResponseListener;

/**
 * Description: <TransactionProvider><br>
 * Author:      chucai.he<br>
 * Date:        2018/12/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TransactionActivity extends BaseActivity implements View.OnClickListener, TransactionListAdapter.OnItemClickListener, SelectTimePopup.OnConfirmTimeListener {

    private LinearLayout mTimeScreenLy;
    private TextView mConditionScreenTv;
    private RecyclerView mSaleList;
    private ArrayList<ListTransaction> mList;
    private TextView mNoDataTv, mStartTv, mEndTv;
    private MyDialog mConditionPopup;
    private SelectTimePopup mTimePopup;


    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, TransactionActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_transaction_list);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }

    @Override
    public void initView() {
        mTimeScreenLy = view.findViewById(R.id.time_screen_ly);
        mConditionScreenTv = view.findViewById(R.id.condition_screen_tv);
        mStartTv = view.findViewById(R.id.start_tv);
        mEndTv = view.findViewById(R.id.end_tv);
        mNoDataTv = view.findViewById(R.id.no_data_tv);
        mSaleList = view.findViewById(R.id.sale_list);
        mSaleList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {
        getListTransaction();
        if (mList == null || mList.size() < 1) {
            mSaleList.setVisibility(View.GONE);
            mNoDataTv.setVisibility(View.VISIBLE);
        } else {
            mNoDataTv.setVisibility(View.GONE);
            mSaleList.setVisibility(View.VISIBLE);
            TransactionListAdapter adapter = new TransactionListAdapter(context, mList);
            mSaleList.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
        }
    }

    private void getListTransaction() {

        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList.clear();
        }

        HttpRequest.transationList("1","10","","","","","","",0,new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Gson gson = new Gson();
                Type type = new TypeToken<Message<ListTransaction>>() {}.getType();
                Message<ListTransaction> message = gson.fromJson(resultJson,type);
                mList = (ArrayList) message.getRspData();
                if("000000".equals(message.getRspCd())){
//                    showShortToast("上传成功");
                }else{
                    showShortToast("获取失败");
                }

            }
        });

//
//        for (int i = 0; i < 10; i++) {
//            ListTransaction listTransaction = new ListTransaction();
//            listTransaction.setPayMoney("467.00");
//            listTransaction.setPayTime("交易日期：2019/12/13 15:23:11");
//            if (i % 3 == 0) {
//                listTransaction.setPayNum("支付宝(2234)");
//                listTransaction.setPayType(PayType.ALI_PAY);
//            } else if (i % 3 == 1) {
//                listTransaction.setPayNum("微信(2234)");
//                listTransaction.setPayType(PayType.WE_CHAT);
//            } else if (i % 3 == 2) {
//                listTransaction.setPayNum("刷卡(2234)");
//                listTransaction.setPayType(PayType.ORDINARY);
//            }
//            if (i % 2 == 0) {
//                listTransaction.setPayResult(0);
//            } else {
//                listTransaction.setPayResult(1);
//            }
//            mList.add(listTransaction);
//        }
    }

    @Override
    public void initEvent() {
        mTimeScreenLy.setOnClickListener(this);
        mConditionScreenTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.time_screen_ly:
                showTimePopup();
                break;
            case R.id.condition_screen_tv:
                showConditionPopup();
                break;
        }
    }


    @Override
    public void onItemClick(int position) {
        ListTransaction transaction = mList.get(position);

        startActivity(TransactionDetailActivity.createIntent(context).putExtra("transaction",transaction));
    }

    private void showConditionPopup() {
        if (mConditionPopup == null)
            mConditionPopup = new MyDialog(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.popup_condition, null);
        TextView cancel_tv = layout.findViewById(R.id.cancel_tv);
        TextView confirm_tv = layout.findViewById(R.id.confirm_tv);
        confirm_tv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mConditionPopup.cancel();
            }
        });
        cancel_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mConditionPopup.cancel();
            }
        });
        mConditionPopup.show();
        mConditionPopup.setCancelable(false);
        mConditionPopup.setContentView(layout);
    }

    private void showTimePopup() {
        if (mTimePopup != null) {
            mTimePopup = null;
        }
        mTimePopup = new SelectTimePopup(getActivity());
        mTimePopup.setOnConfirmTimeListener(this);
        mTimePopup.show();
    }


    @Override
    public void onConfirmTime(String startTime, String endTime) {
        mStartTv.setText(startTime);
        mEndTv.setText(endTime);
    }
}
