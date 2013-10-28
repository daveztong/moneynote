package com.daveztong.moneynotebook.test;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.daveztong.moneynotebook.MoneyNoteDBUtil;

public class MoneyNoteLoader extends AsyncTaskLoader<Cursor> {

	public static final int LOADER_ID = 0;
	
	public MoneyNoteLoader(Context context) {
		super(context);		
	}

	@Override
	public Cursor loadInBackground() {
		MoneyNoteDBUtil moneyNoteDBUtil = new MoneyNoteDBUtil(getContext());
		moneyNoteDBUtil.open();
		Cursor cursor = moneyNoteDBUtil.query();
		moneyNoteDBUtil.close();
		return cursor;
	}

}
