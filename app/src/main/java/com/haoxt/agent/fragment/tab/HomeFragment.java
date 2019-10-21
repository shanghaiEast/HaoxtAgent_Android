package com.haoxt.agent.fragment.tab;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.adapter.SaleListAdapter;
import com.haoxt.agent.application.AppApplication;
import com.haoxt.agent.entity.ListSaleDetail;
import com.haoxt.agent.util.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tft.mpos.library.base.BaseFragment;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.ui.AlertDialog;
import tft.mpos.library.util.StringUtil;

public class HomeFragment extends BaseFragment implements View.OnClickListener,AlertDialog.OnDialogButtonClickListener {


    private RecyclerView mSaleList;
    private ArrayList<ListSaleDetail> mList;
    private TextView mNoDataLy,username,phone;
    private TextView mOrdinaryTv;
    private TextView mSuperTv;
    private TextView mQrTv;
    private TextView mQuickPassTv;
    private HashMap<String, Object> pageData;

    //单例
    public static HomeFragment newInstance() {
        return HomeFragment.HomeFragmentFactory.homeFragment;
    }

    @Override
    public void onDialogButtonClick(int requestCode, boolean isPositive) {
        if (isPositive == false) {
            return;
        }

//        toActivity(RealNameAuthenticationActivity.createIntent(context).putExtra("pageData",(Serializable)pageData));

    }

    private static final class HomeFragmentFactory {
        public static final HomeFragment homeFragment = new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
        //类相关初始化，必须使用>>>>>>>>>>>>>>>>

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;
    }

    @Override
    public void initView() {
        mOrdinaryTv = view.findViewById(R.id.ordinary_tv);
        username = view.findViewById(R.id.username_tv);
        phone = view.findViewById(R.id.phone_number_tv);
        mSuperTv = view.findViewById(R.id.super_tv);
        mQrTv = view.findViewById(R.id.qr_tv);
        mQuickPassTv = view.findViewById(R.id.quick_pass_tv);
        mSaleList = view.findViewById(R.id.sale_list);
        mNoDataLy = view.findViewById(R.id.no_data_ly);
        mSaleList.setLayoutManager(new LinearLayoutManager(getActivity()));

        username.setText(pageData.get("USR_OPR_NM")==null?"":pageData.get("USR_OPR_NM").toString());
        phone.setText(pageData.get("USR_LOGIN_MBL")==null?"":pageData.get("USR_LOGIN_MBL").toString());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            pageData = ((MainTabActivity) context).getPageData();
//            if(pageData.get("USR_OPR_NM")!=null){
//                username.setText(pageData.get("USR_OPR_NM").toString());
//            }
//            if(pageData.get("USR_LOGIN_MBL")!=null){
//                phone.setText(pageData.get("USR_LOGIN_MBL").toString());
//            }
//

    }

    @Override
    public void initData() {

        getUserStatus("");

        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList.clear();
        }
        for (int i = 0; i < 10; i++) {
            ListSaleDetail listSaleDetail = new ListSaleDetail();
            listSaleDetail.id = i;
            listSaleDetail.setSalemoney("支付宝尾号 (2234)交易 236.00元");
            listSaleDetail.setSaletime("交易日期：2019/12/13 15:23:11");
            if (i == 0) {
                listSaleDetail.setTypename("最新");
            } else {
                listSaleDetail.setTypename("今天");
            }
            mList.add(listSaleDetail);
        }
        if (mList == null && mList.size() < 1) {
            mNoDataLy.setVisibility(View.VISIBLE);
            mSaleList.setVisibility(View.INVISIBLE);
        } else {
            mNoDataLy.setVisibility(View.INVISIBLE);
            mSaleList.setVisibility(View.VISIBLE);
            mSaleList.setAdapter(new SaleListAdapter(context, mList));
        }
    }

    private void getUserStatus(String type) {






//        HttpRequest.getUserInfo(0, new OnHttpResponseListener() {
//
//            @Override
//            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
//
//                if(!StringUtil.isEmpty(resultJson)){
//                    Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
//                    Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");
//
//                    if(dataMap!=null&&"000000".equals(dataMap.get("rspCd").toString())){
//
//                        AppApplication.getInstance().setMerchantId(userData.get("AGT_MERC_ID")==null?"":userData.get("AGT_MERC_ID").toString());
//                        AppApplication.getInstance().setSnNo(userData.get("SN_NO")==null?"":userData.get("SN_NO").toString());
//                        AppApplication.getInstance().setUserCreditCardStatus(userData.get("CCARD_VALID_STS")==null?"":userData.get("CCARD_VALID_STS").toString());
//
//
//
//                    }else{
//                        showShortToast("获取商户信息失败");
//                    }
//                }
//            }
//        });

        HttpRequest.chkRealSts(0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                if(!StringUtil.isEmpty(resultJson)){
                    Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
                    Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");

                    if(dataMap!=null&&"000000".equals(dataMap.get("rspCd").toString())){

                        AppApplication.getInstance().setRealNameStatus(userData.get("USR_REAL_STS").toString());

                        //        if(AppApplication.getInstance().getRealNameStatus().equals("0")){
                        //            new AlertDialog(context, "提示", "当前账号未实名认证，请实名认证", true, 0, new AlertDialog.OnDialogButtonClickListener(){
                        //                @Override
                        //                public void onDialogButtonClick(int requestCode, boolean isPositive) {
                        //
                        //                    if (isPositive == true){
                        //                        toActivity(RealNameAuthenticationActivity.createIntent(context));
                        //                    }else{
                        //                        Log.d("状态--------->", "--"+isPositive+"===="+requestCode);
                        //                    }
                        //                }
                        //            }).show();
                        //            return;
                        //
                        //        }else if(AppApplication.getInstance().getUserTermStatus().equals("0")){
                        //            new AlertDialog(context, "提示", "当前账号未绑定机具，请绑定机具", true, 0, new AlertDialog.OnDialogButtonClickListener(){
                        //                @Override
                        //                public void onDialogButtonClick(int requestCode, boolean isPositive) {
                        //
                        //                    if (isPositive == true){
                        //                        toActivity(DeviceBindActivity.createIntent(context));
                        //                    }else{
                        //                        Log.d("状态--------->", "--"+isPositive+"===="+requestCode);
                        //                    }
                        //
                        //                }
                        //            }).show();
                        //            return;
                        //
                        //        }else if(AppApplication.getInstance().getUserCreditCardStatus().equals("0")){
                        //            new AlertDialog(context, "提示", "当前账号未信用卡认证，请先信用卡认证", true, 0, new AlertDialog.OnDialogButtonClickListener(){
                        //                @Override
                        //                public void onDialogButtonClick(int requestCode, boolean isPositive) {
                        //
                        //                    if (isPositive == true){
                        //                        toActivity(CreditcardAuthenticateActivity.createIntent(context,0));
                        //                    }else{
                        //                        Log.d("状态--------->", "--"+isPositive+"===="+requestCode);
                        //                    }
                        //                }
                        //            }).show();
                        //            return;
                        //        }else{
                        //            if("".equals(type)){
                        //
                        //            }else{
                        //                Intent intent = new Intent(context, StartTransationActivity.class);
                        //                intent.putExtra("paytype", type);
                        //                toActivity(intent);
                        //            }
                        //        }

                        if("".equals(type)){

                        }else{
//                            Intent intent = new Intent(context, StartTransationActivity.class);
//                            intent.putExtra("paytype", type);
//                            toActivity(intent);
                        }

                    }else{
                        showShortToast("获取商户信息失败");
                    }
                }
            }
        });



    }

    @Override
    public void initEvent() {
        mOrdinaryTv.setOnClickListener(this);
        mSuperTv.setOnClickListener(this);
        mQrTv.setOnClickListener(this);
        mQuickPassTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ordinary_tv:
                getUserStatus("1");


                break;

            case R.id.super_tv:
                getUserStatus("2");

                break;

            case R.id.qr_tv:
                getUserStatus("3");
                break;

            case R.id.quick_pass_tv:
                getUserStatus("4");
                break;
        }

    }


}
