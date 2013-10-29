/**
 * Project name: MoneyNotebook
 * Package name: com.daveztong.moneynotebook.test
 * Filename: DBTest.java
 * Created time: Oct 25, 2013
 * Copyright: Copyright(c) 2013. All Rights Reserved.
 */

package com.daveztong.moneynotebook.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.daveztong.moneynotebook.MoneyNoteContract.MoneyNote;
import com.daveztong.moneynotebook.MoneyNoteDBUtil;

/**
 * @ClassName: DBTest
 * @Description: TODO
 * @author tangwei
 * @date Oct 25, 2013 4:36:29 PM
 * 
 */
public class DBTest extends AndroidTestCase {

    private static final String TAG = DBTest.class.getSimpleName();

    public void initData() {
        MoneyNoteDBUtil dbUtil = new MoneyNoteDBUtil(getContext());

        dbUtil.open();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 20; i++) {
            long rowId = dbUtil.insert(prepareValues("大米" + i, 57 + i, dateFormat.format(new Date(System.currentTimeMillis()))));
            Log.i("DBTest", rowId + "");
        }
        dbUtil.close();
    }

    public void showData() {
        MoneyNoteDBUtil dbUtil = new MoneyNoteDBUtil(getContext());
        dbUtil.open();
        Cursor cursor = dbUtil.query();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // String msg = cursor.getString(1) + "##" + cursor.getDouble(2) + "##" + cursor.getString(3);
                String msg = cursor.getString(0) + "--" + cursor.getString(1) + "--" + cursor.getDouble(2) + "--" + cursor.getString(3);
                Log.i(TAG, msg);
            }
            cursor.close();
        }
        dbUtil.close();
    }

    public void deleteData() {
        MoneyNoteDBUtil dbUtil = new MoneyNoteDBUtil(getContext());
        dbUtil.open();
        dbUtil.delete(-1);
        dbUtil.close();
    }
    
    public void showCost(){
        MoneyNoteDBUtil dbUtil = new MoneyNoteDBUtil(getContext());
        dbUtil.open();
        dbUtil.getAllCost();
        dbUtil.close();
    }

    private ContentValues prepareValues(String what, double price, String when) {
        ContentValues values = new ContentValues();
        values.put(MoneyNote.COLUMN_NAME_WHAT, what);
        values.put(MoneyNote.COLUMN_NAME_PRICE, price);
        values.put(MoneyNote.COLUMN_NAME_WHEN, when.toString());
        return values;
    }
}
