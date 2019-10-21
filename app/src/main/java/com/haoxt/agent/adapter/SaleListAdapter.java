package com.haoxt.agent.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.entity.ListSaleDetail;

import java.util.List;

/**
 * 用户adapter
 *
 * @author baowen
 */
public class SaleListAdapter extends RecyclerView.Adapter<SaleListAdapter.ViewHolder> {

    private Context context;
    private List<ListSaleDetail> data;

    public SaleListAdapter(Context context, List<ListSaleDetail> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sale_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.messageLabelTv.setText(data.get(position).typename);
        holder.saleInfoTv.setText(data.get(position).salemoney);
        holder.saleTimeTv.setText(data.get(position).saletime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView messageLabelTv, saleInfoTv, saleTimeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            messageLabelTv = itemView.findViewById(R.id.message_label_tv);
            saleInfoTv = itemView.findViewById(R.id.sale_info_tv);
            saleTimeTv = itemView.findViewById(R.id.sale_time_tv);

        }
    }
}