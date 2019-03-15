package com.example.comptemoutons;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Ajouter extends AppCompatActivity {

	private static final int ECART = 10;
    private static final int REQUEST_IMAGE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_layout);

        Button btnPhoto = (Button) findViewById(R.id.buttonPhoto);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        android.support.design.widget.FloatingActionButton btnValider = findViewById(R.id.buttonValider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnValider();
            }
        });

        // On met une image vide dans l'imageView, cela servira pour la condition plus bas
        ImageView imageViewNouveauTroupeau = (ImageView) findViewById(R.id.imageViewNouveauTroupeau);
        imageViewNouveauTroupeau.setImageDrawable(null);

        // Bouton de retour sur la barre de menu
        assert getSupportActionBar() != null; // éviter le nullpointerexception
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // récupération de l'image pour que la classe puisse la traiter plus tard
        Bitmap bitmap = ImagePicker.getBitmapFromResult(this,resultCode,data);
        if (null != bitmap && resultCode == RESULT_OK) {
            ImageView imageViewNouveauTroupeau = (ImageView) findViewById(R.id.imageViewNouveauTroupeau);
            imageViewNouveauTroupeau.setImageBitmap(bitmap);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intentRetour = new Intent();
        setResult(Accueil.AJOUTER_RETOUR,intentRetour);
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        // retour à l'activité précédente
        finish();
        return super.onSupportNavigateUp();
    }

    private void selectImage() {
        Intent takeImageIntent = ImagePicker.getPickImageIntent(this);
        if (takeImageIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(takeImageIntent, REQUEST_IMAGE);
        }
    }

    private void clickBtnValider() {
        ImageView imageViewNouveauTroupeau = (ImageView) findViewById(R.id.imageViewNouveauTroupeau);
        if (null == imageViewNouveauTroupeau.getDrawable()) {
            Toast.makeText(this,R.string.textImageVide,Toast.LENGTH_SHORT).show();
        } else {
            // On récupère la valeur du radiobutton
            RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroupTaille);
            int val = rg.getCheckedRadioButtonId();
            RadioButton rb = (RadioButton) findViewById(val);
            int tailleChoisie = Integer.valueOf(rb.getText().toString());

            // Génération de nombre aléatoire
            Random r = new Random();
            int tailleTroupeau = r.nextInt(ECART*2+1) + tailleChoisie-ECART;

            // La date du jour
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatter.format(new Date());


            // INSERTION DANS BDD
            SQLiteDatabase dbw = new ClientDbHelper(this).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("dateT",date);
            byte[] photo = {Byte.MAX_VALUE,Byte.MIN_VALUE,Byte.MAX_VALUE};
            values.put("photo",photo);
            values.put("taille",tailleTroupeau);
            dbw.insert("Troupeau", null, values);
            dbw.close();


            // Code de l'intent de retour
            Intent intentRetour = new Intent();
            intentRetour.putExtra("tailleTroupeau",String.valueOf(tailleTroupeau));
            setResult(Accueil.AJOUT_TROUPEAU,intentRetour);
            finish();
        }

    }

}
