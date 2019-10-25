package com.haoxt.agent.activity.home.serviceprovider;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;

import com.haoxt.agent.R;
import com.haoxt.agent.adapter.PartnerQueryListAdapter;
import com.haoxt.agent.entity.PartnerQueryListBean;

import java.util.ArrayList;

import tft.mpos.library.base.BaseActivity;

/**
 * @Description: 合作伙伴查询
 * @Author: liuxx
 * @CreateDate: 2019/10/22 17:27
 */

public class PartnerQueryActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private TabHost tabHost;
    LocalActivityManager lam;
    TabHost.TabSpec tabPerfect, tabImperfect;
    private Button btnPartnerSearch;
    private LinearLayout llPerfect;
    private ListView lsPerfect;
    private LinearLayout llImperfect;
    private ListView lsImperfect;
    private ArrayList<PartnerQueryListBean> listPerfect;
    private ArrayList<PartnerQueryListBean> listImperfect;
    private PartnerQueryListAdapter perfectAdapter;
    private PartnerQueryListAdapter imperfectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_query);

        initTab(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initTab(Bundle savedInstanceState) {
        tabHost = findViewById(android.R.id.tabhost);
        lam = new LocalActivityManager(this, false);
        lam.dispatchCreate(savedInstanceState);


        tabHost.setup(lam);

        tabPerfect = tabHost.newTabSpec("one");
        tabPerfect.setIndicator("已完善");
        tabPerfect.setContent(R.id.ll_perfect);

        tabImperfect = tabHost.newTabSpec("two");
        tabImperfect.setIndicator("待完善");
        tabImperfect.setContent(R.id.ll_imperfect);

//        tabHost.addTab(tabPerfect);
//        tabHost.addTab(tabImperfect);
    }

    @Override
    protected void onPause() {
        lam.dispatchPause(isFinishing());
        super.onPause();
    }

    @Override
    protected void onResume() {
        lam.dispatchResume();
        super.onResume();
    }


    @Override
    public void initView() {

        btnPartnerSearch = findViewById(R.id.btn_partner_search);
        llPerfect = findViewById(R.id.ll_perfect);
        lsPerfect = findViewById(R.id.ls_perfect);
        llImperfect = findViewById(R.id.ll_imperfect);
        lsImperfect = findViewById(R.id.ls_imperfect);

    }

    @Override
    public void initData() {
        listPerfect = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PartnerQueryListBean bean = new PartnerQueryListBean();
            bean.setCorporateName("贵州茅台股份有限公司");
            bean.setLegalPersonName("已完善");
            bean.setPhoneNum("18171766352");
            bean.setFirstPartner("三鹿奶粉");
            bean.setIdCard("421124199909190000");
            bean.setOpeningBank("招商银行");
            bean.setBranch("上海市浦东新区川沙支行");
            bean.setBankcardNo("65411********564");
            if (i%2==0)
            bean.setSecondaryPartners("sanlu");
            listPerfect.add(bean);
        }
        perfectAdapter = new PartnerQueryListAdapter(this, listPerfect);
        lsPerfect.setAdapter(perfectAdapter);
        tabPerfect.setIndicator("已完善("+listPerfect.size()+")");
        tabHost.addTab(tabPerfect);

        listImperfect = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PartnerQueryListBean bean = new PartnerQueryListBean();
            bean.setCorporateName("贵州茅台股份有限公司");
            bean.setLegalPersonName("未完善");
            bean.setPhoneNum("18171766352");
            bean.setFirstPartner("三鹿奶粉");
            bean.setIdCard("422125199909191111");
            bean.setOpeningBank("中国银行");
            bean.setBranch("上海市浦东新区张江支行");
            bean.setBankcardNo("65432********123");
            if (i%2==0)
            bean.setSecondaryPartners("sanlu");
            listImperfect.add(bean);
        }
        imperfectAdapter = new PartnerQueryListAdapter(this, listImperfect);
        lsImperfect.setAdapter(imperfectAdapter);
        tabImperfect.setIndicator("待完善("+listImperfect.size()+")");
        tabHost.addTab(tabImperfect);

    }

    @Override
    public void initEvent() {
        btnPartnerSearch.setOnClickListener(this);
        lsPerfect.setOnItemClickListener(this);
        lsImperfect.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_partner_search:
                showShortToast("搜索");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PartnerQueryListBean bean = (PartnerQueryListBean) adapterView.getItemAtPosition(i);
        startActivity(new Intent(this,PartnerDetailActivity.class).putExtra("data", bean));
    }
}
