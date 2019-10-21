package com.haoxt.agent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.model.SubBankInfoSet;

import java.util.ArrayList;


public class SubBankInfoAdapter extends BaseAdapter {

    private ArrayList<SubBankInfoSet> mArrayList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SubBankInfoSet bankInfo;

    public SubBankInfoAdapter(ArrayList<SubBankInfoSet> mArrayList, Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mArrayList = mArrayList;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.base_info_message_item, null);
            mHolder.tvBankName = (TextView) convertView.findViewById(R.id.bank_item_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        bankInfo = mArrayList.get(position);
        mHolder.tvBankName.setText(bankInfo.getSubBankInfo().getLbnkNm());

        return convertView;
    }

    public static class ViewHolder {
        TextView tvBankName;
    }
}
