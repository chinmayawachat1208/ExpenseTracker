package com.expensetracker.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.expensetracker.app.adapter.TransactionAdapter;
import com.expensetracker.app.database.AppDatabase;
import com.expensetracker.app.database.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private AppDatabase database;
    private TextView balanceText, incomeText, expenseText;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getInstance(this);

        balanceText = findViewById(R.id.balanceText);
        incomeText = findViewById(R.id.incomeText);
        expenseText = findViewById(R.id.expenseText);
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // FAB click
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
            startActivity(intent);
        });

        // Load data
        loadTransactions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTransactions();
    }

    private void loadTransactions() {
        List<Transaction> transactions = database.transactionDao().getAllTransactions();

        if (adapter == null) {
            adapter = new TransactionAdapter(this, transactions);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new TransactionAdapter.OnItemClickListener() {
                @Override
                public void onEditClick(Transaction transaction) {
                    Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                    intent.putExtra("TRANSACTION_ID", transaction.getId());
                    startActivity(intent);
                }

                @Override
                public void onDeleteClick(Transaction transaction) {
                    showDeleteDialog(transaction);
                }
            });
        } else {
            adapter.updateTransactions(transactions);
        }

        updateSummary();
    }

    private void updateSummary() {
        Double totalIncome = database.transactionDao().getTotalIncome();
        Double totalExpense = database.transactionDao().getTotalExpense();

        if (totalIncome == null) totalIncome = 0.0;
        if (totalExpense == null) totalExpense = 0.0;

        double balance = totalIncome - totalExpense;

        balanceText.setText(String.format("₹%.2f", balance));
        incomeText.setText(String.format("₹%.2f", totalIncome));
        expenseText.setText(String.format("₹%.2f", totalExpense));
    }

    private void showDeleteDialog(Transaction transaction) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Transaction")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    database.transactionDao().delete(transaction);
                    loadTransactions();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}