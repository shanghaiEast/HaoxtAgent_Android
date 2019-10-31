package com.haoxt.agent.activity.home.terminalmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.haoxt.agent.R;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.util.StringUtil;

/**
 * @Description: 终端详情
 * @Author: liuxx
 * @CreateDate: 2019/10/26 14:54
 */
public class TerminalDetailActivity extends BaseActivity {

    private String terminalSN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_detail);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        terminalSN = StringUtil.get(getIntent().getExtras().get("TER_SN"));

    }

    @Override
    public void initEvent() {

    }

    /**
     * 启动这个Activity的Intent
     *
     * @param
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, TerminalDetailActivity.class);
    }
}
