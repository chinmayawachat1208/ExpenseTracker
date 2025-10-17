package com.expensetracker.app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.expensetracker.app.database.AppDatabase;
import com.expensetracker.app.database.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText titleInput, amountInput, noteInput, dateInput;
    private RadioGroup typeGroup;
    private RadioButton incomeRadio, expenseRadio;
    private Spinner categorySpinner;
    private Button saveButton;
    private AppDatabase database;
    private Calendar calendar;
    private int transactionId = -1;
    private Transaction existingTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        database = AppDatabase.getInstance(this);
        calendar = Calendar.getInstance();

        // Initialize views
        titleInput = findViewById(R.id.titleInput);
        amountInput = findViewById(R.id.amountInput);
        noteInput = findViewById(R.id.noteInput);
        dateInput = findViewById(R.id.dateInput);
        typeGroup = findViewById(R.id.typeGroup);
        incomeRadio = findViewById(R.id.incomeRadio);
        expenseRadio = findViewById(R.id.expenseRadio);
        categorySpinner = findViewById(R.id.categorySpinner);
        saveButton = findViewById(R.id.saveButton);

        // Setup category spinner
        String[] categories = {"Food", "Transport", "Shopping", "Bills", "Entertainment",
                "Health", "Education", "Salary", "Business", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(adapter);

        // Set current date
        updateDateLabel();

        // Date picker
        dateInput.setOnClickListener(v -> showDatePicker());

        // Check if editing
        transactionId = getIntent().getIntExtra("TRANSACTION_ID", -1);
        if (transactionId != -1) {
            loadTransaction();
            saveButton.setText("Update Transaction");
        }

        // Save button
        saveButton.setOnClickListener(v -> saveTransaction());

        // Back button
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void loadTransaction() {
        existingTransaction = database.transactionDao().getTransactionById(transactionId);
        if (existingTransaction != null) {
            titleInput.setText(existingTransaction.getTitle());
            amountInput.setText(String.valueOf(existingTransaction.getAmount()));
            noteInput.setText(existingTransaction.getNote());
            dateInput.setText(existingTransaction.getDate());

            if (existingTransaction.getType().equals("income")) {
                incomeRadio.setChecked(true);
            } else {
                expenseRadio.setChecked(true);
            }

            // Set category
            String[] categories = {"Food", "Transport", "Shopping", "Bills", "Entertainment",
                    "Health", "Education", "Salary", "Business", "Other"};
            for (int i = 0; i < categories.length; i++) {
                if (categories[i].equals(existingTransaction.getCategory())) {
                    categorySpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        dateInput.setText(sdf.format(calendar.getTime()));
    }

    private void saveTransaction() {
        String title = titleInput.getText().toString().trim();
        String amountStr = amountInput.getText().toString().trim();
        String note = noteInput.getText().toString().trim();
        String date = dateInput.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();

        if (title.isEmpty()) {
            titleInput.setError("Title is required");
            return;
        }

        if (amountStr.isEmpty()) {
            amountInput.setError("Amount is required");
            return;
        }

        double amount = Double.parseDouble(amountStr);
        String type = incomeRadio.isChecked() ? "income" : "expense";

        if (transactionId != -1) {
            // Update existing
            existingTransaction.setTitle(title);
            existingTransaction.setAmount(amount);
            existingTransaction.setType(type);
            existingTransaction.setCategory(category);
            existingTransaction.setDate(date);
            existingTransaction.setNote(note);
            database.transactionDao().update(existingTransaction);
            Toast.makeText(this, "Transaction updated!", Toast.LENGTH_SHORT).show();
        } else {
            // Insert new
            Transaction transaction = new Transaction(title, amount, type, category, date, note);
            database.transactionDao().insert(transaction);
            Toast.makeText(this, "Transaction added!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}