package com.example.jokejet;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class JokeRepository {
    Database db;

    public JokeRepository(Database db) {
        this.db = db;
    }

    public Joke addJoke(int id,String content,String category,String type,String delivery,String status){
        SQLiteDatabase db = this.db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Joke.FIELD_ID, id);
        cv.put(Joke.FIELD_CONTENT, content);
        cv.put(Joke.FIELD_CATEGORY, category);
        cv.put(Joke.FIELD_TYPE, type);
        cv.put(Joke.FIELD_DELIVERY,delivery);
        cv.put(Joke.FIELD_STATUS,status);

        long result = db.insertWithOnConflict("joke", null, cv, SQLiteDatabase.CONFLICT_IGNORE);

        if (result == -1) {
            Log.d("JokeRepository", "Failed to insert joke, probably a duplicate.");
            return null;
        } else {
            Joke newJoke = new Joke(id, content, category, type, delivery, status);
            return newJoke;
        }
    }



    public int deleteJoke(int id){
        int numDeleted = 0;

        SQLiteDatabase db = this.db.getWritableDatabase();
        numDeleted = db.delete(Joke.TABLE_NAME, Joke.FIELD_ID + "=?", new String[] {String.valueOf(id)});
        return numDeleted;
    }

    public List<Joke> getAllJokes(String statusOfQuery) {
        SQLiteDatabase db = this.db.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE status='%s'", Joke.TABLE_NAME,statusOfQuery);
        Cursor result = db.rawQuery(query, null);
        result.moveToFirst();
        List<Joke> list = new ArrayList<Joke>(result.getCount());
        while (!result.isAfterLast()) {
            int id = result.getInt(result.getColumnIndex(Joke.FIELD_ID));
            String content = result.getString(result.getColumnIndex(Joke.FIELD_CONTENT));
            String category = result.getString(result.getColumnIndex(Joke.FIELD_CATEGORY));
            String type = result.getString(result.getColumnIndex(Joke.FIELD_TYPE));
            String delivery = result.getString(result.getColumnIndex(Joke.FIELD_DELIVERY));
            String status = result.getString(result.getColumnIndex(Joke.FIELD_STATUS));

            list.add(new Joke(id, content,category,type,delivery,status));
            result.moveToNext();
        }
        result.close();
        return list;

    }
}
