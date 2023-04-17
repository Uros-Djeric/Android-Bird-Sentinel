package com.example.birdsentinel1;


import androidx.core.app.ActivityCompat;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;

import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.birdsentinel1.databinding.ActivityMapsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    String link2 = "https://xeno-canto.org/api/2/recordings?query=cnt:serbia";
    RequestQueue queue = null;

    private LocationListener locationListener;
    private LocationManager locationManager;
    double privremena = 0.0;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private LatLng latLng;
    List<Double> lat = new ArrayList<>();
    List<Double> longit = new ArrayList<>();
    List<String> ime = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(MapsActivity.this, "Ucitavanje mape...", Toast.LENGTH_LONG).show();


        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        queue = Volley.newRequestQueue(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoomLevel = 9.0f;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                try {

                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Ja");
                    markerOptions.visible(true);
                    Marker marker = googleMap.addMarker(markerOptions);
                    marker.showInfoWindow();
                    marker.setVisible(true);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));


                    createRequest();


                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }

    private void createRequest() {

        String requestUrl = link2;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, requestUrl, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    parseJson(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.err.println("Sad android noise :( ");
                        error.printStackTrace();
                    }
                });

        queue.add(jsonObjectRequest);
    }


    private void parseJson(JSONObject response) throws JSONException {

        JSONArray attributions = (JSONArray) response.get("recordings");
        for (int i = 0; i < attributions.length(); i++) {
            String placeholder = "";
            JSONObject prvielement = attributions.getJSONObject(i);


            if (!prvielement.getString("en").equals("null") && prvielement.has("en")) {
                ime.add(prvielement.getString("en"));
            } else {
                ime.add("Pukao");
            }

            if (!prvielement.getString("lng").equals("null") && prvielement.has("lng")) {
                placeholder = prvielement.getString("lng");
                privremena = Double.parseDouble(placeholder);
                longit.add(privremena);
            } else {
                longit.add(19.83694);
            }

            if (!prvielement.getString("lat").equals("null") && prvielement.has("lat")) {
                placeholder = prvielement.getString("lat");
                privremena = Double.parseDouble(placeholder);
                lat.add(privremena);
            } else {
                lat.add(45.25167);
            }
            LatLng smestenaVrednost = new LatLng(lat.get(i), longit.get(i));
            mMap.addMarker(new MarkerOptions().position(smestenaVrednost).title(ime.get(i)));
        }

    }
}