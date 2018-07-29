package pawlliance.com.pawlliance.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.LayoutInflater;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.helper.InputValidation;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.popups.PopUpDeleteAccount;
import pawlliance.com.pawlliance.popups.PopUpEditBirthday;
import pawlliance.com.pawlliance.popups.PopUpEditCity;
import pawlliance.com.pawlliance.popups.PopUpEditDescription;
import pawlliance.com.pawlliance.popups.PopUpEditDogBreed;
import pawlliance.com.pawlliance.popups.PopUpEditDogName;
import pawlliance.com.pawlliance.popups.PopUpEditPassword;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class EditProfileInformationMainLoginArea extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = EditProfileInformationMainLoginArea.this;

    private TextView profileInformationOwnerEmailTextView;
    private TextView profileInformationOwnerCityTextView;
    private TextView profileInformationOwnerDogNameTextView;
    private TextView profileInformationOwnerDogBreedTextView;
    private TextView profileInformationOwnerDogBirthdayTextView;
    private TextView profileInformationOwnerDogDescriptionTextView;

    private Button editProfileInformationUpdateInformationPasswordButton;
    private Button editProfileInformationUpdateInformationCityButton;
    private Button editProfileInformationUpdateInformationDogNameButton;
    private Button editProfileInformationUpdateInformationDogBreedButton;
    private Button editProfileInformationUpdateInformationDogBirthdayButton;
    private Button editProfileInformationUpdateInformationDogDescriptionButton;
    private Button editProfileInformationBackToMainAreaButton;
    private Button editProfileInformationDeleteProfileButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_information_main_login_area);


        initObjects();
        initViews();
        initListener();
    }


    /**
     * This method is to initialize views to be used
     */
    private void initViews() {

        editProfileInformationUpdateInformationPasswordButton = (Button) findViewById(R.id.EditProfileInformationUpdateInformationPasswordButton);
        editProfileInformationUpdateInformationCityButton = (Button) findViewById(R.id.EditProfileInformationUpdateInformationCityButton);
        editProfileInformationUpdateInformationDogNameButton = (Button) findViewById(R.id.EditProfileInformationUpdateInformationDogNameButton);
        editProfileInformationUpdateInformationDogBreedButton = (Button) findViewById(R.id.EditProfileInformationUpdateInformationDogBreedButton);
        editProfileInformationUpdateInformationDogBirthdayButton = (Button) findViewById(R.id.EditProfileInformationUpdateInformationDogBirthdayButton);
        editProfileInformationUpdateInformationDogDescriptionButton = (Button) findViewById(R.id.EditProfileInformationUpdateInformationDogDescriptionButton);
        editProfileInformationBackToMainAreaButton = (Button) findViewById(R.id.EditProfileInformationBackToMainAreaButton);
        editProfileInformationDeleteProfileButton = (Button) findViewById(R.id.EditProfileInformationDeleteProfileButton);

        // set text view to user email address by getting the extra from login activity
        profileInformationOwnerEmailTextView = (TextView) findViewById(R.id.ProfileInformationOwnerEmailTextView);
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        String userEmail = (String) b.get("ownersEmailForPassOn");
        profileInformationOwnerEmailTextView.setText(userEmail);

        // getting specific user based on email address to initialize views.
        currentUser = databaseHelper.getSpecificUser(userEmail);

        // TextView City
        String userCity = currentUser.getCity();
        profileInformationOwnerCityTextView = (TextView) findViewById(R.id.ProfileInformationOwnerCityTextView);
        profileInformationOwnerCityTextView.setText(userCity);

        // TextView Dog Name
        String dogName = currentUser.getDogName();
        profileInformationOwnerDogNameTextView = (TextView) findViewById(R.id.ProfileInformationOwnerDogNameTextView);
        profileInformationOwnerDogNameTextView.setText(dogName);

        // TextView Dog Breed
        String dogBreed = currentUser.getDogBreed();
        profileInformationOwnerDogBreedTextView = (TextView) findViewById(R.id.ProfileInformationOwnerDogBreedTextView);
        profileInformationOwnerDogBreedTextView.setText(dogBreed);

        // TextView Dog Birthday
        String dogBirthday= currentUser.getBirthday();
        profileInformationOwnerDogBirthdayTextView = (TextView) findViewById(R.id.ProfileInformationOwnerDogBirthdayTextView);
        profileInformationOwnerDogBirthdayTextView.setText(dogBirthday);

        // TextView Dog Description
        String dogDescription= currentUser.getDescription();
        profileInformationOwnerDogDescriptionTextView = (TextView) findViewById(R.id.ProfileInformationOwnerDogDescriptionTextView);
        profileInformationOwnerDogDescriptionTextView.setText(dogDescription);

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
        editProfileInformationUpdateInformationPasswordButton.setOnClickListener(this);;
        editProfileInformationUpdateInformationCityButton.setOnClickListener(this);;
        editProfileInformationUpdateInformationDogNameButton.setOnClickListener(this);;
        editProfileInformationUpdateInformationDogBreedButton.setOnClickListener(this);;
        editProfileInformationUpdateInformationDogBirthdayButton.setOnClickListener(this);;
        editProfileInformationUpdateInformationDogDescriptionButton.setOnClickListener(this);;
        editProfileInformationBackToMainAreaButton.setOnClickListener(this);;
        editProfileInformationDeleteProfileButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.EditProfileInformationUpdateInformationPasswordButton:
                // storing the user email for pass on to next class
                Intent previousMainAreaIntent = getIntent();
                Bundle b = previousMainAreaIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for description popup class
                Intent updatePasswordPopUpIntent = new Intent(activity, PopUpEditPassword.class);
                updatePasswordPopUpIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(updatePasswordPopUpIntent);
                break;

            case R.id.EditProfileInformationUpdateInformationCityButton:
                // storing the user email for pass on to next class
                previousMainAreaIntent = getIntent();
                b = previousMainAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for description popup class
                Intent updateCityPopUpIntent = new Intent(activity, PopUpEditCity.class);
                updateCityPopUpIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(updateCityPopUpIntent);
                break;

            case R.id.EditProfileInformationUpdateInformationDogNameButton:
                // storing the user email for pass on to next class
                previousMainAreaIntent = getIntent();
                b = previousMainAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for description popup class
                Intent updateDogNamePopUpIntent = new Intent(activity, PopUpEditDogName.class);
                updateDogNamePopUpIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(updateDogNamePopUpIntent);

                break;

            case R.id.EditProfileInformationUpdateInformationDogBreedButton:
                // storing the user email for pass on to next class
                previousMainAreaIntent = getIntent();
                b = previousMainAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for description popup class
                Intent updateDogBreedPopUpIntent = new Intent(activity, PopUpEditDogBreed.class);
                updateDogBreedPopUpIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(updateDogBreedPopUpIntent);
                break;

            case R.id.EditProfileInformationUpdateInformationDogBirthdayButton:
                // storing the user email for pass on to next class
                previousMainAreaIntent = getIntent();
                b = previousMainAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for description popup class
                Intent updateDogBirthdayPopUpIntent = new Intent(activity, PopUpEditBirthday.class);
                updateDogBirthdayPopUpIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(updateDogBirthdayPopUpIntent);
                break;

            case R.id.EditProfileInformationUpdateInformationDogDescriptionButton:
                // storing the user email for pass on to next class
                previousMainAreaIntent = getIntent();
                b = previousMainAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for description popup class
                Intent updateDogDescriptionPopUpIntent = new Intent(activity, PopUpEditDescription.class);
                updateDogDescriptionPopUpIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(updateDogDescriptionPopUpIntent);
                break;

            case R.id.EditProfileInformationBackToMainAreaButton:
                // storing the user email for pass on to next class
                previousMainAreaIntent = getIntent();
                b = previousMainAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for maps class
                Intent backToMainAreaActivityIntent = new Intent(activity, LoginAreaMain.class);
                backToMainAreaActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(backToMainAreaActivityIntent);
                break;

            case R.id.EditProfileInformationDeleteProfileButton:
                // storing the user email for pass on to next class
                previousMainAreaIntent = getIntent();
                b = previousMainAreaIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for maps class
                Intent deleteAccountPopUpIntent = new Intent(activity, PopUpDeleteAccount.class);
                deleteAccountPopUpIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(deleteAccountPopUpIntent);
                break;
        }
    }

    @Override
    public void onBackPressed(){
        // storing the user email for pass on to next class
        Intent previousMainAreaIntent = getIntent();
        Bundle b = previousMainAreaIntent.getExtras();
        String userEmail = (String) b.get("ownersEmailForPassOn");
        //Intent for maps class
        Intent backToMainAreaActivityIntent = new Intent(activity, LoginAreaMain.class);
        backToMainAreaActivityIntent.putExtra("ownersEmailForPassOn", userEmail);
        startActivity(backToMainAreaActivityIntent);
    }
}
