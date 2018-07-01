/*
 * File: SignUpActivityPart2.java
 * @author daniela kepper
 * Date: 29.05.2018
 */


package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import pawlliance.com.pawlliance.R;

public class SignUpActivityPart2 extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 1;
    private ImageView signUpUserImageDogPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_part2);


        // SPINNER ACTION FOR DOG BREED SELECTOR
        Spinner staticBreedSpinner = (Spinner) findViewById(R.id.SignUpDogBreedSpinner);
        ArrayAdapter<CharSequence> staticBreedAdapter = ArrayAdapter.createFromResource(this, R.array.dogBreeds_array, android.R.layout.simple_spinner_item);         // Create an ArrayAdapter using the string array and a default spinner
        staticBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);         // Specify the layout to use when the list of choices appears
        staticBreedSpinner.setAdapter(staticBreedAdapter);         // Apply the adapter to the spinner

        // SPINNER ACTION FOR DOG GENDER SELECTOR
        Spinner staticGenderSpinner = (Spinner) findViewById(R.id.SignUpDogGenderSpinner);
        ArrayAdapter<CharSequence> staticGenderAdapter = ArrayAdapter.createFromResource(this, R.array.dogGender_array, android.R.layout.simple_spinner_item);  // Create an ArrayAdapter using the string array and a default spinner
        staticGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  // Specify the layout to use when the list of choices appears
        staticGenderSpinner.setAdapter(staticGenderAdapter);         // Apply the adapter to the spinner

        // GET ACCESS TO IMAGE VIEW IF USER DECIDES TO TAKE A PHOTO WITH CAMERA
        signUpUserImageDogPhoto = (ImageView) findViewById(R.id.SignUpDogScreenImageView);

    }

    /**
     * This method will be called when user decides to access device camera to take a profile picture for his/her dog.
     * @param v
     */
    public void signUpDogImageTakePhotoMethod(View v){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // if the user chooses okay, passing the argument
            if (requestCode == CAMERA_REQUEST_CODE) { // results from the camera are here
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data"); // Image from camera saved.
                signUpUserImageDogPhoto.setImageBitmap(cameraImage);
            }
        }
    }
}
