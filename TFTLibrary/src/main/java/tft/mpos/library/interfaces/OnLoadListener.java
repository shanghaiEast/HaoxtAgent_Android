
package tft.mpos.library.interfaces;

/**停止加载监听回调
 * @author baowen
 * @use implements OnStopLoadListener
 */
public interface OnLoadListener {
	/**
	 * 下拉刷新
	 */
	void onRefresh();
	/**
	 * 上拉加载更多
	 */
	void onLoadMore();
}
