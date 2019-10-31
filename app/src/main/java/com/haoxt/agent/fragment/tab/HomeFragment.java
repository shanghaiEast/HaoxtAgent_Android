package com.haoxt.agent.fragment.tab;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.activity.home.achievement_manage.AchievementManageActivity;
import com.haoxt.agent.activity.home.merchantquery.MerchantQueryActivity;
import com.haoxt.agent.activity.home.serviceprovider.ServiceProviderActivity;
import com.haoxt.agent.activity.home.terminalmanagement.TerminalHomeActivity;
import com.haoxt.agent.adapter.HomeGridAdepter;
import com.haoxt.agent.application.AppApplication;
import com.haoxt.agent.entity.HomeMenu;
import com.haoxt.agent.util.HttpRequest;
import com.haoxt.agent.widget.MyGridView;

import java.util.ArrayList;
import java.util.Map;

import tft.mpos.library.base.BaseFragment;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private MyGridView gvHomeMenu;
    private TextView tvProfitMore;
    private TextView tvTransactionMore;
    private TextView tvTerMore;
    private ArrayList<HomeMenu> menus = new ArrayList<>();

    private String[] menuItem = new String[]{"PARTNER", "MERQUERY", "TERMINAL", "TRANSACTION", "TERPURCHASE", "SETTLEMENT"};

    //单例
    public static HomeFragment newInstance() {
        return HomeFragment.HomeFragmentFactory.homeFragment;
    }

    private static final class HomeFragmentFactory {
        public static final HomeFragment homeFragment = new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
        //类相关初始化，必须使用>>>>>>>>>>>>>>>>

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;
    }

    @Override
    public void initView() {

        gvHomeMenu = findViewById(R.id.gv_home_menu);
        tvProfitMore = findViewById(R.id.tv_profit_more);
        tvTransactionMore = findViewById(R.id.tv_transaction_more);
        tvTerMore = findViewById(R.id.tv_ter_more);

    }

    @Override
    public void initData() {

        for (int i = 0; i < menuItem.length; i++) {
            HomeMenu hm = new HomeMenu();
            hm.setMenuAlias(menuItem[i]);
            hm.setOrder(StringUtil.get(i));
            menus.add(hm);
        }

        gvHomeMenu.setAdapter(new HomeGridAdepter(context, menus));

    }

    private void getUserStatus(String type) {

        HttpRequest.chkRealSts(0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                if (!StringUtil.isEmpty(resultJson)) {
                    Map<String, Object> dataMap = StringUtil.json2map(resultJson);
                    Map<String, Object> userData = (Map<String, Object>) dataMap.get("rspMap");

                    if (dataMap != null && "000000".equals(dataMap.get("rspCd").toString())) {

                        AppApplication.getInstance().setRealNameStatus(userData.get("USR_REAL_STS").toString());

                    } else {
                        showShortToast("获取商户信息失败");
                    }
                }
            }
        });


    }

    @Override
    public void initEvent() {
        gvHomeMenu.setOnItemClickListener(new ItemClickListener());
        tvProfitMore.setOnClickListener(this);
        tvTransactionMore.setOnClickListener(this);
        tvTerMore.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_profit_more://我的收益更多
//                showShortToast("我的收益");
                toActivity(AchievementManageActivity.createIntent(getActivity()));

                break;
            case R.id.tv_transaction_more://交易数据汇总更多
                showShortToast("交易数据汇总");
                break;
            case R.id.tv_ter_more://终端数据汇总更多
                showShortToast("终端数据汇总");
                break;
        }
    }

    class ItemClickListener implements AdapterView.OnItemClickListener {
        //"PARTNER", "MERQUERY", "TERMINAL", "TRANSACTION", "TERPURCHASE", "SETTLEMENT"
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            HomeMenu menu = (HomeMenu) adapterView.getItemAtPosition(i);
            switch (menu.getMenuAlias()) {
                case "PARTNER"://合作伙伴
                startActivity(new Intent(getActivity(),ServiceProviderActivity.class));
                    break;
                case "MERQUERY"://商户查询
                    startActivity(new Intent(getActivity(),MerchantQueryActivity.class));
                    break;
                case "TERMINAL"://终端查询
                    startActivity(new Intent(getActivity(), TerminalHomeActivity.class));
                    break;
                case "TRANSACTION"://交易管理
                    showShortToast("交易管理");
                    break;
                case "TERPURCHASE"://机具采购
                    showShortToast("机具采购");
                    break;
                case "SETTLEMENT"://秒结管理
                    showShortToast("秒结管理");
                    break;
            }
        }
    }

}
