

package tft.mpos.library.interfaces;

/**Adapter使用回调
 * @author baowen
 * @param <A> adapter名称
 * @see #createAdapter
 * @see #refreshAdapter
 * @use implements AdapterCallBack<A>,具体参考.DemoListActivity和.DemoListFragment
 */
public interface AdapterCallBack<A> {

	/**创建一个Adapter
	 * @return new A();
	 */
	A createAdapter();

	/**
	 * BaseAdapter#notifyDataSetChanged()有时无效，有时因列表更新不及时而崩溃，所以需要在自定义adapter内自定义一个刷新方法。
	 * 为什么不直接让自定义Adapter implement OnRefreshListener，从而直接 onRefreshListener.onRefresh(List<T> list) ？
	 * 因为这样的话会不兼容部分 Android SDK 或 第三方库的Adapter
	 */
	void refreshAdapter();
}
