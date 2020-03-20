package dk.easv.friendsv2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Date;

import dk.easv.friendsv2.Model.BEFriend;
import dk.easv.friendsv2.Model.Friends;

public class MainActivity extends ListActivity {
    static final int REQUEST_SHOW_DETAILS = 1;
    static final int RESULT_DELETED = 2137;
    public static String TAG = "Friend2";
    private FriendsAdapter arrayAdapter;
    ArrayList<BEFriend> m_friends;
    int entryPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("MyFriends");
        m_friends = new Friends().getAll();
        setFriendsAdapter(m_friends);
    }


    @Override
    public void onListItemClick(ListView parent, View v, int position,
                                long id) {

        Intent x = new Intent(this, DetailActivity.class);
        BEFriend friend = m_friends.get(position);
        entryPosition = position;
        x.putExtra("friend",friend);
        startActivityForResult(x,REQUEST_SHOW_DETAILS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SHOW_DETAILS && resultCode == RESULT_OK) {
            updateFriend(data);
        }else if(requestCode == REQUEST_SHOW_DETAILS && resultCode == RESULT_DELETED){
            removeFriend();
        }
        arrayAdapter.notifyDataSetChanged();
    }
    private void updateFriend(Intent data){
        BEFriend updatedFriend = (BEFriend) data.getSerializableExtra("friend");
        BEFriend friend = m_friends.get(entryPosition);
        updateName(friend,updatedFriend.getName());
        updateEmail(friend,updatedFriend.getEmail());
        updatePhone(friend,updatedFriend.getPhone());
        updateURL(friend,updatedFriend.getURL());
        updatePicture(friend,updatedFriend.getThumbnailFilePath());
        updateBirthday(friend,updatedFriend.getBirthday());
    }
    private void removeFriend(){
        m_friends.remove(entryPosition);
    }

    private void updateName(BEFriend friend,String name){
        if(!isNullOrEmpty(name))
            friend.setName(name);

    }
    private void updatePhone(BEFriend friend,String phone){
        if(!isNullOrEmpty(phone))
            friend.setPhone(phone);
    }
    private void updateEmail(BEFriend friend,String email){
        if(!isNullOrEmpty(email))
            friend.setEmail(email);
    }
    private void updateURL(BEFriend friend,String url){
        if(!isNullOrEmpty(url))
            friend.setURL(url);
    }
    private void updatePicture(BEFriend friend,String filePath){
        if(!isNullOrEmpty(filePath))
            friend.setThumbnailFilePath(filePath);
    }
    private void updateBirthday(BEFriend friend, String birthday){
        if(!isNullOrEmpty(birthday)) {
            Log.v("Data: ",birthday);
            friend.setBirthday(birthday);
        }

    }

    public static boolean isNullOrEmpty(String param) {
        return param == null || param.length() == 0;
    }

    private void setFriendsAdapter(ArrayList<BEFriend> friends) {
        arrayAdapter = new FriendsAdapter(getWindow().getContext(), friends);
        arrayAdapter.notifyDataSetChanged();
        setListAdapter(arrayAdapter);
    }
}
