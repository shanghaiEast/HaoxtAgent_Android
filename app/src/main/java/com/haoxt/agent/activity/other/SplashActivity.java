package com.haoxt.agent.activity.other;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.haoxt.agent.R;

/**闪屏activity，保证点击桌面应用图标后无延时响应
 * @author baowen
 */
public class SplashActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				startActivity(AboutActivity.createIntent(SplashActivity.this));
				finish();
			}
		}, 500);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.fade, R.anim.hold);
	}

}