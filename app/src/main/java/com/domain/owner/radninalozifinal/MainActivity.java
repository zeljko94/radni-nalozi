package com.domain.owner.radninalozifinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dialogs.LoadingDialog;
import models.Materijal;
import models.UpdateProps;
import models.User;
import services.AuthService;
import services.ConvertService;
import services.Session;
import services.ToastService;
import services.VolleyCallback;
import services.VolleyService;

public class MainActivity extends AppCompatActivity {
    EditText email_korisnika_txt;
    EditText lozinka_korisnika_txt;
    Button login_btn;

    AuthService auth;
    VolleyService volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.email_korisnika_txt = findViewById(R.id.email_korisnika_txt);
        this.lozinka_korisnika_txt = findViewById(R.id.lozinka_korisnika_txt);
        this.login_btn = findViewById(R.id.login_btn);

        this.email_korisnika_txt.setText(String.valueOf("admin@gmail.com"));
        this.lozinka_korisnika_txt.setText(String.valueOf("asd"));

        if(Session.getLoggedUser() != null){
            loadDashboard(Session.getLoggedUser().getRole());
        }

        auth = new AuthService(MainActivity.this);
        volley = new VolleyService(MainActivity.this);


        this.login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(email_korisnika_txt.getText());
                String password = String.valueOf(lozinka_korisnika_txt.getText());
                if(!email.equals("")){
                    if(!password.equals("")){
                        auth.login(email, password, new VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject != null){
                                        String userJson = jsonObject.getString("user");
                                        User user = User.jsonToUser(userJson);
                                        if(user != null){
                                            Session.setLoggedUser(user);
                                            loadDashboard(user.getRole());
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            };
                            @Override
                            public void onError(VolleyError error){
                                ToastService.center("Greška prilikom logiranja na sustav!", MainActivity.this);
                            };
                        });
                    }
                    else{
                        ToastService.center("Unesite lozinku!", MainActivity.this);
                    }
                }
                else{
                    ToastService.center("Unesite e-mail!", MainActivity.this);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        if(Session.getLoggedUser() != null){
            loadDashboard(Session.getLoggedUser().getRole());
        }
        */
    }

    private void loadDashboard(String role){
        if(role.toLowerCase().equals("admin")){
            Intent intent = new Intent(MainActivity.this, AdminPocetnaActivity.class);
            startActivity(intent);
        }
        else if(role.toLowerCase().equals("radnik")){
            Intent intent = new Intent(MainActivity.this, RadnikPocetnaActivity.class);
            startActivity(intent);
        }
    }

}
