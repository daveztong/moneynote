/**
 * Project name: MoneyNotebook
 * Package name: com.daveztong.moneynotebook
 * Filename: MoneyNoteDBUtil.java
 * Created time: Oct 25, 2013
 * Copyright: Copyright(c) 2013. All Rights Reserved.
 */

package com.daveztong.moneynotebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.daveztong.moneynotebook.MoneyNoteContract.MoneyNote;

/**
 * @ClassName: MoneyNoteDBUtil
 * @Description: data manipulation
 * @author tangwei
 * @date Oct 25, 2013 4:06:12 PM
 * 
 */
public class MoneyNoteDBUtil {

    private static final String TAG = MoneyNoteDBUtil.class.getSimpleName();


    private SQLiteOpenHelper dbHelper = null;
    private SQLiteDatabase db = null;

    /**
     * @param ctx The context of the application
     */
    public MoneyNoteDBUtil(Context ctx) {
        dbHelper = new MoneyNoteDBHelper(ctx);
    }

    /**
     * @Title: open
     * @Description: Open database connection
     * @return database
     */
    public SQLiteDatabase open() {
        Log.i(TAG, "Ready to open database.");
        db = dbHelper.getWritableDatabase();
        return db;
    }

    /**
     * @Title: close
     * @Description: Close the database
     */
    public void close() {
        Log.i(TAG, "Database is going to close.");
        if (db != null)
            db.close();
    }

    /**
     * @Title: insert
     * @Description: Insert new data
     * @param values to insert
     * @return rowId
     */
    public long insert(ContentValues values) {
        return db.insert(MoneyNote.TABLE_NAME_MoneyNote, null, values);
    }

    /**
     * @Title: delete
     * @Description: Delete row according to a given id
     * @param rowId delete row according to this rowId
     */
    public void delete(long rowId) {
        int count = db.delete(MoneyNote.TABLE_NAME_MoneyNote, MoneyNote._ID + " = " + rowId, null);
        Log.i(TAG, count + "row(s) affected.");
    }

    /**
     * @Title: update
     * @Description: Update the data according to rowId
     * @param values to update
     * @param rowId
     */
    public void update(ContentValues values, long rowId) {
        String whereClause = MoneyNote._ID + " = ?";
        int count = db.update(MoneyNote.TABLE_NAME_MoneyNote, values, whereClause, new String[] { String.valueOf(rowId) });
        Log.i(TAG, count + "row(s) affected.");
    }

    /**
     * @Title: query
     * @Description: Query all the data
     * @return Cursor
     */
    public Cursor query() {
        Log.i(TAG, "Query all the data.");
        return db.query(MoneyNote.TABLE_NAME_MoneyNote, null, null, null, null, null, null);
    }

    /**
     * @ClassName: MoneyNoteDBHelper
     * @Description: Extends SQLiteOpenHelper,create database and table
     * @author tangwei
     * @date Oct 25, 2013 4:50:55 PM
     * 
     */
    public class MoneyNoteDBHelper extends SQLiteOpenHelper {

        private static final String TYPE_TEXT = " TEXT";
        private static final String TYPE_REAL = " REAL";
        private static final String DATABASE_NAME = "moneynote.db";
        private static final int DATABASE_VERSION = 1;
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_TABLE = "CREATE TABLE " + MoneyNote.TABLE_NAME_MoneyNote + "(" + MoneyNote._ID
                + " INTEGER NOT NULL AUTOINCREMENT PRIMARY KEY" + COMMA_SEP + MoneyNote.COLUMN_NAME_WHAT + TYPE_TEXT + COMMA_SEP
                + MoneyNote.COLUMN_NAME_PRICE + TYPE_REAL + COMMA_SEP + MoneyNote.COLUMN_NAME_WHEN + TYPE_TEXT + ");";
        private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + MoneyNote.TABLE_NAME_MoneyNote;

        /**
         * <p>
         * Description: MoneyNoteDBHelper构造器
         * </p>
         * 
         * @param context 上下文对象
         */
        public MoneyNoteDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.i(TAG, "Create database " + DATABASE_NAME + ", version " + DATABASE_VERSION);
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "Create table: " + SQL_CREATE_TABLE);
            db.execSQL(SQL_CREATE_TABLE);
        }

        /*
         * (non-Javadoc)
         * 
         * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_TABLE);
            onCreate(db);
        }

    }

}
