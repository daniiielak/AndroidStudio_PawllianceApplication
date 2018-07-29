package pawlliance.com.pawlliance.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.activities.EditProfileInformationMainLoginArea;
import pawlliance.com.pawlliance.activities.LoginAreaMain;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class PopUpThanksForTheDogWalk extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = PopUpThanksForTheDogWalk.this;

    private Button popUpThanksForTheDogWalkBackToCockpitButton;
    private TextView popUpThanksForTheDogWalkWalkedDogOwnerDogTextView;

    private DatabaseHelper databaseHelper;
    private User currentUser;

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

        // getting Display Metrics of screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // saving display metrics width and height and set Pop-Up Window smaller than background
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.8));

        popUpThanksForTheDogWalkBackToCockpitButton = (Button) findViewById(R.id.PopUpThanksForTheDogWalkBackToCockpitButton);

        // get user-email for DB
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        String userEmail = (String) b.get("ownersEmailForPassOn");
        currentUser = databaseHelper.getSpecificUser(userEmail);

        // set text view to dog name
        popUpThanksForTheDogWalkWalkedDogOwnerDogTextView = (TextView) findViewById(R.id.PopUpThanksForTheDogWalkWalkedDogOwnerDogTextView);
        String dogName = currentUser.getDogName();
        popUpThanksForTheDogWalkWalkedDogOwnerDogTextView.setText(dogName);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
    }

    /**
     * This method is to initialize listeners
     */
    public void initListener() {
        popUpThanksForTheDogWalkBackToCockpitButton.setOnClickListener(this);
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
        }
    }

    // DNC - class closing
}
