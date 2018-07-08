/*
 * File: SignUpActivityPart1.java
 * @author daniela kepper
 * Date: 29.05.2018
 */

package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.w3c.dom.Text;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.helper.InputValidation;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class SignUpActivityPart1 extends AppCompatActivity {

    private final AppCompatActivity activity = SignUpActivityPart1.this;

    // set all variables for input fields and layouts
    private TextInputLayout signUpDogOwnersFullNameTextInputLayout;
    private TextInputLayout signUpOwnersEmailTextInputLayout;
    private TextInputLayout signUpOwnersPasswordTextInputLayout;
    private TextInputLayout signUpOwnersConfirmPasswordTextInputLayout;

    private Spinner signUpCitySpinner;

    private TextInputEditText signUpOwnersFullNameTextInputEditText;
    private TextInputEditText signUpOwnersEmailTextInputEditText;
    private TextInputEditText signUpOwnersPasswordTextInputEditText;
    private TextInputEditText signUpOwnersConfirmPasswordTextInputEditText;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_part1);
        getSupportActionBar().hide();

        initViews();
        initObjects();

        // setting up login button redirect to LoginActivity.class
        Button signUpNextPageButton = (Button) findViewById(R.id.SignUpNextPageButton);
        signUpNextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInputValidation();
                Intent signUpPage2Intent = new Intent(getApplicationContext(), SignUpActivityPart2.class);
                startActivity(signUpPage2Intent);
            }
        });
    }

    private void initViews(){
        // TextInputLayouts
        signUpDogOwnersFullNameTextInputLayout = (TextInputLayout) findViewById(R.id.SignUpDogOwnersFullNameTextInputLayout);
        signUpOwnersEmailTextInputLayout = (TextInputLayout) findViewById(R.id.SignUpOwnersEmailTextInputLayout);
        signUpOwnersPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.SignUpOwnersPasswordTextInputLayout);
        signUpOwnersConfirmPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.SignUpOwnersConfirmPasswordTextInputLayout);

        // TextInputEditTexts
        signUpOwnersFullNameTextInputEditText = (TextInputEditText) findViewById(R.id.SignUpOwnersFullNameTextInputEditText);
        signUpOwnersEmailTextInputEditText = (TextInputEditText) findViewById(R.id.SignUpOwnersEmailTextInputEditText);
        signUpOwnersPasswordTextInputEditText = (TextInputEditText) findViewById(R.id.SignUpOwnersPasswordTextInputEditText);
        signUpOwnersConfirmPasswordTextInputEditText = (TextInputEditText) findViewById(R.id.SignUpOwnersConfirmPasswordTextInputEditText);


        // SPINNER ACTION FOR CITY SELECTOR
        signUpCitySpinner = (Spinner) findViewById(R.id.SignUpCitySpinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        signUpCitySpinner.setAdapter(staticAdapter);

    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
    }

    private void checkInputValidation(){
        
    }

}
