package com.example.comptemoutons;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClientDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "mouton.db";

    public final String SQL_CREATE_USER = "CREATE TABLE IF NOT EXISTS User (idU INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, pwd TEXT);";
    public final String SQL_CREATE_TROUPEAU = "CREATE TABLE IF NOT EXISTS Troupeau (idT INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, dateT TEXT, photo BLOB, taille NUMBER);";

    public final String SQL_DELETE_USER = "DROP TABLE IF EXISTS User;";
    public final String SQL_DELETE_TROUPEAU = "DROP TABLE IF EXISTS Troupeau;";

    public ClientDbHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_TROUPEAU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER);
        db.execSQL(SQL_DELETE_TROUPEAU);
        onCreate(db);
    }
}
