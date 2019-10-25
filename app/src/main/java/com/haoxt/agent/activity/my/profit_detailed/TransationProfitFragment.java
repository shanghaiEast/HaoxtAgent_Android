package com.haoxt.agent.activity.my.profit_detailed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.activity.home.transaction.SelectTimePopup;
import com.haoxt.agent.activity.home.transaction.TransactionDetailActivity;
import com.haoxt.agent.adapter.TransactionListAdapter;
import com.haoxt.agent.adapter.TransactionProfitListAdapter;
import com.haoxt.agent.entity.ListProfitTransaction;
import com.haoxt.agent.entity.ListTransaction;
import com.haoxt.agent.util.DateUtil;
import com.haoxt.agent.widget.MyDialog;

import java.util.ArrayList;

import tft.mpos.library.base.BaseFragment;

public class TransationProfitFragment extends BaseFragment  implements View.OnClickListener ,TransactionProfitListAdapter.OnItemClickListener{

    private String name;
    private RecyclerView mProfitList;
    private ArrayList<ListProfitTransaction> mList;
    private MyDialog mConditionPopup;


    /**创建一个Fragment实例
     * @return
     */
    public static TransationProfitFragment createInstance() {
        return new TransationProfitFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_profit_transation);

//        Bundle bundle = getArguments();
//        name = bundle.getString("name");
//        if (name == null) {
//            name = "参数非法";
//        }

        initView();
        initData();
        initEvent();

        return view;
    }

    TextView tvContent,mNoDataTv;
    Button tocash;
    @Override
    public void initView() {
        tvContent = findView(R.id.txt_content);
        mProfitList = view.findViewById(R.id.rv_profit_list);
        mNoDataTv = view.findViewById(R.id.no_data_tv);
        tocash = view.findViewById(R.id.btn_transation_profit_to_cash);
        mProfitList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        tvContent.setText(name);
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ListProfitTransaction listTransaction = new ListProfitTransaction();
            listTransaction.setType("收益分润");
            listTransaction.setAmount(String.valueOf(12+i));
            listTransaction.setTime(DateUtil.getTime());

            mList.add(listTransaction);
        }


        if (mList == null || mList.size() < 1) {
            mProfitList.setVisibility(View.GONE);
            mNoDataTv.setVisibility(View.VISIBLE);
        } else {
            mNoDataTv.setVisibility(View.GONE);
            mProfitList.setVisibility(View.VISIBLE);
            TransactionProfitListAdapter adapter = new TransactionProfitListAdapter(context, mList);
            mProfitList.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
        }

    }

    @Override
    public void initEvent() {
        tocash.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_transation_profit_to_cash:
                toActivity(CashWithdrawalActivity.createIntent(context));

                break;

            default:
                break;
        }
    }


    @Override
    public void onItemClick(int position) {
        ListProfitTransaction transaction = mList.get(position);

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


}
