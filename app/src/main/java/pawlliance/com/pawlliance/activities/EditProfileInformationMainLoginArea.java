package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.helper.InputValidation;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class EditProfileInformationMainLoginArea extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = EditProfileInformationMainLoginArea.this;

    TextView profileInformationOwnerNameTextView;
    TextView profileInformationOwnerEmailTextView;
    TextView profileInformationOwnerCityTextView;
    TextView profileInformationOwnerDogNameTextView;
    TextView profileInformationOwnerDogBreedTextView;
    TextView profileInformationOwnerDogBirthdayTextView;
    TextView profileInformationOwnerDogDescriptionTextView;

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

        // set text view to user email address by getting the extra from login activity
        profileInformationOwnerEmailTextView = (TextView) findViewById(R.id.ProfileInformationOwnerEmailTextView);
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        String userEmail = (String) b.get("ownersEmailForPassOn");
        profileInformationOwnerEmailTextView.setText(userEmail);

        // getting specific user based on email address to initialize views.
        currentUser = databaseHelper.getSpecificUser(userEmail);

        // TextView Owner Name
        String userName = currentUser.getOwnerFullName();
        profileInformationOwnerNameTextView = (TextView) findViewById(R.id.ProfileInformationOwnerNameTextView);
        profileInformationOwnerNameTextView.setText(userName);

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

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LoginAreaLetsGoForAWalkButton:
                break;
        }
    }
}
