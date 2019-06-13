package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Klijent {
    private String uid;
    private String naziv;
    private double popust;


    public Klijent() {
    }

    @Override
    public String toString() {
        return getNaziv();
    }

    public Klijent(String uid, String naziv, double popust) {
        this.uid = uid;
        this.naziv = naziv;
        this.popust = popust;
    }



    public static Klijent jsonToKlijent(String json) {
        Klijent klijent = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String id = jsonObject.getString("_id");
            String naziv = jsonObject.getString("naziv");
            double popust = jsonObject.getDouble("popust");
            klijent = new Klijent(id, naziv, popust);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return klijent;
    }

    public static ArrayList<Klijent> jsonToKlijentList(String json){
        ArrayList<Klijent> klijenti = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                klijenti.add(jsonToKlijent(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return klijenti;
    }





    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }
}
