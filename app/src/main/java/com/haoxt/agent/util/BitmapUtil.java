package com.haoxt.agent.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;

//import com.tools.base64.BASE64Encoder;

import java.io.ByteArrayOutputStream;

public class BitmapUtil {
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 将Bitmap对象转为字符串
	 * 
	 * @param bm
	 * @return
	 */
	public static String Bitmap2String(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		int options = 100;

		while (baos.toByteArray().length / 1024 > 70) {
			baos.reset();
			options -= 5;
			bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}

		// if( baos.toByteArray().length / 1024>40){ //
		// 循环判断如果压缩后图片是否大于70kb,大于继续压缩
		// baos.reset();// 重置baos即清空baos
		// bm.compress(Bitmap.CompressFormat.JPEG, options, baos);//
		// 这里压缩options%，把压缩后的数据存放到baos中
		// options -= 10;// 每次都减少10
		// }
		return Base64Util.encode(baos.toByteArray());
	}

	public static String SignBitmap2String(Bitmap bm) {

		String string=null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

		byte[]bytes=baos.toByteArray();
		string= Base64.encodeToString(bytes,Base64.DEFAULT);
		return string;
//		int options = 100;
//
//		while (baos.toByteArray().length / 1024 > 70) {
//			baos.reset();
//			options -= 5;
//			bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
//		}

		// if( baos.toByteArray().length / 1024>40){ //
		// 循环判断如果压缩后图片是否大于70kb,大于继续压缩
		// baos.reset();// 重置baos即清空baos
		// bm.compress(Bitmap.CompressFormat.JPEG, options, baos);//
		// 这里压缩options%，把压缩后的数据存放到baos中
		// options -= 10;// 每次都减少10
		// }

//		return new BASE64Encoder().encode(baos.toByteArray());
	}

	/**
	 * 将Bitmap对象转为字符串
	 * 
	 * @param bm
	 * @return
	 */
	public static String Bitmap2String(Bitmap bm, int size) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > size && (options -= 10) >= 0) { // 循环判断如果压缩后图片是否大于sizekb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			// options -= 5;// 每次都减少5
			bm.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		return Base64Util.encode(baos.toByteArray());
	}

	/**
	 * base64转为bitmap
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
}
