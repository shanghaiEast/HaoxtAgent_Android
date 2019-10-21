package com.haoxt.agent.activity.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haoxt.agent.R;
import com.haoxt.agent.activity.home.citypicker.CityBean;
import com.haoxt.agent.activity.home.citypicker.ProvinceActivity;
import com.haoxt.agent.util.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import cn.cloudwalk.libproject.Bulider;
import cn.cloudwalk.libproject.CloudwalkBankCardOCRActivity;
import cn.cloudwalk.libproject.util.ImgUtil;
import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.StringUtil;

/**
 * 绑定银行卡 Activity
 *
 * @author baowen
 * @use toActivity(SettingActivity.createIntent ( ...));
 */
public class MyBankCardAddActivity extends BaseActivity implements OnClickListener {
    private static final int REQUEST_TO_PLACE_PICKER = 32,FORMBANKSEARCH = 1002,FORMBANKSEARCHBASE = 1000;
    public final int MSG_GET_BANK_OK = 1;
    private static final String TAG = "SettingActivity";
    private String bankCardSide,bankName,bankNumber,topBankNo,topBankName,subbranchBankNo,subbranchBankName,
            proId,proName,cityId,cityName;

    private final static int /*REQ_PHOTO1 = 1,*/ REQ_CAMERA1 = 2, /*REQ_PHOTO2 = 3,*/
            REQ_CAMERA2 = 4;
    private  CityBean provinceBean,cityBean,areaBean;



    //启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * 启动这个Activity的Intent
     *
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, MyBankCardAddActivity.class);
    }

    //启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_bank_card_add);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        if(savedInstanceState !=null){

            topBankNo = savedInstanceState.getString("topBankNo");
            topBankName = savedInstanceState.getString("topBankName");
            proId = savedInstanceState.getString("proId");
            proName = savedInstanceState.getString("proName");
            cityId = savedInstanceState.getString("cityId");
            cityName = savedInstanceState.getString("cityName");

        }

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private TextView card_name, card_account, card_openbank, idcard_area, debicard_subbranch;

    private ImageView debit_card_side;

    @Override
    public void initView() {//必须调用

        card_name = (TextView) findViewById(R.id.real_name_auth_debit_card_name);
        card_account = (TextView) findViewById(R.id.real_name_auth_debit_card_account);
        card_openbank = (TextView) findViewById(R.id.real_name_auth_debit_card_openbank);
        idcard_area = (TextView) findViewById(R.id.real_name_auth_debicard_idcard_area);
        debicard_subbranch = (TextView) findViewById(R.id.real_name_auth_debicard_subbranch);

        debit_card_side = (ImageView) findViewById(R.id.btn_real_name_auth_debit_card_side);
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private HashMap<String, Object> pageData;
    @Override
    public void initData() {//必须调用

        pageData = (HashMap<String, Object>) getIntent().getSerializableExtra("pageData");
//        CityListLoader.getInstance().loadProData(this);
    }


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {//必须调用

        findView(R.id.btn_real_name_auth_debit_card_side).setOnClickListener(this);
        findView(R.id.real_name_auth_debit_card_openbank).setOnClickListener(this);
        findView(R.id.real_name_auth_debicard_subbranch).setOnClickListener(this);
        findView(R.id.real_name_auth_debicard_idcard_area).setOnClickListener(this);
        findView(R.id.btn_add_bank_msg_upload).setOnClickListener(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("topBankNo",topBankNo);
        outState.putString("topBankName",topBankName);
        outState.putString("proId",proId);
        outState.putString("proName",proName);
        outState.putString("cityId",cityId);
        outState.putString("cityName",cityName);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_real_name_auth_debit_card_side:
                startBankCard();
                break;

            case R.id.real_name_auth_debit_card_openbank:
                startActivityForResult(BankSearchActivity.createIntent(context).putExtra("searchPageFrom","0"),FORMBANKSEARCHBASE);
                break;

            case R.id.real_name_auth_debicard_subbranch:
                if(StringUtil.isEmpty(topBankNo)){
                    Toast.makeText(this, "请先选择开户总行", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(StringUtil.isEmpty(proId)&&proId!=""){
                    Toast.makeText(this, "请选择省份", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(StringUtil.isEmpty(cityId)&&cityName!=""){
                    Toast.makeText(this, "请选择城市", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(context, BankSearchActivity.class);
                intent.putExtra("searchPageFrom","1");
                intent.putExtra("topBankNo",topBankNo);
                intent.putExtra("province",proId);
                intent.putExtra("city",cityId);
                startActivityForResult(intent,FORMBANKSEARCHBASE) ;

//                toActivity(BankSearchActivity.createIntent(context).putExtra("searchPageFrom","1"));
                break;

            case R.id.real_name_auth_debicard_idcard_area:

                Intent intentArea = new Intent(context, ProvinceActivity.class);
                startActivityForResult(intentArea, ProvinceActivity.RESULT_DATA);

//                Intent intent1 = new Intent(this, ProvinceActivity.class);
//                startActivityForResult(intent1, ProvinceActivity.RESULT_DATA);

//                toActivity(PlacePickerWindow.createIntent(context, getPackageName(), 2), REQUEST_TO_PLACE_PICKER, false);
                break;

            case R.id.btn_add_bank_msg_upload:
                 uploadBankCradMsg();
//                toActivity(MerchantInfoAuthActivity.createIntent(context));

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
//            case REQUEST_TO_PLACE_PICKER:
//                if (data != null) {
//                    ArrayList<String> placeList = data.getStringArrayListExtra(PlacePickerWindow.RESULT_PLACE_LIST);
//                    if (placeList != null) {
//                        String place = "";
//                        for (String s : placeList) {
//                            place += StringUtil.getTrimedString(s);
//                        }
//                        province = placeList.get(0);
//                        city = placeList.get(1);
//                        idcard_area.setText(place);
//                    }
//                }
//                break;

            case FORMBANKSEARCHBASE:
            case FORMBANKSEARCH:
                if(null!=data){
                    String searchPageFrom = data.getStringExtra("searchPageFrom");
                    if(searchPageFrom.equals("0")){
                        topBankNo = data.getStringExtra("bankNo");
                        topBankName =  data.getStringExtra("bankName");
                        card_openbank.setText(data.getStringExtra("bankName"));
                    }else{
                        subbranchBankNo = data.getStringExtra("bankNo");
                        subbranchBankName  = data.getStringExtra("bankName");
                        debicard_subbranch.setText(data.getStringExtra("bankName"));
                    }
                }
                break;

            case ProvinceActivity.RESULT_DATA:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    //省份结果
                     provinceBean = data.getParcelableExtra("province");
                     proId = provinceBean.getId();
                     proName = provinceBean.getName();
                    //城市结果
                     cityBean = data.getParcelableExtra("city");
                     cityName = cityBean.getName();
                     cityId = cityBean.getId();
                    //区域结果
                     areaBean = data.getParcelableExtra("area");

                     idcard_area.setText(provinceBean.getName()+cityBean.getName()+areaBean.getName());

                }


            default:
                break;
        }
    }

    /**
     * 上传银行卡信息
     */
    private void uploadBankCradMsg() {

        String topBank = card_openbank.getText().toString();
        String subBank = debicard_subbranch.getText().toString();

//        if (topBank!=null&&topBank.equals("")) {
//            Toast.makeText(this, "开户总行为空", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (proId==null||proId.equals("")) {
            Toast.makeText(this, "开户省份为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cityId==null||cityId.equals("")) {
            Toast.makeText(this, "开户城市为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (subBank==null||subBank.equals("")) {
            Toast.makeText(this, "开户支行为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (bankNumber==null||bankNumber.equals("")) {
            Toast.makeText(this, "结算账户为空", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpRequest.addStlBankInfo(bankNumber, topBankName,topBankNo,subbranchBankName,subbranchBankNo,proId,cityId,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);

                if("000000".equals(dataMap.get("rspCd").toString())){

//                    toActivity(MyBankCardAddActivity.createIntent(context).putExtra("pageData",(Serializable)pageData));
//                    getActivity().finish();

                    uploadMerchanInfoMsg();


                }else{
                    showShortToast("上传失败");
                }

            }
        });

    }

    /**
     * 上传商户信息 该流程暂未实现传空值
     */
    private void uploadMerchanInfoMsg(){
        HttpRequest.uploadMerchanInfo("","","","","","","",0,new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);

                if("000000".equals(dataMap.get("rspCd").toString())){
                    showShortToast("上传成功");
                    Intent intent = new Intent(context, RealNameAuthResultActivity.class);
                    intent.putExtra("activityfrom", "realnameinfo");
                    toActivity(intent);

                }else{
                    showShortToast("上传失败");
                }

            }
        });

    }


    /**
     * 附件上传
     * @param file
     * @param type
     */
    public void uploadRealNameFile(String file,String type){

        HttpRequest.uploadRealNameFile(file,type,0, new OnHttpResponseListener() {

            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {

                Map<String, Object> dataMap =  StringUtil.json2map(resultJson);

                if("000000".equals(dataMap.get("rspCd").toString())){
                    showShortToast("上传成功");
                }else{
                    showShortToast("登陆失败");
                }

            }
        });
    }
    private void startBankCard() {

        Intent mIntent = new Intent(MyBankCardAddActivity.this, CloudwalkBankCardOCRActivity.class);
        mIntent.putExtra("LICENCE", Bulider.licence);
        mIntent.putExtra("BANKCARD_AUTO_RATIO", false);//是否支持竖版银行卡
        startActivity(mIntent);

        getBankOcrResult();
    }


    private void getBankOcrResult() {
        final Bulider bulider = new Bulider();
        bulider.setBankOCRFinished((bankCardInfo, path) -> {

            String cardNum = bankCardInfo.cardNum;
            String bankName = bankCardInfo.bankName;
            String cardName = bankCardInfo.cardName;
            String cardType = bankCardInfo.cardType;

            bankName = bankCardInfo.bankName;
            bankNumber = bankCardInfo.cardNum;


            Message msg = new Message();
            msg.what = MSG_GET_BANK_OK;
            Bundle bundle = new Bundle();
            bundle.putString("cardNum",cardNum);
            bundle.putString("bankName",bankName);
            bundle.putString("cardName",cardName);
            bundle.putString("cardType",cardType);
            bundle.putString("path",path);

            msg.setData(bundle);
            mHandler.sendMessage(msg);

            Log.i(TAG, "getBankOcrResult: " + path);

        });
    }

    //生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MSG_GET_BANK_OK:

//                    Message msg = new Message();
//                    msg.what = MSG_GET_BANK_OK;
//                    Bundle bundle = new Bundle();
//                    bundle.putString("cardNum",cardNum);
//                    bundle.putString("bankName",bankName);
//                    bundle.putString("cardName",cardName);
//                    bundle.putString("cardType",cardType);
//                    bundle.putString("path",path);

                    bankName =  msg.getData().getString("bankName");
                    bankNumber = msg.getData().getString("cardNum");
                    String path = msg.getData().getString("path");

                    card_name.setText(bankName);
                    card_account.setText(bankNumber);



                    Bitmap img1 = ImgUtil.getBitmapByPath(path);
                    bankCardSide = ImgUtil.bitmapToBase64(img1);
                    debit_card_side.setImageBitmap(img1);

                    uploadRealNameFile(bankCardSide,"3");

                    break;
//                case MSG_DOWN_SUCCESS:
////                    mTipTv.setText("download success");
//                    break;
//                case MSG_DOWN_START:
////                    mTipTv.setText("download start");
//                    break;
            }
        };
    };


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
