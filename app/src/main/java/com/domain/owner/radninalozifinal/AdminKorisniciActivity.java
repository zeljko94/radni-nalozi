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
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import adapters.UsersListAdapter;
import models.User;
import services.ConvertService;
import services.Session;
import services.ToastService;
import services.VolleyCallback;
import services.VolleyService;

public class AdminKorisniciActivity extends AppCompatActivity {
    EditText editTextSearch;
    ListView listViewUsers;
    FloatingActionButton btnDodajKorisnika;

    ArrayList<User> usersList;
    UsersListAdapter usersListAdapter;

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
        setContentView(R.layout.activity_admin_korisnici);


        this.editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        this.listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        this.btnDodajKorisnika = (FloatingActionButton) findViewById(R.id.btnDodajKorisnika);

        usersList = new ArrayList<>();
        volley = new VolleyService(AdminKorisniciActivity.this);
        volley.request("/users", Request.Method.GET, new HashMap<String, String>(), new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                usersList = User.jsonToUserList(response);
                usersListAdapter = new UsersListAdapter(AdminKorisniciActivity.this, usersList);
                listViewUsers.setAdapter(usersListAdapter);
            }

            @Override
            public void onError(VolleyError response) {

            }
        });

        this.listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminKorisniciActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_izmjeni_korisnika, null);
                Button btnCancel = (Button) mView.findViewById(R.id.btnCancel);
                Button btnOk = (Button) mView.findViewById(R.id.btnOk);

                final EditText txtIme = (EditText) mView.findViewById(R.id.txtIme);
                final EditText txtPrezime = (EditText) mView.findViewById(R.id.txtPrezime);
                final EditText txtEmail = (EditText) mView.findViewById(R.id.txtEmail);
                final EditText txtPassword = (EditText) mView.findViewById(R.id.txtPassword);
                final EditText txtRePassword = (EditText) mView.findViewById(R.id.txtRePassword);
                final Spinner spinnerRoles = (Spinner) mView.findViewById(R.id.spinnerRoles);

                User selectedUser = usersList.get(position);
                txtIme.setText(String.valueOf(selectedUser.getName()));
                txtPrezime.setText(String.valueOf(selectedUser.getLastname()));
                txtEmail.setText(String.valueOf(selectedUser.getEmail()));
                txtPassword.setText(String.valueOf(selectedUser.getPassword()));
                txtRePassword.setText(String.valueOf(selectedUser.getPassword()));
                spinnerRoles.setSelection(selectedUser.getRole().equals("admin") ? 0 : 1);

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
                        String ime      = String.valueOf(txtIme.getText());
                        String prezime  = String.valueOf(txtPrezime.getText());
                        String email    = String.valueOf(txtEmail.getText());
                        String password = String.valueOf(txtPassword.getText());
                        String rePassword = String.valueOf(txtRePassword.getText());
                        String role     = String.valueOf(spinnerRoles.getSelectedItem());

                        if(!ime.equals("")){
                            if(!prezime.equals("")){
                                if(!email.equals("")){
                                    if(!password.equals("")){
                                        if(!rePassword.equals("")){
                                            if(password.equals(rePassword)){
                                                User user = new User("", ime, prezime, email, password, role);
                                                volley.request(
                                                        "/users/signup",
                                                        Request.Method.POST,
                                                        ConvertService.toHashMap(user),
                                                        new VolleyCallback() {
                                                            @Override
                                                            public void onSuccess(String response) {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(response);
                                                                    String userJSON = jsonObject.getString("user");
                                                                    User user = User.jsonToUser(userJSON);
                                                                    usersList.add(user);
                                                                    usersListAdapter.notifyDataSetChanged();
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
                                                ToastService.center("Lozinke se ne podudaraju!", AdminKorisniciActivity.this);
                                            }
                                        }else{
                                            ToastService.center("Ponovite lozinku!", AdminKorisniciActivity.this);
                                        }

                                    }
                                    else {
                                        ToastService.center("Unesite lozinku!", AdminKorisniciActivity.this);
                                    }
                                }
                                else {
                                    ToastService.center("Unesite email!", AdminKorisniciActivity.this);
                                }
                            }
                            else{
                                ToastService.center("Unesite prezime!", AdminKorisniciActivity.this);
                            }
                        }
                        else{
                            ToastService.center("Unesite ime!", AdminKorisniciActivity.this);
                        }
                    }
                });
                dialog.show();
            }
        });

        this.btnDodajKorisnika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminKorisniciActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_dodaj_korisnika, null);
                Button btnCancel = (Button) mView.findViewById(R.id.btnCancel);
                Button btnOk = (Button) mView.findViewById(R.id.btnOk);

                final EditText txtIme = (EditText) mView.findViewById(R.id.txtIme);
                final EditText txtPrezime = (EditText) mView.findViewById(R.id.txtPrezime);
                final EditText txtEmail = (EditText) mView.findViewById(R.id.txtEmail);
                final EditText txtPassword = (EditText) mView.findViewById(R.id.txtPassword);
                final EditText txtRePassword = (EditText) mView.findViewById(R.id.txtRePassword);
                final Spinner spinnerRoles = (Spinner) mView.findViewById(R.id.spinnerRoles);



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
                        String ime      = String.valueOf(txtIme.getText());
                        String prezime  = String.valueOf(txtPrezime.getText());
                        String email    = String.valueOf(txtEmail.getText());
                        String password = String.valueOf(txtPassword.getText());
                        String rePassword = String.valueOf(txtRePassword.getText());
                        String role     = String.valueOf(spinnerRoles.getSelectedItem());

                        if(!ime.equals("")){
                            if(!prezime.equals("")){
                                if(!email.equals("")){
                                    if(!password.equals("")){
                                        if(!rePassword.equals("")){
                                            if(password.equals(rePassword)){
                                                User user = new User("", ime, prezime, email, password, role);
                                                volley.request(
                                                        "/users/signup",
                                                        Request.Method.POST,
                                                        ConvertService.toHashMap(user),
                                                        new VolleyCallback() {
                                                            @Override
                                                            public void onSuccess(String response) {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(response);
                                                                    String userJSON = jsonObject.getString("user");
                                                                    User user = User.jsonToUser(userJSON);
                                                                    usersList.add(user);
                                                                    usersListAdapter.notifyDataSetChanged();
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
                                                ToastService.center("Lozinke se ne podudaraju!", AdminKorisniciActivity.this);
                                            }
                                        }else{
                                            ToastService.center("Ponovite lozinku!", AdminKorisniciActivity.this);
                                        }

                                    }
                                    else {
                                        ToastService.center("Unesite lozinku!", AdminKorisniciActivity.this);
                                    }
                                }
                                else {
                                    ToastService.center("Unesite email!", AdminKorisniciActivity.this);
                                }
                            }
                            else{
                                ToastService.center("Unesite prezime!", AdminKorisniciActivity.this);
                            }
                        }
                        else{
                            ToastService.center("Unesite ime!", AdminKorisniciActivity.this);
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}
