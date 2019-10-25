package com.haoxt.agent.activity.home.merchantquery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.haoxt.agent.R;
import com.haoxt.agent.adapter.MerchantQueryListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import tft.mpos.library.base.BaseActivity;

/**
 * @Description: 商户查询
 * @Author: liuxx
 * @CreateDate: 2019/10/23 18:36
 */
public class MerchantQueryActivity extends BaseActivity implements View.OnClickListener, MerchantQueryListAdapter.OnItemClickListener {

    private EditText bankSearcName;
    private Button btnMerchantSearch;
    private RecyclerView rlvMerchantList;
    private ArrayList<HashMap<String, Object>> list;
    private MerchantQueryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_query);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

        bankSearcName = findViewById(R.id.bank_searc_name);
        btnMerchantSearch = findViewById(R.id.btn_merchant_search);
        rlvMerchantList = findViewById(R.id.rlv_merchant_list);

    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("NAME", "贵州茅台股份有限公司");
            hashMap.put("TIME", "2019-09-09  15:23:56");
            hashMap.put("MERNO", "WY098765" + i);
            hashMap.put("FIRSTMERNO", "WY123456" + i);
            list.add(hashMap);
        }
        adapter = new MerchantQueryListAdapter(this,list);
        rlvMerchantList.setAdapter(adapter);
        // 这里必须设置 setLayoutManager布局管理器，否则rv不能显示数据
        rlvMerchantList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void initEvent() {
        btnMerchantSearch.setOnClickListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_merchant_search:
                showShortToast("搜索");
                break;
        }
    }


    @Override
    public void onItemClick(View v, HashMap<String, Object> hashMap) {
        startActivity(new Intent(this,MerchantDetailActivity.class));
    }
}
