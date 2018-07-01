/*
 * File: SignUpActivityPart1.java
 * @author daniela kepper
 * Date: 29.05.2018
 */

package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import pawlliance.com.pawlliance.R;

public class SignUpActivityPart1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_part1);


        // SPINNER ACTION FOR CITY SELECTOR
        Spinner staticSpinner = (Spinner) findViewById(R.id.SignUpCitySpinner);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);


        // setting up login button redirect to LoginActivity.class
        Button signUpNextPageButton = (Button) findViewById(R.id.SignUpNextPageButton);
        signUpNextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpPage2Intent = new Intent(getApplicationContext(), SignUpActivityPart2.class);
                startActivity(signUpPage2Intent);
            }
        });
    }
}
