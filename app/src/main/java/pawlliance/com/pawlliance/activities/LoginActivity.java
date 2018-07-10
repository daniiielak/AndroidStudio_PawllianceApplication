/*
 * File: LoginActivity.java
 * @author daniela kepper
 * Date: 29.05.2018
 * Focus: Login Activity after App Entry Point, allows existing users to log into their profile.
 */

package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.databasedata.AndroidDatabaseManager;
import pawlliance.com.pawlliance.helper.InputValidation;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    // set all variables for input fields and layouts
    private TextInputLayout loginAreaEmailTextInputLayout;
    private TextInputLayout loginAreaPasswordTextInputLayout;

    private TextInputEditText loginAreaEmailTextInputEditText;
    private TextInputEditText loginAreaPasswordTextInputEditText;

    private Button loginAreaLoginButton;
    private Button manageDBButton;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    public void initViews(){
        loginAreaEmailTextInputLayout = (TextInputLayout) findViewById(R.id.LoginAreaEmailTextInputLayout);
        loginAreaPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.LoginAreaPasswordTextInputLayout);
        loginAreaEmailTextInputEditText = (TextInputEditText) findViewById(R.id.LoginAreaEmailTextInputEditText);
        loginAreaPasswordTextInputEditText = (TextInputEditText) findViewById(R.id.LoginAreaPasswordTextInputEditText);
        loginAreaLoginButton = (Button) findViewById(R.id.LoginAreaLoginButton);
        manageDBButton = (Button) findViewById(R.id.ManageDBButton);
    }

    /**
     * This method is to initialize listeners
     */
    public void initListeners(){
        manageDBButton.setOnClickListener(this);
        loginAreaLoginButton.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    public void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ManageDBButton:
                Intent dbmanager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
                startActivity(dbmanager);
                break;
            case R.id.LoginAreaLoginButton:
                verifyLogin();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyLogin(){
        if(!inputValidation.isInputEditTextFilled(loginAreaEmailTextInputEditText, loginAreaEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmail))) {
            return;
        }
        if(!inputValidation.isInputEditTextEmail(loginAreaEmailTextInputEditText, loginAreaEmailTextInputLayout, getString(R.string.SignUpErrorMessageEmail))) {
            return;
        }
        if(!inputValidation.isInputEditTextFilled(loginAreaPasswordTextInputEditText, loginAreaPasswordTextInputLayout, getString(R.string.SignUpErrorMessagePassword))) {
            return;
        }
        if(databaseHelper.checkUser(loginAreaEmailTextInputEditText.getText().toString().trim()
        , loginAreaPasswordTextInputEditText.getText().toString().trim())) {

            Intent loginMainAreaIntent = new Intent(activity, LoginAreaMain.class);
            Snackbar.make(loginAreaLoginButton, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();
            String userEmail = loginAreaEmailTextInputEditText.getText().toString().trim();
            loginMainAreaIntent.putExtra("ownersEmailForPassOn", userEmail);
            emptyInputEditText();
            startActivity(loginMainAreaIntent);
        }
        else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(loginAreaEmailTextInputLayout, getString(R.string.SignUpErrorMessageIncorrectPassword), Snackbar.LENGTH_LONG).show();
        }

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        loginAreaEmailTextInputEditText.setText(null);
        loginAreaPasswordTextInputEditText.setText(null);
    }


    // DNC - class closing
}
