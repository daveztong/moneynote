package com.daveztong.moneynotebook;

import com.daveztong.moneynotebook.MoneyNoteContract.MoneyNote;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewActivity extends FragmentActivity {

    private static final String TAG = AddNewActivity.class.getSimpleName();
    private EditText etWhen, etWhat, etPrice;
    private Button saveNewItemStay, saveNewItemReturn;

    /**
     * @Title: getEtWhen
     * @Description: Get the date of new inserted item
     */
    public EditText getEtWhen() {
        return etWhen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        // find view
        etWhen = (EditText) findViewById(R.id.choose_date);
        etWhat = (EditText) findViewById(R.id.et_what);
        etPrice = (EditText) findViewById(R.id.et_price);

        saveNewItemStay = (Button) findViewById(R.id.saveNewItemStay);
        saveNewItemReturn = (Button) findViewById(R.id.saveNewItemReturn);
        SaveButtonClickListener listener = new SaveButtonClickListener();
        saveNewItemStay.setOnClickListener(listener);
        saveNewItemReturn.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_new, menu);
        return true;
    }

    /**
     * @Title: showDateDialog
     * @Description: 显示日期选择窗口
     * @param view
     */
    public void showDateDialog(View view) {
        DialogFragment dateDialogFragment = new DatePickerFragment();
        dateDialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private class SaveButtonClickListener implements OnClickListener {

        /*
         * (non-Javadoc)
         * 
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
        @Override
        public void onClick(View v) {

            String whatString = etWhat.getText().toString();
            String priceString = etPrice.getText().toString();
            String whenString = etWhen.getText().toString();

            if (TextUtils.isEmpty(whatString) || TextUtils.isEmpty(priceString) || TextUtils.isEmpty(whenString)) {
                Toast.makeText(AddNewActivity.this, "内容不能为空", Toast.LENGTH_LONG).show();
                return;
            }

            MoneyNoteDBUtil dbUtil = new MoneyNoteDBUtil(AddNewActivity.this);
            dbUtil.open();
            ContentValues values = new ContentValues();
            values.put(MoneyNote.COLUMN_NAME_WHAT, whatString);
            values.put(MoneyNote.COLUMN_NAME_PRICE, priceString);
            values.put(MoneyNote.COLUMN_NAME_WHEN, whenString);
            long rowId = dbUtil.insert(values);
            Log.i(TAG, "新插入事项的rowId: " + rowId);
            dbUtil.close();

            switch (v.getId()) {
            case R.id.saveNewItemStay:
                etWhat.setText("");
                etPrice.setText("");
                etWhen.setText("");
                break;
            case R.id.saveNewItemReturn:
                Intent intent = new Intent(AddNewActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                break;
            }
        }
    }
}
