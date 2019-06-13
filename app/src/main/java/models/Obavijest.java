package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Obavijest {
    public String uid;
    public String naslov;
    public String body;
    public String korisnikID;
    public String radniNalogID;
    public boolean isRead;
    public String datum;

    public Obavijest() {
    }

    public Obavijest(String uid, String naslov, String body, String korisnikID, String radniNalogID, boolean isRead, String datum) {
        this.uid = uid;
        this.naslov = naslov;
        this.body = body;
        this.korisnikID = korisnikID;
        this.radniNalogID = radniNalogID;
        this.isRead = isRead;
        this.datum = datum;
    }


    public static Obavijest fromJson(String json) {
        Obavijest obavijest = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String id = jsonObject.getString("_id");
            String naslov = jsonObject.getString("naslov");
            String body = jsonObject.getString("body");
            String korisnikID = jsonObject.getString("korisnikID");
            String radniNalogID = jsonObject.getString("radniNalogID");
            boolean isRead = jsonObject.getBoolean("isRead");
            String datum = jsonObject.getString("datum");
            obavijest = new Obavijest(id, naslov, body, korisnikID, radniNalogID, isRead, datum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  obavijest;
    }

    public static ArrayList<Obavijest> toList(String json){
        ArrayList<Obavijest> obavijesti = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                obavijesti.add(fromJson(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obavijesti;
    }




    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(String korisnikID) {
        this.korisnikID = korisnikID;
    }

    public String getRadniNalogID() {
        return radniNalogID;
    }

    public void setRadniNalogID(String radniNalogID) {
        this.radniNalogID = radniNalogID;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
