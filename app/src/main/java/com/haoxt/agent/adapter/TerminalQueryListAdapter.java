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
public class TerminalQueryListAdapter extends RecyclerView.Adapter<TerminalQueryListAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<HashMap<String, Object>> list;
    private OnItemClickListener onItemClickListener;

    public TerminalQueryListAdapter(Activity context, ArrayList<HashMap<String, Object>> list) {
        this.ctx = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(View v, HashMap<String, Object> hashMap);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.terminal_query_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTerminalSn.setText("SN:"+StringUtil.get(list.get(position).get("TER_SN")));
        int status = (int) list.get(position).get("TER_STATUS");
        holder.tvTerminalStatus.setText("绑定状态:"+ (status==0?"已绑定":"未绑定"));
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v,list.get(position));
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

        private TextView tvTerminalSn, tvTerminalStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTerminalSn = itemView.findViewById(R.id.tv_terminal_sn);
            tvTerminalStatus = itemView.findViewById(R.id.tv_terminal_status);

        }
    }

}
