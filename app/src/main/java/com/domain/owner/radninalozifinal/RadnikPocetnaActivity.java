package com.domain.owner.radninalozifinal;

import android.net.Uri;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.ObavijestiListAdapter;
import models.MaterijalStavka;
import models.Obavijest;
import models.RadniNalogDialogData;
import models.UpdateProps;
import models.User;
import services.Session;
import services.ToastService;
import services.VolleyCallback;
import services.VolleyService;


public class RadnikPocetnaActivity extends AppCompatActivity {

    ListView listViewObavijesti;
    ArrayList<Obavijest> obavijestiList;
    ObavijestiListAdapter obavijestiListAdapter;
    VolleyService volley;




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
        setContentView(R.layout.activity_radnik_pocetna);


        listViewObavijesti = (ListView) findViewById(R.id.listViewObavijesti);

        volley = new VolleyService(RadnikPocetnaActivity.this);
        obavijestiList = new ArrayList<>();


        volley.request(
                "/obavijesti/" + Session.getLoggedUser().getUid(),
                Request.Method.GET,
                new HashMap<String, String>(),
                new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        obavijestiList = Obavijest.toList(response);
                        obavijestiListAdapter = new ObavijestiListAdapter(RadnikPocetnaActivity.this, obavijestiList);
                        listViewObavijesti.setAdapter(obavijestiListAdapter);
                    }

                    @Override
                    public void onError(VolleyError response) {

                    }
                }
        );


        listViewObavijesti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Obavijest obavijest = obavijestiList.get(position);
                obavijest.isRead = true;
                obavijestiListAdapter.notifyDataSetChanged();

                if(!obavijest.isRead){
                    volley.request(
                            "/obavijesti/open/" + obavijest.getUid(),
                            Request.Method.PATCH,
                            UpdateProps.objectToProps(obavijest),
                            new VolleyCallback() {
                                @Override
                                public void onSuccess(String response) {
                                }

                                @Override
                                public void onError(VolleyError response) {

                                }
                            }
                    );

                }


                volley.request(
                        "/radni-nalozi/" + obavijest.getRadniNalogID(),
                        Request.Method.GET,
                        new HashMap<String, String>(),
                        new VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {
                                RadniNalogDialogData rn = RadniNalogDialogData.fromJson(response);//radniNaloziList.get(position);

                                AlertDialog.Builder builder = new AlertDialog.Builder(RadnikPocetnaActivity.this);
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

                            @Override
                            public void onError(VolleyError response) {

                            }
                        }
                );
            }
        });

    }
}
