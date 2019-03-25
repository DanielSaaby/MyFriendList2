package com.example.myfriendlist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.myfriendlist.Interface.IDataAccess;
import com.example.myfriendlist.Model.Friend;

import java.util.ArrayList;
import java.util.List;

public class SQLiteImpl implements IDataAccess {
    private static final String DATABASE_NAME = "sqlite.mDatabase";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Friend";

    private SQLiteDatabase mDatabase;
    private SQLiteStatement insertStmt;

    public SQLiteImpl(Context context) {

        OpenHelper openHelper = new OpenHelper(context);
        mDatabase = openHelper.getWritableDatabase();

        String INSERT = "insert into " + TABLE_NAME
                + "(name) values (?)";

        insertStmt = mDatabase.compileStatement(INSERT);
    }

    public long create(Friend friend) {
        insertStmt.bindString(1, friend.getName());
        return insertStmt.executeInsert();
    }

    public void deleteById() {
        mDatabase.delete(TABLE_NAME, null, null);
    }

    public List<Friend> readAll() {
        List<Friend> list = new ArrayList<>();
        Cursor cursor = mDatabase.query(TABLE_NAME,
                new String[] { "id", "name", "address", "latitude", "longitude", "phoneNumber", "email", "website", "birthday", "imgPath" },
                null, null, null, null, "name");
        if (cursor.moveToFirst()) {
            do {
                list.add(new Friend(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getDouble(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                        ));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }
    public void update(Friend friend)
    {

    }




    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY, name TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
