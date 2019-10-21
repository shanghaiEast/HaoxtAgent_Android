package cn.cloudwalk.libproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.cloudwalk.IDCardSDK;
import cn.cloudwalk.callback.IDCardImgCallback;
import cn.cloudwalk.callback.IDCardInfoCallback;
import cn.cloudwalk.jni.IDCardImg;
import cn.cloudwalk.jni.IDFaceImg;
import cn.cloudwalk.jni.IdCardInfo;
import cn.cloudwalk.libproject.camera.AutoFocusCameraPreview;
import cn.cloudwalk.libproject.camera.Delegate;
import cn.cloudwalk.libproject.util.FileUtil;
import cn.cloudwalk.libproject.util.ImgUtil;
import cn.cloudwalk.libproject.util.Util;
import cn.cloudwalk.libproject.view.OcrMaskView;

public class OcrCameraActivity extends Activity implements Delegate, IDCardImgCallback, IDCardInfoCallback {
    public static final String TAG = "OcrCameraActivity";
    public static String FILEPATH_KEY = "filepath_key";
    private static final int CANCEL_FOCUS = 0, DRAW_LINE = 1, DRAW_PROGRESS = 2;
    int ocr_flag;
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");

    AutoFocusCameraPreview mAutoFoucsCameraPreview;
    OcrMaskView maskView;
    ImageView mIv_idrect;

    int initRet = -1;
    IDCardSDK iDCardSDK = null;
    IDCardImg idCardImg;
    boolean isWork;//正在进行处理

    final String OutJpgName = "idcard.jpg";//保存的图片名
    Bitmap bmpCanLine;//扫描线
    Bitmap bmpfocus;//对焦图
    Bitmap bmpfocused;//聚焦图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.cloudwalk_activity_rect_ocr);

        ocr_flag = getIntent().getIntExtra(Contants.OCR_FLAG, -1);
        if (-1 == ocr_flag) {
            Toast.makeText(this, "params error", Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }

        initView();
        initSDK();
        initCallback();
        Log.i(TAG, "版本信息 APP:" + iDCardSDK.SDK_APP_VERSION + " 算法:" + iDCardSDK.SDK_ALGORITHM_VERSION);
        bmpCanLine = BitmapFactory.decodeResource(getResources(), R.drawable.scan_line);
        bmpfocus = BitmapFactory.decodeResource(getResources(), R.drawable.focus);
        bmpfocused = BitmapFactory.decodeResource(getResources(), R.drawable.focused);
        mAutoFoucsCameraPreview.setFlag(ocr_flag);
        mAutoFoucsCameraPreview.setSizeCallback(new AutoFocusCameraPreview.SizeCallback() {
            @Override
            public void onSizeChange(int width, int height, final int ocrRectW, final int ocrRectH) {
                maskView.setOcr(width, height, ocrRectW, ocrRectH, ocr_flag, bmpCanLine, bmpfocus, bmpfocused);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ocrRectW, ocrRectH);
                        params.addRule(RelativeLayout.CENTER_IN_PARENT);
                        mIv_idrect.setLayoutParams(params);
                        if (ocr_flag == 1) {
                            mIv_idrect.setBackgroundResource(R.drawable.zhengmian2);
                        } else {
                            mIv_idrect.setBackgroundResource(R.drawable.beimian2);
                        }

                    }
                });
            }
        });

        deleteCachedJpg();
    }

    /**
     * 初始化相关参数
     */
    private void initView() {
        mAutoFoucsCameraPreview = (AutoFocusCameraPreview) findViewById(R.id.preview);
        maskView = (OcrMaskView) findViewById(R.id.ocrMaskView);
        mIv_idrect = (ImageView) findViewById(R.id.iv_idrect);
    }

    /**
     * 注册接口
     */
    private void initCallback() {
        iDCardSDK.cwIDCardImgCallback(this);
        iDCardSDK.cwIDCardInfoCallback(this);
        mAutoFoucsCameraPreview.setDelegate(this);
    }

    /**
     * 初始化SDK
     */
    private void initSDK() {
        iDCardSDK = IDCardSDK.getInstance(this);
        initRet = iDCardSDK.cwCreateIdCardRecog(this, Bulider.licence);
        if (initRet != 0) {
            new AlertDialog.Builder(this).setMessage(R.string.facedectfail_appid)
                    .setNegativeButton("确定", new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            finish();
                        }
                    }).show();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAutoFoucsCameraPreview.cwStartCamera();

    }

    @Override
    protected void onStop() {
        mAutoFoucsCameraPreview.cwStopCamera();
        mHandler.removeCallbacksAndMessages(null);
        isWork = false;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (null != iDCardSDK)
            iDCardSDK.cwDestroyIdCardRecog();
        if (bmpCanLine != null && !bmpCanLine.isRecycled()) {
            bmpCanLine.recycle();
        }
        if (bmpfocus != null && !bmpfocus.isRecycled()) {
            bmpfocus.recycle();
        }
        if (bmpfocused != null && !bmpfocused.isRecycled()) {
            bmpfocused.recycle();
        }
        super.onDestroy();
    }

    @Override
    public void onOpenCameraError() {
        // 打开相机出错
        Toast.makeText(this, "相机打开失败,请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocus(float x, float y) {
        // 显示焦点
        maskView.setFocus(x, y);
    }

    @Override
    public void onFocused() {
        // 变换焦点
        maskView.setFocused();
        mHandler.sendEmptyMessageDelayed(CANCEL_FOCUS, 150);
    }

    @Override
    public void IDCardImg(IDCardImg idCardImg) {
        mHandler.obtainMessage(DRAW_LINE, new Rect(idCardImg.left, idCardImg.top, idCardImg.right, idCardImg.bottom)).sendToTarget();
        this.idCardImg = idCardImg;
    }

    @Override
    public void IDCardDetectOk(IDCardImg idCardImg) {
        mAutoFoucsCameraPreview.stopCameraPreview();

        mHandler.obtainMessage(DRAW_LINE, new Rect(idCardImg.left, idCardImg.top, idCardImg.right, idCardImg.bottom)).sendToTarget();

        mHandler.obtainMessage(DRAW_PROGRESS, new Rect(idCardImg.left, idCardImg.top, idCardImg.right, idCardImg.bottom)).sendToTarget();

        this.idCardImg = idCardImg;
    }

    @Override
    public void IDCardInfo(IdCardInfo idCardInfo, IDFaceImg idFaceImg) {
        if (null == idCardInfo && idFaceImg == null) {//识别失败时处理
            isWork = false;
            mAutoFoucsCameraPreview.showCameraPreview();
            mHandler.obtainMessage(DRAW_LINE, new Rect(0, 0, 0, 0)).sendToTarget();
            return;
        }

        //识别成功时处理
        if (!isWork) {
            isWork = true;

            mHandler.obtainMessage(DRAW_LINE, new Rect(idCardImg.left, idCardImg.top, idCardImg.right, idCardImg.bottom)).sendToTarget();
            //存储路径
            String dirpath = Environment.getExternalStorageDirectory() + "/" + "cloudwalk" + "/" + sdf.format(new Date()) + "_idcard/";
            String filepath = dirpath + OutJpgName;
            //创建路径
            FileUtil.mkDir(dirpath);
            //存储
            if (idCardImg.ImgData != null && idCardImg.detect_width > 0 && idCardImg.detect_height > 0) {
                Bitmap bitmap = ImgUtil.byteArrayBGRToBitmap(idCardImg.ImgData, idCardImg.detect_width, idCardImg.detect_height);
                ImgUtil.saveJPGE_After(bitmap, filepath, 95);
            }

            if (ocr_flag == Contants.OCR_FLAG_IDFRONT && idCardImg.flag == ocr_flag) {
                //TODO:身份证头像信息转码，原格式为BGR
                if (idFaceImg != null) {
                    OcrResultActivity.faceBitmap = ImgUtil.byteArrayBGRToBitmap(idFaceImg.FaceImgData, idFaceImg.Idcard_width, idFaceImg.Idcard_height);
                }

                //TODO:身份证识别出的信息转码
                String name = (idCardInfo.name);
                String sex = (idCardInfo.gender);
                String race = (idCardInfo.race);
                String birth = (idCardInfo.birth);
                String address = (idCardInfo.address);
                String id = (idCardInfo.id);

                Intent front = getIntent();
                front.putExtra("name", name);
                front.putExtra("sex", sex);
                front.putExtra("race", race);
                front.putExtra("birth", birth);
                front.putExtra("address", address);
                front.putExtra("id", id);
                front.putExtra(FILEPATH_KEY, filepath);

                setResult(Activity.RESULT_OK, front);// 返回页面
                this.finish();

            } else if (ocr_flag == Contants.OCR_FLAG_IDBACK && idCardImg.flag == ocr_flag) {
                String authority = (idCardInfo.authority);
                String validdate1 = (idCardInfo.validdate1);
                String validdate2 = (idCardInfo.validdate2);

                Intent back = getIntent();
                back.putExtra("authority", authority);
                back.putExtra("validdate1", validdate1);
                back.putExtra("validdate2", validdate2);
                back.putExtra(FILEPATH_KEY, filepath);

                setResult(Activity.RESULT_OK, back);// 返回页面
                this.finish();
            }

            File file = new File(filepath);
            Uri uri = Uri.fromFile(file);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

        }
    }

    public void cwDrawLine(int left, int top, int right, int bottom) {
        maskView.setLine(left, top, right, bottom);
    }

    public void cwDrawProgress() {
        maskView.setDrawProgress();
    }

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == CANCEL_FOCUS) {
                maskView.clearFocus();

            } else if (msg.what == DRAW_LINE) {
                Rect rect = (Rect) msg.obj;
                cwDrawLine(rect.left, rect.top, rect.right, rect.bottom);

            } else if (msg.what == DRAW_PROGRESS) {
                cwDrawProgress();

            }
            super.handleMessage(msg);
        }
    };

    protected void deleteCachedJpg() {
        try {
            String path = Util.getDiskCacheDir(this) + "/" + OutJpgName;
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
