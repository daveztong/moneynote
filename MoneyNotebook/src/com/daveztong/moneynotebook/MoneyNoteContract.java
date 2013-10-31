/**
 * Project name: MoneyNotebook
 * Package name: com.daveztong.moneynotebook
 * Filename: MoneyNoteContract.java
 * Created time: Oct 25, 2013
 * Copyright: Copyright(c) 2013. All Rights Reserved.
 */

package com.daveztong.moneynotebook;

import android.provider.BaseColumns;

/**
 * @ClassName: MoneyNoteContract
 * @Description: MoneyNote contract class
 * @author tangwei
 * @date Oct 25, 2013 4:10:32 PM
 * 
 */
public final class MoneyNoteContract {

    /**
     * <p>
     * Title: MoneyNoteContract
     * </p>
     * <p>
     * Description:MoneyNoteContract. Leave it to default in case someone instance it accidently
     * </p>
     */
    public MoneyNoteContract() {
    }

    /**
     * @ClassName: MoneyNote
     * @Description: MoneyNote
     * @author tangwei
     * @date Oct 25, 2013 4:13:29 PM
     * 
     */
    public static class MoneyNote implements BaseColumns {
        public static final String TABLE_NAME_MoneyNote = "money_note";
        public static final String COLUMN_NAME_WHAT = "what";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_WHEN = "what_time";
        public static final String COLUMN_NAME_IMAGE_PATH  = "imagepath";
    }
}
