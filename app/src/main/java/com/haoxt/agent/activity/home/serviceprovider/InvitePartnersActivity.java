package com.haoxt.agent.activity.home.serviceprovider;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.haoxt.agent.R;
import com.zxing.encoding.EncodingHandler;

import tft.mpos.library.base.BaseActivity;

/**
 * Created by liuxx on 2019/10/22
 * 邀请合作伙伴
 */

public class InvitePartnersActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvRecommender;
    private TextView tvRecommenderPhone;
    private ImageView ivShareBarcode;
    private TextView tvShare;
    private Bitmap qRCodeBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_partners);
        initView();
        initData();
        initEvent();
    }

    @Override
    public void initView() {

        tvRecommender = findViewById(R.id.tv_recommender);
        tvRecommenderPhone = findViewById(R.id.tv_recommender_phone);
        ivShareBarcode = findViewById(R.id.iv_share_barcode);
        tvShare = findViewById(R.id.tv_share);

    }

    @Override
    public void initData() {
        try {
            qRCodeBitmap = EncodingHandler.createQRCode("https://www.baidu.com", 600);
            runUiThread(() -> {
                ivShareBarcode.setImageBitmap(qRCodeBitmap);
            });
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initEvent() {
        tvShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_share://分享按钮
                showShortToast("分享");
                break;

        }
    }
}
