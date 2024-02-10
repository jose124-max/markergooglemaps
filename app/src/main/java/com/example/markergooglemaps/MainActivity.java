package com.example.markergooglemaps;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.markergooglemaps.adapters.adaptadorplaces;
import com.example.markergooglemaps.models.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    public RequestQueue requestQueue;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void mapSatellite(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void mapNormal(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void mapHirbi(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    private void agregarMarcador(LatLng position, String title) {
        mMap.addMarker(new MarkerOptions().position(position).title(title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }
    LatLng defaultLocation;
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        defaultLocation = new LatLng(-1.0123939432800628, -79.4695139058818);
        CameraUpdate camUpd1 = CameraUpdateFactory.newLatLngZoom(defaultLocation, 17);
        mMap.moveCamera(camUpd1);

        agregarMarcador(defaultLocation, "Universidad Tecnica Estatal de Quevedo");
        mMap.setInfoWindowAdapter(new adaptadorplaces(LayoutInflater.from(getApplicationContext())));

        mMap.setOnMapClickListener(this);
    }

    public void clickRestaurant(View view){
        mMap.clear();
        agregarMarcador(defaultLocation, "Universidad Tecnica Estatal de Quevedo");
        Toast.makeText(this, "Cargando restaurantes", Toast.LENGTH_SHORT).show();
        obtenerLugares(defaultLocation, 1500,"restaurant");
    }
    public void clickBares(View view){
        mMap.clear();
        agregarMarcador(defaultLocation, "Universidad Tecnica Estatal de Quevedo");
        Toast.makeText(this, "Cargando bares", Toast.LENGTH_SHORT).show();
        obtenerLugares(defaultLocation, 1500,"bar");
    }
    public void clickcafe(View view){
        mMap.clear();
        agregarMarcador(defaultLocation, "Universidad Tecnica Estatal de Quevedo");
        Toast.makeText(this, "Cargando Cafés", Toast.LENGTH_SHORT).show();
        obtenerLugares(defaultLocation, 1500,"cafe");
    }
    private void obtenerLugares(@NonNull LatLng latLng, double radius, String lugar) {
        // La URL de la API
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?fields=name" +
                "&location=" + latLng.latitude + "," + latLng.longitude +
                "&radius=" + radius +
                "&type="+lugar +
                "&key=AIzaSyCx2klhltS0foiCjqvuxh27SV67y_VyZ_w";
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        ArrayList<Places> lstplace = Places.JsonObjectsBuild(results);
                        for(int i=0;i<lstplace.size();i++){
                            LatLng punto = new LatLng(Double.valueOf(lstplace.get(i).lat),Double.valueOf(lstplace.get(i).lng));
                            mMap.addMarker(new MarkerOptions().position(punto).title("Marker suplentes!! #" + i)).setTag(lstplace.get(i));
                            Picasso.get()
                                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" + lstplace.get(i).photo_reference + "&key=AIzaSyCx2klhltS0foiCjqvuxh27SV67y_VyZ_w")
                                    .fetch();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error al obtener lugares", Toast.LENGTH_SHORT).show();
                });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();
        agregarMarcador(latLng, "Ubicación seleccionada");
        defaultLocation = latLng;
    }
}
