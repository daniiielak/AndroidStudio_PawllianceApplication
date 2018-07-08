/*
 * File: SignUpActivityPart1.java
 * @author daniela kepper
 * Date: 29.05.2018
 */

package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
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

public class SignUpActivityPart1 extends AppCompatActivity implements View.OnClickListener {

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

    Button signUpNextPageButton;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_part1);
        getSupportActionBar().hide();

        initViews();
        initListener();
        initObjects();
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

        // Button
        signUpNextPageButton = (Button) findViewById(R.id.SignUpNextPageButton);

        // SPINNER ACTION FOR CITY SELECTOR
        signUpCitySpinner = (Spinner) findViewById(R.id.SignUpCitySpinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        signUpCitySpinner.setAdapter(staticAdapter);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
    }

    /**
     * This method is to initialize listeners
     */
    public void initListener(){
        signUpNextPageButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
     @Override
     public void onClick(View v){
         switch(v.getId()){
             case R.id.SignUpNextPageButton:
                 checkInputValidation();
                 break;
         }
     }

    private void checkInputValidation(){
        if (!inputValidation.isInputEditTextFilled(signUpOwnersFullNameTextInputEditText, signUpDogOwnersFullNameTextInputLayout, getString(R.string.SignUpErrorMessageOwnersName))) {
            return;
        }
        if(!inputValidation.isInputEditTextFilled(signUpOwnersEmailTextInputEditText, signUpOwnersEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmail))) {
            return;
        }
        if(!inputValidation.isInputEditTextEmail(signUpOwnersEmailTextInputEditText, signUpOwnersEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmail))) {
            return;
        }
        if(!inputValidation.isInputEditTextFilled(signUpOwnersPasswordTextInputEditText, signUpOwnersPasswordTextInputLayout, getString(R.string.SignUpErrorMessagePassword))) {
            return;
        }
        if(!inputValidation.isInputEditTextMatches(signUpOwnersPasswordTextInputEditText, signUpOwnersConfirmPasswordTextInputEditText,
                signUpOwnersConfirmPasswordTextInputLayout, getString(R.string.SignUpErrorMessagePasswordMatch))) {
            return;
        }
        if(!databaseHelper.checkUser(signUpOwnersEmailTextInputEditText.getText().toString().trim())) {
            // setting up login button redirect to LoginActivity.class
            signUpNextPageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emptyInputEditText();
                    Intent signUpPage2Intent = new Intent(getApplicationContext(), SignUpActivityPart2.class);
                    startActivity(signUpPage2Intent);
                }
            });
        }
        else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(signUpOwnersEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmailExists), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText(){
        signUpOwnersFullNameTextInputEditText.setText(null);
        signUpOwnersEmailTextInputEditText.setText(null);
        signUpOwnersPasswordTextInputEditText.setText(null);
        signUpOwnersConfirmPasswordTextInputEditText.setText(null);
    }

    // DNC - class closing
}
