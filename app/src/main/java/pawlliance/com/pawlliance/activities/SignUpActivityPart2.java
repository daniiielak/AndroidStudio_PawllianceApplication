/*
 * File: SignUpActivityPart2.java
 * @author daniela kepper
 * Date: 29.05.2018
 * Source: https://github.com/discospiff/PlantPlaces15s305/blob/master/app/src/main/java/nw15s305/plantplaces/com/plantplaces15s305/ColorCaptureActivity.java
 */


package pawlliance.com.pawlliance.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.helper.InputValidation;
import pawlliance.com.pawlliance.model.User;
import pawlliance.com.pawlliance.sql.DatabaseHelper;

public class SignUpActivityPart2 extends AppCompatActivity implements View.OnClickListener {
    final Calendar dogBirthdayCalendar = Calendar.getInstance();
    private final AppCompatActivity activity = SignUpActivityPart2.this;

    // set all variables for input fields and layouts
    private TextInputLayout signUpDogNameTextInputLayout;
    private TextInputLayout signUpDogBirthdayTextInputLayout;

    private TextInputEditText signUpDogsNameTextInputEditText;
    private TextInputEditText signUpDogsBirthdayTextInputEditText;

    private Spinner signUpDogBreedSpinner;
    private Spinner signUpDogGenderSpinner;

    private Button signUpCompleteSignUpButton;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    // Camera and Gallery variables
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 2;
    public static final int GALLERY_ACCESS_REQUEST_CODE = 3;
    private ImageView signUpUserImageDogPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_part2);
        getSupportActionBar().hide();

        initViews();
        initListener();
        initObjects();

    }

    /**
     * This method is to initialize views to be used
     */
    private void initViews(){

        signUpDogNameTextInputLayout = (TextInputLayout) findViewById(R.id.SignUpDogNameTextInputLayout);
        signUpDogBirthdayTextInputLayout = (TextInputLayout) findViewById(R.id.SignUpDogBirthdayTextInputLayout);
        signUpDogsNameTextInputEditText = (TextInputEditText) findViewById(R.id.SignUpDogsNameTextInputEditText);
        signUpCompleteSignUpButton = (Button) findViewById(R.id.SignUpCompleteSignUpButton);

        // Date Picker für Edit Text
        signUpDogsBirthdayTextInputEditText = (TextInputEditText) findViewById(R.id.SignUpDogsBirthdayTextInputEditText);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dogBirthdayCalendar.set(Calendar.YEAR, year);
                dogBirthdayCalendar.set(Calendar.MONTH, month);
                dogBirthdayCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        signUpDogsBirthdayTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignUpActivityPart2.this, date, dogBirthdayCalendar.get(Calendar.YEAR),
                        dogBirthdayCalendar.get(Calendar.MONTH),
                        dogBirthdayCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // SPINNER ACTION FOR DOG BREED SELECTOR
        signUpDogBreedSpinner = (Spinner) findViewById(R.id.SignUpDogBreedSpinner);
        ArrayAdapter<CharSequence> staticBreedAdapter = ArrayAdapter.createFromResource(this, R.array.dogBreeds_array, android.R.layout.simple_spinner_item);         // Create an ArrayAdapter using the string array and a default spinner
        staticBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);         // Specify the layout to use when the list of choices appears
        signUpDogBreedSpinner.setAdapter(staticBreedAdapter);         // Apply the adapter to the spinner

        // SPINNER ACTION FOR DOG GENDER SELECTOR
        signUpDogGenderSpinner = (Spinner) findViewById(R.id.SignUpDogGenderSpinner);
        ArrayAdapter<CharSequence> staticGenderAdapter = ArrayAdapter.createFromResource(this, R.array.dogGender_array, android.R.layout.simple_spinner_item);  // Create an ArrayAdapter using the string array and a default spinner
        staticGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  // Specify the layout to use when the list of choices appears
        signUpDogGenderSpinner.setAdapter(staticGenderAdapter);         // Apply the adapter to the spinner

        // GET ACCESS TO IMAGE VIEW IF USER DECIDES TO TAKE A PHOTO WITH CAMERA
        signUpUserImageDogPhoto = (ImageView) findViewById(R.id.SignUpDogScreenImageView);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    /**
     * This method is to initialize listeners
     */
    public void initListener(){
        signUpCompleteSignUpButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.SignUpCompleteSignUpButton:
                postDataToSQLite();
                break;
        }
    }


    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if(!inputValidation.isInputEditTextFilled(signUpDogsNameTextInputEditText, signUpDogNameTextInputLayout, getString(R.string.SignUpErrorMessageDogName))) {
            return;
        }
        if(!inputValidation.isInputEditTextFilled(signUpDogsBirthdayTextInputEditText, signUpDogBirthdayTextInputLayout, getString(R.string.SignUpErrorMessageDogBirthday))) {
            return;
        }

        // GET USER INPUT FROM PREVIOUS INTENT
        Intent previousSignUpActivityPart1Intent = getIntent();

        Bundle b = previousSignUpActivityPart1Intent.getExtras();
        String ownerName;
        String ownerEmail;
        String ownerCity;
        String ownerPassword;

        //if(b!=null)
        //{
            ownerName = (String) b.get("uniqueOwnerName");
            //System.out.println("Name: " + ownerName);
            ownerEmail = (String) b.get("uniqueOwnerEmail");
            //System.out.println("Email: " + ownerEmail);
            ownerCity = (String) b.get("uniqueOwnerCity");
            //System.out.println("City: " + ownerCity);
            ownerPassword = (String) b.get("uniqueOwnerPassword");
            //System.out.println("Password: " + ownerPassword);
        //}

        // adding user to the database with all full information
        user.setOwnerFullName(ownerName);
        user.setEmail(ownerEmail);
        user.setCity(ownerCity);
        user.setPassword(ownerPassword);
        user.setDogName(signUpDogsNameTextInputEditText.getText().toString().trim());
        user.setDogBreed(signUpDogBreedSpinner.getSelectedItem().toString());
        user.setBirthday(signUpDogsBirthdayTextInputEditText.getText().toString());
        user.setDogGender(signUpDogGenderSpinner.getSelectedItem().toString());
        user.setImagePath("image path place holder");
        user.setDescription("Welcome to " + signUpDogsNameTextInputEditText.getText().toString().trim() + "'s Pawlliance Page!");

        databaseHelper.addUser(user);
        System.out.println("Successfully added new user with ID" + user.getUserID() + " and user name " + user.getOwnerFullName() + " to the database.");

        Intent finishRegistrationIntent = new Intent(getApplicationContext(), LoginActivity.class);
        finishRegistrationIntent.putExtra("successfulMessage", R.string.SignUpSuccessMessage);
        startActivity(finishRegistrationIntent);
        emptyInputEditText();
        //
    }

    private void updateLabel(){
        String myDateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myDateFormat, Locale.GERMAN);
        signUpDogsBirthdayTextInputEditText.setText(sdf.format(dogBirthdayCalendar.getTime()));
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        signUpDogsNameTextInputEditText.setText(null);
        signUpDogsBirthdayTextInputEditText.setText(null);
    }


    /**
     * This method will be called when user decides to access device camera to take a profile picture for his/her dog.
     * @param v
     */
    public void signUpDogImageTakePhotoMethod(View v){
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            invokeCamera();
        }
        else {
            String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};  // request permission
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE){ // we have heard back from our request for camera and write external storage.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                invokeCamera();
            }
            else {
                Toast.makeText(this, "System cannot operate camera without permission.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void invokeCamera(){
        Uri pictureUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", createImageFile()); // get file reference
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);  // tell the camera to save the image
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);    // tell the camera to request write permission
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private File createImageFile(){
        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);  // the public picture directory
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss"); // timestamp to make a unique name
        String timestamp = sdf.format(new Date());
        File imageFile = new File(picturesDirectory, "PawllianceProfilePicture" + timestamp + ".jpg");    // put together the directory and timestamp with pawliiance name to make unique name
        return imageFile;
    }

    /**
     * This method will be called when user decides to access device gallery to choose a profile picture for his/her dog.
     * @param v
     */

    public void signUpDogImageChoosePhotoFromGalleryMethod(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);  // invoke the image gallery using implicit intent.
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);   // look for data at specified location
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);  // get URI representation
        photoPickerIntent.setDataAndType(data, "image/*");    // set data and type to get all image type
        startActivityForResult(photoPickerIntent, GALLERY_ACCESS_REQUEST_CODE); // invoke this activity and get something back from it.
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) { // if the user chooses okay, passing the argument
            if (requestCode == CAMERA_REQUEST_CODE) { // results from the camera are here
                Toast.makeText(this, "Image Saved.", Toast.LENGTH_LONG).show();
            }
            if(requestCode == GALLERY_ACCESS_REQUEST_CODE) {    // if code reaches this line, everything was done successfully
                Uri imageUri = data.getData();   // we are hearing back from the image gallery so let's take the address of the image on the SD card
                InputStream inputStream;   // declare a stream to read the image data from the SD card.

                try {       // try method to save the input stream
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream); // get image from the stream
                    signUpUserImageDogPhoto.setImageBitmap(image);
                }
                catch (FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show(); // show a message to user that the image is unavailable.
                }
            }
        }
    }

}