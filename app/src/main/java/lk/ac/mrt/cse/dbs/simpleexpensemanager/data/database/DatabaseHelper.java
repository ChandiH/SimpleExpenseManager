package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper{

    protected Context context;
    private static final int version = 1;
    private static final String Database_Name = "database.db";

    public static final String ACCOUNTS_TABLE = "accounts";
    public static final String ID_Column = "ID";
    public static final String ACCOUNT_NO_Column = "accountNo";
    public static final String BANK_NAME_Column = "bankName";
    public static final String ACCOUNT_HOLDER_NAME_Column = "accountHolderName";
    public static final String BALANCE_Column = "balance";

    public static final String TRANSACTIONS_Table = "transactions";
    public static final String TRANSACTION_ID_Column = "Transaction_ID";
    public static final String TRANSACTION_DATE_Column = "Transaction_Date";
    public static final String ACCOUNT_TYPE_Column = "accountType";
    public static final String EXPENSE_TYPE_Column = "expenseType";
    public static final String AMOUNT_Column = "amount";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_Acc_Table_query = "CREATE TABLE " + ACCOUNTS_TABLE +
                " ( " +
                ID_Column + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACCOUNT_NO_Column + " TEXT, " +
                BANK_NAME_Column + " TEXT," +
                ACCOUNT_HOLDER_NAME_Column + " TEXT, " +
                BALANCE_Column + " REAL" +
                ");";

        String create_trans_Table_query = "CREATE TABLE " + TRANSACTIONS_Table +
                " ( " +
                TRANSACTION_ID_Column + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TRANSACTION_DATE_Column + " TEXT," +
                ACCOUNT_TYPE_Column + " TEXT," +
                EXPENSE_TYPE_Column + " TEXT," +
                AMOUNT_Column + " REAL" +
                ");";

        sqLiteDatabase.execSQL(create_Acc_Table_query);
        sqLiteDatabase.execSQL(create_trans_Table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
