package com.daveztong.moneynotebook.test;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.daveztong.moneynotebook.MoneyNoteDBUtil;

public class MoneyNoteLoader extends AsyncTaskLoader<Cursor> {

    public static final int LOADER_ID = 0;
    private static final String TAG = MoneyNoteLoader.class.getSimpleName();
    
    private Cursor dataCursor = null;
    private MoneyNoteDBUtil moneyNoteDBUtil = new MoneyNoteDBUtil(getContext());

    public MoneyNoteLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        Log.i(TAG, "loadInBackground");
        moneyNoteDBUtil.open();
        Cursor cursor = moneyNoteDBUtil.query();
        //moneyNoteDBUtil.close();
        return cursor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.content.Loader#deliverResult(java.lang.Object)
     */
    @Override
    public void deliverResult(Cursor data) {
        super.deliverResult(data);
        Log.i(TAG, "deliverResult");
        moneyNoteDBUtil.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.content.Loader#onStartLoading()
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(dataCursor!=null)
            deliverResult(dataCursor);
        else {
            forceLoad();
        }
        Log.i(TAG, "onStartLoading");
    }
}
