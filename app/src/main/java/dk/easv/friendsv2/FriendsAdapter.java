package dk.easv.friendsv2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;

import dk.easv.friendsv2.Model.BEFriend;

public class FriendsAdapter extends ArrayAdapter<BEFriend> {

    private ArrayList<BEFriend> friends;

    FriendsAdapter(@NonNull Context context, @NonNull ArrayList<BEFriend> friends) {
        super(context, 0, friends);
        this.friends = friends;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_friend, null);
        }




        BEFriend friend = friends.get(position);
        TextView txtScore = view.findViewById(R.id.friendName);
        txtScore.setText(friend.getName());



        ImageView imageView = view.findViewById(R.id.thumbnail);

        if(friend.getThumbnailFilePath() != null){
            Bitmap  bitmap= BitmapFactory.decodeFile(friend.getThumbnailFilePath());
            imageView.setImageBitmap(bitmap);}
        else
        imageView.setImageResource(R.mipmap.matesome);

        return view;
    }
}