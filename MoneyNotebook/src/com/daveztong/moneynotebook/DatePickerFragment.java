/**
 * Project name: MoneyNotebook
 * Package name: com.daveztong.moneynotebook
 * Filename: DatePickerFragment.java
 * Created time: Oct 28, 2013
 * Copyright: Copyright(c) 2013. All Rights Reserved.
 */

package com.daveztong.moneynotebook;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

/**
 * @ClassName: DatePickerFragment
 * @Description: TODO
 * @author tangwei
 * @date Oct 28, 2013 11:57:24 AM
 * 
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String strDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        ((AddNewActivity) getActivity()).getEtWhen().setText(strDate);
    }

}
