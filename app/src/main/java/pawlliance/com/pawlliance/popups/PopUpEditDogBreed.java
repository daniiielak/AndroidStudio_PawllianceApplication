package pawlliance.com.pawlliance.popups;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.activities.EditProfileInformationMainLoginArea;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class PopUpEditDogBreed extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = PopUpEditDogBreed.this;

    private Spinner popUpEditDogBreedSpinner;

    private Button popUpDogBreedSaveButton;
    private Button popUpDogBreedCancelButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_dog_breed);

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
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.4));


        popUpDogBreedSaveButton = (Button) findViewById(R.id.PopUpDogBreedSaveButton);
        popUpDogBreedCancelButton = (Button) findViewById(R.id.PopUpDogBreedCancelButton);

        // SPINNER ACTION FOR DOG BREED SELECTOR
        popUpEditDogBreedSpinner = (Spinner) findViewById(R.id.PopUpEditDogBreedSpinner);
        ArrayAdapter<CharSequence> staticBreedAdapter = ArrayAdapter.createFromResource(this, R.array.dogBreeds_array, android.R.layout.simple_spinner_item);         // Create an ArrayAdapter using the string array and a default spinner
        staticBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);         // Specify the layout to use when the list of choices appears
        popUpEditDogBreedSpinner.setAdapter(staticBreedAdapter);         // Apply the adapter to the spinner

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
        popUpDogBreedSaveButton.setOnClickListener(this);
        popUpDogBreedCancelButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PopUpDogBreedCancelButton:
                // storing the user email for pass on to next class
                Intent previousEditProfileInformationIntent = getIntent();
                Bundle b = previousEditProfileInformationIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for previous class
                Intent goBackToEditProfileInformationPageIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                goBackToEditProfileInformationPageIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToEditProfileInformationPageIntent);
                break;

            case R.id.PopUpDogBreedSaveButton:
                // set text view to user email address by getting the extra from previous activity
                previousEditProfileInformationIntent = getIntent();
                b = previousEditProfileInformationIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                System.out.println("This is the owner's email: " + userEmail);

                // getting specific user based on email address to update description
                String newDogBreed = popUpEditDogBreedSpinner.getSelectedItem().toString();
                if (!newDogBreed.equals("")) {
                    currentUser = databaseHelper.getSpecificUser(userEmail);
                    currentUser.setDogBreed(newDogBreed);
                    databaseHelper.updateUser(currentUser);
                }

                //Intent for previous class
                goBackToEditProfileInformationPageIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                goBackToEditProfileInformationPageIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToEditProfileInformationPageIntent);
                break;
        }
    }

}

