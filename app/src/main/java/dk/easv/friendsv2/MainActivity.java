package dk.easv.friendsv2;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import dk.easv.friendsv2.Model.BEFriend;
import dk.easv.friendsv2.Model.Friends;

public class MainActivity extends ListActivity {
    static final int REQUEST_SHOW_DETAILS = 1;
    public static String TAG = "Friend2";
    ListAdapter adapter;
    Friends m_friends;
    int entryPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Friends v2");
        m_friends = new Friends();
        setAdapter(m_friends);
    }


    @Override
    public void onListItemClick(ListView parent, View v, int position,
                                long id) {

        Intent x = new Intent(this, DetailActivity.class);
        Log.d(TAG, "Detail activity will be started");
        BEFriend friend = m_friends.getAll().get(position);
        entryPosition = position;
        x.putExtra("friend",friend);
        startActivityForResult(x,REQUEST_SHOW_DETAILS);
        Log.d(TAG, "Detail activity is started");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SHOW_DETAILS && resultCode == RESULT_OK) {
            updateFriend(data);
        }
    }
    private void updateFriend(Intent data){
        BEFriend updatedFriend = (BEFriend) data.getSerializableExtra("friend");
        BEFriend friend = m_friends.getAll().get(entryPosition);
        updateName(friend,updatedFriend.getName());
        updateEmail(friend,updatedFriend.getEmail());
        updatePhone(friend,updatedFriend.getPhone());
        updateURL(friend,updatedFriend.getURL());
        updatePicture(friend,updatedFriend.getThumbnailFilePath());
        updateFavorite(friend,updatedFriend.isFavorite());
    }

    private void updateName(BEFriend friend,String name){
        if(!isNullOrEmpty(name)) {
            friend.setName(name);
            setAdapter(m_friends);
        }
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
    private void updateFavorite(BEFriend friend,boolean favorite){
            friend.setFavorite(favorite);
    }

    public static boolean isNullOrEmpty(String param) {
        return param == null || param.length() == 0;
    }
    private void setAdapter(Friends friends){
        adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        friends.getNames());
        setListAdapter(adapter);
    }
}
