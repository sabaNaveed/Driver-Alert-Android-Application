package com.example.dellpc.fyp1;

import android.provider.BaseColumns;

/**
 * Created by Zainab Nazir on 08/05/2019.
 */

public class DBContract {
    public static abstract class USER implements BaseColumns
    {
        public static final String TABLE_NAME = "Users";
        public static final String COL_FULL_NAME = "full_name";
        public static final String COL_NUMBER = "number";

    }
}

