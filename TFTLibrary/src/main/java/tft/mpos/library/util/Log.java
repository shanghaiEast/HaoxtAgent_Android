
package tft.mpos.library.util;

/**测试用Log,不要使用android.util.Log
 * @modifier baowen
 */
public class Log {

	/**
	 * @param TAG
	 * @param msg
	 */
	public static void d(String TAG, String msg) {
		if (! SettingUtil.isReleased) {
			android.util.Log.d(TAG, msg);
		}
	}
	/**
	 * @param TAG
	 * @param msg
	 * @param tr
	 */
	public static void d(String TAG, String msg, Throwable tr) {
		if (! SettingUtil.isReleased) {
			android.util.Log.d(TAG, msg, tr);
		}
	}

	/**
	 * @param TAG
	 * @param msg
	 */
	public static void v(String TAG, String msg) {
		if (! SettingUtil.isReleased) {
			android.util.Log.v(TAG, msg);
		}
	}

	/**
	 * @param TAG
	 * @param msg
	 */
	public static void i(String TAG, String msg) {
		if (! SettingUtil.isReleased) {
			android.util.Log.i(TAG, msg);
		}
	}

	/**
	 * @param TAG
	 * @param msg
	 */
	public static void e(String TAG, String msg) {
		if (! SettingUtil.isReleased) {
			android.util.Log.e(TAG, msg);
		}
	}

	/**
	 * @param TAG
	 * @param msg
	 */
	public static void w(String TAG, String msg) {
		if (! SettingUtil.isReleased) {
			android.util.Log.w(TAG, msg);
		}
	}

}
