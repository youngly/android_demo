package com.remote.youngly.sqlitedatebasedemo;

import android.provider.BaseColumns;

/**
 * Created by youngly on 2018/3/23.
 * E-mail: youngly.yang@gometech.com.cn
 */

public final class FeedReaderContract {
    private FeedReaderContract() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
