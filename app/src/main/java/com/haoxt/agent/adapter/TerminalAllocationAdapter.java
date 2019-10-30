package com.haoxt.agent.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.agent.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Description: 机具划拨列表adapter
 * @Author: liuxx
 * @CreateDate: 2019/10/28 9:09
 */
public class TerminalAllocationAdapter extends RecyclerView.Adapter<TerminalAllocationAdapter.ViewHolder> {

    private ArrayList<String> mList;
    private Context mContext;
    private OnItemClickListener itemClickListener;
    private ArrayList<HashMap<String, Object>> listData;

    public TerminalAllocationAdapter(Context context) {
        mList = new ArrayList<>();
        mList.add("1");
        mContext = context;
        listData = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.terminal_allocation_list_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvBatch.setText("划拨批次" + (i+1));
        viewHolder.tvDelete.setOnClickListener(view -> delData(i));
        viewHolder.tvDelete.setVisibility(mList.size() == 1 ? View.GONE : View.VISIBLE);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("snNoStart", viewHolder.etStartTerminalno.getText());
        hashMap.put("snNoEnd", viewHolder.etEndTerminalno.getText());
        hashMap.put("snNum", viewHolder.etInputTerCount.getText());
        listData.add(hashMap);
//        viewHolder.itemView.setOnClickListener(v -> mkkListener.success(mList.get(i)));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //添加数据
    public void addData(int i) {
        mList.add(i, "1");
        notifyItemInserted(mList.size());
    }

    //删除数据
    public void delData(int i) {
        mList.remove(i);
        listData.remove(i);
        notifyItemRemoved(i);
    }

    public ArrayList<HashMap<String, Object>> getListData() {
        return listData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvBatch;
        TextView tvDelete;
        ImageView ivReduce;
        EditText etInputTerCount;
        ImageView ivAdd;
        EditText etStartTerminalno;
        ImageView ivTerStartScan;
        EditText etEndTerminalno;
        ImageView ivTerEndScan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBatch = itemView.findViewById(R.id.tv_batch);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            ivReduce = itemView.findViewById(R.id.iv_reduce);
            etInputTerCount = itemView.findViewById(R.id.et_input_ter_count);
            ivAdd = itemView.findViewById(R.id.iv_add);
            etStartTerminalno = itemView.findViewById(R.id.et_start_terminalno);
            ivTerStartScan = itemView.findViewById(R.id.iv_ter_start_scan);
            etEndTerminalno = itemView.findViewById(R.id.et_end_terminalno);
            ivTerEndScan = itemView.findViewById(R.id.iv_ter_end_scan);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface mkkListener {
        void success(String data);
    }

    private mkkListener mkkListener;

    public void setmkkListener(mkkListener mkkListeners) {
        mkkListener = mkkListeners;
    }
}

