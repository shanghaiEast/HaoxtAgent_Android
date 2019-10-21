//package cn.cloudwalk.libproject.camera;
//
//import android.annotation.TargetApi;
//import android.content.Context;
//import android.content.res.Configuration;
//import android.graphics.ImageFormat;
//import android.graphics.Matrix;
//import android.graphics.RectF;
//import android.graphics.SurfaceTexture;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraCaptureSession;
//import android.hardware.camera2.CameraCharacteristics;
//import android.hardware.camera2.CameraDevice;
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.CameraMetadata;
//import android.hardware.camera2.CaptureRequest;
//import android.hardware.camera2.params.StreamConfigurationMap;
//import android.media.Image;
//import android.media.ImageReader;
//import android.media.MediaRecorder;
//import android.os.Build;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.util.AttributeSet;
//import android.util.Size;
//import android.view.Surface;
//import android.view.TextureView;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.TimeUnit;
//
//import cn.cloudwalk.CloudwalkSDK;
//import cn.cloudwalk.FaceInterface;
//import cn.cloudwalk.libproject.Bulider;
//import cn.cloudwalk.libproject.Contants;
//import cn.cloudwalk.libproject.util.ImgUtil;
//
///**
// * Created by Mr.Rain on 2017/3/28.
// *
// * @author Rain
// */
//
//@TargetApi(Build.VERSION_CODES.LOLLIPOP)
//public class CameraTextureView extends TextureView implements TextureView.SurfaceTextureListener {
//
//    Context context;
//    private Size mPreviewSize;
//    private Size mVideoSize;
//    private ImageReader mImageReader;
//    private MediaRecorder mMediaRecorder;
//    private int orientation;
//    private Handler mHandler;
//    private HandlerThread mBackgroundThread;
//    private CameraDevice mCameraDevice;
//    private CameraCaptureSession mPreviewSession;
//    private Semaphore mCameraOpenCloseLock = new Semaphore(1);
//    private String cameraId;
//    private boolean mPreviewing = true;
//    private CaptureRequest.Builder mPreviewBuilder;
//
//    public CameraTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.context = context;
//    }
//
//    public CameraTextureView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CameraTextureView(Context context) {
//        this(context, null);
//    }
//
//    public void cwStartCamera() {
//
//        startBackgroundThread();
//        if (mPreviewing) {
//            requestLayout();
//        }
//        if (this.isAvailable()) {
//            openCamera(getWidth(), getHeight());
//        } else {
//            this.setSurfaceTextureListener(this);
//        }
//    }
//
//    /**
//     * 开启子线程
//     */
//    private void startBackgroundThread() {
//        mBackgroundThread = new HandlerThread("CameraBackground");
//        mBackgroundThread.start();
//        mHandler = new Handler(mBackgroundThread.getLooper());
//    }
//
//    /**
//     * 停止子线程
//     */
//    private void stopBackgroundThread() {
//        mBackgroundThread.quitSafely();
//        try {
//            mBackgroundThread.join();
//            mBackgroundThread = null;
//            mHandler = null;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void cwStopCamera() {
//        closeCamera();
//        stopBackgroundThread();
//    }
//
//    /**
//     * 设置屏幕方向
//     *
//     * @param orientation Configuration.ORIENTATION_LANDSCAPE 或者
//     *                    Configuration.ORIENTATION_PORTRAIT
//     */
//    public void setScreenOrientation(int orientation) {
//        this.orientation = orientation;
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        openCamera(width, height);
//    }
//
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//        if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
//            configureTransform(width, height);
//        }
//    }
//
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        return true;
//    }
//
//    private void openCamera(int width, int height) {
//        //获得所有摄像头的管理者CameraManager
//        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
//        try {
//            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
//                throw new RuntimeException("Time out waiting to lock camera opening.");
//            }
//            if (!isSwitch) {
//                try {
//                    cameraId = cameraManager.getCameraIdList()[1];//0是后置，1是前置
//                } catch (Exception e) {
//                    cameraId = cameraManager.getCameraIdList()[0];//0是后置，1是前置
//                }
//            } else {
//                cameraId = cameraManager.getCameraIdList()[0];//0是后置，1是前置
//            }
//            //获得某个摄像头的特征，支持的参数
//            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics
//                    (cameraId);
//            int level = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
//            //支持的STREAM CONFIGURATION
//            StreamConfigurationMap map = characteristics.get(CameraCharacteristics
//                    .SCALER_STREAM_CONFIGURATION_MAP);
//            //摄像头支持的预览Size数组
//            mVideoSize = CameraConfigurationManager.chooseVideoSize(map.getOutputSizes
//                    (MediaRecorder.class));
//            mPreviewSize = CameraConfigurationManager.chooseOptimalSize(map.getOutputSizes
//                    (SurfaceTexture.class), Contants.PREVIEW_W, Contants.PREVIEW_H, mVideoSize);
//            if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
//                configureTransform(width, height);
//            }
//            //打开相机
//            cameraManager.openCamera(cameraId, mCameraDeviceStateCallback, null);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 翻转变化
//     *
//     * @param viewWidth
//     * @param viewHeight
//     */
//    private void configureTransform(int viewWidth, int viewHeight) {
//        Matrix matrix = new Matrix();
//        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
//        RectF bufferRect = new RectF(0, 0, Contants.PREVIEW_H, Contants.PREVIEW_W);
//        float centerX = viewRect.centerX();
//        float centerY = viewRect.centerY();
//        bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
//        matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
//        float scale = Math.max(
//                (float) viewHeight / Contants.PREVIEW_H,
//                (float) viewWidth / Contants.PREVIEW_W);
//        matrix.postScale(scale, scale, centerX, centerY);
//        matrix.postRotate(270, centerX, centerY);
//        this.setTransform(matrix);
//    }
//
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//    }
//
//    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice
//            .StateCallback() {
//
//        @Override
//        public void onOpened(CameraDevice camera) {
//            mCameraDevice = camera;
//            long time = System.currentTimeMillis();
//            startPreview();
//            //Log.i("Time", "预览时间:" + (System.currentTimeMillis() - time));
//            mCameraOpenCloseLock.release();
//        }
//
//        @Override
//        public void onDisconnected(CameraDevice camera) {
//            mCameraOpenCloseLock.release();
//            camera.close();
//            mCameraDevice = null;
//        }
//
//        @Override
//        public void onError(CameraDevice camera, int error) {
//            mCameraOpenCloseLock.release();
//            camera.close();
//            mCameraDevice = null;
//        }
//    };
//
//    private void updatePreview() {
//        if (null == mCameraDevice) {
//            return;
//        }
//        try {
//            setUpCaptureRequestBuilder(mPreviewBuilder);
//            mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, mHandler);
//            //成功配置后，便开始进行相机图像的监听
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 设置相关的模式等
//     *
//     * @param builder
//     */
//    private void setUpCaptureRequestBuilder(CaptureRequest.Builder builder) {
//        builder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
//    }
//
//    private void startPreview() {
//        if (mCameraDevice == null) {
//            return;
//        }
//        try {
//            long time = System.currentTimeMillis();
//            closePreviewSession();
//            setRecord();
//            setImageReader();
//            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
//            List<Surface> surfaces = new ArrayList<>();
//            SurfaceTexture texture = this.getSurfaceTexture();
//            assert texture != null;
//            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
//            Surface textureSurface = new Surface(texture);
//            Surface recorderSurface = mMediaRecorder.getSurface();
//            Surface imageSurface = mImageReader.getSurface();
//            surfaces.add(textureSurface);
//            surfaces.add(recorderSurface);
//            surfaces.add(imageSurface);
//            mPreviewBuilder.addTarget(textureSurface);
//            mPreviewBuilder.addTarget(recorderSurface);
//            mPreviewBuilder.addTarget(imageSurface);
//            //Log.i("Time", "预览设置时间:" + (System.currentTimeMillis() - time));
//
//            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
//                @Override
//                public void onConfigured(CameraCaptureSession session) {
//                    mPreviewSession = session;
//                    updatePreview();
//                    try {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mMediaRecorder.start();
//                            }
//                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onConfigureFailed(CameraCaptureSession session) {
//
//                }
//            }, mHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void closePreviewSession() throws CameraAccessException {
//        if (mPreviewSession != null && mCameraDevice != null) {
////            mPreviewSession.stopRepeating();
////            mPreviewSession.abortCaptures();
//            mPreviewSession.close();
//            mPreviewSession = null;
//        }
//    }
//
//    private void setImageReader() {
//        mImageReader = ImageReader.newInstance(mPreviewSize.getWidth(), mPreviewSize.getHeight(),
//                ImageFormat.YUV_420_888, 10);
//        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
//            @Override
//            public void onImageAvailable(ImageReader reader) {
//                Image image = reader.acquireNextImage();
//                byte[] NV21_data = ImgUtil.getDataFromImage(image, ImgUtil.COLOR_FormatNV21);
//                if (!isSwitch) {
//                    if (Configuration.ORIENTATION_PORTRAIT == orientation) {
//                        CloudwalkSDK.getInstance().cwPushFrame(NV21_data, Contants.PREVIEW_W,
//                                Contants.PREVIEW_H, FaceInterface.ImageForm.CW_IMG_NV21,
//                                FaceInterface.CaremaType.FRONT_PORTRAIT);
//                    } else {// 横屏 水平镜像
//                        CloudwalkSDK.getInstance().cwPushFrame(NV21_data, Contants.PREVIEW_W,
//                                Contants.PREVIEW_H, FaceInterface.ImageForm.CW_IMG_NV21,
//                                FaceInterface.CaremaType.FRONT_LANDSCAPE);
//                    }
//                } else {
//                    if (Configuration.ORIENTATION_PORTRAIT == orientation) {// 竖屏 旋转90
//                        CloudwalkSDK.getInstance().cwPushFrame(NV21_data, Contants.PREVIEW_W,
//                                Contants.PREVIEW_H, FaceInterface.ImageForm.CW_IMG_NV21,
//                                FaceInterface.CaremaType.BACK_PORTRAIT);
//
//                    } else {// 横屏不做处理
//                        CloudwalkSDK.getInstance().cwPushFrame(NV21_data, Contants.PREVIEW_W,
//                                Contants.PREVIEW_H, FaceInterface.ImageForm.CW_IMG_NV21,
//                                FaceInterface.CaremaType.BACK_LANDSCAPE);
//                    }
//                }
//                image.close();
//            }
//        }, mHandler);
//    }
//
//    public void setRecord() throws IOException {
//
//        mMediaRecorder = new MediaRecorder();
//        if (!isSwitch) {
//            mMediaRecorder.setOrientationHint(270);
//        } else {
//            mMediaRecorder.setOrientationHint(90);
//        }
//
//        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置从麦克风采集声音
//        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE); // 设置从摄像头采集图像
//        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);  // 设置视频的输出格式 为MP4
//        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264); // 设置视频的编码格式
//        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//设置音频的输出格式
//        mMediaRecorder.setVideoSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());//设置输出的视频尺寸
//        mMediaRecorder.setVideoEncodingBitRate(2 * 640 * 480);// 设置帧频率
//        mMediaRecorder.setVideoFrameRate(30); //设置帧率
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        String path = Bulider.publicFilePath + File.separator + "Android_" + format.format(new
//                Date(System.currentTimeMillis())) + ".mp4";
//        mMediaRecorder.setOutputFile(path);
//        mMediaRecorder.prepare();
//    }
//
//    private void closeCamera() {
//        try {
//            mPreviewing = false;
//            mCameraOpenCloseLock.acquire();
//            closePreviewSession();
//            if (null != mMediaRecorder) {
//                mMediaRecorder.stop();
//                mMediaRecorder.reset();
//                mMediaRecorder.release();
//                mMediaRecorder = null;
//            }
//            if (mImageReader != null) {
//                mImageReader.close();
//                mImageReader = null;
//            }
//            if (null != mCameraDevice) {
//                mCameraDevice.close();
//                mCameraDevice = null;
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException("Interrupted while trying to lock camera closing.");
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        } finally {
//            mCameraOpenCloseLock.release();
//        }
//    }
//
//
//    boolean isSwitch;
//
//    /**
//     * 切换摄像头
//     */
//    public void switchCarema() {
//        cwStopCamera();
//        if (!isSwitch) {
//            isSwitch = true;
//        } else {
//            isSwitch = false;
//        }
//        cwStartCamera();
//    }
//
//    static class CompareSizesByArea implements Comparator<Size> {
//
//        @Override
//        public int compare(Size lhs, Size rhs) {
//            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
//                    (long) rhs.getWidth() * rhs.getHeight());
//        }
//
//    }
//}
