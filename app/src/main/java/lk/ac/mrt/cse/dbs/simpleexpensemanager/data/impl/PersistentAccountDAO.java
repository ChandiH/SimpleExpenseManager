package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO extends DatabaseHelper implements AccountDAO {

    public PersistentAccountDAO(Context context){
        super(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> acc_number_list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String get_All_query = "SELECT * FROM " + ACCOUNTS_TABLE;
        Cursor cursor = sqLiteDatabase.rawQuery(get_All_query,null);

        if (cursor.moveToFirst()){
            do{
                acc_number_list.add(cursor.getString(1));
                System.out.println(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return acc_number_list;
    }

    @Override
    public List<Account> getAccountsList() {
        System.out.println("inside get Accounts");
        List<Account> acc_list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String get_All_query = "SELECT * FROM " + ACCOUNTS_TABLE;
        Cursor cursor = sqLiteDatabase.rawQuery(get_All_query,null);

        if (cursor.moveToFirst()){
            do{
                System.out.println("Cursor moved");
                Account acc = new Account(cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),cursor.getDouble(4));
                acc_list.add(acc);
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return acc_list;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Account acc;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String get_query = "SELECT * FROM " + ACCOUNTS_TABLE + " WHERE " + ACCOUNT_NO_Column + "=" + accountNo;
        Cursor cursor = sqLiteDatabase.rawQuery(get_query,null);

        if (cursor.moveToFirst()){
            acc = new Account(cursor.getString(1),
                    cursor.getString(2),cursor.getString(3),cursor.getDouble(4));
        } else{
            throw new InvalidAccountException("No such Account");
        }
        sqLiteDatabase.close();
        return acc;
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_NO_Column, account.getAccountNo());
        contentValues.put(BANK_NAME_Column, account.getBankName());
        contentValues.put(ACCOUNT_HOLDER_NAME_Column, account.getAccountHolderName());
        contentValues.put(BALANCE_Column, account.getBalance());

        sqLiteDatabase.insert(ACCOUNTS_TABLE, null, contentValues);
        sqLiteDatabase.close();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(ACCOUNTS_TABLE, ACCOUNT_NO_Column + "=? ", new String[]{String.valueOf(accountNo)});
        sqLiteDatabase.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account acc = getAccount(accountNo);
        ContentValues cv = new ContentValues();

        cv.put(ACCOUNT_NO_Column, acc.getAccountNo());
        cv.put(BANK_NAME_Column, acc.getBankName());
        cv.put(ACCOUNT_HOLDER_NAME_Column, acc.getAccountHolderName());

        if(expenseType==ExpenseType.EXPENSE){
            cv.put(BALANCE_Column, acc.getBalance()-amount);
        }else{
            cv.put(BALANCE_Column, acc.getBalance()+amount);
        }
    }
}
