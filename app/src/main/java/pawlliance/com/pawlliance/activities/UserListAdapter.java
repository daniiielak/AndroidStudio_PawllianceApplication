package pawlliance.com.pawlliance.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.model.User;

public class UserListAdapter extends ArrayAdapter<User>{

    private static final String TAG ="UserListAdapter";
    private Context mContext;
    int mResource;

    public UserListAdapter(@NonNull Context context, int resource, ArrayList<User> objects) {
        super(context, resource);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // get the user information that we need for item layout
        String dogName = getItem(position).getDogName();
        String city = getItem(position).getCity();
        String dogBreed = getItem(position).getDogBreed();
        String description = getItem(position).getDescription();

        // creating the user object with the information
        User currentUser = new User(dogName, city, dogBreed, description);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.layout_pawlliance_friends, parent, false);

        TextView layoutFriendsDogName = (TextView) convertView.findViewById(R.id.LayoutFriendsDogName);
        TextView layoutFriendsDogCity = (TextView) convertView.findViewById(R.id.LayoutFriendsDogCity);
        TextView layoutFriendsDogBreed = (TextView) convertView.findViewById(R.id.LayoutFriendsDogBreed);
        TextView layoutFriendsDogDescription = (TextView) convertView.findViewById(R.id.LayoutFriendsDogDescription);

        layoutFriendsDogName.setText(currentUser.getDogName());
        layoutFriendsDogCity.setText(currentUser.getCity());
        layoutFriendsDogBreed.setText(currentUser.getDogBreed());
        layoutFriendsDogDescription.setText(currentUser.getDescription());

        return convertView;
    }
}
