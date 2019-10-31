package com.haoxt.agent.activity.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.fragment.tab.MainTabActivity;
import com.haoxt.agent.application.AppApplication;
import com.haoxt.agent.util.HttpRequest;

import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

public class LoginActivity extends BaseActivity implements OnClickListener {

    private TextView mRegisterTv;
    private EditText mNumberEt;
    private EditText mPasswordEt;
    private TextView mForgetTv;
    private Button mLoginBt,rememberPwd;
    private CheckBox mShowPasswordCb;

    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);


        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }

    @Override
    public void initView() {
        mNumberEt = (EditText)findViewById(R.id.login_number_et);
        mPasswordEt = (EditText)findViewById(R.id.login_password_et);
        mShowPasswordCb = (CheckBox)findViewById(R.id.show_password_cb);

        rememberPwd = (Button) findViewById(R.id.login_remember_pwd);
        mForgetTv = (TextView)findViewById(R.id.login_forget_tv);

        mLoginBt = (Button)findViewById(R.id.login_login_bt);
        mRegisterTv = (TextView)findViewById(R.id.login_register_tv);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        mForgetTv.setOnClickListener(this);
        mLoginBt.setOnClickListener(this);
        mRegisterTv.setOnClickListener(this);
        mShowPasswordCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_forget_tv:
                toActivity(ForgetPasswordActivity.createIntent(context));
                break;

            case R.id.login_login_bt:
                login();
                break;

            case R.id.login_register_tv:
                toActivity(RegisterActivity.createIntent(context));
                break;

            case R.id.login_remember_pwd:
//                toActivity(RegisterActivity.createIntent(context));
                break;
        }
    }

    private void login() {

//        String phone = mNumberEt.getText().toString();
//        String pwd = mPasswordEt.getText().toString();

        String phone = "18616972092";
        String pwd = "qwer1234";

//        String phone = "17667389706";
//        String pwd = "c123456";

//        String phone = "15512345678";
//        String pwd = "z123456";

        if (phone.equals("")) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pwd.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpRequest.login(phone, pwd,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Intent intent = MainTabActivity.createIntent(context);
//                intent.putExtra("pageData",(Serializable)userData);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
                enterAnim = exitAnim = R.anim.null_anim;
                finish();

//                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);
//                Map<String, Object>  userData = (Map<String, Object>) dataMap.get("rspMap");
//
//                if("000000".equals(dataMap.get("rspCd").toString())){
//
//                    AppApplication.getInstance().setToken(userData.get("TOKEN_ID").toString());
//                    AppApplication.getInstance().isLogin = true;
//
//                    AppApplication.getInstance().setUserno(userData.get("USRUNO")==null?"":userData.get("USRUNO").toString());
//
//                    AppApplication.getInstance().setUserStatus(userData.get("USR_STATUS")==null?"":userData.get("USR_STATUS").toString());
//                    AppApplication.getInstance().setRealNameStatus(userData.get("USR_REAL_STS")==null?"":userData.get("USR_REAL_STS").toString());
//                    AppApplication.getInstance().setUserStlStatus(userData.get("USR_STL_STS")==null?"":userData.get("USR_STL_STS").toString());
//                    AppApplication.getInstance().setUserTermStatus(userData.get("USR_TERM_STS")==null?"":userData.get("USR_TERM_STS").toString());
//                    AppApplication.getInstance().setUserCreditCardStatus(userData.get("CCARD_VALID_STS")==null?"":userData.get("CCARD_VALID_STS").toString());
//
//                    String blueTooth = userData.get("BLUE_TOOTH")==null?"":userData.get("BLUE_TOOTH").toString();
//                    if (!blueTooth.equals("")){
//                        String[] blueToothArray=blueTooth.split(",");
//                        if (blueToothArray.length==2){
//                            AppApplication.getInstance().setBluetooth(blueToothArray[0]);
//                            AppApplication.getInstance().setMac(blueToothArray[1]);
//                        }else{
//                            AppApplication.getInstance().setBluetooth(blueToothArray[0]);
//                        }
//                    }
//
//                    Intent intent = MainTabActivity.createIntent(context);
//                    intent.putExtra("pageData",(Serializable)userData);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//
//                    overridePendingTransition(R.anim.bottom_push_in, R.anim.hold);
//                    enterAnim = exitAnim = R.anim.null_anim;
//                    finish();
//
//                }else{
//                    showShortToast("登陆失败");
//                }

            }
        });

    }
}
