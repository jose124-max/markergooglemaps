package com.example.markergooglemaps.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.Image;
import com.bumptech.glide.Glide;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.markergooglemaps.R;
import com.example.markergooglemaps.models.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

public class adaptadorplaces implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "adaptadorplaces";
    private LayoutInflater inflater;
    private Places places;

    public adaptadorplaces(LayoutInflater inflater){
        this.inflater = inflater;
        View v = inflater.inflate(R.layout.infowindowadapter, null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow( Marker marker) {
        if (marker == null) {
            return null;
        }
        try {

        View v = inflater.inflate(R.layout.infowindowadapter, null);
        places = (Places) marker.getTag();
        ImageView image = (ImageView)v.findViewById(R.id.imgicono);
        ((TextView)v.findViewById(R.id.lblNombre)).setText(places.name);
        ((TextView)v.findViewById(R.id.lblDireccion)).setText(places.vicinity);
        ((TextView)v.findViewById(R.id.lblweb)).setText(places.open_now);
            Picasso.get().load(places.icon).into(image);

        ImageView image2 = (ImageView)v.findViewById(R.id.imgUsr);
        if (places.photo_reference != null && !places.photo_reference.isEmpty()) {
            //Glide.with(v).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" + places.photo_reference + "&key=AIzaSyCx2klhltS0foiCjqvuxh27SV67y_VyZ_w").into(image);
            Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" + places.photo_reference + "&key=AIzaSyCx2klhltS0foiCjqvuxh27SV67y_VyZ_w").into(image2);

        }
        return v;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}