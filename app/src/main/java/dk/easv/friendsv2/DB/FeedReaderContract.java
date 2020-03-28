package dk.easv.friendsv2.DB;

import android.provider.BaseColumns;

final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        static final String DATABASE_NAME = "sqlite.Friendsbase";
        static final int    DATABASE_VERSION = 2;
        static final String TABLE_NAME = "Friends";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_THUMBNAIL_FILE_PATH = "thumbnailFilePath";
        public static final String COLUMN_NAME_BIRTHDAY = "birthday";
    }
}