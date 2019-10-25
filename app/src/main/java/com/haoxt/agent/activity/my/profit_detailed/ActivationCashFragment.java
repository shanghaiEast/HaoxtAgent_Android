package com.haoxt.agent.activity.my.profit_detailed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxt.agent.R;

import tft.mpos.library.base.BaseFragment;

public class ActivationCashFragment extends BaseFragment {

    private String name;


    /**创建一个Fragment实例
     * @return
     */
    public static ActivationCashFragment createInstance() {
        return new ActivationCashFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_msg_content);

//        Bundle bundle = getArguments();
//        name = bundle.getString("name");
//        if (name == null) {
//            name = "参数非法";
//        }

        initView();
        initData();
        initEvent();

        return view;
    }

    TextView tvContent;
    @Override
    public void initView() {
        tvContent = findView(R.id.txt_content);
//        tvContent.setText(name);
    }

    @Override
    public void initData() {



    }

    @Override
    public void initEvent() {

    }
}
