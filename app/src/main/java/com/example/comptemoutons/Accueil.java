package com.example.comptemoutons;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Accueil extends AppCompatActivity {

    static final int AJOUT_TROUPEAU = 5;
    static final int AJOUTER_RETOUR = 6;
    private static final int REQUEST_PERMISSIONS = 24;

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_layout);

        final android.support.design.widget.FloatingActionButton btnAjouter = findViewById(R.id.buttonAjouter);
        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               clickBtnAjouter();
            }
        });

        this.lv = (ListView) findViewById(R.id.listViewAccueil);
        lv.addHeaderView(getLayoutInflater().inflate(R.layout.list_header, null, false));
        updateListView();

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
            updateListView();
            String tailleTroupeau = data.getStringExtra("tailleTroupeau");
            Toast.makeText(this,"La taille du troupeau est de " + tailleTroupeau + " moutons",Toast.LENGTH_SHORT).show();
        }

    }

    private void clickBtnAjouter() {
        // Demande des permissions seulement si l'appareil a une version >= Android 6
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {
            startActivityForResult(new Intent(Accueil.this,Ajouter.class),AJOUT_TROUPEAU);
        }
    }

    private void updateListView() {
        SQLiteDatabase dbr = new ClientDbHelper(this).getReadableDatabase();

        String[] col = {"idT", "dateT", "photo", "taille"};
        String[] select = {};
        Cursor curs = dbr.query("Troupeau", col, "", select, null, null, null);

        List<Map<String,String>> listeTroupeaux = new LinkedList<>();
        List<Bitmap> imagesTroupeaux = new LinkedList<>();

        if(curs.moveToFirst()) {
            do {
                Map<String,String> donneesTroupeau = new HashMap<>();
                donneesTroupeau.put("idT",curs.getString(curs.getColumnIndexOrThrow("idT")));
                donneesTroupeau.put("dateT",curs.getString(curs.getColumnIndexOrThrow("dateT")));
                //donneesTroupeau.put("photo",new String(curs.getBlob(curs.getColumnIndexOrThrow("photo")), StandardCharsets.UTF_8));
                donneesTroupeau.put("taille",String.valueOf(curs.getInt(curs.getColumnIndexOrThrow("taille"))));
                listeTroupeaux.add(donneesTroupeau);

                Bitmap imageTroupeau = bytesToBitmap(curs.getBlob(curs.getColumnIndexOrThrow("photo")));
                imagesTroupeaux.add(imageTroupeau);

            } while(curs.moveToNext());
        }
        dbr.close();

        CustomListAdapter customListAdapter = new CustomListAdapter(this, listeTroupeaux,imagesTroupeaux);
        this.lv.setAdapter(customListAdapter);
    }

    private static Bitmap bytesToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
