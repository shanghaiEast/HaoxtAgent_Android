package com.haoxt.agent.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.haoxt.agent.model.User;
import com.haoxt.agent.view.UserView;
import tft.mpos.library.base.BaseAdapter;

/**用户adapter
 * @author baowen
 */
public class UserAdapter extends BaseAdapter<User, UserView> {
	//	private static final String TAG = "UserAdapter";

	public UserAdapter(Activity context) {
		super(context);
	}

	@Override
	public UserView createView(int position, ViewGroup parent) {
		return new UserView(context, parent);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

}