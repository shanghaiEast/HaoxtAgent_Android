//package cn.cloudwalk.libproject.camera;
//
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.hardware.Camera;
//import android.hardware.Camera.PreviewCallback;
//import android.hardware.Camera.Size;
//import android.util.AttributeSet;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//import cn.cloudwalk.IDCardSDK;
//import cn.cloudwalk.libproject.util.LogUtils;
//
///**
// * 自动聚焦并预览回调</br>
// * 每隔1500ms调用mCamera.autoFocus并且回调视频帧
// *
// * @author yusr
// */
//public class OneShotCameraPreview extends SurfaceView implements SurfaceHolder.Callback,
//        PreviewCallback {
//
//    private static final String TAG = LogUtils.makeLogTag("OneShotCameraPreview");
//    private Camera mCamera;
//    int caremaId = Camera.CameraInfo.CAMERA_FACING_BACK;
//    public static final int COLORTYPE_BGR = 1, COLORTYPE_BGRA = 2, COLORTYPE_YUV = 3,
//            COLORTYPE_NV21 = 4, COLORTYPE_NV12 = 5;
//    private boolean mPreviewing = true;
//    private boolean mAutoFocus = true;
//    private boolean mSurfaceCreated = false;
//
//    Context context;
//    private CameraConfigurationManager mCameraConfigurationManager;
//
//    public OneShotCameraPreview(Context context) {
//        this(context, null);
//    }
//
//    public OneShotCameraPreview(Context context, AttributeSet attrs, int defStyle) {
//
//        super(context, attrs, defStyle);
//        this.context = context;
//        this.setKeepScreenOn(true);//保持屏幕常亮
//    }
//
//    public OneShotCameraPreview(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public void setCamera(Camera camera) {
//        mCamera = camera;
//        if (mCamera != null) {
//            mCameraConfigurationManager = new CameraConfigurationManager(getContext());
//            mCameraConfigurationManager.initFromCameraParameters(mCamera);
//
//            getHolder().addCallback(this);
//            if (mPreviewing) {
//                requestLayout();
//            } else {
//                showCameraPreview();
//            }
//        }
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder surfaceHolder) {
//        mSurfaceCreated = true;
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
//        if (surfaceHolder.getSurface() == null) {
//            return;
//        }
//        stopCameraPreview();
//        showCameraPreview();
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//        mSurfaceCreated = false;
//        stopCameraPreview();
//    }
//
//    public void showCameraPreview() {
//        if (mCamera != null) {
//            try {
//                mPreviewing = true;
//                mCamera.setPreviewDisplay(getHolder());
//
//                mCameraConfigurationManager.setDesiredCameraParameters(mCamera, caremaId);
//                mCamera.startPreview();
//                if (mAutoFocus) {
//                    mAutoFocus = false;
//                    autoFocus();
//                }
//            } catch (Exception e) {
//                LogUtils.LOGE(TAG, e.toString());
//            }
//        }
//    }
//
//    private void autoFocus() {
//
//        try {
//            if (mCamera != null)
//                mCamera.autoFocus(autoFocusCB);
//        } catch (Exception e) {
//            // 聚焦失败
//            e.printStackTrace();
//        }
//    }
//
//    public void stopCameraPreview() {
//        if (mCamera != null) {
//            try {
//                removeCallbacks(doAutoFocus);
//                closeOneShotPreview();
//                mAutoFocus = true;
//                mPreviewing = false;
//                mCamera.cancelAutoFocus();
//                mCamera.stopPreview();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void openFlashlight() {
//        if (flashLightAvaliable()) {
//            mCameraConfigurationManager.openFlashlight(mCamera);
//        }
//    }
//
//    public void closeFlashlight() {
//        if (flashLightAvaliable()) {
//            mCameraConfigurationManager.closeFlashlight(mCamera);
//        }
//    }
//
//    private boolean flashLightAvaliable() {
//        return mCamera != null && mPreviewing && mSurfaceCreated
//                && getContext().getPackageManager().hasSystemFeature(PackageManager
//                .FEATURE_CAMERA_FLASH);
//    }
//
//    private Runnable doAutoFocus = new Runnable() {
//        public void run() {
//            if (mCamera != null && mPreviewing && mSurfaceCreated) {
//                LogUtils.LOGE(TAG, "doAutoFocus");
//                mCamera.autoFocus(autoFocusCB);
//            }
//        }
//    };
//
//    public void openOneShotPreview() {
//        if (mCamera != null) {
//            mCamera.setOneShotPreviewCallback(this);
//        }
//    }
//
//    public void closeOneShotPreview() {
//        if (mCamera != null) {
//            mCamera.setOneShotPreviewCallback(null);
//        }
//    }
//
//    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
//        public void onAutoFocus(boolean success, Camera camera) {
//            if (success) {
//                postDelayed(doAutoFocus, 1500);
//                openOneShotPreview();
//            }
//        }
//    };
//
//    /******************************************************************/
//    public Size getPreviewSize() {
//        Camera.Parameters parameters = mCamera.getParameters();
//        return parameters.getPreviewSize();
//    }
//
//    Delegate mDelegate;
//
//    public void setDelegate(Delegate mDelegate) {
//        this.mDelegate = mDelegate;
//    }
//
//    /**
//     * 打开摄像头开始预览，但是并未开始识别
//     */
//    public void cwStartCamera() {
//        if (mCamera != null) {
//            return;
//        }
//
//        try {
//            mCamera = Camera.open(caremaId);
//        } catch (Exception e) {
//            if (mDelegate != null) {
//                mDelegate.onOpenCameraError();
//            }
//        }
//        setCamera(mCamera);
//    }
//
//    /**
//     * 关闭摄像头预览，并且隐藏扫描框
//     */
//    public void cwStopCamera() {
//        if (mCamera != null) {
//            stopCameraPreview();
//
//            mCamera.release();
//            mCamera = null;
//        }
//    }
//
//    @Override
//    public void onPreviewFrame(byte[] data, Camera camera) {
//        Camera.Size size = getPreviewSize();
//        int width = size.width;
//        int height = size.height;
//        IDCardSDK.getInstance(context).cwPushFrame(data, width, height, COLORTYPE_NV21);
//
//    }
//}