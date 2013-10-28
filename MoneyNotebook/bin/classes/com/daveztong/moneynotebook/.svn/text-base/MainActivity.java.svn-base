package com.daveztong.moneynotebook;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ListView;

import com.daveztong.moneynotebook.MoneyNoteContract.MoneyNote;
import com.daveztong.moneynotebook.test.MoneyNoteLoader;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{

	private ListView lvMoneyNoteItems;
	private SimpleCursorAdapter cursorAdapter = null;
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();
        
        getLoaderManager().initLoader(MoneyNoteLoader.LOADER_ID, null, (android.app.LoaderManager.LoaderCallbacks<Cursor>) this);
 }

    private void initView() {
    	
    	lvMoneyNoteItems=(ListView) findViewById(R.id.lv_items);

    	String[] from = new String[]{MoneyNote.COLUMN_NAME_WHAT,MoneyNote.COLUMN_NAME_PRICE,MoneyNote.COLUMN_NAME_WHEN};
    	int[] to = new int[]{R.id.tv_what,R.id.tv_price,R.id.tv_when};
    	cursorAdapter = new SimpleCursorAdapter(this, R.layout.money_note_item, null, from, to,0);
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
		return new MoneyNoteLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		
	}
}
