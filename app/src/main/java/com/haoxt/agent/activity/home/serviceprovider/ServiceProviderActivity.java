package com.haoxt.agent.activity.home.serviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.haoxt.agent.R;

import tft.mpos.library.base.BaseActivity;

/**
 * Created by liuxx on 2019/10/22
 */

public class ServiceProviderActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rlInvitePartners;
    private RelativeLayout rlPartnerQuery;
    private RelativeLayout rlInviteSalesman;
    private RelativeLayout rlSalesmanQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceprovider);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

        rlInvitePartners = findViewById(R.id.rl_invite_partners);
        rlPartnerQuery = findViewById(R.id.rl_partner_query);
        rlInviteSalesman = findViewById(R.id.rl_invite_salesman);
        rlSalesmanQuery = findViewById(R.id.rl_salesman_query);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        rlInvitePartners.setOnClickListener(this);
        rlPartnerQuery.setOnClickListener(this);
        rlInviteSalesman.setOnClickListener(this);
        rlSalesmanQuery.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_invite_partners://邀请合作伙伴
                startActivity(new Intent(this, InvitePartnersActivity.class));
                break;
            case R.id.rl_partner_query://合作伙伴查询
                startActivity(new Intent(this,PartnerQueryActivity.class));
                break;
            case R.id.rl_invite_salesman://邀请业务员
                showShortToast("邀请业务员");
                break;
            case R.id.rl_salesman_query://业务员查询
                showShortToast("业务员查询");
                break;
        }
    }
}
