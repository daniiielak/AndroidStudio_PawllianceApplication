package pawlliance.com.pawlliance.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import pawlliance.com.pawlliance.R;
import pawlliance.com.pawlliance.popups.PopUpEditPassword;
import pawlliance.com.pawlliance.popups.PopUpThanksForTheDogWalk;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private Button mapsActivityStartDogWalkButton;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Marker marker;
    private LocationListener locationListener;
    private ArrayList<LatLng> mapPoints;
    private PolylineOptions polylineOptions;
    private int numberChanges;
    private LatLng mapStartPoint;
    private LatLng mapEndPoint;
    private double totalWalkingDistance = 0;
    private LocalDate currentDate;
    private Date currentDateSQLFormat;
    private LocalTime walkingStartTime;
    private Time walkingStartTimeSQLFormat;
    private LocalTime walkingEndTime;
    private Time walkingEndTimeSQLFormat;
    private double totalWalkingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // store user email address by getting the extra from login activity
        Intent previousLoginAreaIntent = getIntent();
        Bundle b = previousLoginAreaIntent.getExtras();
        String userEmail = (String) b.get("ownersEmailForPassOn");

        initViews();
        initObjects();
        initListeners();

    }

    /**
     * This method is to initialize views to be used
     */
    private void initViews(){
        mapsActivityStartDogWalkButton = (Button) findViewById(R.id.MapsActivityStartDogWalkButton);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        mapPoints = new ArrayList<LatLng>();
        polylineOptions = new PolylineOptions();
        numberChanges = 0;

        // Setting the color & width of the polyline
        polylineOptions.color(Color.BLUE);
        polylineOptions.width(15);
    }

    /**
     * This method is to initialize listeners
     */
    public void initListeners(){
        mapsActivityStartDogWalkButton.setOnClickListener(this);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.MapsActivityStartDogWalkButton:
                if(mapsActivityStartDogWalkButton.getText().equals("Start Your Dog Walk")) {
                    mapsActivityStartDogWalkButton.setText("Finish Your Dog Walk");
                    startActivity();
                    break;
                }
                else if(mapsActivityStartDogWalkButton.getText().equals("Finish Your Dog Walk")){
                    endActivity();
                    //mapsActivityStartDogWalkButton.setText("Start Your Dog Walk");

                    // storing the user email for pass on to next class
                    Intent previousMainAreaIntent = getIntent();
                    Bundle b = previousMainAreaIntent.getExtras();
                    String userEmail = (String) b.get("ownersEmailForPassOn");

                    //Intent for description popup class
                    Intent thanksForTheDogWalkPopUpIntent = new Intent(MapsActivity.this, PopUpThanksForTheDogWalk.class);
                    thanksForTheDogWalkPopUpIntent.putExtra("ownersEmailForPassOn", userEmail);
                    startActivity(thanksForTheDogWalkPopUpIntent);
                    break;
                }
                else {
                    break;
                }
        }
    }

    private void startActivity(){
        System.out.println("Activity started!");
        // locations manager object
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        // location listener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // get the location name from latitude and longitude
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    if (numberChanges < 1){
                        LatLng startPoint = new LatLng(latitude, longitude);

                        // save map start point in variable
                        mapStartPoint = startPoint;

                        // save in array list
                        mapPoints.add(startPoint);
                        // Setting points of polyline
                        polylineOptions.add(startPoint);
                        // Adding the polyline to the map
                        mMap.addPolyline(polylineOptions);
                        mMap.addMarker(new MarkerOptions().position(startPoint).title("Your Dog Walk Starting Location"));
                        mMap.setMaxZoomPreference(20);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 12.0f));

                        // save walking date and start time
                        currentDate = currentDate.now();
                        System.out.println("This is the current date: " + currentDate);
                        currentDateSQLFormat = Date.valueOf(currentDate.toString());

                        walkingStartTime = walkingStartTime.now();
                        System.out.println("This is the start time: " + walkingStartTime);
                        walkingStartTimeSQLFormat = Time.valueOf(walkingStartTime.toString());

                        // increment changes to pass on to else on next change
                        numberChanges++;
                    }

                    if(numberChanges >= 1) {
                        LatLng lastPoint = new LatLng(latitude, longitude);

                        // save in array list
                        mapPoints.add(lastPoint);
                        // save new end point
                        mapEndPoint = lastPoint;
                        // Setting points of polyline
                        polylineOptions.add(lastPoint);
                        //polylineOptions.addAll(mapPoints);
                        // Adding the polyline to the map
                        mMap.addPolyline(polylineOptions);
                    }

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void endActivity(){
        System.out.println("Activity finished!");

        // set walking end time and duration
        walkingEndTime = walkingEndTime.now();
        System.out.println("This is the end time: " + walkingEndTime);
        walkingEndTimeSQLFormat = Time.valueOf(walkingEndTime.toString());

        totalWalkingTime = Duration.between(walkingStartTime, walkingEndTime).toMinutes();
        System.out.println("Total walking minutes: " + totalWalkingTime);

        // set walking distance
        Location startLocation = new Location("Starting Point");
        startLocation.setLatitude(mapStartPoint.latitude);
        startLocation.setLongitude(mapStartPoint.longitude);

        Location endLocation = new Location("End Point");
        endLocation.setLatitude(mapEndPoint.latitude);
        endLocation.setLongitude(mapEndPoint.longitude);

        totalWalkingDistance += endLocation.distanceTo(startLocation);
        System.out.println("Total Walking Distance: " + totalWalkingDistance);

        // setting back num changes in case another activity is started in same field.
        numberChanges = 0;
        mMap.clear();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Dublin and move the camera
        LatLng dublin = new LatLng(-53, 6);
        mMap.addMarker(new MarkerOptions().position(dublin).title("Marker in Dublin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dublin));
    }

}
