package models;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RadniNalogDialogData {
    public String uid;
    public String naziv;
    public String opis;
    public String datumPocetka, datumZavrsetka, datumKreiranja;
    public String napomena;
    public double total;
    public Klijent klijent;
    public ArrayList<User> izvrsitelji;
    public ArrayList<MaterijalStavka> materijali;

    public RadniNalogDialogData() {
    }

    public RadniNalogDialogData(String uid, String naziv, Klijent klijent, String opis, String datumPocetka, String datumZavrsetka, String datumKreiranja, String napomena, double total, ArrayList<User> izvrsitelji, ArrayList<MaterijalStavka> materijali) {
        this.uid = uid;
        this.naziv = naziv;
        this.klijent = klijent;
        this.opis = opis;
        this.datumPocetka = datumPocetka;
        this.datumZavrsetka = datumZavrsetka;
        this.datumKreiranja = datumKreiranja;
        this.napomena = napomena;
        this.total = total;
        this.izvrsitelji = izvrsitelji;
        this.materijali = materijali;
    }

    public static RadniNalogDialogData fromJson(String json){
        RadniNalogDialogData result = new RadniNalogDialogData();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nalog = jsonObject.getJSONObject("nalog");
            String uid = nalog.getString("_id");
            String naziv = nalog.getString("naziv");
            String opis = nalog.getString("opis");
            String datumPocetka = nalog.getString("datumPocetka");
            String datumZavrsetka = nalog.getString("datumZavrsetka");
            double total          = nalog.getDouble("total");
            String napomena       = nalog.getString("napomena");
            String datumKreiranja = nalog.getString("datumKreiranja");
            result.setUid(uid);
            result.setNaziv(naziv);
            result.setOpis(opis);
            result.setDatumPocetka(datumPocetka);
            result.setDatumZavrsetka(datumZavrsetka);
            result.setDatumKreiranja(datumKreiranja);
            result.setTotal(total);
            result.setNapomena(napomena);

            String klijentJSON = nalog.getString("klijentID");
            Klijent klijentObj = Klijent.jsonToKlijent(klijentJSON);
            result.setKlijent(klijentObj);

            String izvrsiteljiJSON = jsonObject.getString("izvrsitelji");
            ArrayList<User> izvrsitelji = User.jsonToUserList(izvrsiteljiJSON);
            result.setIzvrsitelji(izvrsitelji);

            String stavkeJSON = jsonObject.getString("stavke");
            ArrayList<MaterijalStavka> stavke = MaterijalStavka.jsonToList(stavkeJSON);
            result.setMaterijali(stavke);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<RadniNalogDialogData> jsonToList(String json){
        ArrayList<RadniNalogDialogData> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                result.add(fromJson(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(String datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
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

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(String datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public String getDatumZavrsetka() {
        return datumZavrsetka;
    }

    public void setDatumZavrsetka(String datumZavrsetka) {
        this.datumZavrsetka = datumZavrsetka;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<User> getIzvrsitelji() {
        return izvrsitelji;
    }

    public void setIzvrsitelji(ArrayList<User> izvrsitelji) {
        this.izvrsitelji = izvrsitelji;
    }

    public ArrayList<MaterijalStavka> getMaterijali() {
        return materijali;
    }

    public void setMaterijali(ArrayList<MaterijalStavka> materijali) {
        this.materijali = materijali;
    }
}
