package com.daveztong.moneynotebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.daveztong.moneynotebook.MoneyNoteContract.MoneyNote;
import com.daveztong.moneynotebook.test.MoneyNoteLoader;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView lvMoneyNoteItems;
    private SimpleCursorAdapter cursorAdapter = null;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        getSupportLoaderManager().initLoader(MoneyNoteLoader.LOADER_ID, null, this);
    }

    private void initView() {

        lvMoneyNoteItems = (ListView) findViewById(R.id.lv_items);

        String[] from = new String[] { MoneyNote.COLUMN_NAME_WHAT, MoneyNote.COLUMN_NAME_PRICE, MoneyNote.COLUMN_NAME_WHEN };
        int[] to = new int[] { R.id.tv_what, R.id.tv_price, R.id.tv_when };
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.money_note_item, null, from, to, 0);
        lvMoneyNoteItems.setAdapter(cursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
   
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader");
        return new MoneyNoteLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(TAG, data.getCount() + "");
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i(TAG, "onLoaderReset");
        cursorAdapter.swapCursor(null);
    }
    
    public void addNew(View view){
        startActivity(new Intent(this, AddNewActivity.class));
    }
}
