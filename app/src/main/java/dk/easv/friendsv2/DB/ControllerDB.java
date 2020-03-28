package dk.easv.friendsv2.DB;

import android.content.Context;

public class ControllerDB {
    public static IFriendsDB getInstance(Context c)
    {
        return new FriendsDB(c);
    }
}
