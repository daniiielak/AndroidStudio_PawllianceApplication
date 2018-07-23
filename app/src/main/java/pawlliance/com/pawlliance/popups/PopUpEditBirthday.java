package pawlliance.com.pawlliance.popups;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.activities.EditProfileInformationMainLoginArea;
import pawlliance.com.pawlliance.activities.SignUpActivityPart2;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class PopUpEditBirthday extends AppCompatActivity implements View.OnClickListener {
    final Calendar dogBirthdayCalendar = Calendar.getInstance();
    private final AppCompatActivity activity = PopUpEditBirthday.this;

    private TextInputLayout popUpEditBirthdayTextInputLayout;
    private TextInputEditText popUpEditBirthdayTextInputEditText;

    private Button popUpBirthdaySaveButton;
    private Button popUpBirthdayCancelButton;

    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_edit_birthday);

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

        popUpEditBirthdayTextInputLayout = (TextInputLayout) findViewById(R.id.PopUpEditBirthdayTextInputLayout);
        popUpBirthdaySaveButton = (Button) findViewById(R.id.PopUpBirthdaySaveButton);
        popUpBirthdayCancelButton = (Button) findViewById(R.id.PopUpBirthdayCancelButton);

        // Date Picker für Edit Text
        popUpEditBirthdayTextInputEditText = (TextInputEditText) findViewById(R.id.PopUpEditBirthdayTextInputEditText);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dogBirthdayCalendar.set(Calendar.YEAR, year);
                dogBirthdayCalendar.set(Calendar.MONTH, month);
                dogBirthdayCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        popUpEditBirthdayTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PopUpEditBirthday.this, date, dogBirthdayCalendar.get(Calendar.YEAR),
                        dogBirthdayCalendar.get(Calendar.MONTH),
                        dogBirthdayCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
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
        popUpBirthdaySaveButton.setOnClickListener(this);
        popUpBirthdayCancelButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.PopUpBirthdayCancelButton:
                // storing the user email for pass on to next class
                Intent previousEditProfileInformationIntent = getIntent();
                Bundle b = previousEditProfileInformationIntent.getExtras();
                String userEmail = (String) b.get("ownersEmailForPassOn");
                //Intent for previous class
                Intent goBackToEditProfileInformationPageIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                goBackToEditProfileInformationPageIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToEditProfileInformationPageIntent);
                break;

            case R.id.PopUpBirthdaySaveButton:
                // set text view to user email address by getting the extra from previous activity
                previousEditProfileInformationIntent = getIntent();
                b = previousEditProfileInformationIntent.getExtras();
                userEmail = (String) b.get("ownersEmailForPassOn");
                System.out.println("This is the owner's email: " + userEmail);

                // getting specific user based on email address to update description
                String newBirthday = popUpEditBirthdayTextInputEditText.getText().toString();
                if (!newBirthday.equals("")) {
                    currentUser = databaseHelper.getSpecificUser(userEmail);
                    currentUser.setBirthday(newBirthday);
                    databaseHelper.updateUser(currentUser);
                }

                //Intent for previous class
                goBackToEditProfileInformationPageIntent = new Intent(activity, EditProfileInformationMainLoginArea.class);
                goBackToEditProfileInformationPageIntent.putExtra("ownersEmailForPassOn", userEmail);
                startActivity(goBackToEditProfileInformationPageIntent);
                break;
        }
    }

    private void updateLabel(){
        String myDateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.GERMAN);
        popUpEditBirthdayTextInputEditText.setText(sdf.format(dogBirthdayCalendar.getTime()));
    }

    // DNC - class closing
}
