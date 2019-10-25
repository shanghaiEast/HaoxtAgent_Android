package com.haoxt.agent.activity.my.profit_detailed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.haoxt.agent.R;
import com.haoxt.agent.activity.other.DemoListFragment;

import java.util.ArrayList;

import tft.mpos.library.base.BaseFragment;
import tft.mpos.library.base.BaseTabActivity;
import tft.mpos.library.interfaces.OnBottomDragListener;

/**
 * 收益明细
 */
public class ProfitDetailedAvtivity extends BaseTabActivity implements View.OnClickListener, OnBottomDragListener {


    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, ProfitDetailedAvtivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //		needReload = true;

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }

    @Override
    public void initView() {
        super.initView();

        tvBaseTabTitle.setTextColor(Color.WHITE);
        rlbaseTabHeader.setBackgroundColor(Color.parseColor("#ff6666"));
    }

    /**当需要自定义 tab bar layout时，要实现此方法
     */
    @Override
    public int getTopTabViewResId() {
        return R.layout.top_tab_view;
    }


    @Override
    public String getTitleName() {
        return "收益明细";
    }

    @Override
    public String getForwardName() {
        return "筛选";
    }

    @Override
    protected String[] getTabNames() {
        return new String[] {"交易分润", "激活返现"};
    }

    @Override
    protected Fragment getFragment(int position) {

        BaseFragment fragment = null;

        if(position ==0){
            fragment = TransationProfitFragment.createInstance();
        }else{
            fragment = ActivationCashFragment.createInstance();
        }

        return fragment;
    }


    @Override
    public void initEvent() {//必须在onCreate方法内调用
        super.initEvent();
        topTabView.setOnTabSelectedListener(this);//覆盖super.initEvent();内的相同代码


    }


    @Override
    public void onDragBottom(boolean rightToLeft) {
        //示例代码<<<<<<<<<<<<<<<<<<
        if (rightToLeft) {
            showShortToast("onTabSelected  position = " + "筛选");
//            toActivity(WebViewActivity.createIntent(context, "百度首页", "www.baidu.com"));
            return;
        }

        finish();
        //示例代码>>>>>>>>>>>>>>>>>>
    }

    @Override
    public void onTabSelected(TextView tvTab, int position, int id) {
        super.onTabSelected(tvTab, position, id);
        //示例代码<<<<<<<<<<<<<<<<<<
        showShortToast("onTabSelected  position = " + position);
        //示例代码>>>>>>>>>>>>>>>>>>
    }

}
