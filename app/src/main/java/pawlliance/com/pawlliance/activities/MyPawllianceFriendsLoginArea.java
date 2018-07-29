package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.popups.PopUpEditPassword;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class MyPawllianceFriendsLoginArea extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = MyPawllianceFriendsLoginArea.this;

    private Button myPawllianceFriendsBackToMainAreaButton;
    private ListView listViewPawllianceFriends;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pawlliance_friends_login_area);


        initObjects();
        initViews();
        initListener();

    }

    /**
     * This method is to initialize views to be used
     */
    private void initViews() {
        myPawllianceFriendsBackToMainAreaButton = (Button) findViewById(R.id.MyPawllianceFriendsBackToMainAreaButton);
        listViewPawllianceFriends = (ListView) findViewById(R.id.ListViewPawllianceFriends);

        // store user email address by getting the extra from login activity
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        String userEmail = (String) b.get("ownersEmailForPassOn");
        populateListView();
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){

        databaseHelper = new DatabaseHelper(activity);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListener() {
        myPawllianceFriendsBackToMainAreaButton.setOnClickListener(this);;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.MyPawllianceFriendsBackToMainAreaButton:
                // storing the user email for pass on to next class
                Intent previousMainAreaIntent = getIntent();
                Bundle b = previousMainAreaIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                // Intent to go back to cockpit
                Intent backToMainAreaActivityIntent = new Intent(activity, LoginAreaMain.class);
                backToMainAreaActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(backToMainAreaActivityIntent);
                break;
        }
    }

    private void populateListView(){
        ArrayList<User> userList = databaseHelper.getAllUser();

        UserListAdapter adapter = new UserListAdapter(getBaseContext(), R.layout.layout_pawlliance_friends, userList);
        listViewPawllianceFriends.setAdapter(adapter);
    }

    // DNC - class closing
}
