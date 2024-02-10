package com.example.markergooglemaps.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Places {
    public String lat;
    public String lng;
    public String icon;
    public String name;
    public String vicinity;
    public String photo_reference;
    public String open_now;

    public String lat() { return lat; }

    public void setlat(String lat) { this.lat = lat; }

    public String getlng() { return lng; }

    public void setlng(String location_lng) { this.lng = lng; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getVicinity() { return vicinity; }

    public void setVicinity(String vicinity) { this.vicinity = vicinity; }




    public Places(JSONObject a) throws JSONException {
        JSONObject geometry = a.getJSONObject("geometry");
        JSONObject location = geometry.getJSONObject("location");
        lat = location.getString("lat").toString();
        lng = location.getString("lng").toString();
        icon = a.getString("icon").toString();
        name = a.getString("name").toString();
        vicinity = a.getString("vicinity").toString();


        if(!a.isNull("opening_hours")){
            JSONObject horario = a.getJSONObject("opening_hours");
            if(horario.getString("open_now")=="true")
                open_now = ("Abierto");
            else open_now = ("Cerrado");
        }else
        {
            open_now = "No tiene horario";
        }
        //para sacar las photos
        JSONObject JSONlista = null;
        JSONlista = a;


        if(!JSONlista.isNull("photos")   ){
            JSONArray JSONlistaphoto = JSONlista.getJSONArray("photos");
            JSONObject photreferen = JSONlistaphoto.getJSONObject(0);
            photo_reference = photreferen.getString("photo_reference").toString();
        }else
        {
            photo_reference = "No tiene foto";
        }
    }


    public static ArrayList<Places> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Places> places = new ArrayList<>();

        for (int i = 0; i < datos.length() && i<20; i++) {
            places.add(new Places(datos.getJSONObject(i)));
        }
        return places;
    }

}