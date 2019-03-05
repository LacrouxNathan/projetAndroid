package com.example.comptemoutons;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClientDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "mouton.db";

    public final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS User (idU INTEGER PRIMARY KEY, pwd TEXT); CREATE TABLE Mouton (idM INTEGER, nom TEXT, poids REAL, taille REAL); ";
    public final String SQL_DELETE = "DROP TABLE IF EXISTS Clients;";

    public ClientDbHelper(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
