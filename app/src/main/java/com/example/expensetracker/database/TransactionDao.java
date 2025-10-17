package com.expensetracker.app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("SELECT * FROM transactions ORDER BY id DESC")
    List<Transaction> getAllTransactions();

    @Query("SELECT * FROM transactions WHERE type = 'income' ORDER BY id DESC")
    List<Transaction> getIncomeTransactions();

    @Query("SELECT * FROM transactions WHERE type = 'expense' ORDER BY id DESC")
    List<Transaction> getExpenseTransactions();

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'income'")
    Double getTotalIncome();

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'expense'")
    Double getTotalExpense();

    @Query("SELECT * FROM transactions WHERE id = :id")
    Transaction getTransactionById(int id);

    @Query("DELETE FROM transactions")
    void deleteAll();
}