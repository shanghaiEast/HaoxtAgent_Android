package com.haoxt.agent.activity.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haoxt.agent.R;
import com.haoxt.agent.util.HttpRequest;

import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, ForgetPasswordActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forget_password);


        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }

    private EditText phone,code,pwd;
    private Button verificationCodeBtn;
    @Override
    public void initView() {

        phone = findViewById(R.id.find_number_et);
        code = findViewById(R.id.find_auth_code_et);
        verificationCodeBtn =  findViewById(R.id.find_auth_code_bt);
        pwd  = findViewById(R.id.find_password_et);

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

        verificationCodeBtn.setOnClickListener(this);
        findViewById(R.id.find_pwd_commit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.find_pwd_commit:
                findpassword();
                break;

            case R.id.find_auth_code_bt:
                getVerificationCode();
                break;

            default:
                break;
        }
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

    private void findpassword() {
        String number,codetx,pwdtx;

        number = phone.getText().toString();
        codetx = code.getText().toString();
        pwdtx = pwd.getText().toString();

        if (number.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (codetx.equals("")) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pwdtx.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpRequest.resetpassword(number, codetx,pwdtx,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {


                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);

                if("000000".equals(dataMap.get("rspCd").toString())){
                    showShortToast("密码重置成功");
                }else{
                    showShortToast("密码重置失败");
                }



            }
        });



    }
}
