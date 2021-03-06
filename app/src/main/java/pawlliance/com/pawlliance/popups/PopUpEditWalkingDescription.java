package pawlliance.com.pawlliance.popups;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.activities.EditProfileInformationMainLoginArea;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.model.WalkingActivity;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class PopUpEditWalkingDescription extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = PopUpEditWalkingDescription.this;

    private TextInputLayout walkingActivityWalkDescriptionTextInputLayout;
    private TextInputEditText walkingActivityUpdateDescriptionTextInputEditText;
    private Button popUpWalkingActivityDescriptionCancelButton;
    private Button popUpWalkingActivityDescriptionSaveButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;
    private WalkingActivity currentWalkingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_walking_description);

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

        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));

        walkingActivityWalkDescriptionTextInputLayout = (TextInputLayout) findViewById(R.id.WalkingActivityWalkDescriptionTextInputLayout);
        walkingActivityUpdateDescriptionTextInputEditText = (TextInputEditText) findViewById(R.id.WalkingActivityUpdateDescriptionTextInputEditText);
        popUpWalkingActivityDescriptionCancelButton = (Button) findViewById(R.id.PopUpWalkingActivityDescriptionCancelButton);
        popUpWalkingActivityDescriptionSaveButton = (Button) findViewById(R.id.PopUpWalkingActivityDescriptionSaveButton);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
        currentWalkingActivity = new WalkingActivity();
    }

    /**
     * This method is to initialize listeners
     */
    public void initListener(){
        popUpWalkingActivityDescriptionCancelButton.setOnClickListener(this);
        popUpWalkingActivityDescriptionSaveButton.setOnClickListener(this);
    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.PopUpWalkingActivityDescriptionCancelButton:
                // storing the user email for pass on to next class
                Intent previousEditProfileInformationIntent = getIntent();
                Bundle b = previousEditProfileInformationIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                int passOnWalkingID = (Integer) b.get("walkingIDForPassOn");

                //Intent for previous class
                Intent goBacktoWalkingActivity = new Intent(activity, PopUpThanksForTheDogWalk.class);
                goBacktoWalkingActivity.putExtra("ownersEmailForPassOn", userEmail);
                goBacktoWalkingActivity.putExtra("walkingIDForPassOn", passOnWalkingID);
                startActivity(goBacktoWalkingActivity);
                break;

            case R.id.PopUpWalkingActivityDescriptionSaveButton:
                // set text view to user email address by getting the extra from previous activity
                previousEditProfileInformationIntent = getIntent();
                b = previousEditProfileInformationIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                System.out.println("This is the owner's email: " + userEmail);
                passOnWalkingID = (Integer) b.get("walkingIDForPassOn");
                System.out.println("This is the walking ID: " + passOnWalkingID);

                // getting specific user based on email address to update description
                String newDescription = walkingActivityUpdateDescriptionTextInputEditText.getText().toString();
                if (!newDescription.equals("")) {
                    currentWalkingActivity = databaseHelper.getSpecificWalkingActivity(passOnWalkingID);
                    currentWalkingActivity.setWalkingDescription(newDescription);
                    databaseHelper.updateWalkingActivity(currentWalkingActivity);
                }

                //Intent for previous class
                goBacktoWalkingActivity = new Intent(activity, PopUpThanksForTheDogWalk.class);
                goBacktoWalkingActivity.putExtra("ownersEmailForPassOn", userEmail);
                goBacktoWalkingActivity.putExtra("walkingIDForPassOn", passOnWalkingID);
                startActivity(goBacktoWalkingActivity);
                break;
        }
    }

    // DNC - class closing
}
