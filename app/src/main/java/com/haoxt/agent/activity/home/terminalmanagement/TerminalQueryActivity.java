package com.haoxt.agent.activity.home.terminalmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.adapter.TerminalQueryListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.util.StringUtil;

/**
 * @Description: 终端查询
 * @Author: liuxx
 * @CreateDate: 2019/10/26 11:10
 */
public class TerminalQueryActivity extends BaseActivity implements TerminalQueryListAdapter.OnItemClickListener, View.OnClickListener {

    private RecyclerView rlvTerminalList;
    private TextView tvTerQueryScreen;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
    private TerminalQueryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_query);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        tvTerQueryScreen = findViewById(R.id.tv_ter_query_screen);
        rlvTerminalList = findViewById(R.id.rlv_terminal_list);
    }

    @Override
    public void initData() {

        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("TER_SN","GY201908241142000131111111111"+i);
            hashMap.put("TER_STATUS",i%2==0?0:1);
            list.add(hashMap);
        }

        adapter = new TerminalQueryListAdapter(this,list);
        rlvTerminalList.setAdapter(adapter);
        rlvTerminalList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initEvent() {
        adapter.setOnItemClickListener(this);
        tvTerQueryScreen.setOnClickListener(this);
    }

    /**
     * 启动这个Activity的Intent
     *
     * @param
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, TerminalQueryActivity.class);
    }

    @Override
    public void onItemClick(View v, HashMap<String, Object> hashMap) {
        startActivity(TerminalDetailActivity.createIntent(this).putExtra("TER_SN", StringUtil.get(hashMap.get("TER_SN"))));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_ter_query_screen://筛选
                showShortToast("筛选");
                break;
        }
    }
}
