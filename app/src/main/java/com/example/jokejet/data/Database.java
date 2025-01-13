package com.example.jokejet.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jokejet.model.Joke;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "jokejet.sqlite";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String proizvodjacTableQuery = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT, " +
                        "%s TEXT);",
                Joke.TABLE_NAME,
                Joke.FIELD_ID,
                Joke.FIELD_CONTENT,
                Joke.FIELD_CATEGORY,
                Joke.FIELD_TYPE,
                Joke.FIELD_DELIVERY,
                Joke.FIELD_STATUS
        );
        db.execSQL(proizvodjacTableQuery);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s;", Joke.TABLE_NAME));
        onCreate(db);
    }
}
