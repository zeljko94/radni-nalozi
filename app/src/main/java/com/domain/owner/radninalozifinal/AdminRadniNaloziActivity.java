package com.domain.owner.radninalozifinal;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.MaterijalStavkaListAdapter;
import adapters.RadniNalogListAdapter;
import adapters.RadniciListAdapter;
import adapters.SpinnerRadniciAdapter;
import adapters.UsersListAdapter;
import models.Klijent;
import models.Materijal;
import models.MaterijalStavka;
import models.RadniNalog;
import models.RadniNalogDialogData;
import models.User;
import services.ConvertService;
import services.Session;
import services.ToastService;
import services.VolleyCallback;
import services.VolleyService;

public class AdminRadniNaloziActivity extends AppCompatActivity {
    FloatingActionButton btnDodajRadniNalog;
    EditText editTextSearch;
    ListView listViewRadniNalozi;
    VolleyService volley;

    public static ArrayList<User> radniciListStore;
    ArrayList<User> dodaniRadniciList;
    ArrayList<Materijal> materijaliListStore;
    ArrayList<Klijent> klijentiListStore;

    ArrayList<RadniNalogDialogData> radniNaloziList;
    RadniNalogListAdapter radniNalogListAdapter;
    ArrayList<MaterijalStavka> materijalStavkeList;

    public static RadniciListAdapter radniciListAdapter;
    public static MaterijalStavkaListAdapter materijalStavkaListAdapter;

    RadniNalogDialogData nalog;





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Logout){
            Session.destroy(getApplicationContext());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_radni_nalozi);

        btnDodajRadniNalog = (FloatingActionButton) findViewById(R.id.btnDodajRadniNalog);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        listViewRadniNalozi = (ListView) findViewById(R.id.listViewRadniNalozi);


        dodaniRadniciList = new ArrayList<>();


        volley = new VolleyService(AdminRadniNaloziActivity.this);
        radniNaloziList = new ArrayList<>();

        volley.request(
                "/radni-nalozi",
                Request.Method.GET,
                new HashMap<String, String>(),
                new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        radniNaloziList = RadniNalogDialogData.jsonToList(response);
                        radniNalogListAdapter = new RadniNalogListAdapter(AdminRadniNaloziActivity.this, radniNaloziList);
                        listViewRadniNalozi.setAdapter(radniNalogListAdapter);
                    }

                    @Override
                    public void onError(VolleyError response) {

                    }
                }
        );

        listViewRadniNalozi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RadniNalogDialogData rn = radniNaloziList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminRadniNaloziActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_pregled_radnog_naloga, null);


                final TextView txtNazivProjekta = (TextView) mView.findViewById(R.id.txtNazivProjekta);
                final TextView txtNazivKlijenta = (TextView) mView.findViewById(R.id.txtNazivKlijenta);
                final TextView txtOpisProjekta = (TextView) mView.findViewById(R.id.txtOpisProjekta);
                final TextView txtPocetniDatum = (TextView) mView.findViewById(R.id.txtPocetniDatum);
                final TextView txtZavrsniDatum = (TextView) mView.findViewById(R.id.txtZavrsniDatum);
                final TextView txtTotal = (TextView) mView.findViewById(R.id.txtTotal);
                final TextView txtNapomene = (TextView) mView.findViewById(R.id.txtNapomene);
                final TextView txtIzvrsitelji = (TextView) mView.findViewById(R.id.txtIzvrsitelji);
                final TextView txtMaterijali = (TextView) mView.findViewById(R.id.txtMaterijali);

                txtNazivProjekta.setText(String.valueOf(rn.getNaziv()));
                txtNazivKlijenta.setText(String.valueOf(rn.getKlijent().getNaziv()));
                txtOpisProjekta.setText(String.valueOf(rn.getOpis()));
                txtPocetniDatum.setText(String.valueOf(rn.getDatumPocetka()));
                txtZavrsniDatum.setText(String.valueOf(rn.getDatumZavrsetka()));
                txtTotal.setText(String.valueOf(rn.getTotal() + " KM"));
                txtNapomene.setText(String.valueOf(rn.getNapomena()));

                String izvString = "";
                for(User iz: rn.getIzvrsitelji()){
                    izvString += iz.getName() + " " + iz.getLastname() + "\n";
                }

                String materijaliString = "";
                for(MaterijalStavka ms: rn.getMaterijali()){
                    materijaliString += ms.getMaterijal().getNaziv() + "  x" + ms.getKolicina() + "\n";
                }
                txtMaterijali.setText(String.valueOf(materijaliString));

                txtIzvrsitelji.setText(String.valueOf(izvString));
                builder.setView(mView);
                final AlertDialog dialog = builder.create();

                dialog.show();
            }
        });


        btnDodajRadniNalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminRadniNaloziActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_dodaj_radni_nalog, null);
                Button btnCancel = (Button) mView.findViewById(R.id.btnCancel);
                Button btnOk = (Button) mView.findViewById(R.id.btnOk);

                final Spinner spinnerKlijent, spinnerRadnici, spinnerMaterijali;
                final SpinnerRadniciAdapter radniciSpinnerAdapter;
                final EditText txtNaziv, txtOpis, txtDatumPocetka, txtDatumZavrsetka, txtMaterijalKolicina, txtNapomena, txtTotal;
                final Button btnDodajIzvrsitelja, btnDodajMaterijal;
                final ListView listViewIzvristelji, listViewMaterijali;

                spinnerKlijent = (Spinner) mView.findViewById(R.id.spinnerKlijent);
                spinnerMaterijali = (Spinner) mView.findViewById(R.id.spinnerMaterijali);
                spinnerRadnici = (Spinner) mView.findViewById(R.id.spinnerRadnici);
                listViewIzvristelji = (ListView) mView.findViewById(R.id.listViewIzvrsitelji);
                listViewMaterijali = (ListView) mView.findViewById(R.id.listViewMaterijali);
                radniciSpinnerAdapter = new SpinnerRadniciAdapter(AdminRadniNaloziActivity.this, radniciListStore);

                txtNapomena = (EditText) mView.findViewById(R.id.txtNapomena);
                txtNaziv = (EditText) mView.findViewById(R.id.txtNaziv);
                txtOpis = (EditText) mView.findViewById(R.id.txtOpis);
                txtDatumPocetka = (EditText) mView.findViewById(R.id.txtDatumPocetka);
                txtDatumZavrsetka = (EditText) mView.findViewById(R.id.txtDatumZavrsetka);
                txtMaterijalKolicina = (EditText) mView.findViewById(R.id.txtMaterijalKolicina);
                btnDodajIzvrsitelja = (Button) mView.findViewById(R.id.btnDodajIzvrsitelja);
                txtTotal = (EditText) mView.findViewById(R.id.txtTotal);
                btnDodajMaterijal = (Button) mView.findViewById(R.id.btnDodajMaterijal);

                txtNapomena.setText(String.valueOf("ASDASD"));
                txtNaziv.setText(String.valueOf("Naziv"));
                txtOpis.setText(String.valueOf("opis"));
                txtDatumPocetka.setText(String.valueOf("datum pocetka"));
                txtDatumZavrsetka.setText(String.valueOf("datum zav"));
                txtMaterijalKolicina.setText(String.valueOf("5"));
                txtTotal.setText(String.valueOf("1234"));

                radniciListAdapter = new RadniciListAdapter(AdminRadniNaloziActivity.this, dodaniRadniciList);
                listViewIzvristelji.setAdapter(radniciListAdapter);

                materijalStavkeList = new ArrayList<>();



                btnDodajIzvrsitelja.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User selectedRadnik = (User) spinnerRadnici.getSelectedItem();

                        if(selectedRadnik != null){
                            dodaniRadniciList.add(selectedRadnik);
                            radniciListStore.remove(selectedRadnik);
                            spinnerRadnici.setSelection(-1);
                            radniciSpinnerAdapter.notifyDataSetChanged();
                            radniciListAdapter.notifyDataSetChanged();
                        }
                    }
                });

                btnDodajMaterijal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Materijal materijal = (Materijal) spinnerMaterijali.getSelectedItem();
                        if(materijal != null){
                            int kolicina = 0;
                            try{
                                kolicina = Integer.valueOf((String.valueOf(txtMaterijalKolicina.getText())));
                            }
                            catch (Exception ex){
                                ToastService.center("Unesite količinu!",AdminRadniNaloziActivity.this);
                            }
                            if(kolicina > 0){
                                MaterijalStavka ms = new MaterijalStavka("", materijal, kolicina);

                                boolean found = false;
                                for(MaterijalStavka stavka: materijalStavkeList){
                                    if(stavka.getMaterijal().getUid().equals(materijal.getUid())){
                                        stavka.setKolicina(stavka.getKolicina() + ms.getKolicina());
                                        found = true;
                                    }
                                }
                                if(!found){
                                    materijalStavkeList.add(ms);
                                }
                                materijalStavkaListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                volley.request(
                        "/users/radnici",
                        Request.Method.GET,
                        new HashMap<String, String>(),
                        new VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {
                                radniciListStore = User.jsonToUserList(response);
                                SpinnerRadniciAdapter spinnerRadniciAdapter = new SpinnerRadniciAdapter(AdminRadniNaloziActivity.this, radniciListStore);
                                spinnerRadniciAdapter.setDropDownViewResource(R.layout.spinner_list_dropdown);
                                spinnerRadnici.setAdapter(spinnerRadniciAdapter);

                            }

                            @Override
                            public void onError(VolleyError response) {

                            }
                        }
                );

                volley.request(
                        "/materijali",
                        Request.Method.GET,
                        new HashMap<String, String>(),
                        new VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {
                                materijaliListStore = Materijal.jsonToMaterijalList(response);
                                ArrayAdapter spinnerMaterijaliAdapter = new ArrayAdapter(AdminRadniNaloziActivity.this,
                                        android.R.layout.simple_spinner_item, materijaliListStore.toArray());
                                spinnerMaterijali.setAdapter(spinnerMaterijaliAdapter);
                                materijalStavkaListAdapter = new MaterijalStavkaListAdapter(AdminRadniNaloziActivity.this, materijalStavkeList);
                                listViewMaterijali.setAdapter(materijalStavkaListAdapter);
                            }

                            @Override
                            public void onError(VolleyError response) {

                            }
                        }
                );

                volley.request(
                        "/klijenti",
                        Request.Method.GET,
                        new HashMap<String, String>(),
                        new VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {
                                klijentiListStore = Klijent.jsonToKlijentList(response);
                                ArrayAdapter spinnerKlijentiAdapter = new ArrayAdapter(AdminRadniNaloziActivity.this,
                                        android.R.layout.simple_spinner_item, klijentiListStore.toArray());
                                spinnerKlijent.setAdapter(spinnerKlijentiAdapter);
                            }

                            @Override
                            public void onError(VolleyError response) {

                            }
                        }
                );

                builder.setView(mView);
                final AlertDialog dialog = builder.create();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nalog = new RadniNalogDialogData();
                        nalog.setUid("");
                        nalog.datumPocetka = String.valueOf(txtDatumPocetka.getText());
                        nalog.datumZavrsetka = String.valueOf(txtDatumZavrsetka.getText());
                        nalog.naziv = String.valueOf(txtNaziv.getText());
                        nalog.opis = String.valueOf(txtOpis.getText());
                        nalog.klijent = (Klijent) spinnerKlijent.getSelectedItem();
                        nalog.total = Double.valueOf(String.valueOf(txtTotal.getText()));
                        nalog.napomena = String.valueOf(txtNapomena.getText());
                        //nalog.datu = String.valueOf(txtDatumPocetka.getText());
                        nalog.setIzvrsitelji(dodaniRadniciList);
                        nalog.setMaterijali(materijalStavkeList);


                        //Log.d("TEST", new Gson().toJson(nalog));
                        volley.request(
                                "/radni-nalozi",
                                Request.Method.POST,
                                new Gson().toJson(nalog),
                                new VolleyCallback() {
                                    @Override
                                    public void onSuccess(String response) {
                                        Log.d("TEST", response);
                                        RadniNalogDialogData result = RadniNalogDialogData.fromJson(response);
                                        if(result != null){
                                            radniNaloziList.add(result);
                                            radniNalogListAdapter.notifyDataSetChanged();
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onError(VolleyError response) {
                                        Log.d("TEST", response.toString());
                                        dialog.dismiss();
                                    }
                                }
                        );
                    }
                });
                dialog.show();
            }
        });
    }


    public static void OnIzvrsiteljRemove(User user){
        radniciListStore.add(user);
        radniciListAdapter.notifyDataSetChanged();
    }
}
