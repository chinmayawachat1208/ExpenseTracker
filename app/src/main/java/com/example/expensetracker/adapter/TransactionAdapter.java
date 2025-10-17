package com.expensetracker.app.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.expensetracker.app.R;
import com.expensetracker.app.database.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private Context context;
    private List<Transaction> transactions;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Transaction transaction);

        void onDeleteClick(Transaction transaction);
    }

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.titleText.setText(transaction.getTitle());
        holder.categoryText.setText(transaction.getCategory());
        holder.dateText.setText(transaction.getDate());

        String amountStr = String.format("₹%.2f", transaction.getAmount());
        holder.amountText.setText(amountStr);

        // Color based on type
        if (transaction.getType().equals("income")) {
            holder.amountText.setTextColor(context.getResources().getColor(R.color.income_green));
            holder.typeIndicator.setCardBackgroundColor(context.getResources().getColor(R.color.income_bg));

            GradientDrawable gradient = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{
                            context.getResources().getColor(R.color.income_green),
                            context.getResources().getColor(R.color.income_green_light)
                    }
            );
            gradient.setCornerRadius(30f);
            holder.typeIndicator.setBackground(gradient);
        } else {
            holder.amountText.setTextColor(context.getResources().getColor(R.color.expense_red));
            holder.typeIndicator.setCardBackgroundColor(context.getResources().getColor(R.color.expense_bg));

            GradientDrawable gradient = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{
                            context.getResources().getColor(R.color.expense_red),
                            context.getResources().getColor(R.color.expense_red_light)
                    }
            );
            gradient.setCornerRadius(30f);
            holder.typeIndicator.setBackground(gradient);
        }

        // Animation
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_in));

        // Click listeners
        holder.editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(transaction);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(transaction);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void updateTransactions(List<Transaction> newTransactions) {
        this.transactions = newTransactions;
        notifyDataSetChanged();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        CardView cardView, typeIndicator;
        TextView titleText, amountText, categoryText, dateText;
        ImageButton editButton, deleteButton;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            typeIndicator = itemView.findViewById(R.id.typeIndicator);
            titleText = itemView.findViewById(R.id.titleText);
            amountText = itemView.findViewById(R.id.amountText);
            categoryText = itemView.findViewById(R.id.categoryText);
            dateText = itemView.findViewById(R.id.dateText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}