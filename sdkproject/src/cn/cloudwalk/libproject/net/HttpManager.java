/**
 * Project Name:cwFaceForDev3
 * File Name:HttpManager.java
 * Package Name:cn.cloudwalk.dev.mobilebank.util
 * Date:2016-5-10 11:55:20
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */

package cn.cloudwalk.libproject.net;

import android.os.AsyncTask;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.cloudwalk.libproject.util.Base64Util;
import cn.cloudwalk.libproject.util.LogUtils;

/**
 * ClassName: OkHttpManager <br/>
 * Description:<br/>
 * date: 2016-5-10 11:55:20 <br/>
 *
 * @author 284891377
 * @version
 * @since JDK 1.7
 */
public class HttpManager {
    private static final String TAG = LogUtils.makeLogTag("HttpManager");

    public static void postAsync(final String url, final List<BasicNameValuePair> pairs,
                                 final DataCallBack dataCallBack) {
        new AsyncTask<Object, Object, String>() {
            @Override
            protected String doInBackground(Object... param) {
                String result = null;
                try {
                    result = ApacheHttpUtil.post(url, pairs);
                    pairs.clear();
                } catch (OutOfMemoryError e) {

                    e.printStackTrace();
                }
                return result;
            }

            protected void onPostExecute(String result) {

                try {
                    JSONObject jb = new JSONObject(result);
                    if (jb.optInt("result") == 0) {
                        dataCallBack.requestSucess(jb);
                    } else {
                        String errorMsg = jb.optString("info");
                        dataCallBack.requestFailure("错误码:" + jb.optInt("result") + " 错误信息:" +
                                errorMsg);
                    }
                } catch (Exception e) {

                    dataCallBack.requestFailure("网络异常,请检查网络!");
                    e.printStackTrace();

                }

            }

            ;
        }.execute("");
    }

    /****************** 数据回掉接口 ****************************/
    public interface DataCallBack {
        void requestFailure(String errorMsg);

        void requestSucess(JSONObject jb);
    }

    /****************** api ****************************/
    //后端活体POC
    public static void cwFaceSerLivess(String ipStr,String app_id, String app_secret,String faceInfo,DataCallBack dataCallBack){
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("app_id", app_id));
        pairs.add(new BasicNameValuePair("app_secret", app_secret));
        pairs.add(new BasicNameValuePair("param", faceInfo));

        postAsync(ipStr + "/faceliveness", pairs, dataCallBack);
    }

    public static void cwFaceComper(String ipStr, String app_id, String app_secret, String
			imgABase64,
                                    String imgBBase64, DataCallBack dataCallBack) {
        try {
            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("app_id", app_id));
            pairs.add(new BasicNameValuePair("app_secret", app_secret));
            pairs.add(new BasicNameValuePair("imgA", imgABase64));
            pairs.add(new BasicNameValuePair("imgB", imgBBase64));
            imgABase64 = null;
            imgBBase64 = null;
            postAsync(ipStr + "/face/tool/compare", pairs, dataCallBack);
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }

    }

    public static void cwIDOcr(String ipStr, String app_id, String app_secret, String imgBase64,
							   int getFace,
                               DataCallBack dataCallBack) {
        try {
            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("app_id", app_id));
            pairs.add(new BasicNameValuePair("app_secret", app_secret));
            pairs.add(new BasicNameValuePair("img", imgBase64));
            pairs.add(new BasicNameValuePair("getFace", getFace + ""));
            imgBase64 = null;
            postAsync(ipStr + "/ocr", pairs, dataCallBack);
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }

    }

    public static void cwBankOcr(String ipStr, String app_id, String app_secret,
                                 byte[] imgAData, DataCallBack dataCallBack) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("app_id", app_id));
        pairs.add(new BasicNameValuePair("app_secret", app_secret));
        pairs.add(new BasicNameValuePair("img", Base64Util.encode(imgAData)));

        postAsync(ipStr + "/ocr/bankcard", pairs, dataCallBack);
    }

    public static void cwMaskAddImg(String ipStr, String app_id, String app_secret,
                                    byte[] imgAData, byte[] imgMaskData, DataCallBack
                                            dataCallBack) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("app_id", app_id));
        pairs.add(new BasicNameValuePair("app_secret", app_secret));
        pairs.add(new BasicNameValuePair("imgSrc", Base64Util.encode(imgAData)));
        pairs.add(new BasicNameValuePair("imgMask", Base64Util.encode(imgMaskData)));

        postAsync(ipStr + "/tool/digitalwater/addImage", pairs, dataCallBack);
    }

    public static void cwMaskDelImg(String ipStr, String app_id, String app_secret,
                                    byte[] imgAData, byte[] imgMaskData, DataCallBack
                                            dataCallBack) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("app_id", app_id));
        pairs.add(new BasicNameValuePair("app_secret", app_secret));
        pairs.add(new BasicNameValuePair("imgSrc", Base64Util.encode(imgMaskData)));
        pairs.add(new BasicNameValuePair("imgMask", Base64Util.encode(imgMaskData)));

        postAsync(ipStr + "/tool/digitalwater/removeImage", pairs, dataCallBack);
    }

    public static void cwMaskDetectImg(String ipStr, String app_id, String app_secret,
                                       byte[] imgAData, byte[] imgMaskData, DataCallBack
                                               dataCallBack) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("app_id", app_id));
        pairs.add(new BasicNameValuePair("app_secret", app_secret));
        pairs.add(new BasicNameValuePair("imgSrc", Base64Util.encode(imgMaskData)));
        pairs.add(new BasicNameValuePair("imgMask", Base64Util.encode(imgMaskData)));

        postAsync(ipStr + "/tool/digitalwater/detectImage", pairs, dataCallBack);
    }

    public static void cwMaskAddStr(String ipStr, String app_id, String app_secret,
                                    byte[] imgAData, String strMsk, DataCallBack dataCallBack) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("app_id", app_id));
        pairs.add(new BasicNameValuePair("app_secret", app_secret));
        pairs.add(new BasicNameValuePair("imgSrc", Base64Util.encode(imgAData)));
        pairs.add(new BasicNameValuePair("strMask", strMsk));

        postAsync(ipStr + "/tool/digitalwater/addString", pairs, dataCallBack);
    }

    public static void cwMaskDelStr(String ipStr, String app_id, String app_secret,
                                    byte[] imgAData, String strMsk, DataCallBack dataCallBack) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("app_id", app_id));
        pairs.add(new BasicNameValuePair("app_secret", app_secret));
        pairs.add(new BasicNameValuePair("imgSrc", Base64Util.encode(imgAData)));
        pairs.add(new BasicNameValuePair("strMask", strMsk));

        postAsync(ipStr + "/tool/digitalwater/removeString", pairs, dataCallBack);
    }

    public static void cwMaskDetectStr(String ipStr, String app_id, String app_secret,
                                       byte[] imgAData, String strMsk, DataCallBack dataCallBack) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("app_id", app_id));
        pairs.add(new BasicNameValuePair("app_secret", app_secret));
        pairs.add(new BasicNameValuePair("imgSrc", Base64Util.encode(imgAData)));
        pairs.add(new BasicNameValuePair("strMask", strMsk));

        postAsync(ipStr + "/tool/digitalwater/detectString", pairs, dataCallBack);
    }
    public static void cwCreateGroup(String ipStr, String app_id, String app_secret, String
			groupId, String tag,
                                     DataCallBack dataCallBack) {
        try {
            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("app_id", app_id));
            pairs.add(new BasicNameValuePair("app_secret", app_secret));
            pairs.add(new BasicNameValuePair("groupId", groupId));
            pairs.add(new BasicNameValuePair("tag", tag));

            postAsync(ipStr + "/face/clustering/group/create", pairs, dataCallBack);
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }
    }

    public static void cwDelGroup(String ipStr, String app_id, String app_secret, String groupId,
								  String tag,
                                  DataCallBack dataCallBack) {
        try {
            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("app_id", app_id));
            pairs.add(new BasicNameValuePair("app_secret", app_secret));
            pairs.add(new BasicNameValuePair("groupId", groupId));

            postAsync(ipStr + "/face/clustering/group/delete", pairs, dataCallBack);
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
    }

    public static void cwCreateFace(String ipStr, String app_id, String app_secret, String
			faceId, String groupId, String imgBase64,
                                    String tag, DataCallBack dataCallBack) {
        try {
            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("app_id", app_id));
            pairs.add(new BasicNameValuePair("app_secret", app_secret));
            pairs.add(new BasicNameValuePair("img", imgBase64));
            pairs.add(new BasicNameValuePair("faceId", faceId));
            pairs.add(new BasicNameValuePair("groupId", groupId));
            pairs.add(new BasicNameValuePair("tag", tag));
            imgBase64 = null;
            postAsync(ipStr + "/face/clustering/face/create", pairs, dataCallBack);
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }

    }

    public static void cwAddFaceToGroup(String ipStr, String app_id, String app_secret, String
			faceId, String groupId,
                                        DataCallBack dataCallBack) {
        try {
            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("app_id", app_id));
            pairs.add(new BasicNameValuePair("app_secret", app_secret));
            pairs.add(new BasicNameValuePair("faceId", faceId));
            pairs.add(new BasicNameValuePair("groupId", groupId));

            postAsync(ipStr + "/face/clustering/group/addFace", pairs, dataCallBack);
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }
    }

    public static void cwDelFaceToGroup(String ipStr, String app_id, String app_secret, String
			faceId, String groupId,
                                        DataCallBack dataCallBack) {
        try {
            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("app_id", app_id));
            pairs.add(new BasicNameValuePair("app_secret", app_secret));
            pairs.add(new BasicNameValuePair("faceId", faceId));
            pairs.add(new BasicNameValuePair("groupId", groupId));

            postAsync(ipStr + "/face/clustering/group/removeFace", pairs, dataCallBack);
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }
    }

    public static void cwFaceRecg(String ipStr, String app_id, String app_secret, String groupId, String imgBase64,
                                  int topN, DataCallBack dataCallBack) {
        try {
            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("app_id", app_id));
            pairs.add(new BasicNameValuePair("app_secret", app_secret));
            pairs.add(new BasicNameValuePair("img", imgBase64));
            pairs.add(new BasicNameValuePair("groupId", groupId));
            pairs.add(new BasicNameValuePair("topN", topN + ""));
            imgBase64 = null;
            postAsync(ipStr + "/face/recog/group/identify", pairs, dataCallBack);
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }
    }

    public static void cwFaceAttribute(String ipStr, String app_id, String app_secret, String imgBase64,
                                       DataCallBack dataCallBack) {
        try {
            List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
            pairs.add(new BasicNameValuePair("app_id", app_id));
            pairs.add(new BasicNameValuePair("app_secret", app_secret));
            pairs.add(new BasicNameValuePair("img", imgBase64));
            imgBase64 = null;
            postAsync(ipStr + "/face/tool/attribute", pairs, dataCallBack);
        } catch (OutOfMemoryError e) {

            e.printStackTrace();
        }

    }

}
