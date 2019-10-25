package com.haoxt.agent.activity.home.merchantquery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haoxt.agent.R;

import tft.mpos.library.base.BaseFragment;

/**
 * @Description: 商户结算信息页面
 * @Author: liuxx
 * @CreateDate: 2019/10/24 16:20
 */
public class SettlementInfoFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_settlementinfo);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
