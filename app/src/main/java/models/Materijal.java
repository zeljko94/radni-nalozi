package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Materijal {
    private String uid;
    private String naziv;
    private double cijena;

    public Materijal(){

    }

    public Materijal(String uid, String naziv, double cijena) {
        this.uid = uid;
        this.naziv = naziv;
        this.cijena = cijena;
    }

    @Override
    public String toString() {
        return getNaziv();
    }

    public static Materijal jsonToMaterijal(String json) {
        Materijal materijal = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String id = jsonObject.getString("_id");
            String naziv = jsonObject.getString("naziv");
            double cijena = jsonObject.getDouble("cijena");
            materijal = new Materijal(id, naziv, cijena);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  materijal;
    }

    public static ArrayList<Materijal> jsonToMaterijalList(String json){
        ArrayList<Materijal> materijali = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                materijali.add(jsonToMaterijal(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return materijali;
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

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }
}
