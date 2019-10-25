package com.haoxt.agent.activity.home.merchantquery;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.haoxt.agent.R;
import com.haoxt.agent.adapter.MerFragmentPagerAdapter;

import tft.mpos.library.base.BaseActivity;

/**
 * @Description: 商户详情页面
 * @Author: liuxx
 * @CreateDate: 2019/10/24 15:39
 */
public class MerchantDetailActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MerFragmentPagerAdapter merFragmentPagerAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_detail);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= findViewById(R.id.vp_merdetail);
        merFragmentPagerAdapter = new MerFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(merFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = findViewById(R.id.tl_merdetail);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
