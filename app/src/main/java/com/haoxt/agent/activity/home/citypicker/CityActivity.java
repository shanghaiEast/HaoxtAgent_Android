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

public class CityActivity extends Activity {

    private TextView mCityNameTv;

    private ImageView mImgBack;

    private RecyclerView mCityRecyclerView;

    private CityInfoBean mProInfo = null;

    private String cityName = "";

    private CityBean cityBean = new CityBean();

    private CityBean area = new CityBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);
        mProInfo = this.getIntent().getParcelableExtra(BUNDATA);
        initView();

        setData(mProInfo);

    }

    private void setData(CityInfoBean mProInfo) {

        HttpRequest.qryProvCity("CITY", "",mProInfo.getId(),0, new OnHttpResponseListener() {
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

                    CityAdapter cityAdapter = new CityAdapter(CityActivity.this, cityList);
                    mCityRecyclerView.setAdapter(cityAdapter);
                    cityAdapter.setOnItemClickListener(new CityAdapter.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(View view, int position) {

                            cityBean.setId(cityList.get(position).getId());
                            cityBean.setName(cityList.get(position).getName());

                            Intent intent = new Intent(CityActivity.this, AreaActivity.class);
                            intent.putExtra(BUNDATA, cityList.get(position));
                            startActivityForResult(intent, RESULT_DATA);
                        }
                    });

                }else{
                    showShortToast(CityActivity.this,"登陆失败");
                }

            }
        });

//        if (mProInfo != null && mProInfo.getCityList().size() > 0) {
//            mCityNameTv.setText("" + mProInfo.getName());
//
//            final List<CityInfoBean> cityList = mProInfo.getCityList();
//            if (cityList == null) {
//                return;
//            }
//
//            CityAdapter cityAdapter = new CityAdapter(CityActivity.this, cityList);
//            mCityRecyclerView.setAdapter(cityAdapter);
//            cityAdapter.setOnItemClickListener(new CityAdapter.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(View view, int position) {
//
//                    cityBean.setId(cityList.get(position).getId());
//                    cityBean.setName(cityList.get(position).getName());
//
//                    Intent intent = new Intent(CityActivity.this, AreaActivity.class);
//                    intent.putExtra(BUNDATA, cityList.get(position));
//                    startActivityForResult(intent, RESULT_DATA);
//                }
//            });
//
//        }
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
        mCityNameTv = (TextView) findViewById(R.id.cityname_tv);
        mCityRecyclerView = (RecyclerView) findViewById(R.id.city_recyclerview);
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCityRecyclerView.addItemDecoration(new RecycleViewDividerForList(this, LinearLayoutManager.HORIZONTAL, true));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_DATA && data != null) {
            area = data.getParcelableExtra("area");
            Intent intent = new Intent();
            intent.putExtra("city", cityBean);
            intent.putExtra("area", area);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
