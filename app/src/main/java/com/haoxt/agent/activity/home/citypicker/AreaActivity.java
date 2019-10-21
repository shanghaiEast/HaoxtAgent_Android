package com.haoxt.agent.activity.home.citypicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haoxt.agent.R;
import com.haoxt.agent.model.CityInfoBean;
import com.haoxt.agent.model.Message;
import com.haoxt.agent.util.HttpRequest;
import com.haoxt.agent.widget.RecycleViewDividerForList;

import java.lang.reflect.Type;
import java.util.List;

import tft.mpos.library.interfaces.OnHttpResponseListener;

import static com.haoxt.agent.activity.home.citypicker.ProvinceActivity.RESULT_DATA;
import static com.haoxt.agent.application.AppApplication.BUNDATA;
import static tft.mpos.library.util.CommonUtil.showShortToast;


public class AreaActivity extends Activity {
    
    private TextView mCityNameTv;
    
    private ImageView mImgBack;
    
    private RecyclerView mCityRecyclerView;
    
    private CityInfoBean mProCityInfo = null;
    
    private CityBean areaBean = new CityBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);
        mProCityInfo = this.getIntent().getParcelableExtra(BUNDATA);
        initView();

        setData();

    }

    private void setData() {

        if (mProCityInfo != null) {
            mCityNameTv.setText("" + mProCityInfo.getName());

            HttpRequest.qryProvCity("AREA", "",mProCityInfo.getId(),0, new OnHttpResponseListener() {
                @Override
                public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                    Gson gson = new Gson();
                    Type type = new TypeToken<Message<CityInfoBean>>() {}.getType();
                    Message<CityInfoBean> message = gson.fromJson(resultJson,type);

                    if("000000".equals(message.getRspCd())){

                        List<CityInfoBean> cityList = message.getRspData();
                        if (cityList == null) {
                            return;
                        }

                        CityAdapter cityAdapter = new CityAdapter(AreaActivity.this, cityList);
                        mCityRecyclerView.setAdapter(cityAdapter);
                        cityAdapter.setOnItemClickListener(new CityAdapter.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(View view, int position) {

                                areaBean.setName(cityList.get(position).getName());
                                areaBean.setId(cityList.get(position).getId());

                                //将计算的结果回传给第一个Activity
                                Intent reReturnIntent = new Intent();
                                reReturnIntent.putExtra("area", areaBean);
                                setResult(RESULT_DATA, reReturnIntent);
                                //退出第二个Activity
                                AreaActivity.this.finish();

                            }
                        });

                    }else{
                        showShortToast(AreaActivity.this,"登陆失败");
                    }

                }
            });

//            final List<CityInfoBean> cityList = mProCityInfo.getCityList();
//            if (cityList == null) {
//                return;
//            }
//
//            CityAdapter cityAdapter = new CityAdapter(AreaActivity.this, cityList);
//            mCityRecyclerView.setAdapter(cityAdapter);
//            cityAdapter.setOnItemClickListener(new CityAdapter.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(View view, int position) {
//
//                    areaBean.setName(cityList.get(position).getName());
//                    areaBean.setId(cityList.get(position).getId());
//
//                    //将计算的结果回传给第一个Activity
//                    Intent reReturnIntent = new Intent();
//                    reReturnIntent.putExtra("area", areaBean);
//                    setResult(RESULT_DATA, reReturnIntent);
//                    //退出第二个Activity
//                    AreaActivity.this.finish();
//
//                }
//            });
            
        }
    }
    
    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_left);
        mCityNameTv = (TextView) findViewById(R.id.cityname_tv);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCityRecyclerView = (RecyclerView) findViewById(R.id.city_recyclerview);
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCityRecyclerView.addItemDecoration(new RecycleViewDividerForList(this, LinearLayoutManager.HORIZONTAL, true));
        
    }
    
}
