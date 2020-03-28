package dk.easv.friendsv2.DB;

import java.util.ArrayList;

import dk.easv.friendsv2.Model.BEFriend;

public interface IFriendsDB {

    long insert(BEFriend friend);

    void deleteAll();

    ArrayList<BEFriend> selectAll();

    void update(BEFriend friend);

}
