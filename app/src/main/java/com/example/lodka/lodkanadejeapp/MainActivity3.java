package com.example.lodka.lodkanadejeapp;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
    LatLng loc;
    float[] results;
    double latitude;
    double longitude;
    Button actual;
    TextView label;
    Context context = null;
    String themeSetting;
    SharedPreferences themeInfo ;
    SharedPreferences.Editor editor;
    static Boolean checker3 = false;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        themeInfo = getSharedPreferences("THEMECONFIG",0);
        themeSetting = themeInfo.getString("theme","Základná");

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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        actual = (Button) findViewById(R.id.actualize);
        label = (TextView) findViewById(R.id.distanceLabel);
        label.setTextColor(Color.BLUE);
        actual.setOnClickListener(this);

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
                changeTheme("Základná");
                checker3 = true;
            }
            if (themeSetting.equals("Matrix")) {
                setTheme(R.style.AppThemeMatrixDivider);
                changeTheme("Matrix");
                checker3 = true;
            }
            if (themeSetting.equals("Gamers")) {
                setTheme(R.style.AppThemeGamersDivider);
                changeTheme("Gamers");
                checker3 = true;
            }
        }

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

    public void changeTheme(String str) {

        LinearLayout navHeader = (LinearLayout) findViewById(R.id.nav_header);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button sett = (Button) findViewById(R.id.button_settings);
        TextView text = (TextView) findViewById(R.id.textview);
        SharedPreferences themeInfo = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = themeInfo.edit();
        View header = navigationView.getHeaderView(0);
        LinearLayout linear = (LinearLayout) header.findViewById(R.id.nav_header_logo);
        if (str.equals("Základná")) {
            navigationView.setItemIconTintList(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_normal));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_normal));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity3.this, R.drawable.border_top_bottom_normal));
            //navHeader.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.border_top_bottom_normal));
            sett.setTextColor(ContextCompat.getColorStateList(MainActivity3.this, R.color.colorWhite));
            text.setTextColor(ContextCompat.getColorStateList(MainActivity3.this, R.color.colorDefault));
            toolbar.setBackground(ContextCompat.getDrawable(MainActivity3.this, R.color.colorPrimary));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            linear.setBackground(ContextCompat.getDrawable(MainActivity3.this, R.drawable.border_top_bottom_normal_logo));
            editor.putString("theme", "Základná");
            editor.putInt("setItemIconTintList", R.drawable.menu_text_color_normal);
            editor.putInt("setItemTextColor", R.drawable.menu_text_color_normal);
            editor.putInt("setBackgroundDrawable", R.drawable.border_top_bottom_normal);
            editor.putInt("navHeaderSetBackgroundDrawable", R.drawable.border_top_bottom_normal);
            editor.putInt("settSetTextColor", R.color.colorWhite);
            editor.putInt("textSetTextColor", R.color.colorDefault);
            editor.putInt("toolbarSetBackground", R.color.colorPrimary);
            editor.putInt("toolbarGetNavigationIcon()", R.color.colorWhite);
            editor.putInt("linearSetBackground", R.drawable.border_top_bottom_normal_logo);
            editor.commit();
            if (!checker3)
                Utils.changeToTheme(MainActivity3.this, Utils.THEME_DEFAULT);

        }
        if (str.equals("Matrix")) {

            navigationView.setItemIconTintList(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_matrix));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_matrix));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity3.this, R.drawable.border_top_bottom_matrix));
            // navHeader.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.border_top_bottom_matrix));
            sett.setTextColor(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_matrix));
            text.setTextColor(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_matrix));
            toolbar.setBackground(ContextCompat.getDrawable(MainActivity3.this, R.drawable.border_top_bottom_matrix));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.drawable.menu_text_color_matrix), PorterDuff.Mode.SRC_ATOP);
            linear.setBackground(ContextCompat.getDrawable(MainActivity3.this, R.drawable.border_top_bottom_matrix_logo));
            editor.putString("theme", "Matrix");
            editor.putInt("setItemIconTintList", R.drawable.menu_text_color_matrix);
            editor.putInt("setItemTextColor", R.drawable.menu_text_color_matrix);
            editor.putInt("setBackgroundDrawable", R.drawable.border_top_bottom_matrix);
            editor.putInt("navHeaderSetBackgroundDrawable", R.drawable.border_top_bottom_matrix);
            editor.putInt("settSetTextColor", R.drawable.menu_text_color_matrix);
            editor.putInt("textSetTextColor", R.drawable.menu_text_color_matrix);
            editor.putInt("toolbarSetBackground", R.drawable.border_top_bottom_matrix);
            editor.putInt("toolbarGetNavigationIcon()", R.drawable.menu_text_color_matrix);
            editor.putInt("linearSetBackground", R.drawable.border_top_bottom_matrix_logo);
            editor.commit();
            if (!checker3)
                Utils.changeToTheme(MainActivity3.this, Utils.THEME_MATRIX);

        }
        if (str.equals("Gamers")) {

            navigationView.setItemIconTintList(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_gamers));
            navigationView.setItemTextColor(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_gamers));
            navigationView.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity3.this, R.drawable.border_top_bottom_gamers));
            //navHeader.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.border_top_bottom_gamers));
            sett.setTextColor(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_gamers));
            text.setTextColor(ContextCompat.getColorStateList(MainActivity3.this, R.drawable.menu_text_color_gamers));
            toolbar.setBackground(ContextCompat.getDrawable(MainActivity3.this, R.drawable.border_top_bottom_gamers));
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.drawable.menu_text_color_gamers), PorterDuff.Mode.SRC_ATOP);
            linear.setBackground(ContextCompat.getDrawable(MainActivity3.this, R.drawable.border_top_bottom_gamers_logo));
            editor.putString("theme", "Gamers");
            editor.putInt("setItemIconTintList", R.drawable.menu_text_color_gamers);
            editor.putInt("setItemTextColor", R.drawable.menu_text_color_gamers);
            editor.putInt("setBackgroundDrawable", R.drawable.border_top_bottom_gamers);
            editor.putInt("navHeaderSetBackgroundDrawable", R.drawable.border_top_bottom_gamers);
            editor.putInt("settSetTextColor", R.drawable.menu_text_color_gamers);
            editor.putInt("textSetTextColor", R.drawable.menu_text_color_gamers);
            editor.putInt("toolbarSetBackground", R.drawable.border_top_bottom_gamers);
            editor.putInt("toolbarGetNavigationIcon()", R.drawable.menu_text_color_gamers);
            editor.putInt("linearSetBackground", R.drawable.border_top_bottom_gamers_logo);
            editor.commit();
            if (!checker3)
                Utils.changeToTheme(MainActivity3.this, Utils.THEME_GAMERS);

        }
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
        int id2 = item.getItemId();

        if (id2 == R.id.nav_domov) {
            Intent myIntent = new Intent(com.example.lodka.lodkanadejeapp.MainActivity3.this, MainActivity.class);
            myIntent.putExtra("website","http://lodkanadeje.maweb.eu/");
            com.example.lodka.lodkanadejeapp.MainActivity3.this.startActivity(myIntent);
        } else if (id2 == R.id.nav_gallery) {
            Intent myIntent = new Intent(com.example.lodka.lodkanadejeapp.MainActivity3.this, MainActivity.class);
            myIntent.putExtra("website","http://lodkanadeje.rajce.idnes.cz/");
            com.example.lodka.lodkanadejeapp.MainActivity3.this.startActivity(myIntent);
        } else if (id2 == R.id.nav_facebook) {
            SharingToSocialMedia("com.facebook.katana");
        } else if (id2 == R.id.nav_twitter) {
            SharingToSocialMedia("com.twitter.android");
        } else if (id2 == R.id.nav_mail){
            Intent myIntent = new Intent(com.example.lodka.lodkanadejeapp.MainActivity3.this, com.example.lodka.lodkanadejeapp.MainActivity2.class);
            com.example.lodka.lodkanadejeapp.MainActivity3.this.startActivity(myIntent);
        } else if (id2 == R.id.nav_instagram){
            SharingToSocialMedia("com.instagram.android");
        } else if (id2 == R.id.nav_snapchat){
            SharingToSocialMedia("com.snapchat.android");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void SharingToSocialMedia(final String application) {


        if(application == "com.facebook.katana") {


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "http://www.lodkanadeje.maweb.eu/");

            boolean installed = checkAppInstall(application);
            if (installed) {
                intent.setPackage(application);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
            }
        }else if(application == "com.twitter.android"){


            Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.mipmap.lodkauvod);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, "http://www.lodkanadeje.maweb.eu/");

            boolean installed = checkAppInstall(application);
            if (installed) {
                intent.setPackage(application);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
            }
        }else if(application == "com.instagram.android"){

            final Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.mipmap.lodkauvod2);

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Pripomienka")
                    .setMessage("Pri zdieľaní nezabudnite prosím roztiahnúť obrázok na celú obrazovku")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("image/png");
                            intent.putExtra(Intent.EXTRA_STREAM, uri);
                            boolean installed = checkAppInstall(application);


                            if (installed) {
                                intent.setPackage(application);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else if(application == "com.snapchat.android") {

            final Uri uri = Uri.parse("android.resource://" + this.getPackageName() + "/" + R.mipmap.lodkauvod2);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            boolean installed = checkAppInstall(application);


            if (installed) {
                intent.setPackage(application);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Nie je nainštalovaná aplikácia", Toast.LENGTH_LONG).show();
            }
        }
    }



    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

}
