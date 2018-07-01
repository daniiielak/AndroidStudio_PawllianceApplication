/*
 * File: SignUpActivityPart2.java
 * @author daniela kepper
 * Date: 29.05.2018
 */


package pawlliance.com.pawlliance.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import pawlliance.com.pawlliance.R;

public class SignUpActivityPart2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_part2);


        // SPINNER ACTION FOR DOG BREED SELECTOR
        Spinner staticSpinner = (Spinner) findViewById(R.id.SignUpDogBreedSpinner);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.dogBreeds_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

    }
}
