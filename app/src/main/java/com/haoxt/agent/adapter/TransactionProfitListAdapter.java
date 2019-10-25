package com.haoxt.agent.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.common.PayType;
import com.haoxt.agent.entity.ListProfitTransaction;
import com.haoxt.agent.entity.ListTransaction;

import java.util.List;

/**
 * 用户adapter
 *
 * @author baowen
 */
public class TransactionProfitListAdapter extends RecyclerView.Adapter<TransactionProfitListAdapter.ViewHolder> {

    private Context context;
    private List<ListProfitTransaction> data;
    private OnItemClickListener mOnItemClickListener;

    public TransactionProfitListAdapter(Context context, List<ListProfitTransaction> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction_profit_list, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ListProfitTransaction transaction = data.get(position);

        holder.type.setText(transaction.getType());
        holder.money.setText(transaction.getAmount());
        holder.time.setText(transaction.getTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


//    public static void setText(TextView textView, int payResult) {
//        switch (payResult) {
//            case 0:
//                textView.setText(R.string.deals_done);
//                break;
//            case 1:
//                textView.setText(R.string.failure_deal);
//                break;
//            case 2:
//                textView.setText(R.string.unknown_transaction);
//                break;
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView type, money, time;
        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tx_transation_type);
            money = itemView.findViewById(R.id.tx_transation_money);
            time = itemView.findViewById(R.id.tx_transation_time);


        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}