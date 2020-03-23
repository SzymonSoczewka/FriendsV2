package dk.easv.friendsv2;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dk.easv.friendsv2.Model.BEFriend;
import dk.easv.friendsv2.Model.Friends;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_SHOW_DETAILS = 1;
    static final int RESULT_DELETED = 2137;
    static final int RESULT_CREATED = 1234;
    public static String TAG = "Friend2";
    private FriendsAdapter arrayAdapter;
    private ListView listView;
    ArrayList<BEFriend> m_friends;
    int entryPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("MyFriends");
        m_friends = new Friends().getAll();
        listView = findViewById(R.id.listView);
        setFriendsAdapter(m_friends);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent x = new Intent(getWindow().getContext(), DetailActivity.class);
                BEFriend friend = m_friends.get(position);
                entryPosition = position;
                x.putExtra("friend",friend);
                x.putExtra("modeUpdate",true);
                startActivityForResult(x,REQUEST_SHOW_DETAILS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SHOW_DETAILS && resultCode == RESULT_OK) {
            updateFriend(data);
        }else if(requestCode == REQUEST_SHOW_DETAILS && resultCode == RESULT_DELETED){
            removeFriend();
        }
        else if(requestCode == REQUEST_SHOW_DETAILS && resultCode == RESULT_CREATED){
            createFriend(data);
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
    private void createFriend(Intent data){
        BEFriend newFriend = (BEFriend) data.getSerializableExtra("friend");
        m_friends.add(newFriend);
    }
    private void removeFriend(){
        m_friends.remove(entryPosition);
    }

    private void setFriendsAdapter(ArrayList<BEFriend> friends) {
        arrayAdapter = new FriendsAdapter(getWindow().getContext(), friends);
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);
    }

    public static Comparator<BEFriend> friendNameComparatorAsc = new Comparator<BEFriend>() {
        @Override
        public int compare(BEFriend friend, BEFriend friend2) {
            //ascending order
            return friend.getName().compareTo(friend2.getName());
        }
    };
    public static Comparator<BEFriend> friendNameComparatorDesc = new Comparator<BEFriend>() {
        @Override
        public int compare(BEFriend friend, BEFriend friend2) {
            //descending order
            return friend2.getName().compareTo(friend.getName());
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addButt:
                Intent x = new Intent(getWindow().getContext(), DetailActivity.class);
                BEFriend friend = new BEFriend("Name","+45 12 34 56 78",null,null,null);
                x.putExtra("modeUpdate",false);
                x.putExtra("friend",friend);
                startActivityForResult(x,REQUEST_SHOW_DETAILS);
                break;
            case R.id.sortByNameAsc:
                Collections.sort(m_friends,friendNameComparatorAsc);
                break;
            case R.id.sortByNameDesc:
                Collections.sort(m_friends,friendNameComparatorDesc);
                break;
        }
        arrayAdapter.notifyDataSetChanged();
        return true;
    }

}
