package com.domain.owner.radninalozifinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import services.Session;

public class AdminPocetnaActivity extends AppCompatActivity {

    ImageButton btnKorisnici, btnRadniNalozi, btnKlijenti, btnMaterijali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pocetna);

        this.btnKorisnici = findViewById(R.id.btnKorisnici);
        this.btnRadniNalozi = findViewById(R.id.btnRadniNalozi);
        this.btnKlijenti = findViewById(R.id.btnKlijenti);
        this.btnMaterijali = findViewById(R.id.btnMaterijali);


        this.btnKorisnici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity("users");
            }
        });

        this.btnRadniNalozi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity("nalozi");
            }
        });

        this.btnKlijenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity("klijenti");
            }
        });

        this.btnMaterijali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity("materijali");
            }
        });
    }

    public void openActivity(String name){
        if(name.equals("users")){
            Intent intent = new Intent(AdminPocetnaActivity.this, AdminKorisniciActivity.class);
            startActivity(intent);
        }
        else if(name.equals("nalozi")){
            Intent intent = new Intent(AdminPocetnaActivity.this, AdminRadniNaloziActivity.class);
            startActivity(intent);
        }
        else if(name.equals("klijenti")){
            Intent intent = new Intent(AdminPocetnaActivity.this, AdminKlijentiActivity.class);
            startActivity(intent);
        }
        else if(name.equals("materijali")){
            Intent intent = new Intent(AdminPocetnaActivity.this, AdminMaterijaliActivity.class);
            startActivity(intent);
        }
    }


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
}
