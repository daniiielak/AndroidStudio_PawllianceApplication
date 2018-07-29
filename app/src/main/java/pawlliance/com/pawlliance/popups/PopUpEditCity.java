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

public class PopUpEditCity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = PopUpEditCity.this;

    private Spinner popUpEditCitySpinner;

    private Button popUpCitySaveButton;
    private Button popUpCityCancelButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_city);

        initViews();
        initListener();
        initObjects();
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


        popUpCitySaveButton = (Button) findViewById(R.id.PopUpCitySaveButton);
        popUpCityCancelButton = (Button) findViewById(R.id.PopUpCityCancelButton);

        // SPINNER ACTION FOR CITY SELECTOR
        popUpEditCitySpinner = (Spinner) findViewById(R.id.PopUpEditCitySpinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        popUpEditCitySpinner.setAdapter(staticAdapter);

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
        popUpCitySaveButton.setOnClickListener(this);
        popUpCityCancelButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PopUpCityCancelButton:
                // storing the user email for pass on to next class
                Intent previousEditProfileInformationIntent = getIntent();
                Bundle b = previousEditProfileInformationIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for previous class
                Intent goBackToEditProfileInformationPageIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                goBackToEditProfileInformationPageIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToEditProfileInformationPageIntent);
                break;

            case R.id.PopUpCitySaveButton:
                // set text view to user email address by getting the extra from previous activity
                previousEditProfileInformationIntent = getIntent();
                b = previousEditProfileInformationIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                System.out.println("This is the owner's email: " + userEmail);

                // getting specific user based on email address to update description
                String newCity = popUpEditCitySpinner.getSelectedItem().toString();
                if (!newCity.equals("")) {
                    currentUser = databaseHelper.getSpecificUser(userEmail);
                    currentUser.setCity(newCity);
                    databaseHelper.updateUser(currentUser);
                }

                //Intent for previous class
                goBackToEditProfileInformationPageIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                goBackToEditProfileInformationPageIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToEditProfileInformationPageIntent);
                break;
        }
    }

    // DNC - class closing
}
