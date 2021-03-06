package com.example.lodka.lodkanadejeapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import Modules.DirectionFinder;
import Modules.Route;


public class MainActivity3 extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener, OnMapReadyCallback, LocationSource.OnLocationChangedListener {

    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    String destination;
    String origin = "Kosice";
    int distance;
    float[] results;
    double latitude;
    double longitude;
    Button actual;
    TextView label;
    Context context = null;
    String themeSetting;
    ProcessFunction processer;
    SharedPreferences themeInfo ;
    static Boolean checker3 = false;
    NavigationView navigationView;
    DrawerLayout drawer;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        themeInfo = getSharedPreferences("THEMECONFIG",0);
        themeSetting = themeInfo.getString("theme","Základná");
        processer = new ProcessFunction();
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_floating_button));

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_floating_button));

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        actual = (Button) findViewById(R.id.actualize);
        label = (TextView) findViewById(R.id.distanceLabel);
        label.setTextColor(Color.BLUE);
        actual.setOnClickListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        MultiTouchListener touchListener=new MultiTouchListener(this);
        fab.setOnTouchListener(touchListener);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!drawer.isDrawerVisible(GravityCompat.START))
                {
                    drawer.openDrawer(Gravity.LEFT);
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_floating_button));
                }
                else
                {
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_floating_button));
                }
            }
        });
        Button buttonSettings = (Button) toolbar.findViewById(R.id.button_settings);

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity3.this, Settings_activity.class);
                MainActivity3.this.startActivity(myIntent);

            }
        });
        if(!themeSetting.equals(themeInfo)) {
            if (themeSetting.equals("Základná")) {
                setTheme(R.style.AppTheme);
                processer.changeTheme("Základná", checker3, MainActivity3.this, navigationView, drawer);
                checker3 = true;
            }
            if (themeSetting.equals("Matrix")) {
                setTheme(R.style.AppThemeMatrixDivider);
                processer.changeTheme("Matrix", checker3, MainActivity3.this, navigationView, drawer);
                checker3 = true;
            }
            if (themeSetting.equals("Gamers")) {
                setTheme(R.style.AppThemeGamersDivider);
                processer.changeTheme("Gamers", checker3, MainActivity3.this, navigationView, drawer);
                checker3 = true;
            }
        }
        fab.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity3.this, themeInfo.getInt("fabBackground",R.color.colorPrimary)));
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

        // Add a marker in Sydney and move the camera
        LatLng vymennik = new LatLng(48.675966, 21.300348);
        mMap.addMarker(new MarkerOptions()
                .position(vymennik)
                .title("Výmenník Važecká")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("lodkabp", 100, 100))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vymennik, 18));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        sendRequest();


    }

    boolean twice;
    @Override
    public void onBackPressed() {

        if(twice == true){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice = true;

        Toast.makeText(MainActivity3.this, "Znova stlačte tlačidlo 'Späť' pre ukončenie aplikácie", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        }, 3000);

    }


    public void onDirectionFinderSuccess(List<Route> routes) {
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }

    }


    public void onDirectionFinderStart() {

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "mipmap", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    private void sendRequest() {

        destination = "48.675966, 21.300348";
        setup();
}
    @Override
    public void onClick(View v) {
        sendRequest();
    }
    public void setup()
    {
        label.setText("");
        LatLng latlng = getLocation();




        double latDest = 48.675966;
        double longDest = 21.300348;
        results = new float[1];
        if(latlng == null){
            latitude = 48.675966;
            longitude = 21.300348;
        }
        else {
            latitude = latlng.latitude;
            longitude = latlng.longitude;
        }
        origin = latitude + ", " + longitude;
        try {
            new DirectionFinder(this, origin, destination).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Location.distanceBetween(latitude,longitude,latDest,longDest,results);
        distance = math(results[0]);
        label.append(String.valueOf(distance) + "m do cieľa");
    }


    @Override
    public void onLocationChanged(Location location) {
        sendRequest();
    }

    public LatLng getLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        Double lat,lon;

        try {
            lat = location.getLatitude();
            lon = location.getLongitude();

            return new LatLng(lat, lon);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }



    public static int math(float f) {
        int c = (int) ((f) + 0.5f);
        float n = f + 0.5f;
        return (n - c) % 2 == 0 ? (int) f : c;
    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id3 = item.getItemId();
        processer = new ProcessFunction();
        processer.MenuProcessing(id3, this, processer);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
