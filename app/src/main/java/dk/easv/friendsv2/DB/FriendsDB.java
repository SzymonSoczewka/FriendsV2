package dk.easv.friendsv2.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.easv.friendsv2.Model.BEFriend;

public class FriendsDB implements IFriendsDB{

    private static final String DATABASE_NAME = "sqlite.Friendsbase";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "Friends";

    private SQLiteDatabase mDatabase;
    private SQLiteStatement insertStmt;

    public FriendsDB(Context context) {

        OpenHelper openHelper = new OpenHelper(context);
        mDatabase = openHelper.getWritableDatabase();

        String INSERT = "insert into " + TABLE_NAME
                + "(name,address,email,phone,url,thumbnailFilePath,birthday) values (?,?,?,?,?,?,?)";

        insertStmt = mDatabase.compileStatement(INSERT);
    }

    public long insert(BEFriend friend) {
        insertStmt.bindString(1, friend.getName());
        insertStmt.bindString(2, friend.getAddress());
        insertStmt.bindString(3, friend.getEmail());
        insertStmt.bindString(4, friend.getPhone());
        insertStmt.bindString(5, friend.getURL());
        insertStmt.bindString(6, friend.getThumbnailFilePath());
        insertStmt.bindString(7, friend.getBirthday());
        return insertStmt.executeInsert();
    }

    public void deleteAll() {
        mDatabase.delete(TABLE_NAME, null, null);
    }

    public ArrayList<BEFriend> selectAll() {
        ArrayList<BEFriend> list = new ArrayList<BEFriend>();
        Cursor cursor = mDatabase.query(TABLE_NAME,
                new String[] {"id","name","address","email","phone","url","thumbnailFilePath","birthday" },
                null, null, null, null, "name");
        if (cursor.moveToFirst()) {
            do {
                Log.v("xD", cursor.getString(0));
                list.add(new BEFriend(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }
    public void update(BEFriend p)
    {

    }




    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "address TEXT, " +
                    "email TEXT, " +
                    "phone TEXT, " +
                    "url TEXT, " +
                    "thumbnailFilePath TEXT, " +
                    "birthday TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
