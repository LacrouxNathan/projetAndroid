package com.example.comptemoutons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        // Bouton de retour sur la barre de menu
        assert getSupportActionBar() != null; // éviter le nullpointerexception
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        // retour à l'activité précédente
        finish();
        return super.onSupportNavigateUp();
    }
}
