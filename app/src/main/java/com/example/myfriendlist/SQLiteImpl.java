package com.example.myfriendlist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

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
	private SQLiteStatement updateStmt;
	private SQLiteStatement deleteStmt;




	public SQLiteImpl(Context context) {

		OpenHelper openHelper = new OpenHelper(context);
		mDatabase = openHelper.getWritableDatabase();


		String INSERT = "INSERT INTO " + TABLE_NAME
				+ "(name, address, latitude, longitude, phonenumber, email, website, birthday, imgPath) VALUES (?,?,?,?,?,?,?,?,?)";

		insertStmt = mDatabase.compileStatement(INSERT);

		String UPDATE = "UPDATE " + TABLE_NAME + " SET name = ?, address = ? , latitude = ?, longitude = ?, phonenumber = ?, email = ?, website = ?, birthday = ?, imgPath = ? WHERE id = ?";
		updateStmt = mDatabase.compileStatement(UPDATE);

		String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
		deleteStmt = mDatabase.compileStatement(DELETE);




	}

	@Override
	/**
	 * Insert friend into database
	 */
	public long insert(Friend f) {
		insertStmt.bindString(1, f.getName());
		insertStmt.bindString(2, f.getAddress());
		insertStmt.bindDouble(3, f.getLatitude());
		insertStmt.bindDouble(4, f.getLongitude());
		insertStmt.bindLong(5, f.getPhoneNumber());
		insertStmt.bindString(6, f.getEMail());
		insertStmt.bindString(7, f.getWebsite());
		insertStmt.bindString(8, f.getBirthday());
		insertStmt.bindString(9, f.getImgPath());

		return insertStmt.executeInsert();
	}

	@Override
	/**
	 * Deletes all friends from the database
	 */
	public void deleteAll()
	{
		mDatabase.delete(TABLE_NAME, null, null);
	}

	@Override
	/**
	 * Deletes a specfic friend with the requested ID
	 */
	public void deleteById(int id) {
		deleteStmt.bindLong(1, id);
		deleteStmt.execute();
	}

	@Override
	/**
	 * Selects all friends from the Database
	 */
	public ArrayList<Friend> selectAll() {
		ArrayList<Friend> friendList = new ArrayList<>();
		Cursor cursor = mDatabase.query(TABLE_NAME, new String[] { "id", "name", "address", "latitude", "longitude", "phonenumber", "email", "website", "birthday", "imgPath"}, null, null, null, null, "null");

		if (cursor.moveToFirst()) {
			do {
				friendList.add(new Friend(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9)));
			} while (cursor.moveToNext());
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		return friendList;
	}

	@Override
	/**
	 * Updates a friend in the database
	 */
	public void update(Friend f) {
		updateStmt.bindString(1, f.getName());
		updateStmt.bindString(2, f.getAddress());
		updateStmt.bindDouble(3, f.getLatitude());
		updateStmt.bindDouble(4, f.getLongitude());
		updateStmt.bindLong(5, f.getPhoneNumber());
		updateStmt.bindString(6, f.getEMail());
		updateStmt.bindString(7, f.getWebsite());
		updateStmt.bindString(8, f.getBirthday());
		updateStmt.bindString(9, f.getImgPath());
		updateStmt.bindLong(10, f.getId());

		updateStmt.execute();

		Log.d("TAG", "WE HIT UPDATE " + f.getName() );


	}


	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}


		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME
			+ "(id INTEGER PRIMARY KEY, name TEXT, address TEXT, latitude REAL, longitude REAL, phonenumber INTEGER, email TEXT, website TEXT, birthday TEXT, imgPath TEXT)");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}
