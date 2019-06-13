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

import adapters.MaterijaliListAdapter;
import models.Materijal;
import services.ConvertService;
import services.Session;
import services.ToastService;
import services.VolleyCallback;
import services.VolleyService;

public class AdminMaterijaliActivity extends AppCompatActivity {

    EditText editTextSearch;
    ListView listViewMaterijali;
    FloatingActionButton btnDodajMaterijal;

    ArrayList<Materijal> materijaliList;
    MaterijaliListAdapter materijaliListAdapter;

    VolleyService volleyService;




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
        setContentView(R.layout.activity_admin_materijali);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        listViewMaterijali = (ListView) findViewById(R.id.listViewMaterijali);
        btnDodajMaterijal = (FloatingActionButton) findViewById(R.id.btnDodajMaterijal);

        volleyService = new VolleyService(AdminMaterijaliActivity.this);
        materijaliList = new ArrayList<>();

        volleyService.request("/materijali", Request.Method.GET, new HashMap<String, String>(), new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                materijaliList = Materijal.jsonToMaterijalList(response);
                materijaliListAdapter = new MaterijaliListAdapter(AdminMaterijaliActivity.this, materijaliList);
                listViewMaterijali.setAdapter(materijaliListAdapter);
            }

            @Override
            public void onError(VolleyError response) {

            }
        });

        listViewMaterijali.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Materijal materijal = materijaliList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMaterijaliActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_izmjeni_materijal, null);
                Button btnCancel = (Button) mView.findViewById(R.id.btnCancel);
                Button btnOk = (Button) mView.findViewById(R.id.btnOk);

                final EditText txtNaziv = (EditText) mView.findViewById(R.id.txtNaziv);
                final EditText txtCijena = (EditText) mView.findViewById(R.id.txtCijena);

                txtNaziv.setText(String.valueOf(materijal.getNaziv()));
                txtCijena.setText(String.valueOf(materijal.getCijena()));

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
                        String cijena  = String.valueOf(txtCijena.getText());

                        if(!naziv.equals("")){
                            if(!cijena.equals("")){
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
                                ToastService.center("Unesite cijenu materijala!", AdminMaterijaliActivity.this);
                            }
                        }
                        else{
                            ToastService.center("Unesite naziv materijala!", AdminMaterijaliActivity.this);
                        }
                    }
                });
                dialog.show();
            }
        });

        btnDodajMaterijal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMaterijaliActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_dodaj_materijal, null);
                Button btnCancel = (Button) mView.findViewById(R.id.btnCancel);
                Button btnOk = (Button) mView.findViewById(R.id.btnOk);

                final EditText txtNaziv = (EditText) mView.findViewById(R.id.txtNaziv);
                final EditText txtCijena = (EditText) mView.findViewById(R.id.txtCijena);


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
                        String cijena  = String.valueOf(txtCijena.getText());

                        if(!naziv.equals("")){
                            if(!cijena.equals("")){
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
                            }
                            else{
                                ToastService.center("Unesite cijenu materijala!", AdminMaterijaliActivity.this);
                            }
                        }
                        else{
                            ToastService.center("Unesite naziv materijala!", AdminMaterijaliActivity.this);
                        }
                    }
                });
                dialog.show();
            }
        });

    }
}
