package com.haoxt.agent.activity.home.achievement_manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.activity.home.transaction.SelectTimePopup;

import java.util.ArrayList;

import tft.mpos.library.base.BaseActivity;

/**
 * 条件过滤 Activity
 */
public class AchievementConditionFilterAvtivity extends BaseActivity implements  CompoundButton.OnCheckedChangeListener,SelectTimePopup.OnConfirmTimeListener {

    ArrayList<String> selected = new ArrayList<>();
    private SelectTimePopup mTimePopup;
    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, AchievementConditionFilterAvtivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.condition_filter_activity);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }

    private TextView filterOk;
    private TextView  startime,endtime;
    private CheckBox all1_cb,transation_cb,jihuo_cb,kuaiti_cb,all2_cb,wait_cb,pass_cb,reject_cb;
    @Override
    public void initView() {

        filterOk =  findViewById(R.id.tx_condition_filter_ok);
        filterOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String startimeTx = startime.getText()==null?"":startime.getText().toString();
                String endtimeTx = endtime.getText()==null?"":startime.getText().toString();

                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("checkboxlist", selected);
                resultIntent.putExtra("startime",startimeTx);
                resultIntent.putExtra("endtime",endtimeTx);
                setResult(1000, resultIntent);
                selected.clear();
                finish();

            }
        });

        startime =  findViewById(R.id.startime_et);
        endtime =  findViewById(R.id.endtime_et);
        startime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePopup();
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePopup();
            }
        });

        int chk_id [] = {R.id.all1_cb ,R.id.transation_cb ,R.id.jihuo_cb, R.id.kuaiti_cb,
                         R.id.all2_cb , R.id.wait_cb , R.id.pass_cb , R.id.reject_cb};

        for(int id : chk_id){
           CheckBox chk = findViewById(id);
           chk.setOnCheckedChangeListener(this);
        }

//        all1_cb =  findViewById(R.id.all1_cb);
//        transation_cb =  findViewById(R.id.transation_cb);
//        jihuo_cb =  findViewById(R.id.jihuo_cb);
//        kuaiti_cb =  findViewById(R.id.kuaiti_cb);
//
//        all2_cb =  findViewById(R.id.all2_cb);
//        wait_cb =  findViewById(R.id.wait_cb);
//        pass_cb =  findViewById(R.id.pass_cb);
//        reject_cb =  findViewById(R.id.reject_cb);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked){
            selected.add(compoundButton.getText().toString());
        }else{
            selected.remove(compoundButton.getText().toString());
        }
    }

    private void showTimePopup() {
        if (mTimePopup != null) {
            mTimePopup = null;
        }
        mTimePopup = new SelectTimePopup(getActivity());
        mTimePopup.setOnConfirmTimeListener(this);
        mTimePopup.show();
    }


    @Override
    public void onConfirmTime(String startTime, String endTime) {
        startime.setText(startTime);
        endtime.setText(endTime);
    }
}
