package com.daveztong.moneynotebook;

import com.daveztong.moneynotebook.MoneyNoteContract.MoneyNote;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddNewActivity extends FragmentActivity {

    private static final String TAG = AddNewActivity.class.getSimpleName();
    private static final int  CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private EditText etWhen, etWhat, etPrice;
    private Button saveNewItemStay, saveNewItemReturn;
    private ImageView ivNewItem;
    private Long rowId = null;

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
        ivNewItem = (ImageView) findViewById(R.id.iv_new_item);

        saveNewItemStay = (Button) findViewById(R.id.saveNewItemStay);
        saveNewItemReturn = (Button) findViewById(R.id.saveNewItemReturn);
        SaveButtonClickListener listener = new SaveButtonClickListener();
        saveNewItemStay.setOnClickListener(listener);
        saveNewItemReturn.setOnClickListener(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();
        Intent incomingIntent = getIntent();
        Bundle extras = incomingIntent.getExtras();

        if (extras != null && extras.containsKey(MoneyNote.COLUMN_NAME_WHAT)) {
            etWhat.setText(extras.getString(MoneyNote.COLUMN_NAME_WHAT));
            etPrice.setText(extras.getDouble(MoneyNote.COLUMN_NAME_PRICE) + "");
            etWhen.setText(extras.getString(MoneyNote.COLUMN_NAME_WHEN));
            String imgPath = extras.getString(MoneyNote.COLUMN_NAME_IMAGE_PATH);
            if ("".equals(imgPath) || imgPath == null) {
                ivNewItem.setImageResource(R.drawable.default_img);
            } else {
                ivNewItem.setImageURI(Uri.parse(imgPath));
                CameraHelper.curImgPath = imgPath;
            }
            rowId = extras.getLong(MoneyNote._ID);
        }

        // 更新按钮名称
        if (rowId != null) {
            saveNewItemStay.setText(getString(R.string.btn_update_stay));
            saveNewItemReturn.setText(getString(R.string.btn_update_return));
        }
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
                Toast.makeText(AddNewActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues values = new ContentValues();
            values.put(MoneyNote.COLUMN_NAME_IMAGE_PATH, CameraHelper.curImgPath);
            values.put(MoneyNote.COLUMN_NAME_WHAT, whatString);
            values.put(MoneyNote.COLUMN_NAME_PRICE, priceString);
            values.put(MoneyNote.COLUMN_NAME_WHEN, whenString);
            // 打开数据库
            MoneyNoteDBUtil dbUtil = new MoneyNoteDBUtil(AddNewActivity.this);
            dbUtil.open();

            // 如果时更新
            if (rowId != null) {
                dbUtil.update(values, rowId);
            } else {
                long rowId = dbUtil.insert(values);
                Log.i(TAG, "新增记录的rowId: " + rowId);
            }
            // 关闭数据库
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

    public void takePicture(View view) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        CameraHelper cameraHelper = new CameraHelper(this);
        Uri imgUri = cameraHelper.getOutputMediaFileUri(CameraHelper.MEDIA_TYPE_IMAGE);
        CameraHelper.tempPath = imgUri.toString();
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(openCameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK == resultCode && requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            CameraHelper.curImgPath = CameraHelper.tempPath;
            ivNewItem.setImageURI(Uri.parse(CameraHelper.tempPath));
        }
    }
}
