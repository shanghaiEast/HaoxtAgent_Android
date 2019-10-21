package cn.cloudwalk.libproject.callback;


import cn.cloudwalk.jni.BankCardInfo;

public interface BankOcrResultCallback {

    void onBankOcrDetFinished(BankCardInfo bankCardInfo, String path);
}
