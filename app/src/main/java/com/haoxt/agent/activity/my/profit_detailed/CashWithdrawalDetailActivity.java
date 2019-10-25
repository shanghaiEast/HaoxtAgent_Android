package com.haoxt.agent.activity.my.profit_detailed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.common.PayType;
import com.haoxt.agent.entity.ListTransaction;
import com.haoxt.agent.entity.TransactionDetail;

import java.util.ArrayList;

import tft.mpos.library.base.BaseActivity;

/**
 * 提现明细详情
 */
public class CashWithdrawalDetailActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<TransactionDetail> mList;


    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, CashWithdrawalDetailActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdrawal_detail);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    private TextView profit,status,date,number,fee,time,des;
    @Override
    public void initView() {
        profit =  findViewById(R.id.tx_cash_withdrawal_detail_transation_profit);
        status = findViewById(R.id.tx_cash_withdrawal_detail_status);
        date = findViewById(R.id.tx_cash_withdrawal_detail_date);
        number = findViewById(R.id.tx_cash_withdrawal_detail_number);
        fee = findViewById(R.id.tx_cash_withdrawal_detail_fee);
        time = findViewById(R.id.tx_cash_withdrawal_detail_examineTime);
        des = findViewById(R.id.tx_cash_withdrawal_detail_examineDes);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initData() {


    }


    @Override
    public void initEvent() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.tv_order_detail_purchase://前往签购单
////                toActivity(ReviseLoginPwdActivity.createIntent(context));
//                break;


            default:
                break;
        }
    }


}
