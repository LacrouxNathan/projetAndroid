package com.example.comptemoutons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Random;

public class Ajouter extends AppCompatActivity {

    private static final int PICK_FROM_GALLARY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_layout);

        Button btnPhoto = (Button) findViewById(R.id.buttonPhoto);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLARY);
            }
        });

        android.support.design.widget.FloatingActionButton btnValider = findViewById(R.id.buttonValider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // On récupère la valeur du radiobutton
                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupTaille);
                int val = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(val);
                int tailleChoisie = Integer.valueOf(rb.getText().toString());

                // Génération de nombre aléatoire
                Random r = new Random();
                int tailleTroupeau = r.nextInt(11) + tailleChoisie-5;

                // Code de l'intent de retour
                Intent intentRetour = new Intent();
                intentRetour.putExtra("tailleTroupeau",String.valueOf(tailleTroupeau));
                setResult(Accueil.AJOUT_TROUPEAU,intentRetour);
                finish();

            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("il faut désormais traiter la photo");

    }

    @Override
    public void onBackPressed() {
        Intent intentRetour = new Intent();
        setResult(Accueil.AJOUTER_RETOUR,intentRetour);
        finish();
    }

}
