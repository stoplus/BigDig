package com.example.den.bigdig;
/**
 * Created by den on 26.02.2018.
 */

import android.net.Uri;
import android.provider.BaseColumns;

public class ContractLinks {

    public static final String AUTHORITY = "com.example.den.bigdig.Links";
    public static final String PATH_LINKS_DATA = "/links_data";
    public static final String PATH_STATUS_VALUE = "/status_value";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + PATH_LINKS_DATA);

    public static final String CONTENT_LINKS_LIST = "vnd.android.cursor.dir/vnd.com.example.den.bigdig.links_data";
    public static final String CONTENT_LINKS_ITEM = "vnd.android.cursor.item/vnd.com.example.den.bigdig.links_data";

    public static final String DATABASE_NAME = "links";
    public static final int DATABASE_VERSION = 1;


    public static class Links implements BaseColumns {

        private Links() {
        }

        public static final String TABLE_NAME_LINK_DATA = "link_data";
        public static final String ID = "_id";
        public static final String NAME_LINK = "link";
        public static final String STATUS_LINK = "status_link";
        public static final String TIME = "time";

        public static final String TABLE_NAME_STATUS = "status";
        public static final String STATUS_VALUE = "value";
    }//class Links
}//class ContractLinks
