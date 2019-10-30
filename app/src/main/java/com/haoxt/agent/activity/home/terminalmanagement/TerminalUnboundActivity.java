package com.haoxt.agent.activity.home.terminalmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.haoxt.agent.R;
import com.haoxt.agent.activity.other.ScanActivity;
import com.haoxt.agent.util.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.util.StringUtil;

import static com.zxing.activity.CaptureActivity.RESULT_QRCODE_STRING;

/**
 * @Description: 终端解绑
 * @Author: liuxx
 * @CreateDate: 2019/10/25 16:54
 */
public class TerminalUnboundActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private final static int SCAN_CODE = 1;
    private EditText etInputTerminalno;
    private ImageView ivTerUnboundScan;
    private Button btnTerUnbound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ter_unbound);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

        etInputTerminalno = findViewById(R.id.et_input_terminalno);
        ivTerUnboundScan = findViewById(R.id.iv_ter_unbound_scan);
        btnTerUnbound = findViewById(R.id.btn_ter_unbound);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        ivTerUnboundScan.setOnClickListener(this);
        btnTerUnbound.setOnClickListener(this);
        etInputTerminalno.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ter_unbound_scan://扫描
                startActivityForResult(ScanActivity.createIntent(this), SCAN_CODE);
                break;
            case R.id.btn_ter_unbound://提交按钮
                String terminalNO = StringUtil.get(etInputTerminalno.getText()).trim();
                if ("".equals(terminalNO)) {
                    showShortToast("请扫描或输入需要解绑的终端序列号");
                } else {
                    unbound();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCAN_CODE:
                if (RESULT_OK == resultCode) {
                    etInputTerminalno.setText(data.getExtras().getString(RESULT_QRCODE_STRING));
                } else {
                    showShortToast("获取二维码信息失败");
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        etInputTerminalno.setSelection(etInputTerminalno.getText().length());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void unbound() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("termSnNo", StringUtil.get(etInputTerminalno.getText()));
        HttpRequest.terNnbound(map, 0, (requestCode, resultJson, e) -> {
            Map<String, Object> dataMap = StringUtil.json2map(resultJson);
            if (!"".equals(resultJson)) {
                showShortToast(StringUtil.get(dataMap.get("message")));
            } else {
                showShortToast("网络错误，请稍后重试！");
            }
        });
    }
}
