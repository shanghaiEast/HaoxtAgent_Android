package com.haoxt.agent.activity.other;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;

import com.haoxt.agent.R;
import com.haoxt.agent.model.User;
import tft.mpos.library.base.BaseActivity;
import tft.mpos.library.interfaces.OnBottomDragListener;
import tft.mpos.library.manager.CacheManager;
import tft.mpos.library.util.JSON;
import tft.mpos.library.util.Log;
import tft.mpos.library.util.StringUtil;

/**二维码界面Activity
 * @author baowen
 */
public class QRCodeActivity extends BaseActivity implements OnBottomDragListener {
	private static final String TAG = "QRCodeActivity";

	//启动方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	/**启动这个Activity的Intent
	 * @param context
	 * @param userId
	 * @return
	 */
	public static Intent createIntent(Context context, long userId) {
		return new Intent(context, QRCodeActivity.class).
				putExtra(INTENT_ID, userId);
	}

	//启动方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	private long userId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode_activity, this);

		intent = getIntent();
		userId = intent.getLongExtra(INTENT_ID, userId);

		//功能归类分区方法，必须调用<<<<<<<<<<
		initView();
		initData();
		initEvent();
		//功能归类分区方法，必须调用>>>>>>>>>>

	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	private ImageView ivQRCodeHead;
	private TextView tvQRCodeName;

	private ImageView ivQRCodeCode;
	private View ivQRCodeProgress;
	@Override
	public void initView() {//必须调用
		autoSetTitle();

		ivQRCodeHead = findView(R.id.ivQRCodeHead);
		tvQRCodeName = findView(R.id.tvQRCodeName);

		ivQRCodeCode = findView(R.id.ivQRCodeCode);
		ivQRCodeProgress = findView(R.id.ivQRCodeProgress);
	}


	//UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>










	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


	private User user;
	@Override
	public void initData() {//必须调用

		ivQRCodeProgress.setVisibility(View.VISIBLE);
		runThread(TAG + "initData", new Runnable() {

			@Override
			public void run() {

				user = CacheManager.getInstance().get(User.class, "" + userId);
				if (user == null) {
					user = new User(userId);
				}
				runUiThread(new Runnable() {
					@Override
					public void run() {
						Glide.with(context).load(user.getHead()).into(ivQRCodeHead);
						tvQRCodeName.setText(StringUtil.getTrimedString(
								StringUtil.isNotEmpty(user.getName(), true)
								? user.getName() : user.getPhone()));
					}
				});

				setQRCode(user);
			}
		});

	}

	private Bitmap qRCodeBitmap;
	protected void setQRCode(User user) {
		if (user == null) {
			Log.e(TAG, "setQRCode  user == null" +
					" || StringUtil.isNotEmpty(user.getPhone(), true) == false >> return;");
			return;
		}

		try {
			qRCodeBitmap = EncodingHandler.createQRCode(JSON.toJSONString(user)
					, (int) (2 * getResources().getDimension(R.dimen.qrcode_size)));
		} catch (WriterException e) {
			e.printStackTrace();
			Log.e(TAG, "initData  try {Bitmap qrcode = EncodingHandler.createQRCode(contactJson, ivQRCodeCode.getWidth());" +
					" >> } catch (WriterException e) {" + e.getMessage());
		}

		runUiThread(new Runnable() {
			@Override
			public void run() {
					ivQRCodeProgress.setVisibility(View.GONE);
					ivQRCodeCode.setImageBitmap(qRCodeBitmap);
			}
		});
	}

	//Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>








	//Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void initEvent() {//必须调用

	}

	//系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	public void onDragBottom(boolean rightToLeft) {
		if (rightToLeft) {

			return;
		}

		finish();
	}



	//生命周期、onActivityResult<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	@Override
	protected void onDestroy() {
		super.onDestroy();

		ivQRCodeProgress = null;
		ivQRCodeCode = null;
		user = null;

		if (qRCodeBitmap != null) {
			if (qRCodeBitmap.isRecycled() == false) {
				qRCodeBitmap.recycle();
			}
			qRCodeBitmap = null;
		}
		if (ivQRCodeCode != null) {
			ivQRCodeCode.setImageBitmap(null);
			ivQRCodeCode = null;
		}
	}

	//生命周期、onActivityResult>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


	//Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>







	//内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<



	//内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}