package com.haoxt.agent.activity.home.serviceprovider;

import android.os.Bundle;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.entity.PartnerQueryListBean;

import tft.mpos.library.base.BaseActivity;

/**
 * @Description: 合作伙伴详情
 * @Author: liuxx
 * @CreateDate: 2019/10/23 15:37
 */
public class PartnerDetailActivity extends BaseActivity {

    private PartnerQueryListBean bean;
    private TextView tvPartnername;
    private TextView tvPhonenum;
    private TextView tvIdcard;
    private TextView tvOpenbank;
    private TextView tvBranch;
    private TextView tvBankcardno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_detail);
        initView();
        initData();
    }

    @Override
    public void initView() {

        tvPartnername = findViewById(R.id.tv_partnername);
        tvPhonenum = findViewById(R.id.tv_phonenum);
        tvIdcard = findViewById(R.id.tv_idcard);
        tvOpenbank = findViewById(R.id.tv_openbank);
        tvBranch = findViewById(R.id.tv_branch);
        tvBankcardno = findViewById(R.id.tv_bankcardno);

    }

    @Override
    public void initData() {
        bean = (PartnerQueryListBean) getIntent().getSerializableExtra("data");
        tvPartnername.setText(bean.getLegalPersonName());
        tvPhonenum.setText(bean.getPhoneNum());
        tvIdcard.setText(bean.getIdCard());
        tvOpenbank.setText(bean.getOpeningBank());
        tvBranch.setText(bean.getBranch());
        tvBankcardno.setText(bean.getBankcardNo());
    }

    @Override
    public void initEvent() {

    }
}
