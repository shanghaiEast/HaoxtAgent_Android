package com.haoxt.agent.activity.home.terminalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoxt.agent.R;

import tft.mpos.library.base.BaseActivity;

/**
 * @Description: 终端管理首页
 * @Author: liuxx
 * @CreateDate: 2019/10/25 14:29
 */
public class TerminalHomeActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout llTerminalQuery;
    private RelativeLayout llTerminalUnbound;
    private LinearLayout llTerminalAllocation;
    private LinearLayout llTerminalCallback;
    private LinearLayout llTerminalMove;
    private TextView tvDetail;
    private TextView tvTerTotal;
    private TextView tvTerActivated;
    private TextView tvTerNotactive;
    private TextView tvTerUnbound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ter_home);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

        llTerminalQuery = findViewById(R.id.ll_terminal_query);
        llTerminalUnbound = findViewById(R.id.ll_terminal_unbound);
        llTerminalAllocation = findViewById(R.id.ll_terminal_allocation);
        llTerminalCallback = findViewById(R.id.ll_terminal_callback);
        llTerminalMove = findViewById(R.id.ll_terminal_move);
        tvDetail = findViewById(R.id.tv_detail);
        tvTerTotal = findViewById(R.id.tv_ter_total);
        tvTerActivated = findViewById(R.id.tv_ter_activated);
        tvTerNotactive = findViewById(R.id.tv_ter_notactive);
        tvTerUnbound = findViewById(R.id.tv_ter_unbound);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        llTerminalQuery.setOnClickListener(this);
        llTerminalUnbound.setOnClickListener(this);
        llTerminalAllocation.setOnClickListener(this);
        llTerminalCallback.setOnClickListener(this);
        llTerminalMove.setOnClickListener(this);
        tvDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_terminal_query://机具查询
                startActivity(TerminalQueryActivity.createIntent(this));
                break;
            case R.id.ll_terminal_unbound://机具解绑
                startActivity(new Intent(this,TerminalUnboundActivity.class));
                break;
            case R.id.ll_terminal_allocation://机具划拨
                startActivity(TerminalAllocationActivity.createIntent(this));
                break;
            case R.id.ll_terminal_callback://机具回调
                showShortToast("机具回调");
                break;
            case R.id.ll_terminal_move://机具迁移
                showShortToast("机具迁移");
                break;
            case R.id.tv_detail://查看库存详情
                showShortToast("查看库存详情");
                break;
        }
    }
}
