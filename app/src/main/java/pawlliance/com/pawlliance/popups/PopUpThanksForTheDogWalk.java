package pawlliance.com.pawlliance.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.activities.EditProfileInformationMainLoginArea;
import pawlliance.com.pawlliance.activities.LoginAreaMain;
import pawlliance.com.pawlliance.activities.MapsActivity;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.model.WalkingActivity;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class PopUpThanksForTheDogWalk extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = PopUpThanksForTheDogWalk.this;

    private Button popUpThanksForTheDogWalkBackToCockpitButton;
    private Button popUpThanksForTheDogWalkEditDescriptionButton;
    private TextView popUpThanksForTheDogWalkWalkedDogOwnerDogTextView;
    private TextView popUpThanksForTheDogWalkWalkedDogOwnerDateTextView;
    private TextView popUpThanksForTheDogWalkWalkedDogOwnerDistanceTextView;
    private TextView popUpThanksForTheDogWalkWalkedDogOwnerDurationTextView;
    private TextView popUpThanksForTheDogWalkWalkedDogOwnerDescriptionTextView;

    private DatabaseHelper databaseHelper;
    private User currentUser;
    private WalkingActivity currentWalkingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_thanks_for_the_dog_walk);

        initObjects();
        initViews();
        initListener();
    }

    /**
     * This method is to initialize views to be used
     */
    private void initViews() {

        popUpThanksForTheDogWalkBackToCockpitButton = (Button) findViewById(R.id.PopUpThanksForTheDogWalkBackToCockpitButton);
        popUpThanksForTheDogWalkEditDescriptionButton = (Button) findViewById(R.id.PopUpThanksForTheDogWalkEditDescriptionButton);

        // get user-email and walkingID for DB
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        String userEmail = (String) b.get("ownersEmailForPassOn");
        currentUser = databaseHelper.getSpecificUser(userEmail);
        int currentWalkingID = (Integer) b.get("walkingIDForPassOn");
        currentWalkingActivity = databaseHelper.getSpecificWalkingActivity(currentWalkingID);

        // set text view to dog name
        popUpThanksForTheDogWalkWalkedDogOwnerDogTextView = (TextView) findViewById(R.id.PopUpThanksForTheDogWalkWalkedDogOwnerDogTextView);
        String dogName = currentUser.getDogName();
        popUpThanksForTheDogWalkWalkedDogOwnerDogTextView.setText(dogName);

        // set text view to date
        popUpThanksForTheDogWalkWalkedDogOwnerDateTextView = (TextView) findViewById(R.id.PopUpThanksForTheDogWalkWalkedDogOwnerDateTextView);
        String date = currentWalkingActivity.getWalkingDate();
        popUpThanksForTheDogWalkWalkedDogOwnerDateTextView.setText(date);

        // set text view to distance
        popUpThanksForTheDogWalkWalkedDogOwnerDistanceTextView = (TextView) findViewById(R.id.PopUpThanksForTheDogWalkWalkedDogOwnerDistanceTextView);
        Double distance = currentWalkingActivity.getTotalWalkingDistance();
        popUpThanksForTheDogWalkWalkedDogOwnerDistanceTextView.setText(distance.toString());

        // set text view to duration
        popUpThanksForTheDogWalkWalkedDogOwnerDurationTextView = (TextView) findViewById(R.id.PopUpThanksForTheDogWalkWalkedDogOwnerDurationTextView);
        Double duration = currentWalkingActivity.getTotalWalkingTime();
        popUpThanksForTheDogWalkWalkedDogOwnerDurationTextView.setText(duration.toString());

        // set text view to description
        popUpThanksForTheDogWalkWalkedDogOwnerDescriptionTextView = (TextView) findViewById(R.id.PopUpThanksForTheDogWalkWalkedDogOwnerDescriptionTextView);
        String walkingDescription = currentWalkingActivity.getWalkingDescription();
        popUpThanksForTheDogWalkWalkedDogOwnerDescriptionTextView.setText(walkingDescription);

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
        popUpThanksForTheDogWalkBackToCockpitButton.setOnClickListener(this);
        popUpThanksForTheDogWalkEditDescriptionButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PopUpThanksForTheDogWalkBackToCockpitButton:
                // storing the user email for pass on to next class
                Intent previousIntent = getIntent();
                Bundle b = previousIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for previous class
                Intent goBackToCockpitIntent = new Intent(activity, LoginAreaMain.class);
                goBackToCockpitIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToCockpitIntent);
                break;

            case R.id.PopUpThanksForTheDogWalkEditDescriptionButton:
                // storing the user email for pass on to next class
                previousIntent = getIntent();
                b = previousIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                int passOnWalkingID = currentWalkingActivity.getWalkingID();

                //Intent for description popup class
                Intent editWalkingDescriptionPopUp = new Intent (activity, PopUpEditWalkingDescription.class);
                editWalkingDescriptionPopUp.putExtra("ownersEmailForPassOn", userEmail);
                editWalkingDescriptionPopUp.putExtra("walkingIDForPassOn", passOnWalkingID);
                startActivity(editWalkingDescriptionPopUp);
                break;
        }
    }

    // DNC - class closing
}
