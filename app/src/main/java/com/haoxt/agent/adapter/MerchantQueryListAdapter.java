package com.haoxt.agent.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxt.agent.R;

import java.util.ArrayList;
import java.util.HashMap;

import tft.mpos.library.util.StringUtil;

/**
 * @Description: 商户查询adapter
 * @Author: liuxx
 * @CreateDate: 2019/10/23 11:07
 */
public class MerchantQueryListAdapter extends RecyclerView.Adapter<MerchantQueryListAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<HashMap<String, Object>> list;
    private OnItemClickListener onItemClickListener;

    public MerchantQueryListAdapter(Activity context, ArrayList<HashMap<String, Object>> list) {
        this.ctx = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v,HashMap<String, Object> hashMap);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.merchant_query_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCorporatename.setText(StringUtil.get(list.get(position).get("NAME")));
        holder.tvCreatTime.setText("开通时间:"+StringUtil.get(list.get(position).get("TIME")));
        holder.tvMerchantnum.setText("商户编号:"+StringUtil.get(list.get(position).get("MERNO")));
        holder.tvFirstpartner.setText("所属一级业务员:"+StringUtil.get(list.get(position).get("FIRSTMERNO")));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v,list.get(position));
                }
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCorporatename, tvCreatTime, tvMerchantnum, tvFirstpartner;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCorporatename = itemView.findViewById(R.id.tv_corporatename);
            tvCreatTime = itemView.findViewById(R.id.tv_creat_time);
            tvMerchantnum = itemView.findViewById(R.id.tv_merchantnum);
            tvFirstpartner = itemView.findViewById(R.id.tv_firstpartner);
        }
    }

}
