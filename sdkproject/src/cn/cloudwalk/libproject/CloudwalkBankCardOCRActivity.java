package cn.cloudwalk.libproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.cloudwalk.BankOcrSDK;
import cn.cloudwalk.jni.BankCardInfo;
import cn.cloudwalk.jni.callback.BankCardCallback;
import cn.cloudwalk.libproject.camera.AutoFocusCameraPreview;
import cn.cloudwalk.libproject.camera.Delegate;
import cn.cloudwalk.libproject.progressHUD.CwProgressHUD;
import cn.cloudwalk.libproject.util.FileUtil;
import cn.cloudwalk.libproject.util.ImgUtil;
import cn.cloudwalk.libproject.util.Util;
import cn.cloudwalk.libproject.view.OcrMaskView;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class CloudwalkBankCardOCRActivity extends Activity implements Delegate, BankCardCallback {

    private static final String TAG = "CardFrontOCR";

    public CwProgressHUD processDialog;
    private Dialog mDialog;

    private static final int CANCEL_FOCUS = 0, DRAW_LINE = 1;
    AutoFocusCameraPreview mAutoFoucsCameraPreview;
    OcrMaskView maskView;
    ImageView mIv_idrect;
    Bitmap bitmap;//抓拍的卡片bitmap
    int ocr_flag = Contants.OCR_FLAG_BANKCARD;

    public BankOcrSDK bankOcrSDK;
    private BankCardInfo bankCardInfo;
    int initRet = -1;
    String licence;
    volatile boolean isWork;//正在进行处理
    Bitmap bmpCanLine;//扫描线
    Bitmap bmpfocus;
    Bitmap bmpfocused;
    final String OutJpgName = "bankcard.jpg";//保存的图片名
    boolean mAutoRatio;
    int mLastOrientation = SCREEN_ORIENTATION_LANDSCAPE;//默认屏幕方向
    OrientationEventListener mOrientationListener;//方向检测回调Listener
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CANCEL_FOCUS:
                    maskView.clearFocus();
                    break;
                case DRAW_LINE:
                    cwDrawLine();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void cwDrawLine() {
        maskView.setLine(bankCardInfo.left, bankCardInfo.top, bankCardInfo.right, bankCardInfo.bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAutoRatio = getIntent().getBooleanExtra("BANKCARD_AUTO_RATIO", false);//是否支持竖版银行卡识别
        if (!mAutoRatio && (getRequestedOrientation() == SCREEN_ORIENTATION_LANDSCAPE)) {//Manifest中配置横屏,不支持竖卡识别,默认横屏识别横卡
            setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);//不支持竖版银行卡则强制横屏显示
            mLastOrientation = SCREEN_ORIENTATION_LANDSCAPE;
        } else if (mAutoRatio && (getRequestedOrientation() == SCREEN_ORIENTATION_LANDSCAPE)) {//Manifest中配置横屏,支持竖卡识别,默认竖屏识别竖卡,可根据Gsensor调整
            setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
            mLastOrientation = SCREEN_ORIENTATION_PORTRAIT;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cloudwalk_activity_bankocr);
        if (getIntent() != null) {
            licence = getIntent().getStringExtra("LICENCE");
        } else {
            finish();
        }

        initView();
        initSDK();
        Log.i(TAG, "版本信息 APP:" + bankOcrSDK.SDK_APP_VERSION + " 算法:" + bankOcrSDK.SDK_ALGORITHM_VERSION);
        bmpCanLine = BitmapFactory.decodeResource(getResources(), R.drawable.scan_line);
        bmpfocus = BitmapFactory.decodeResource(getResources(), R.drawable.focus);
        bmpfocused = BitmapFactory.decodeResource(getResources(), R.drawable.focused);
        mAutoFoucsCameraPreview.setAutoRatio(mAutoRatio);
        maskView.setAutoRatio(mAutoRatio);


        Point point = getScreenSize();
        mAutoFoucsCameraPreview.setScreenSize(point.x, point.y);
        mAutoFoucsCameraPreview.setFlag(ocr_flag);
        mAutoFoucsCameraPreview.setSizeCallback(new AutoFocusCameraPreview.SizeCallback() {
            @Override
            public void onSizeChange(int width, int height, final int ocrRectW, final int ocrRectH) {
                maskView.setOcr(width, height, ocrRectW, ocrRectH, ocr_flag, bmpCanLine, bmpfocus, bmpfocused);
            }
        });

        deleteCachedJpg();

        processDialog = CwProgressHUD.create(this).setStyle(CwProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在识别中...").setCancellable(true).setAnimationSpeed(2)
                .setCancellable(false).setDimAmount(0.5f);

        //根据GSensor来控制横竖屏切换
        mOrientationListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;  //手机平放时，检测不到有效的角度
                }
                //只检测是否有两个角度的改变
                if (orientation > 350 || orientation < 10) { //0度
                    //竖屏 正向
                    if (mLastOrientation != SCREEN_ORIENTATION_PORTRAIT) {
                        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
                        mLastOrientation = SCREEN_ORIENTATION_PORTRAIT;
                    }
                } else if (orientation > 260 && orientation < 280) { //270度
                    //横屏 正向
                    if (mLastOrientation != SCREEN_ORIENTATION_LANDSCAPE) {
                        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
                        mLastOrientation = SCREEN_ORIENTATION_LANDSCAPE;
                    }
                } else {
                    //do nothing
                    return;
                }
            }
        };


        //启用方向检测
        if (mOrientationListener.canDetectOrientation() && mAutoRatio) {
            mOrientationListener.enable();
        } else {
            mOrientationListener.disable();
        }

    }

    protected Point getScreenSize() {
        int realWidth = 0, realHeight = 0;
        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        realWidth = metrics.widthPixels;
        realHeight = metrics.heightPixels;
        try {
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Point(realWidth, realHeight);
    }


    /**
     * 注册接口
     */
    private void initCallback() {
        bankOcrSDK.cwBankCardCallback(this);
        mAutoFoucsCameraPreview.setDelegate(this);
    }

    /**
     * 初始化SDK
     */
    private void initSDK() {
        bankOcrSDK = BankOcrSDK.getInstance(this);
        if (0 != initRet)
            initRet = bankOcrSDK.cwCreateCardHandle(this, licence);
        Log.i("initret", initRet + "");
        if (initRet != 0) {
            mDialog = new AlertDialog.Builder(this).setMessage("初始化失败，授权码无效")
                    .setNegativeButton("确定", new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                        }
                    }).show();
        }
    }

    /**
     * 初始化相关参数
     */
    private void initView() {
        mAutoFoucsCameraPreview = (AutoFocusCameraPreview) findViewById(R.id.CameraPreview);
        maskView = (OcrMaskView) findViewById(R.id.maskView);
        mIv_idrect = (ImageView) findViewById(R.id.iv_idrect);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCallback();
        mAutoFoucsCameraPreview.cwStartCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bitmap = null;
        isWork = false;
        mAutoFoucsCameraPreview.cwStopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
        bankOcrSDK.cwDestroyCardHandle();
        if (bmpCanLine != null && !bmpCanLine.isRecycled()) {
            bmpCanLine.recycle();
        }
        if (bmpfocus != null && !bmpfocus.isRecycled()) {
            bmpfocus.recycle();
        }
        if (bmpfocused != null && !bmpfocused.isRecycled()) {
            bmpfocused.recycle();
        }
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onOpenCameraError() {

    }

    @Override
    public void onFocus(float x, float y) {
        maskView.setFocus(x, y);

    }

    @Override
    public void onFocused() {
        maskView.setFocused();
        mHandler.sendEmptyMessageDelayed(CANCEL_FOCUS, 150);
    }

    @Override
    public void BankCardInfo(BankCardInfo bankCardInfo) {
        this.bankCardInfo = bankCardInfo;//返回扫描时,卡片对齐信息，如他的四个角的坐标信息
        mHandler.sendEmptyMessage(DRAW_LINE);
    }

    @Override
    public void BankCardData(BankCardInfo bankCardInfo) {//这里的bankCardInfo多返回银行卡抓拍图片信息，以及离线识别银行卡信息
        mHandler.removeCallbacksAndMessages(null);
        if (bankCardInfo != null) {
            String cardNum = bankCardInfo.cardNum;
            String bankName = bankCardInfo.bankName;
            String cardName = bankCardInfo.cardName;
            String cardType = bankCardInfo.cardType;

            //图片质量不高或卡片不支持时,处理unknown情况
            if (bankName.equals("unknown")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (processDialog != null && processDialog.isShowing()) {
                            processDialog.dismiss();
                        }
                        mAutoFoucsCameraPreview.showCameraPreview();
                        isWork = false;
                    }
                });
                return;
            }
            //bgr字节数组保存成图片,在内存不足的情况下,可能会返回空
            bitmap = ImgUtil.byteArrayBGRToBitmap(bankCardInfo.jpgdata, bankCardInfo.width, bankCardInfo.height);
            //存储路径
            String dirpath = Environment.getExternalStorageDirectory() + "/" + "cloudwalk" + "/" + sdf.format(new Date()) + "_bankcard/";
            String path = dirpath + OutJpgName;
            //创建路径
            FileUtil.mkDir(dirpath);
            if (bitmap != null) {
                ImgUtil.saveJPGE_After(bitmap, path, 100);
                Log.i("here", "hereocr");
                finish();
                Bulider.mBankOcrResultCallback.onBankOcrDetFinished(bankCardInfo, path);
//                Intent mIntent = new Intent(CloudwalkBankCardOCRActivity.this, BankCardResultActivity.class);
//                mIntent.putExtra("cardNum", cardNum);
//                mIntent.putExtra("bankName", bankName);
//                mIntent.putExtra("cardName", cardName);
//                mIntent.putExtra("cardType", cardType);
//                mIntent.putExtra("cardPath", path);
//
//                startActivity(mIntent);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (processDialog != null && processDialog.isShowing()) {
                            processDialog.dismiss();
                        }
                        mAutoFoucsCameraPreview.showCameraPreview();
                        isWork = false;
                    }
                });
            }

            File file = new File(path);
            Uri uri = Uri.fromFile(file);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

        } else {
            //识别失败了
            //finish();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (processDialog != null && processDialog.isShowing()) {
                        processDialog.dismiss();
                    }
                    mAutoFoucsCameraPreview.showCameraPreview();
                    isWork = false;
                }
            });
        }
    }

    /**
     * 银行卡开始识别回调
     */
    @Override
    public void BankCardRecognizing() {
        if (!isWork) {//是否正在进行处理
            deleteCachedJpg();
            isWork = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (processDialog != null && !processDialog.isShowing()) {
                        processDialog.setLabel(getString(R.string.bank_loading)).show();
                    }
                    mAutoFoucsCameraPreview.stopCameraPreview();
                }
            });
        }
    }

    //重写onConfiguratonChanged,避免设置屏幕方向引起Activity重新创建而出现横竖屏切换时黑屏
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int type = newConfig.orientation;
        if (type == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mLastOrientation = SCREEN_ORIENTATION_LANDSCAPE;
            //横屏
        } else if (type == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            //竖屏
            mLastOrientation = SCREEN_ORIENTATION_PORTRAIT;
        }
        Log.i("jinwei", "newConfig" + type);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /**
     * 清除可能已存储的银行卡图片
     */
    protected void deleteCachedJpg() {
        try {
            String path = Util.getDiskCacheDir(CloudwalkBankCardOCRActivity.this) + "/" + OutJpgName;
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
