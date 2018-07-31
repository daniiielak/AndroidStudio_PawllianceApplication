package pawlliance.com.pawlliance.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import pawlliance.com.pawlliance.R;

public class UserListAdapter extends BaseAdapter{

    LayoutInflater mInflater;
    ArrayList<String> dogNamesList;
    ArrayList<String> cityList;
    ArrayList<String> dogBreedList;
    ArrayList<String> dogDescriptionList;


    public UserListAdapter(@NonNull Context context, ArrayList<String> dogNamesList, ArrayList<String> cityList, ArrayList<String> dogBreedList, ArrayList<String> dogDescriptionList) {
        this.dogNamesList = dogNamesList;
        this.cityList = cityList;
        this.dogBreedList = dogBreedList;
        this.dogDescriptionList = dogDescriptionList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return dogNamesList.size();
    }

    @Override
    public Object getItem(int i){
        return dogNamesList.get(i);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @NonNull
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = mInflater.inflate(R.layout.layout_pawlliance_friends, null);

        TextView layoutFriendsDogName = (TextView) v.findViewById(R.id.LayoutFriendsDogName);
        TextView layoutFriendsDogCity = (TextView) v.findViewById(R.id.LayoutFriendsDogCity);
        TextView layoutFriendsDogBreed = (TextView) v.findViewById(R.id.LayoutFriendsDogBreed);
        TextView layoutFriendsDogDescription = (TextView) v.findViewById(R.id.LayoutFriendsDogDescription);

        // get the user information that we need for item layout
        String dogName = dogNamesList.get(i);
        String city = cityList.get(i);
        String dogBreed = dogBreedList.get(i);
        String description = dogDescriptionList.get(i);

        layoutFriendsDogName.setText(dogName);
        layoutFriendsDogCity.setText(city);
        layoutFriendsDogBreed.setText(dogBreed);
        layoutFriendsDogDescription.setText(description);

        return v;
    }
}