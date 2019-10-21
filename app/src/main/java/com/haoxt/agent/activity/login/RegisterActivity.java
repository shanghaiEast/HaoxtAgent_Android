package com.haoxt.agent.activity.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.haoxt.agent.R;
import com.haoxt.agent.util.HttpRequest;

import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private boolean isAgreement;


    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);


        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    private EditText phone,pwd_et,verificationCode;
    private CheckBox regietercb;
    private Button verificationCodeBtn;
    @Override
    public void initView() {

        phone = findViewById(R.id.regieter_number_et);
        verificationCode = findViewById(R.id.regieter_verification_code);
        pwd_et = findViewById(R.id.regieter_password_et);
        regietercb = findViewById(R.id.regieter_agreement_cb);
        verificationCodeBtn = findViewById(R.id.regieter_get_verification_code);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onReturnClick(View v) {
        finish();
    }

    @Override
    public void initEvent() {

//        mBackIv.setOnClickListener(this);
        findViewById(R.id.regieter_get_verification_code).setOnClickListener(this);
        findViewById(R.id.regieter_show_password_et).setOnClickListener(this);
        findViewById(R.id.regieter_user_agreement).setOnClickListener(this);
        findViewById(R.id.regieter_commit).setOnClickListener(this);

        regietercb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isAgreement = isChecked;
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back_iv:
                getActivity().onBackPressed();
                break;

            case R.id.regieter_get_verification_code:

                getVerificationCode();
                break;

            case R.id.regieter_show_password_et:
                break;

            case R.id.regieter_agreement_cb:
                break;

            case R.id.regieter_user_agreement:
                break;

            case R.id.regieter_commit:

                registeUser();
                break;

            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void registeUser() {

        String number,code,pwd;

        number = phone.getText().toString();
        code = verificationCode.getText().toString();
        pwd = pwd_et.getText().toString();

        if(!isAgreement){
            Toast.makeText(this, "请勾选服务协议", Toast.LENGTH_SHORT).show();
            return;
        }

        if (number.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (code.equals("")) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pwd.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpRequest.register(number, code,pwd,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);

                if("000000".equals(dataMap.get("rspCd").toString())){
                    showShortToast("注册成功");
                }else{
                    showShortToast("注册失败");
                }
            }
        });


    }

    /**
     * 获取验证码
     */
    private void getVerificationCode() {

       String number = phone.getText().toString();

        HttpRequest.getVerificationCode("0", number,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);

                verificationCodeBtn.setEnabled(false);
                verificationCodeBtn.setTextColor(context.getResources().getColor(R.color.gray_bdbdbd));

                showShortToast(dataMap.get("rspInf").toString());



            }
        });
    }
}