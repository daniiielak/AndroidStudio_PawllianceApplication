/*
 * File: LoginActivity.java
 * @author daniela kepper
 * Date: 29.05.2018
 * Focus: Login Activity after App Entry Point, allows existing users to log into their profile.
 */

package pawlliance.com.pawlliance.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pawlliance.com.pawlliance.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
