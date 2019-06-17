package com.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDataBaseDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserDetailDB";
    private static String TABLE_USER_DETAIL = "UserDetail";
    private static String USERID = "id";
    private static String NAME = "name";
    private static String EMAIL = "email";
    private static String PHONENO = "phoneNumber";
    private static String CITY = "city";
    private static String STATE = "state";


    public SQLiteDataBaseDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_USER_DETAIL +
                "(" + USERID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                EMAIL + " TEXT, " +
                PHONENO + " TEXT, " +
                CITY + " TEXT, " +
                STATE + " TEXT " + ")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_USER_DETAIL;
        db.execSQL(sql);
        onCreate(db);
    }


    public void insertUserData(String name, String email, String phone, String city, String state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(EMAIL, email);
        values.put(PHONENO, phone);
        values.put(CITY, city);
        values.put(STATE, state);

        db.insert(TABLE_USER_DETAIL, null, values);
        db.close();
    }

    public List<UserDetail> getUserDetail() {

        List<UserDetail> userDetailList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USER_DETAIL;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                UserDetail detail = new UserDetail();
                detail.setId(cursor.getInt(cursor.getColumnIndex(USERID)));
                detail.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                detail.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                detail.setPhone(cursor.getString(cursor.getColumnIndex(PHONENO)));
                detail.setCity(cursor.getString(cursor.getColumnIndex(CITY)));
                detail.setState(cursor.getString(cursor.getColumnIndex(STATE)));
                userDetailList.add(detail);

            } while (cursor.moveToNext());
        }
        // close db connection
        database.close();
        // return list
        return userDetailList;
    }

    public void deleteByUserId(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_USER_DETAIL + " WHERE id=" + id);
        database.close();
    }

    public void updateUserDetailById(int id, String name, String email, String phone, String city, String state) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(EMAIL, email);
        values.put(PHONENO, phone);
        values.put(CITY, city);
        values.put(STATE, state);

        database.update(TABLE_USER_DETAIL, values, "id=" + id, null);
        database.close();
    }

}
