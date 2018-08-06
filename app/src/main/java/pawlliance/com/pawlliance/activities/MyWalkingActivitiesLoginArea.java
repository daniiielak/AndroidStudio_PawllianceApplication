package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class MyWalkingActivitiesLoginArea extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = MyWalkingActivitiesLoginArea.this;

    private Button myWalkingActivitiesBackToMainAreaButton;
    private Button myWalkingActivityDeleteWalkingActivityButton;
    private ListView listViewMyWalkingActivities;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_walking_activities_login_area);

        initObjects();
        initViews();
        initListener();
    }

    /**
     * This method is to initialize views to be used
     */
    private void initViews() {
        myWalkingActivitiesBackToMainAreaButton = (Button) findViewById(R.id.MyWalkingActivitiesBackToMainAreaButton);
        myWalkingActivityDeleteWalkingActivityButton = (Button) findViewById(R.id.MyWalkingActivityDeleteWalkingActivityButton);
        listViewMyWalkingActivities = (ListView) findViewById(R.id.ListViewMyWalkingActivities);

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
        myWalkingActivitiesBackToMainAreaButton.setOnClickListener(this);;
        //myWalkingActivityDeleteWalkingActivityButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.MyWalkingActivitiesBackToMainAreaButton:
                // storing the user email for pass on to next class
                Intent previousMainAreaIntent = getIntent();
                Bundle b = previousMainAreaIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                // Intent to go back to cockpit
                Intent backToMainAreaActivityIntent = new Intent(activity, LoginAreaMain.class);
                backToMainAreaActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(backToMainAreaActivityIntent);
                break;
            //case R.id.MyWalkingActivityDeleteWalkingActivityButton:

        }
    }

    private void populateListView(){

        // getting info from user for walking activities
        Intent previousMainAreaIntent = getIntent();
        Bundle b = previousMainAreaIntent.getExtras();
        String userEmail = (String) b.get("ownersEmailForPassOn");
        User currentUser = databaseHelper.getSpecificUser(userEmail);
        System.out.println("This is the current user Email " + currentUser.getEmail());
        int currentUserID = currentUser.getUserID();
        System.out.println("This is the current userID: " + currentUserID);

        ArrayList<String> walkingActivitiesWalkingDatesList = databaseHelper.getAllWalkingActivityWalkingDates(currentUserID);
        ArrayList<String> walkingActivitiesDogsList = databaseHelper.getAllWalkingActivityDogs(currentUserID);
        ArrayList<Double> walkingActivitiesTotalWalkingTimeList = databaseHelper.getAllWalkingActivityTotalWalkingTime(currentUserID);
        ArrayList<Double> walkingActivitiesTotalWalkingDistanceList = databaseHelper.getAllWalkingActivityTotalWalkingDistance(currentUserID);
        ArrayList<String> walkingActivitiesDescriptionList = databaseHelper.getAllWalkingActivityDescriptions(currentUserID);

        WalkingActivityListAdapter adapter = new WalkingActivityListAdapter(this, walkingActivitiesWalkingDatesList, walkingActivitiesDogsList, walkingActivitiesTotalWalkingTimeList, walkingActivitiesTotalWalkingDistanceList, walkingActivitiesDescriptionList);
        listViewMyWalkingActivities.setAdapter(adapter);
    }

    // DNC - class closing tag
}
