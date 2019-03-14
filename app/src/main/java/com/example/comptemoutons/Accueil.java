package com.example.comptemoutons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class Accueil extends AppCompatActivity {

    static final int AJOUT_TROUPEAU = 5;
    static final int AJOUTER_RETOUR = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_layout);

        final android.support.design.widget.FloatingActionButton btnAjouter = findViewById(R.id.buttonAjouter);
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(view.getContext(),Ajouter.class),AJOUT_TROUPEAU);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.accueil_menu_layout,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuDeconnexion:
                startActivity(new Intent(this,Connexion.class));
                return true;
            case R.id.menuAbout:
                startActivity(new Intent(this,About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AJOUT_TROUPEAU) {
            String tailleTroupeau = data.getStringExtra("tailleTroupeau");
            Toast.makeText(this,"La taille du troupeau est de " + tailleTroupeau + " moutons",Toast.LENGTH_SHORT).show();
        }

    }

}
