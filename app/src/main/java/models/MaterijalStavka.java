package models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MaterijalStavka {
    public String uid;
    public Materijal materijal;
    public int kolicina;


    public MaterijalStavka(String uid, Materijal materijal, int kolicina){
        this.uid = uid;
        this.materijal = materijal;
        this.kolicina = kolicina;
    }


    public static MaterijalStavka fromJson(String json) {
        MaterijalStavka ms = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String id = jsonObject.getString("_id");
            int kolicina = jsonObject.getInt("kolicina");
            String materijalJSON = jsonObject.getString("materijalID");
            Materijal materijal = Materijal.jsonToMaterijal(materijalJSON);
            ms = new MaterijalStavka(id, materijal, kolicina);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ms;
    }

    public static ArrayList<MaterijalStavka> jsonToList(String json){
        ArrayList<MaterijalStavka> stavke = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                stavke.add(fromJson(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stavke;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Materijal getMaterijal() {
        return materijal;
    }

    public void setMaterijal(Materijal materijal) {
        this.materijal = materijal;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
