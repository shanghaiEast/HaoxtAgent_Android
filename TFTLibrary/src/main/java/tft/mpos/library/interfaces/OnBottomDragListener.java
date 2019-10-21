

package tft.mpos.library.interfaces;

/**拖拽View底部的回调接口
 * @author baowen
 * @use implements OnBottomDragListener
 */
public interface OnBottomDragListener {

	/**
	 * @param rightToLeft ？从右向左 : 从左向右
	 */
	void onDragBottom(boolean rightToLeft);
}
