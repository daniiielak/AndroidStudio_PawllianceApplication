/*
 * File: SignUpActivity.java
 * @author daniela kepper
 * Date: 29.05.2018
 * Focus: App Entry Point after opening the application.
 */

package pawlliance.com.pawlliance.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pawlliance.com.pawlliance.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
