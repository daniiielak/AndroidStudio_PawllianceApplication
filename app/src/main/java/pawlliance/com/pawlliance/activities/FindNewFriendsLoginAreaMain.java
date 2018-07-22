package pawlliance.com.pawlliance.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pawlliance.com.pawlliance.R;

public class FindNewFriendsLoginAreaMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_new_friends_login_area_main);

        // store user email address by getting the extra from login activity
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        String userEmail = (String) b.get("ownersEmailForPassOn");

    }
}
