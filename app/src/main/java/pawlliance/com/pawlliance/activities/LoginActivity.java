/*
 * File: LoginActivity.java
 * @author daniela kepper
 * Date: 29.05.2018
 * Focus: Login Activity after App Entry Point, allows existing users to log into their profile.
 */

package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.databasedata.AndroidDatabaseManager;

public class LoginActivity extends AppCompatActivity {

    private Button manageDBButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initViews();



        manageDBButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });
    }

    public void initViews(){
        manageDBButton = (Button) findViewById(R.id.ManageDBButton);
    }
}
