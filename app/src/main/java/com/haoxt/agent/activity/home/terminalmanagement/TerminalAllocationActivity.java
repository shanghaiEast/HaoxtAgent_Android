package com.haoxt.agent.activity.home.terminalmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.adapter.TerminalAllocationAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tft.mpos.library.base.BaseActivity;

/**
 * @Description: 机具划拨
 * @Author: liuxx
 * @CreateDate: 2019/10/26 17:00
 */
public class TerminalAllocationActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTerAllocationDetail;
    private TextView tvSelectSalesman;
    private RecyclerView rlvTerminalList;
    private TextView tvAddTerminal;
    private Button btnTerminalAllocationCommit;
    private TerminalAllocationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_allocation);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

        tvTerAllocationDetail = findViewById(R.id.tv_ter_allocation_detail);
        tvSelectSalesman = findViewById(R.id.tv_select_salesman);
        rlvTerminalList = findViewById(R.id.rlv_terminal_list);
        tvAddTerminal = findViewById(R.id.tv_add_terminal);
        btnTerminalAllocationCommit = findViewById(R.id.btn_terminal_allocation_commit);

    }

    @Override
    public void initData() {
        List<String> list = new ArrayList<>();
        list.add("1");
        mAdapter = new TerminalAllocationAdapter(this);
        rlvTerminalList.setAdapter(mAdapter);
        rlvTerminalList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initEvent() {
        tvTerAllocationDetail.setOnClickListener(this);
        tvSelectSalesman.setOnClickListener(this);
        tvAddTerminal.setOnClickListener(this);
        btnTerminalAllocationCommit.setOnClickListener(this);
    }

    /**
     * 启动这个Activity的Intent
     *
     * @param
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, TerminalAllocationActivity.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ter_allocation_detail://划拨详情
                break;
            case R.id.tv_select_salesman://选择划拨业务员
                break;
            case R.id.tv_add_terminal://增加批次
                mAdapter.addData(1);
                break;
            case R.id.btn_terminal_allocation_commit://确定按钮
                ArrayList<HashMap<String,Object>> list =mAdapter.getListData();
                System.out.println(list.toString());
                break;
        }
    }
}
