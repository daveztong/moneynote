package com.daveztong.moneynotebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.daveztong.moneynotebook.MoneyNoteContract.MoneyNote;
import com.daveztong.moneynotebook.MoneyNoteCursorAdapter.ViewHolder;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView lvMoneyNoteItems;
    private MoneyNoteCursorAdapter cursorAdapter = null;
    private TextView tvTotalCost;
    private CheckBox delCheckBox;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        getSupportLoaderManager().initLoader(MoneyNoteLoader.LOADER_ID, null, this);
    }

    /**
     * @Title: initView
     * @Description: 初始化界面
     */
    private void initView() {

        // 删除复选框
        delCheckBox = (CheckBox) findViewById(R.id.del_chk);

        // 总支出
        tvTotalCost = (TextView) findViewById(R.id.tv_total_cost);
        MoneyNoteDBUtil dbUtil = new MoneyNoteDBUtil(this);
        dbUtil.open();
        tvTotalCost.setText("总支出: " + dbUtil.getAllCost());
        dbUtil.close();

        lvMoneyNoteItems = (ListView) findViewById(R.id.lv_items);

        // String[] from = new String[] { MoneyNote.COLUMN_NAME_WHAT, MoneyNote.COLUMN_NAME_PRICE, MoneyNote.COLUMN_NAME_WHEN };
        // int[] to = new int[] { R.id.tv_what, R.id.tv_price, R.id.tv_when };
        // cursorAdapter = new SimpleCursorAdapter(this, R.layout.cp_money_note_item, null, from, to, 0);
        cursorAdapter = new MoneyNoteCursorAdapter(this, null, 0);
        lvMoneyNoteItems.setAdapter(cursorAdapter);

        // 单击列表项时，跳转至新增页面，并用列表项的数据对其进行填充
        lvMoneyNoteItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                String name = viewHolder.tvName.getText().toString();
                double price = Double.valueOf(viewHolder.tvPrice.getText().toString());
                String atWhen = viewHolder.tvWhen.getText().toString();

                Cursor data = cursorAdapter.getCursor();
                data.moveToPosition(position);

                // String name = data.getString(data.getColumnIndexOrThrow(MoneyNote.COLUMN_NAME_WHAT));
                // double price = data.getDouble(data.getColumnIndexOrThrow(MoneyNote.COLUMN_NAME_PRICE));
                // String atWhen = data.getString(data.getColumnIndexOrThrow(MoneyNote.COLUMN_NAME_WHEN));
                String imagePath = data.getString(data.getColumnIndexOrThrow(MoneyNote.COLUMN_NAME_IMAGE_PATH));
                long rowId = data.getLong(data.getColumnIndexOrThrow(MoneyNote._ID));

                Bundle extras = new Bundle();
                extras.putString(MoneyNote.COLUMN_NAME_WHAT, name);
                extras.putDouble(MoneyNote.COLUMN_NAME_PRICE, price);
                extras.putString(MoneyNote.COLUMN_NAME_WHEN, atWhen);
                extras.putString(MoneyNote.COLUMN_NAME_IMAGE_PATH, imagePath);
                extras.putLong(MoneyNote._ID, rowId);

                Intent intent = new Intent(MainActivity.this, AddNewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.batchOperation:
            delCheckBox.setVisibility(View.VISIBLE);
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
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

    /**
     * @Title: addNew
     * @Description: 响应按钮单击，跳转至新增页面
     * @param view
     */
    public void addNew(View view) {
        startActivity(new Intent(this, AddNewActivity.class));
    }
}
