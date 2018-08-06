package pawlliance.com.pawlliance.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.activities.AppEntryPoint;
import pawlliance.com.pawlliance.activities.EditProfileInformationMainLoginArea;
import pawlliance.com.pawlliance.activities.LoginAreaMain;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.model.WalkingActivity;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class PopUpDeleteWalkingActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = PopUpDeleteWalkingActivity.this;


    private Button popUpDeleteWalkingActivityButton;
    private Button popUpDontDeleteWalkingActivityButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;
    private WalkingActivity currentWalkingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_delete_walking);

        initViews();
        initObjects();
        initListener();
    }

    /**
     * This method is to initialize views to be used
     */
    private void initViews() {

        // getting Display Metrics of screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // saving display metrics width and height and set Pop-Up Window smaller than background
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.6));

        popUpDeleteWalkingActivityButton = (Button) findViewById(R.id.PopUpDeleteWalkingActivityButton);
        popUpDontDeleteWalkingActivityButton = (Button) findViewById(R.id.PopUpDontDeleteWalkingActivityButton);
    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
        currentWalkingActivity = new WalkingActivity();
    }

    /**
     * This method is to initialize listeners
     */
    public void initListener() {
        popUpDeleteWalkingActivityButton.setOnClickListener(this);
        popUpDontDeleteWalkingActivityButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PopUpDontDeleteWalkingActivityButton:
                // storing the user email for pass on to next class
                Intent previousThanksForYourDogWalkActivity = getIntent();
                Bundle b = previousThanksForYourDogWalkActivity.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                int passOnWalkingID = (Integer) b.get("walkingIDForPassOn");

                //Intent for previous class
                Intent goBackToThanksForYourWalkIntent = new Intent(activity, PopUpThanksForTheDogWalk.class);
                goBackToThanksForYourWalkIntent.putExtra("ownersEmailForPassOn", userEmail);
                goBackToThanksForYourWalkIntent.putExtra("walkingIDForPassOn", passOnWalkingID);
                startActivity(goBackToThanksForYourWalkIntent);
                break;

            case R.id.PopUpDeleteWalkingActivityButton:
                // set text view to user email address by getting the extra from previous activity
                previousThanksForYourDogWalkActivity = getIntent();
                b = previousThanksForYourDogWalkActivity.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                passOnWalkingID = (Integer) b.get("walkingIDForPassOn");

                // getting specific user based on email address to update description
                currentWalkingActivity = databaseHelper.getSpecificWalkingActivity(passOnWalkingID);
                databaseHelper.deleteWalkingActivity(currentWalkingActivity);

                //Intent for previous class
                Intent goBackToLoginMainAreaIntent = new Intent(activity, LoginAreaMain.class);
                goBackToLoginMainAreaIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToLoginMainAreaIntent);
                break;
        }
    }
    // DNC - class closing tag
}
