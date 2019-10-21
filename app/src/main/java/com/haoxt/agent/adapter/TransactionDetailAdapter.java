package com.haoxt.agent.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.entity.TransactionDetail;

import java.util.List;

/**
 * 用户adapter
 *
 * @author baowen
 */
public class TransactionDetailAdapter extends RecyclerView.Adapter<TransactionDetailAdapter.ViewHolder> {

    private Context context;
    private List<TransactionDetail> data;
    private OnSalesSlipClickListener mOnSalesSlipClickListener;

    public TransactionDetailAdapter(Context context, List<TransactionDetail> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_detail, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        TransactionDetail transactionDetail = data.get(position);
        holder.mKeyTv.setText(transactionDetail.key);
        holder.mValueTv.setText(transactionDetail.value);
        if (TextUtils.equals("点击查看签购单", transactionDetail.value)) {
            holder.mValueTv.setTextColor(context.getResources().getColor(R.color.button_normal));
            holder.mValueTv.setOnClickListener(view -> {
                if (mOnSalesSlipClickListener != null) {
                    mOnSalesSlipClickListener.OnSalesSlipClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mKeyTv, mValueTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mKeyTv = itemView.findViewById(R.id.key_tv);
            mValueTv = itemView.findViewById(R.id.value_tv);
        }
    }

    public void setOnSalesSlipClickListener(OnSalesSlipClickListener listener) {
        mOnSalesSlipClickListener = listener;
    }

    public interface OnSalesSlipClickListener {
        void OnSalesSlipClick();
    }

}