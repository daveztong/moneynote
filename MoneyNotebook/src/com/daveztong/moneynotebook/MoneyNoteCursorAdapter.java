/**
 * Project name: MoneyNotebook
 * Package name: com.daveztong.moneynotebook
 * Filename: MoneyNoteCursorAdapter.java
 * Created time: Oct 30, 2013
 * Copyright: Copyright(c) 2013. All Rights Reserved.
 */

package com.daveztong.moneynotebook;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.daveztong.moneynotebook.MoneyNoteContract.MoneyNote;

/**
 * @ClassName: MoneyNoteCursorAdapter
 * @Description: TODO
 * @author tangwei
 * @date Oct 30, 2013 1:04:51 PM
 * 
 */
public class MoneyNoteCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater = null;

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param context 上下文对象
     * @param c Cursor
     * @param flags Control the cursor's behavior
     */
    public MoneyNoteCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = LayoutInflater.from(context);
    }

    public MoneyNoteCursorAdapter(Context context, Cursor c, int flags, boolean chkVisiable) {
        super(context, c, flags);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.cp_money_note_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor == null)
            return;
        String name = cursor.getString(cursor.getColumnIndexOrThrow(MoneyNote.COLUMN_NAME_WHAT));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(MoneyNote.COLUMN_NAME_PRICE));
        String when = cursor.getString(cursor.getColumnIndexOrThrow(MoneyNote.COLUMN_NAME_WHEN));
        String imagepath = cursor.getString(cursor.getColumnIndexOrThrow(MoneyNote.COLUMN_NAME_IMAGE_PATH));

        ViewHolder viewHolder = null;
        if (view.getTag() == null) {
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_what);
            viewHolder.tvPrice = (TextView) view.findViewById(R.id.tv_price);
            viewHolder.tvWhen = (TextView) view.findViewById(R.id.tv_when);
            viewHolder.ivIcon = (ImageView) view.findViewById(R.id.iv_item);
            viewHolder.chkDel = (CheckBox) view.findViewById(R.id.del_chk);

            // improve performance
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvName.setText(name);
        viewHolder.tvPrice.setText(price);
        viewHolder.tvWhen.setText(when);
        if (!"".equals(imagepath) && imagepath != null) {
            imagepath = imagepath.substring(7);
            Bitmap bmp = BitmapHelper.decodeSampledBitmapFromFile(imagepath, 50, 50);
            viewHolder.ivIcon.setImageBitmap(bmp);
        }
        viewHolder.chkDel.setChecked(false);

    }

    public class ViewHolder {
        TextView tvName, tvPrice, tvWhen;
        ImageView ivIcon;
        CheckBox chkDel;
    }

}
