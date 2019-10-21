package com.haoxt.agent.activity.home.citypicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import static com.haoxt.agent.application.AppApplication.BUNDATA;
import static tft.mpos.library.util.CommonUtil.showShortToast;


public class ProvinceActivity extends Activity {
    
    private TextView mCityNameTv;
    
    private RecyclerView mCityRecyclerView;
    
    public static final int RESULT_DATA = 1001;
    
    private CityBean provinceBean = new CityBean();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);
        initView();
        setData();
        
    }
    
    private void setData() {

        HttpRequest.qryProvCity("PROV", "","",0, new OnHttpResponseListener() {
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

                    CityAdapter cityAdapter = new CityAdapter(ProvinceActivity.this, cityList);
                    mCityRecyclerView.setAdapter(cityAdapter);
                    cityAdapter.setOnItemClickListener(new CityAdapter.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(View view, int position) {

                            provinceBean.setId(cityList.get(position).getId());
                            provinceBean.setName(cityList.get(position).getName());
                            Intent intent = new Intent(ProvinceActivity.this, CityActivity.class);
                            intent.putExtra(BUNDATA, cityList.get(position));
                            startActivityForResult(intent, RESULT_DATA);

                        }
                    });
                }else{
                    showShortToast(ProvinceActivity.this,"登陆失败");
                }

            }
        });
        

        
    }
    
    private void initView() {
        mCityNameTv = (TextView) findViewById(R.id.cityname_tv);
        mCityNameTv.setText("选择省份");
        mCityRecyclerView = (RecyclerView) findViewById(R.id.city_recyclerview);
        mCityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCityRecyclerView.addItemDecoration(new RecycleViewDividerForList(this, LinearLayoutManager.HORIZONTAL, true));
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_DATA && data != null) {
            CityBean area = data.getParcelableExtra("area");
            CityBean city = data.getParcelableExtra("city");
            Intent intent = new Intent();
            intent.putExtra("province", provinceBean);
            intent.putExtra("city", city);
            intent.putExtra("area", area);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
    
}
