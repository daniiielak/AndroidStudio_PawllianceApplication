package pawlliance.com.pawlliance.popups;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.activities.EditProfileInformationMainLoginArea;
import pawlliance.com.pawlliance.activities.MapsActivity;
import pawlliance.com.pawlliance.activities.SignUpActivityPart2;
import pawlliance.com.pawlliance.helper.InputValidation;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class PopUpEditDescription extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = PopUpEditDescription.this;

    private TextInputLayout popUpEditDescriptionTextInputLayout;
    private TextInputEditText popUpEditDescriptionTextInputEditText;
    private Button popUpDescriptionCancelButton;
    private Button popUpDescriptionSaveButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_description);

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

        popUpEditDescriptionTextInputLayout = (TextInputLayout) findViewById(R.id.PopUpDescriptionTextInputLayout);
        popUpEditDescriptionTextInputEditText = (TextInputEditText) findViewById(R.id.PopUpDescriptionTextInputEditText);
        popUpDescriptionCancelButton = (Button) findViewById(R.id.PopUpDescriptionCancelButton);
        popUpDescriptionSaveButton = (Button) findViewById(R.id.PopUpDescriptionSaveButton);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        currentUser = new User();
    }

    /**
     * This method is to initialize listeners
     */
    public void initListener(){
        popUpDescriptionCancelButton.setOnClickListener(this);
        popUpDescriptionSaveButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.PopUpDescriptionCancelButton:
                // storing the user email for pass on to next class
                Intent previousEditProfileInformationIntent = getIntent();
                Bundle b = previousEditProfileInformationIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for previous class
                Intent goBackToEditProfileInformationPageIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                goBackToEditProfileInformationPageIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToEditProfileInformationPageIntent);
                break;

            case R.id.PopUpDescriptionSaveButton:
                // set text view to user email address by getting the extra from previous activity
                previousEditProfileInformationIntent = getIntent();
                b = previousEditProfileInformationIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                System.out.println("This is the owner's email: " + userEmail);

                // getting specific user based on email address to update description
                String newDescription = popUpEditDescriptionTextInputEditText.getText().toString();
                    if (!newDescription.equals("")) {
                        currentUser = databaseHelper.getSpecificUser(userEmail);
                        currentUser.setDescription(newDescription);
                        databaseHelper.updateUser(currentUser);
                    }

                //Intent for previous class
                goBackToEditProfileInformationPageIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                goBackToEditProfileInformationPageIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToEditProfileInformationPageIntent);
                break;
        }
    }

    // DNC - class closing tag
}
