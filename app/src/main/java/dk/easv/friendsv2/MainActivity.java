package dk.easv.friendsv2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

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

        friend.setName(updatedFriend.getName());
        friend.setBirthday(updatedFriend.getBirthday());
        friend.setEmail(updatedFriend.getEmail());
        friend.setPhone(updatedFriend.getPhone());
        friend.setThumbnailFilePath(updatedFriend.getThumbnailFilePath());
        friend.setURL(updatedFriend.getURL());
    }
    private void removeFriend(){
        m_friends.remove(entryPosition);
    }
    
    private void setFriendsAdapter(ArrayList<BEFriend> friends) {
        arrayAdapter = new FriendsAdapter(getWindow().getContext(), friends);
        arrayAdapter.notifyDataSetChanged();
        setListAdapter(arrayAdapter);
    }
}
