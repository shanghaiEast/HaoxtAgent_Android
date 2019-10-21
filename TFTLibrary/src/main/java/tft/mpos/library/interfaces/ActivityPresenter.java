

package tft.mpos.library.interfaces;

import android.app.Activity;
import android.view.View;

/**Activity的逻辑接口
 * @author baowen
 * @use implements ActivityPresenter
 * @warn 对象必须是Activity
 */
public interface ActivityPresenter extends Presenter {

	/**获取Activity
	 * @must 在非抽象Activity中 return this;
	 */
	public Activity getActivity();//无public导致有时自动生成的getActivity方法会缺少public且对此报错

	/**返回按钮被点击
	 * *Activity的返回按钮和底部弹窗的取消按钮几乎是必备，正好原生支持反射；而其它比如Fragment极少用到，也不支持反射
	 * @param v
	 */
	public void onReturnClick(View v);
	
	/**前进按钮被点击
	 * *Activity常用导航栏右边按钮，而且底部弹窗BottomWindow的确定按钮是必备；而其它比如Fragment极少用到，也不支持反射
	 * @param v
	 */
	public void onForwardClick(View v);
	
}