package com.haoxt.agent.activity.home.achievement_manage;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.model.Achievement;

import java.util.List;

/**
 * 业绩查询 adapter
 *
 * @author baowen
 */
public class AchievementSearchListAdapter extends RecyclerView.Adapter<AchievementSearchListAdapter.ViewHolder> {

    private Context context;
    private List<Achievement> data;
    private OnItemClickListener mOnItemClickListener;

    public AchievementSearchListAdapter(Context context, List<Achievement> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.achievement_search_list_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Achievement achievement = data.get(position);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(position);
            }
        });

        holder.transactionDate.setText(achievement.getTransactionDate());
        holder.totalTransaction.setText(achievement.getTotalTransaction());
        holder.quantityTransaction.setText(achievement.getQuantityTransaction());
        holder.newAchievementTerminal.setText(achievement.getNewAchievementTerminal());
        holder.onlineMerchant.setText(achievement.getOnlineMerchant());
        holder.averageAmount.setText(achievement.getAverageAmount());
        holder.newActivation.setText(achievement.getNewActivation());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView transactionDate, totalTransaction, quantityTransaction, newAchievementTerminal,onlineMerchant,averageAmount,newActivation;

        public ViewHolder(View itemView) {
            super(itemView);
            transactionDate = itemView.findViewById(R.id.item_transactionDate);
            totalTransaction = itemView.findViewById(R.id.item_totalTransaction);
            quantityTransaction = itemView.findViewById(R.id.item_quantityTransaction);
            newAchievementTerminal = itemView.findViewById(R.id.item_newAchievementTerminal);
            onlineMerchant = itemView.findViewById(R.id.item_onlineMerchant);
            averageAmount = itemView.findViewById(R.id.item_averageAmount);
            newActivation = itemView.findViewById(R.id.item_newActivation);

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}