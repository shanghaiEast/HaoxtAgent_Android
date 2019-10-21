package cn.cloudwalk.libproject.callback;


import org.json.JSONObject;

import cn.cloudwalk.jni.BankCardInfo;

public interface IDCardOcrResultCallback {

    void onIDCardOcrDetFinished(JSONObject frontJb, JSONObject backJb);
}
