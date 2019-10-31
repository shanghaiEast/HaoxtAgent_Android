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

package com.haoxt.agent.util;

import com.haoxt.agent.application.AppApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tft.mpos.library.interfaces.OnHttpResponseListener;
import tft.mpos.library.manager.HttpManager;
import tft.mpos.library.model.Parameter;
import tft.mpos.library.util.MD5Util;
import tft.mpos.library.util.SettingUtil;
import tft.mpos.library.util.StringUtil;

/**HTTP请求工具类
 * @author Lemon
 * @use 添加请求方法xxxMethod >> HttpRequest.xxxMethod(...)
 * @must 所有请求的url、请求方法(GET, POST等)、请求参数(key-value方式，必要key一定要加，没提供的key不要加，value要符合指定范围)
 *       都要符合后端给的接口文档
 */
public class HttpRequest {
	//	private static final String TAG = "HttpRequest";



	/**基础URL，这里服务器设置可切换*/
	public static final String URL_BASE = SettingUtil.getServerAddress(true);
	public static final String PAGE_NUM = "pageNum";




	//示例代码<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	//user<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	public static final String RANGE = "range";

	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String CURRENT_USER_ID = "currentUserId";

	public static final String PHONE = "phone";
	public static final String PASSWORD = "password";
	static String token =  AppApplication.getInstance().getToken();


	/**翻译，根据有道翻译API文档请求
	 * http://fanyi.youdao.com/openapi?path=data-mode
	 * <br > 本Demo中只有这个是真正可用，其它需要自己根据接口文档新增或修改
	 * @param word
	 * @param requestCode
	 * @param listener
	 */
	public static void translate(String word, final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("q", word);
		request.put("keyfrom", "ZBLibrary");
		request.put("key", 1430082675);
		request.put("type", "data");
		request.put("doctype", "json");
		request.put("version", "1.1");
		String token =  AppApplication.getInstance().getToken();

		HttpManager.getInstance().get(token,request, "http://fanyi.youdao.com/openapi.do", requestCode, listener);
	}



	//account<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	/**
	 * 获取验证码
	 * @param type
	 * @param phone
	 */
	public static void getVerificationCode(final String type, final String phone, final  int requestCode, final OnHttpResponseListener listener){
		Map<String, Object> request = new HashMap<>();
		request.put("USR_LOGIN", phone);
		request.put("BUS_TYPE",type);
		String token =  AppApplication.getInstance().getToken();

		HttpManager.getInstance().post(token, request, URL_BASE + "/vcode/get", requestCode, listener);
	}

	/**注册
	 * @param phone
	 * @param password
	 * @param listener
	 */
	public static void register(final String phone, final String code, final String password,
			final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("USR_LOGIN", phone);
		request.put("VCODE", code);
		request.put("USR_LOGIN_PWD", MD5Util.MD5(password));
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token, request, URL_BASE + "/usr/register/add", requestCode, listener);
	}

	/**重置密码
	 * @param phone
	 * @param password
	 * @param listener
	 */
	public static void resetpassword(final String phone, final String code, final String password,
								final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("USR_LOGIN", phone);
		request.put("NEW_USR_LOGIN_PWD", MD5Util.MD5(password));
		request.put("VERIFICATION_CODE",code);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token, request, URL_BASE + "/password/login/find", requestCode, listener);
	}

	/**登陆
	 * @param phone
	 * @param password
	 * @param listener
	 */
	public static void login(final String phone, final String password,
			final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("USR_LOGIN", phone);
		request.put("USR_LOGIN_PWD", MD5Util.MD5(password));
		request.put("USR_OPR_MBL",phone);
		request.put("TOKEN_ID", "");
		request.put("USR_STATUS", "");
		request.put("USR_STL_STS", "");
		request.put("USR_TERM_STS", "");
		request.put("USR_NM", "");
		request.put("BLUE_TOOTH", "");
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token, request, URL_BASE + "/usr/login", requestCode, listener);
	}

	//account>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	/**商户信息查询
	 * @param requestCode
	 * @param listener
	 */
	public static void getUserInfo( final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
//		request.put(CURRENT_USER_ID, AppApplication.getInstance().getCurrentUserId());
//		request.put(USER_ID, userId);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/usr/get", requestCode, listener);
	}

	/**
	 * 身份认证信息提交
	 * @param name
	 * @param number
	 * @param dateStart
	 * @param dateEnd
	 * @param islongtime
	 * @param requestCode
	 * @param listener
	 */
	public static void updIdentityCard(final String name, final String number,String dateStart,String dateEnd,String islongtime,
							 final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("CARD_NAME", name);
		request.put("CARD_NO", number);
		request.put("CER_EXP_DT_START",dateStart);
		request.put("CER_EXP_DT_END", dateEnd);
		request.put("IS_LONG", islongtime);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token, request, URL_BASE + "/usr/updIdentityCard", requestCode, listener);
	}

	/**
	 * 银行卡认证信息提交
	 * @param cardNo
	 * @param topBankName
	 * @param topBankNo
	 * @param branchBankName
	 * @param branchBankNo
	 * @param cardProvince
	 * @param cardCity
	 * @param requestCode
	 * @param listener
	 */
	public static void addStlBankInfo(final String cardNo, final String topBankName,final String topBankNo,final String branchBankName,
									  final String branchBankNo, final String cardProvince ,final String cardCity,final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("STL_CARD_NO", cardNo);
		request.put("STL_BANK_NAME_HO", topBankName);
		request.put("STL_BANK_NUM_HO",topBankNo);
		request.put("STL_BANK_NAME_SUB", branchBankName);
		request.put("STL_BANK_NUM_SUB", branchBankNo);
		request.put("STL_BANK_PROV", cardProvince);
		request.put("STL_BANK_CITY", cardCity);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token, request, URL_BASE + "/stl/addStlBankInfo", requestCode, listener);
	}

	/**
	 * 附件上传
	 * @param file
	 * @param type
	 * @param requestCode
	 * @param listener
	 */
	public static void uploadRealNameFile(final String file, final String type,final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("file", file);
		request.put("FILE_TYPE", type);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token, request, URL_BASE + "/pub/uploadFile", requestCode, listener);
	}


	/**
	 * 总行列表
	 * @param value
	 * @param requestCode
	 * @param listener
	 */
	public static void getTopBankList(final String value,final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("FLD_NM", "AGTSTL_BNK_LIST");
		request.put("SAERCH", value);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token, request, URL_BASE + "/pubthlp/bankList", requestCode, listener);
	}

	/**
	 * 支行列表
	 * @param value
	 * @param requestCode
	 * @param listener
	 */
	public static void getBranchBankList(final String value,final String bankno,final String province,final String city,final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("SEARCH", value);
		request.put("blbnkNo", bankno);
		request.put("provCd", province);
		request.put("cityCd", city);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token, request, URL_BASE + "/pubthlp/openBankList", requestCode, listener);
	}

	/**
	 * 获取省市区
	 * @param flag
	 * @param search
	 * @param value
	 * @param requestCode
	 * @param listener
	 */
	public static void qryProvCity(final String flag,final String search,final String value,final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("FLAG", flag);
		request.put("SEARCH", search);
		request.put("VALUE", value);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token, request, URL_BASE + "/pubthlp/qryProvCity", requestCode, listener);
	}

	/**
	 * 商户信息及提交
	 * @param name
	 * @param industryBig
	 * @param industrySmall
	 * @param province
	 * @param city
	 * @param stlCycle
	 * @param accountType
	 * @param requestCode
	 * @param listener
	 */
	public static void uploadMerchanInfo( final String name, final String industryBig,final String industrySmall,final String province,final String city,final String stlCycle,final String accountType,
										  final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
//		request.put("MERC_NM", name);
//		request.put("PARENT_MCC", industryBig);
//		request.put("MCC_CD", industrySmall);
//		request.put("PROVINCE", province);
//		request.put("CITY", city);
//		request.put("STL_CYCLE", stlCycle);
//		request.put("ACO_TYP_LIST", accountType);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/usr/open", requestCode, listener);
	}

	/**
	 * 修改登陆密码
	 * @param old
	 * @param newPwd
	 * @param requestCode
	 * @param listener
	 */
	public static void updatePassWord( final String old, final String newPwd,
										  final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("OLD_USR_LOGIN_PWD", old);
		request.put("NEW_USR_LOGIN_PWD", newPwd);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/password/login/update", requestCode, listener);
	}

	/**
	 * 修改手机号(第一步)
	 * @param phone
	 * @param code
	 * @param requestCode
	 * @param listener
	 */
	public static void updatePhone1( final String phone, final String code,
									   final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("USR_MOBILE", phone);
		request.put("VCODE", code);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/usr/upLogPhone/one", requestCode, listener);
	}

	/**
	 * 修改手机号(第二步)
	 * @param phone
	 * @param code
	 * @param requestCode
	 * @param listener
	 */
	public static void updatePhone2( final String phone, final String code,
									   final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("USR_MOBILE", phone);
		request.put("VCODE", code);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/usr/upLogPhone/two", requestCode, listener);
	}

	/**
	 * 查询用户额度
	 * @param requestCode
	 * @param listener
	 */
	public static void quota(final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().get(token,request, URL_BASE + "/stl/quota", requestCode, listener);
	}

	/**
	 * 结算卡
	 * @param requestCode
	 * @param listener
	 */
	public static void getDebitCard(final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().get(token,request, URL_BASE + "/stl/get", requestCode, listener);
	}

	/**
	 * 结算卡修改
	 * @param province
	 * @param city
	 * @param barchNo
	 * @param barchName
	 * @param cardNo
	 * @param topNo
	 * @param topName
	 * @param requestCode
	 * @param listener
	 */
	public static void updStlBankInfo( final String province, final String city,final String barchNo ,final String barchName,final String cardNo,final String topNo,final String topName,
									 final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("STL_BANK_PROV", province);
		request.put("STL_BANK_CITY", city);
		request.put("STL_BNK_NO", barchNo);
		request.put("STL_BNK_NM", barchName);
		request.put("STL_ACO_NO", cardNo);
		request.put("STL_BNK_LIST", topNo);
		request.put("BANK_NM", topName);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/stl/updStlBankInfo", requestCode, listener);
	}


	/**
	 * 机具申领
	 * @param name
	 * @param phone
	 * @param province
	 * @param city
	 * @param area
	 * @param address
	 * @param requestCode
	 * @param listener
	 */
	public static void deviceApply( final String name, final String phone,final String province ,final String city,final String area,final String address,
									   final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("CONTACT", name);
		request.put("PHONE", phone);
		request.put("PROV", province);
		request.put("CITY", city);
		request.put("AREA", area);
		request.put("ADDRESS", address);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/term/tmstapply", requestCode, listener);
	}


	/**
	 * 机具申领查看
	 * @param requestCode
	 * @param listener
	 */
	public static void deviceApplyInfo(final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/term/getTmstapply", requestCode, listener);
	}

	/**
	 * 机具信息
	 * @param requestCode
	 * @param listener
	 */
	public static void myPosInfo(final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/term/list", requestCode, listener);
	}

	/**
	 * 认证状态
	 * @param requestCode
	 * @param listener
	 */
	public static void chkRealSts(final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/usr/chkRealSts", requestCode, listener);
	}

    /**
     * 系统公告
     * @param requestCode
     * @param listener
     */
    public static void getNotices(final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        String token =  AppApplication.getInstance().getToken();
        HttpManager.getInstance().post(token,request, URL_BASE + "/pub/getNotices", requestCode, listener);
    }

    /**
     * 机具绑定
     * @param requestCode
     * @param listener
     */
    public static void bandingDevice(final String sn,final String bluetoothname,final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("USR_SN_NO", sn);
        request.put("BLUE_TOOTH", bluetoothname);
        String token =  AppApplication.getInstance().getToken();
        HttpManager.getInstance().post(token,request, URL_BASE + "/term/add", requestCode, listener);
    }

	/**
	 * 上传机具信息
	 * @param info
	 * @param requestCode
	 * @param listener
	 */
	public static void uploadBuletoothInfo(final String info,final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("BLUE_TOOTH", info);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/term/addBlueTooth", requestCode, listener);
	}

	/**
	 * 下发密钥
	 * @param sn
	 * @param snTypNo
	 * @param mercId
	 * @param requestCode
	 * @param listener
	 */
	public static void updateApptrans(final String sn,final String snTypNo,final String mercId,final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("transType", "downloadMasterKey");
		request.put("snNo", sn);
		request.put("snTypNo", snTypNo);
		request.put("mercId", mercId);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/apptrans/trans", requestCode, listener);
	}

	/**
	 * 签到
	 * @param sn
	 * @param snTypNo
	 * @param mercId
	 * @param latitude
	 * @param longitude
	 * @param requestCode
	 * @param listener
	 */
	public static void signDevice(final String sn,final String snTypNo,final String mercId,String latitude,final String longitude,final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("transType", "signIn");
		request.put("snNo", sn);
		request.put("snTypNo", snTypNo);
		request.put("mercId", mercId);
		request.put("version", "V1.1.9 ");
		request.put("longitude", longitude);
		request.put("latitude", latitude);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/apptrans/trans", requestCode, listener);
	}


	/**
     * 认证记录
     * @param num
     * @param size
     * @param cardno
     * @param requestCode
     * @param listener
     */
    public static void authlist(final String num,final String size,final String cardno,final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("PAGE_NUM", num);
        request.put("PAGE_SIZE", size);
        request.put("CARDNO", cardno);
        String token =  AppApplication.getInstance().getToken();
        HttpManager.getInstance().post(token,request, URL_BASE + "/check/list", requestCode, listener);
    }

    /**
     * 认证提额
     * @param no
     * @param nankname
     * @param username
     * @param cardid
     * @param bankphone
     * @param requestCode
     * @param listener
     */
    public static void authPromoteLimit( final String no, final String nankname,final String username ,final String cardid,final String bankphone,
                                    final int requestCode, final OnHttpResponseListener listener) {
        Map<String, Object> request = new HashMap<>();
        request.put("CARD_NO", no);
        request.put("BANK_NM", nankname);
        request.put("NAME", username);
        request.put("CER_NO", cardid);
        request.put("BANK_PHONE", bankphone);
        String token =  AppApplication.getInstance().getToken();
        HttpManager.getInstance().post(token,request, URL_BASE + "/usr/authPromoteLimit", requestCode, listener);
    }


	/**
	 * 交易列表
	 * @param page
	 * @param pageSize
	 * @param startTime
	 * @param endtTime
	 * @param orderStatus
	 * @param startOrder
	 * @param endOrder
	 * @param type
	 * @param requestCode
	 * @param listener
	 */
	public static void transationList( final String page, final String pageSize,final String startTime ,final String endtTime,final String orderStatus,final String startOrder,final String endOrder,final String type,
										 final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("PAGE_NUM", page);
		request.put("PAGE_SIZE", pageSize);
		request.put("ORD_TIM_START", startTime);
		request.put("ORD_TIM_END", endtTime);
		request.put("TXN_STS", orderStatus);
		request.put("ORD_AMT_START", startOrder);
		request.put("ORD_AMT_END", endOrder);
		request.put("TXN_CD", type);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/ord/list", requestCode, listener);
	}

//	if (_payWayTag == 0) {
//		paytypeString = @"aliPay";
//
//	}else{
//		paytypeString = @"wxPay";
//	}

	/**
	 * 交易（二维码）
	 * @param snNo
	 * @param mercId
	 * @param amount
	 * @param scanAuthCode
	 * @param provNm
	 * @param cityNm
	 * @param areaNm
	 * @param requestCode
	 * @param listener
	 */
	public static void transation( final String snNo, final String mercId,final String amount ,final String scanAuthCode,final String transType,final String provNm,final String cityNm,final String areaNm,
									   final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("snNo", snNo); //机具号
		request.put("snTypNo", "3303");
		request.put("mercId", mercId); //商户ID
		request.put("posEntryModeCode", "031");
		request.put("amount", amount);
		request.put("scanAuthCode", scanAuthCode);
		request.put("transType", transType);
		request.put("provNm", provNm);
		request.put("cityNm", cityNm);
		request.put("areaNm", areaNm);
		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/apptrans/trans", requestCode, listener);
	}



	/**
	 * 交易（刷卡）
	 * @param transType
	 * @param snNo
	 * @param mercId
	 * @param pan
	 * @param amount
	 * @param dateExpiration
	 * @param posEntryModeCode
	 * @param cardSequenceNumbe
	 * @param track2
	 * @param track3
	 * @param pin
	 * @param ICSystemRelated
	 * @param snSeq
	 * @param randomKey
	 * @param hdSeqData
	 * @param snNetWorkNo
	 * @param longitude
	 * @param latitude
	 * @param secretFree
	 * @param actPayType
	 * @param provNm
	 * @param cityNm
	 * @param areaNm
	 * @param requestCode
	 * @param listener
	 */
	public static void posTransation( final String transType, final String snNo, final String snTypNo, final String mercId,final String pan,final String amount ,final String dateExpiration,final String posEntryModeCode,
									  final String cardSequenceNumbe,final String track2,final String track3,final String pin,final String ICSystemRelated, final String snSeq,final String randomKey,
                                      final String hdSeqData,final String snNetWorkNo,final String longitude,final String latitude,final String secretFree,final String actPayType, final String provNm,final String cityNm,final String areaNm,
								   final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("transType", transType);
		request.put("snNo", snNo);
		request.put("snTypNo", snTypNo);
		request.put("mercId", mercId);
		request.put("pan", pan);
		request.put("amount", amount);
		request.put("dateExpiration", dateExpiration);
		request.put("posEntryModeCode", posEntryModeCode);
		request.put("cardSequenceNumbe", cardSequenceNumbe);
		request.put("track2", track2);
		request.put("track3", track3);
		request.put("pin", pin);
		request.put("ICSystemRelated", ICSystemRelated);
		request.put("systemsTraceAuditNumbe", "");
		request.put("deviceType", "03");
		request.put("snSeq:", snSeq);
		request.put("randomKey", randomKey);
		request.put("hdSeqData", hdSeqData);
		request.put("version", "V1.1.9  ");
		request.put("snNetWorkNo", snNetWorkNo);
		request.put("longitude", longitude);
		request.put("latitude", latitude);
		request.put("secretFree", secretFree);
		request.put("actPayType", actPayType);
		request.put("provNm", provNm);
		request.put("cityNm", cityNm);
		request.put("areaNm", areaNm);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/apptrans/trans", requestCode, listener);
	}


	/**
	 * 交易查询
	 * @param snNo
	 * @param mercId
	 * @param sourceBatchNo
	 * @param requestCode
	 * @param listener
	 */
	public static void transationSearch( final String snNo, final String mercId,final String sourcetransDate ,final String sourcePosRequestId,final String sourceBatchNo,
								   final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("snNo", snNo); //机具号
		request.put("snTypNo", "3303");
		request.put("mercId", mercId); //商户ID
		request.put("transType", "scanpayQuery");
		request.put("sourcetransDate", sourcetransDate);
		request.put("sourcePosRequestId", sourcePosRequestId);
		request.put("sourceBatchNo", sourceBatchNo);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/apptrans/trans", requestCode, listener);
	}

	/**
	 * 上传签购单
	 * @param cseqNo
	 * @param batchNo
	 * @param trmNo
	 * @param actDt
	 * @param strBaseImg
	 * @param requestCode
	 * @param listener
	 */
	public static void upLoadSign( final String systemsTraceAuditNumber, final String batchNo,final String cardAcceptorTerminalId ,final String actDt,final String strBaseImg,
										 final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("cseqNo", systemsTraceAuditNumber);
		request.put("batchNo", batchNo);
		request.put("trmNo", cardAcceptorTerminalId);
		request.put("actDt", actDt); //当天时间
		request.put("strBaseImg", strBaseImg);

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/sign/upLoadSign", requestCode, listener);
	}

	/**
	 * 查询签购单
	 * @param cseqNo
	 * @param batchNo
	 * @param trmNo
	 * @param actDt
	 * @param requestCode
	 * @param listener
	 */
	public static void qrySignBillDtl( final String cseqNo, final String batchNo,final String trmNo ,final String actDt,
								   final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put("cseqNo", cseqNo);
		request.put("batchNo", batchNo);
		request.put("trmNo", trmNo); //商户ID
		request.put("actDt", actDt); //当天时间

		String token =  AppApplication.getInstance().getToken();
		HttpManager.getInstance().post(token,request, URL_BASE + "/sign/qrySignBillDtl", requestCode, listener);
	}





	public static final int USER_LIST_RANGE_ALL = 0;
	public static final int USER_LIST_RANGE_RECOMMEND = 1;
	/**获取用户列表
	 * @param range
	 * @param pageNum
	 * @param requestCode
	 * @param listener
	 */
	public static void getUserList(int range, int pageNum, final int requestCode, final OnHttpResponseListener listener) {
		Map<String, Object> request = new HashMap<>();
		request.put(CURRENT_USER_ID, AppApplication.getInstance().getCurrentUserId());
		request.put(RANGE, range);
		request.put(PAGE_NUM, pageNum);
		String token =  AppApplication.getInstance().getToken();

		HttpManager.getInstance().get(token,request, URL_BASE + "/user/list", requestCode, listener);
	}


	//user>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	//示例代码>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	/**添加请求参数，value为空时不添加，最快在 19.0 删除
	 * @param list
	 * @param key
	 * @param value
	 */
	@Deprecated
	public static void addExistParameter(List<Parameter> list, String key, Object value) {
		if (list == null) {
			list = new ArrayList<Parameter>();
		}
		if (StringUtil.isNotEmpty(key, true) && StringUtil.isNotEmpty(value, true) ) {
			list.add(new Parameter(key, value));
		}
	}

	/**
	 * 终端解绑
	 * @param map
	 * @param requestCode
	 * @param listener
	 */
	public static void terNnbound(Map<String, Object> map,int requestCode, final OnHttpResponseListener listener) {
		HttpManager.getInstance().get(token,map,URL_BASE+"/tms/appTmststock/unBind",requestCode,listener);
	}

}