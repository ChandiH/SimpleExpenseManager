package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO extends DatabaseHelper implements TransactionDAO {

    public PersistentTransactionDAO(Context context) {
        super(context);
    }


    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateAsString = df.format(date);

        contentValues.put(TRANSACTION_DATE_Column, dateAsString);
        contentValues.put(ACCOUNT_TYPE_Column, accountNo);
        contentValues.put(EXPENSE_TYPE_Column, String.valueOf(expenseType));
        contentValues.put(AMOUNT_Column, amount);

        sqLiteDatabase.insert(TRANSACTIONS_Table, null, contentValues);
        sqLiteDatabase.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactionArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String get_All_query = "SELECT * FROM " + TRANSACTIONS_Table;
        Cursor cursor = sqLiteDatabase.rawQuery(get_All_query,null);

        if (cursor.moveToFirst()){
            do{
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = df.parse(cursor.getString(1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ExpenseType expenseType;
                if(Objects.equals(cursor.getString(3), "EXPENSE")){
                    expenseType = ExpenseType.EXPENSE;
                }else{
                    expenseType = ExpenseType.INCOME;
                }

                Transaction transaction = new Transaction(date, cursor.getString(2), expenseType,cursor.getDouble(4));
                transactionArrayList.add(transaction);
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return transactionArrayList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactionArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String get_All_query = "SELECT * FROM " + TRANSACTIONS_Table;
        Cursor cursor = sqLiteDatabase.rawQuery(get_All_query,null);

        if (cursor.moveToFirst()){
            int i = 0;
            do{
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = df.parse(cursor.getString(1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ExpenseType expenseType;
                if(Objects.equals(cursor.getString(3), "EXPENSE")){
                    expenseType = ExpenseType.EXPENSE;
                }else{
                    expenseType = ExpenseType.INCOME;
                }

                Transaction transaction = new Transaction(date, cursor.getString(2), expenseType,cursor.getDouble(4));
                transactionArrayList.add(transaction);
                i += 1;
            }while(cursor.moveToNext() && i<limit);
        }
        sqLiteDatabase.close();
        return transactionArrayList;
    }
}
