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
    Friends m_friends;
    int entryPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Friends v2");
        m_friends = new Friends();

        String[] friends;

        friends = m_friends.getNames();

        ListAdapter adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        friends);

        setListAdapter(adapter);

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
            updatePicture(data);
        }
    }
    private void updatePicture(Intent data){
        BEFriend updatedFriend = (BEFriend) data.getSerializableExtra("friend");
        m_friends.getAll().get(entryPosition).setThumbnailFilePath(updatedFriend.getThumbnailFilePath());
    }
}
