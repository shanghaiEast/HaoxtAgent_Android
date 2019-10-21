package com.haoxt.agent.manager;

import org.json.JSONObject;

import com.haoxt.agent.interfaces.OnHttpResponseListener;
import tft.mpos.library.util.Log;
import tft.mpos.library.util.StringUtil;

/**Http请求结果解析类
 * *适合类似以下固定的json格式
 * <br>   {
   <br>     "code": 100,
   <br>     "data": {//可以为任何实体类json，通过Json.parseObject(json, Class<T>)解析；
                      或者是其它类型的JSONObject，解析方式如 {@link #onHttpResponse} 内所示
   <br>         ...
   <br>      }
   <br>   }
 * @author baowen
 * @use 把请求中的listener替换成new OnHttpResponseListenerImpl(listener)
 */
public class OnHttpResponseListenerImpl implements OnHttpResponseListener
, tft.mpos.library.interfaces.OnHttpResponseListener {
	private static final String TAG = "OnHttpResponseListenerImpl";

	OnHttpResponseListener listener;
	public OnHttpResponseListenerImpl(OnHttpResponseListener listener) {
		this.listener = listener;
	}



	/**tft.mpos.library.manager.HttpManager.OnHttpResponseListener的回调方法，这里转用listener处理
	 */
	@Override
	public void onHttpResponse(int requestCode, String resultJson, Exception e) {
		Log.i(TAG, "onHttpResponse  requestCode = " + requestCode + "; resultJson = " + resultJson
				+ "; \n\ne = " + (e == null ? null : e.getMessage()));

		int resultCode = 0;
		String resultData = null;
		Exception exception = null;
		try {
			JSONObject jsonObject = new JSONObject(resultJson);
			resultCode = jsonObject.getInt("code");//TODO code改为接口文档给的key
			resultData = jsonObject.getString("data");//TODO data改为接口文档给的key
		} catch (Exception e1) {
			Log.e(TAG, "onHttpResponse  try { sonObject = new JSONObject(resultJson);... >>" +
					" } catch (JSONException e1) {\n" + e1.getMessage());
			exception = e1;
		}
		Log.i(TAG, "onHttpResponse  resultCode = " + resultCode + "; resultData = " + resultData);

		if (listener == null) {
			listener = this;
		}
		if ((e == null && exception == null) || resultCode > 0 || StringUtil.isNotEmpty(resultData, true)) {
			listener.onHttpSuccess(requestCode, resultCode, resultData);
		} else {
			listener.onHttpError(requestCode, new Exception(TAG + ": e = " + e + "; \n exception = " + exception));
		}
	}

	@Override
	public void onHttpSuccess(int requestCode, int resultCode, String resultData) {
		Log.i(TAG, "onHttpSuccess  requestCode = " + requestCode + "; resultCode = " + resultCode
				+ "; resultData = \n" + resultData);
	}
	@Override
	public void onHttpError(int requestCode, Exception e) {
		Log.i(TAG, "onHttpSuccess  requestCode = " + requestCode + "; e = " + e);
	}

}
