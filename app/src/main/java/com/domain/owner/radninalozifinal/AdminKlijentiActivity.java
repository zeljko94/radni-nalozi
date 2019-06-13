package com.domain.owner.radninalozifinal;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import adapters.KlijentiListAdapter;
import models.Klijent;
import services.ConvertService;
import services.Session;
import services.ToastService;
import services.VolleyCallback;
import services.VolleyService;

public class AdminKlijentiActivity extends AppCompatActivity {

    EditText editTextSearch;
    ListView listViewKlijenti;
    FloatingActionButton btnDodajKlijenta;

    ArrayList<Klijent> klijentiList;
    KlijentiListAdapter klijentiListAdapter;
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
        setContentView(R.layout.activity_admin_klijenti);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        listViewKlijenti = (ListView) findViewById(R.id.listViewKlijenti);
        btnDodajKlijenta = (FloatingActionButton) findViewById(R.id.btnDodajKlijenta);

        klijentiList = new ArrayList<>();
        volley = new VolleyService(AdminKlijentiActivity.this);
        volley.request(
                "/klijenti",
                Request.Method.GET,
                new HashMap<String, String>(),
                new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        klijentiList = Klijent.jsonToKlijentList(response);
                        klijentiListAdapter = new KlijentiListAdapter(AdminKlijentiActivity.this, klijentiList);
                        listViewKlijenti.setAdapter(klijentiListAdapter);
                    }

                    @Override
                    public void onError(VolleyError response) {

                    }
                }
        );

        listViewKlijenti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Klijent klijent = klijentiList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminKlijentiActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_izmjeni_klijenta, null);
                Button btnCancel = (Button) mView.findViewById(R.id.btnCancel);
                Button btnOk = (Button) mView.findViewById(R.id.btnOk);

                final EditText txtNaziv = (EditText) mView.findViewById(R.id.txtNaziv);
                final EditText txtPopust = (EditText) mView.findViewById(R.id.txtPopust);

                txtNaziv.setText(String.valueOf(klijent.getNaziv()));
                txtPopust.setText(String.valueOf(klijent.getPopust()));

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
                        String naziv      = String.valueOf(txtNaziv.getText());
                        String popust     = String.valueOf(txtPopust.getText());

                        if(!naziv.equals("")){
                                /*
                                Materijal materijal = new Materijal("", naziv, Double.valueOf(cijena));
                                volleyService.request(
                                        "/materijali",
                                        Request.Method.POST,
                                        ConvertService.toHashMap(materijal),
                                        new VolleyCallback() {
                                            @Override
                                            public void onSuccess(String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    Materijal materijal = Materijal.jsonToMaterijal(jsonObject.toString());
                                                    materijaliList.add(materijal);
                                                    materijaliListAdapter.notifyDataSetChanged();
                                                    dialog.dismiss();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onError(VolleyError response) {

                                            }
                                        }
                                );
                                */
                        }
                        else{
                            ToastService.center("Unesite naziv klijenti!", AdminKlijentiActivity.this);
                        }
                    }
                });
                dialog.show();
            }
        });

        btnDodajKlijenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminKlijentiActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_dodaj_klijenta, null);
                Button btnCancel = (Button) mView.findViewById(R.id.btnCancel);
                Button btnOk = (Button) mView.findViewById(R.id.btnOk);

                final EditText txtNaziv = (EditText) mView.findViewById(R.id.txtNaziv);
                final EditText txtPopust = (EditText) mView.findViewById(R.id.txtPopust);


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
                        String naziv      = String.valueOf(txtNaziv.getText());
                        String popust  = String.valueOf(txtPopust.getText());

                        if(!naziv.equals("")){
                            Klijent klijent = new Klijent("", naziv, Double.valueOf(popust));
                            volley.request(
                                    "/klijenti",
                                    Request.Method.POST,
                                    ConvertService.toHashMap(klijent),
                                    new VolleyCallback() {
                                        @Override
                                        public void onSuccess(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                Klijent klijent = Klijent.jsonToKlijent(jsonObject.toString());
                                                klijentiList.add(klijent);
                                                klijentiListAdapter.notifyDataSetChanged();
                                                dialog.dismiss();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError(VolleyError response) {

                                        }
                                    }
                            );
                        }
                        else{
                            ToastService.center("Unesite naziv klijenta!", AdminKlijentiActivity.this);
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}
