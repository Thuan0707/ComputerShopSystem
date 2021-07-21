package com.example.computershopsystem.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.ActivityGoogleMapsApiBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GoogleMapsApi extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private ActivityGoogleMapsApiBinding binding;

    private final int REQUEST_LOCATION_PERMISSION = 1;

    private Marker homeMarker = null;
    private String city, region, strAdr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapsApiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtAddress.setText("");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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


        // Add a marker in computer shop and move the camera
        LatLng shop = new LatLng(10.031461, 105.763215);

        mMap.addMarker(new MarkerOptions().position(shop).title("Here is our Computer shop :>"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shop, 15f));

        LatLng hmmm = new LatLng(10.029031,105.757339);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull @NotNull LatLng pos) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addressList = null;
                setHomeMarker();
                homeMarker = mMap.addMarker(new MarkerOptions().position(pos).title("Your Address"));
                homeMarker.showInfoWindow();
                try {
                    addressList = geocoder.getFromLocation(pos.latitude, pos.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setAddress(addressList);
            }
        });


//        GroundOverlayOptions homeOverlay = new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.cart))
//                .position(hmmm, 100);

//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

//        mMap.addGroundOverlay(homeOverlay);

//        customeMap(mMap);


        enableMyLocation();


    }

    private void setAddress(List<Address> addressList) {
        for (Address address : addressList) {
            if (address.getAddressLine(0) != null) {
                binding.txtAddress.setText(address.getAddressLine(0));
                strAdr = address.getAddressLine(0);
                Log.d("address", "setAddress: " + strAdr);
            }
            if (address.getAddressLine(1) != null) {
                binding.txtAddress.setText(
                        binding.txtAddress.getText().toString() + address.getAddressLine(1));
            }
        }
    }

    private void setHomeMarker(){
        if(homeMarker != null) homeMarker.remove();
    }



    private void customeMap(GoogleMap googleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e("Abc", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Abc", "Can't find style. Error: ", e);
        }
    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    }
}