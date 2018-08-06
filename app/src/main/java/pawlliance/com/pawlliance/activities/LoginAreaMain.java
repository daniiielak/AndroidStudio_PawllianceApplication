package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pawlliance.com.pawlliance.R;

public class LoginAreaMain extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginAreaMain.this;

    // set all variables for input fields and layouts
    TextView loginAreaEmailTextView;
    Button loginAreaLetsGoForAWalkButton;
    Button loginAreaMyWalkingActivitiesButton;
    Button loginAreaFindNewFurryFriendsButton;
    Button loginAreaUpdateProfileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_area_main);

        initViews();
        initListener();
    }

    /**
     * This method is to initialize views to be used
     */
    private void initViews(){

        loginAreaLetsGoForAWalkButton = (Button) findViewById(R.id.LoginAreaLetsGoForAWalkButton);
        loginAreaMyWalkingActivitiesButton = (Button) findViewById(R.id.LoginAreaMyWalkingActivitiesButton);
        loginAreaFindNewFurryFriendsButton = (Button) findViewById(R.id.LoginAreaFindNewFurryFriendsButton);
        loginAreaUpdateProfileButton = (Button) findViewById(R.id.LoginAreaUpdateProfileButton);

        // set text view to user email address by getting the extra from login activity
        String userEmail = "You're logged in!";
        loginAreaEmailTextView = (TextView) findViewById(R.id.LoginAreaEmailTextView);
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        userEmail = (String) b.get("ownersEmailForPassOn");
        loginAreaEmailTextView.setText(userEmail);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListener(){
        loginAreaLetsGoForAWalkButton.setOnClickListener(this);
        loginAreaMyWalkingActivitiesButton.setOnClickListener(this);
        loginAreaFindNewFurryFriendsButton.setOnClickListener(this);
        loginAreaUpdateProfileButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.LoginAreaLetsGoForAWalkButton:
                // storing the user email for pass on to next class
                Intent previousLoginAreaIntent = getIntent();
                Bundle b = previousLoginAreaIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for maps class
                Intent mapsActivityIntent = new Intent(activity, MapsActivity.class);
                mapsActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(mapsActivityIntent);
                break;

            case R.id.LoginAreaMyWalkingActivitiesButton:
                // storing the user email for pass on to next class
                previousLoginAreaIntent = getIntent();
                b = previousLoginAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for new friends class
                Intent myPawllianceActivities = new Intent(activity, MyWalkingActivitiesLoginArea.class);
                myPawllianceActivities.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(myPawllianceActivities);
                break;

            case R.id.LoginAreaFindNewFurryFriendsButton:
                // storing the user email for pass on to next class
                previousLoginAreaIntent = getIntent();
                b = previousLoginAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for new friends class
                Intent findNewFriendsActivityIntent = new Intent(activity, MyPawllianceFriendsLoginArea.class);
                findNewFriendsActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(findNewFriendsActivityIntent);
                break;

            case R.id.LoginAreaUpdateProfileButton:
                // storing the user email for pass on to next class
                previousLoginAreaIntent = getIntent();
                b = previousLoginAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for new friends class
                Intent updateProfileActivityIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                updateProfileActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(updateProfileActivityIntent);
                break;
        }
    }

}
