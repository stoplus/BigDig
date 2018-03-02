package com.example.den.bigdig;

/**
 * Created by den on 26.02.2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDatabaseManager extends SQLiteOpenHelper {

    public SqliteDatabaseManager(Context context) {
        super(context, ContractLinks.DATABASE_NAME, null, ContractLinks.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table status
        String CREATE_STATUS_TABLE = "CREATE TABLE IF NOT EXISTS " + ContractLinks.Links.TABLE_NAME_STATUS
                + "("
                + ContractLinks.Links.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ContractLinks.Links.STATUS_VALUE + " INTEGER NOT NULL"
                + ")";
        db.execSQL(CREATE_STATUS_TABLE);

        // create table link_data
        String CREATE_LINK_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " + ContractLinks.Links.TABLE_NAME_LINK_DATA
                + "("
                + ContractLinks.Links.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ContractLinks.Links.NAME_LINK + " TEXT NOT NULL,"
                + ContractLinks.Links.STATUS_LINK + " INTEGER REFERENCES status (" + ContractLinks.Links.ID + "),"
                + ContractLinks.Links.TIME + " TIME NOT NULL"
                + ")";
        db.execSQL(CREATE_LINK_DATA_TABLE);
    }//onCreate

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContractLinks.Links.TABLE_NAME_STATUS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContractLinks.Links.TABLE_NAME_LINK_DATA);
        onCreate(sqLiteDatabase);
    }//onUpgrade
}//class SqliteDatabaseManager