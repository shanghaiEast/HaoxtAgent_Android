package com.haoxt.agent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.haoxt.agent.activity.home.merchantquery.EssentialInfoFragment;
import com.haoxt.agent.activity.home.merchantquery.QuotaInfoFragment;
import com.haoxt.agent.activity.home.merchantquery.RateInfoFragment;
import com.haoxt.agent.activity.home.merchantquery.SettlementInfoFragment;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class MerFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"基本信息", "结算信息", "费率","额度"};

    public MerFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new SettlementInfoFragment();
        } else if (position == 2) {
            return new RateInfoFragment();
        }else if (position==3){
            return new QuotaInfoFragment();
        }
        return new EssentialInfoFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
