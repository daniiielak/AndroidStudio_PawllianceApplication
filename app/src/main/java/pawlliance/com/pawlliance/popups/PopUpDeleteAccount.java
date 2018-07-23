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
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class PopUpDeleteAccount extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = PopUpDeleteAccount.this;

    private Button popUpDeleteAccountButton;
    private Button popUpDontDeleteAccountButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_delete_account);

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

        popUpDeleteAccountButton = (Button) findViewById(R.id.PopUpDeleteAccountButton);
        popUpDontDeleteAccountButton = (Button) findViewById(R.id.PopUpDontDeleteAccountButton);
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
        popUpDeleteAccountButton.setOnClickListener(this);
        popUpDontDeleteAccountButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PopUpDontDeleteAccountButton:
                // storing the user email for pass on to next class
                Intent previousEditProfileInformationIntent = getIntent();
                Bundle b = previousEditProfileInformationIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for previous class
                Intent goBackToEditProfileInformationPageIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                goBackToEditProfileInformationPageIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToEditProfileInformationPageIntent);
                break;

            case R.id.PopUpDeleteAccountButton:
                // set text view to user email address by getting the extra from previous activity
                previousEditProfileInformationIntent = getIntent();
                b = previousEditProfileInformationIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                System.out.println("This is the owner's email: " + userEmail);

                // getting specific user based on email address to update description
                currentUser = databaseHelper.getSpecificUser(userEmail);
                databaseHelper.deleteUser(currentUser);

                //Intent for previous class
                Intent goBackToAppEntryPointPageIntent = new Intent(activity, AppEntryPoint.class);
                startActivity(goBackToAppEntryPointPageIntent);
                break;
        }
    }

    // DNC - class closing
}
