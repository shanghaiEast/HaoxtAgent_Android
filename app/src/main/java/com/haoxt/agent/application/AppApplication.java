/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.haoxt.agent.application;

import com.haoxt.agent.manager.DataManager;
import com.haoxt.agent.model.User;
import com.haoxt.agent.service.LocationService;

import tft.mpos.library.base.BaseApplication;
import tft.mpos.library.util.StringUtil;


import android.os.Vibrator;
import android.util.Log;

/**
 * Application
 *
 * @author baowen
 */
public class AppApplication extends BaseApplication {
    public static final String BUNDATA = "bundata";
    private static final String TAG = "DemoApplication";
    private static AppApplication context;
    public static AppApplication getInstance() {
        return context;
    }

    public LocationService locationService;
    public Vibrator mVibrator;
    public String token;
    public String merchantId;
    public String userno;
    public String snNo;
    public String bluetooth;
    public String mac;
    public String userStatus;                   //用户状态      0-不正常 1-正常
    public String realNameStatus;               //实名认证状态  0-未认证 1-已认证
    public String userStlStatus;                //结算卡状态    0-无结算信息 1-已有结算信息
    public String userTermStatus;               //终端认证状态  0-未绑定 1-已绑定
    public String userCreditCardStatus;         //信用卡认证状态



//    暂时用的临时变量,后期接口调通后按实际情况来
    public boolean isLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

//        locationService = new LocationService(getApplicationContext());
//        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());

    }


    /**
     * 获取当前用户id
     *
     * @return
     */
    public long getCurrentUserId() {
        currentUser = getCurrentUser();
        Log.d(TAG, "getCurrentUserId  currentUserId = " + (currentUser == null ? "null" : currentUser.getId()));
        return currentUser == null ? 0 : currentUser.getId();
    }

    /**
     * 获取当前用户phone
     *
     * @return
     */
    public String getCurrentUserPhone() {
        currentUser = getCurrentUser();
        return currentUser == null ? null : currentUser.getPhone();
    }


    private static User currentUser = null;

    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = DataManager.getInstance().getCurrentUser();
        }
        return currentUser;
    }

    public void saveCurrentUser(User user) {
        if (user == null) {
            Log.e(TAG, "saveCurrentUser  currentUser == null >> return;");
            return;
        }
        if (user.getId() <= 0 && StringUtil.isNotEmpty(user.getName(), true) == false) {
            Log.e(TAG, "saveCurrentUser  user.getId() <= 0" +
                    " && StringUtil.isNotEmpty(user.getName(), true) == false >> return;");
            return;
        }

        currentUser = user;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }

    public void logout() {
        currentUser = null;
        DataManager.getInstance().saveCurrentUser(currentUser);
    }

    /**
     * 判断是否为当前用户
     *
     * @param userId
     * @return
     */
    public boolean isCurrentUser(long userId) {
        return DataManager.getInstance().isCurrentUser(userId);
    }

    public boolean isLoggedIn() {
        return getCurrentUserId() > 0;
    }


    public String getToken() {

        DataManager.getInstance().getCurrentToken();
        return token;
    }

    public void setToken(String token) {

        DataManager.getInstance().setCurrentToken(token);
        this.token = token;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getRealNameStatus() {
        return realNameStatus;
    }

    public void setRealNameStatus(String realNameStatus) {
        this.realNameStatus = realNameStatus;
    }

    public String getUserStlStatus() {
        return userStlStatus;
    }

    public void setUserStlStatus(String userStlStatus) {
        this.userStlStatus = userStlStatus;
    }

    public String getUserTermStatus() {
        return userTermStatus;
    }

    public void setUserTermStatus(String userTermStatus) {
        this.userTermStatus = userTermStatus;
    }

    public String getUserCreditCardStatus() {
        return userCreditCardStatus;
    }

    public void setUserCreditCardStatus(String userCreditCardStatus) {
        this.userCreditCardStatus = userCreditCardStatus;
    }
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
