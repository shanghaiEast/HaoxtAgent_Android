package com.haoxt.agent.activity.home.achievement_manage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.haoxt.agent.R;
import com.haoxt.agent.model.Achievement;
import com.haoxt.agent.util.DateUtil;

import java.util.ArrayList;

import tft.mpos.library.base.BaseFragment;

/**
 * 业绩查询按日查询
 */
public class AchievementSearchFragment extends BaseFragment implements AchievementSearchListAdapter.OnItemClickListener{

    private LinearLayout mTimeScreenLy;
    private TextView mConditionScreenTv;
    private ArrayList<Achievement> mList;

//    //单例
//    public static EmbroideryContentFragment newInstance() {
//        return EmbroideryContentFragment.EmbroideryContentFragmentFactory.transactionListFragment;
//    }

    @Override
    public void onItemClick(int position) {
//        startActivity(TransactionDetailActivity.createIntent(context).putExtra("transaction",transaction));
    }

//    private static final class EmbroideryContentFragmentFactory {
//        public static final EmbroideryContentFragment transactionListFragment = new EmbroideryContentFragment();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_achievement_search_list);
        //类相关初始化，必须使用>>>>>>>>>>>>>>>>

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        return view;
    }

    private TextView mNoDataTv,number,passingRate;
    private RecyclerView mSaleList;
    @Override
    public void initView() {
        number = view.findViewById(R.id.achievement_search_header_terminal_number);
        passingRate = view.findViewById(R.id.achievement_search_header_terminal_open);

        mNoDataTv = view.findViewById(R.id.achievement_search_no_data_tv);
        mSaleList = view.findViewById(R.id.achievement_search_list);
        mSaleList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initData() {

        mList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Achievement achievement = new Achievement();
            achievement.setTransactionDate(DateUtil.getDate());
            achievement.setTotalTransaction("100"+i);
            achievement.setQuantityTransaction("200"+i);
            achievement.setNewAchievementTerminal("234"+i);
            achievement.setOnlineMerchant("679"+i);
            achievement.setAverageAmount("100021");
            achievement.setNewActivation("123123");
            mList.add(achievement);
        }

        if (mList == null || mList.size() < 1) {
            mSaleList.setVisibility(View.GONE);
            mNoDataTv.setVisibility(View.VISIBLE);
        } else {
            mNoDataTv.setVisibility(View.GONE);
            mSaleList.setVisibility(View.VISIBLE);
            AchievementSearchListAdapter adapter = new AchievementSearchListAdapter(context, mList);
            mSaleList.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
        }

    }

    @Override
    public void initEvent() {

    }
}
