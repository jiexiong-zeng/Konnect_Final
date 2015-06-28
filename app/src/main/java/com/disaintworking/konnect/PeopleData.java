package com.disaintworking.konnect;

import android.provider.BaseColumns;import java.lang.String;

/**
 * Created by Swaggiott on 3/3/2015.
 */
public class PeopleData {
    public String mName;
    public float mNumber;
    public String mURI;

    public PeopleData(String name, float number, String uri) {
        mName = name;
        mNumber = number;
        mURI =  uri;
    }

    public static abstract class DataInfo implements BaseColumns
    {
        public static final String NAME = "name";
        public static final String NUMBER = "number";
        public static final String URI = "uri";
        public static final String TABLE_NAME = "people_table";
    }
}
