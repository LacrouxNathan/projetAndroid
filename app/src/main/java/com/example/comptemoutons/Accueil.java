package com.example.comptemoutons;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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

        SQLiteDatabase db = new ClientDbHelper(this).getReadableDatabase();

        String[] col = {"idT", "dateT", "photo", "taille"};
        String[] select = {};
        Cursor curs = db.query("Troupeau", col, "", select, null, null, null);

        List<Map<String,String>> listeTroupeaux = new LinkedList<>();

        if(curs.moveToFirst()) {
            do {
                Map<String,String> donneesTroupeau = new HashMap<>();
                donneesTroupeau.put("idT",curs.getString(curs.getColumnIndexOrThrow("idT")));
                donneesTroupeau.put("dateT",curs.getString(curs.getColumnIndexOrThrow("dateT")));
                donneesTroupeau.put("photo",new String(curs.getBlob(curs.getColumnIndexOrThrow("photo")), StandardCharsets.UTF_8));
                donneesTroupeau.put("taille",String.valueOf(curs.getInt(curs.getColumnIndexOrThrow("taille"))));

                listeTroupeaux.add(donneesTroupeau);

            } while(curs.moveToNext());
        }

        db.close();

        ListView lv = (ListView) findViewById(R.id.listViewAccueil);

        ListAdapter adapter = new SimpleAdapter(this, listeTroupeaux, R.layout.list_row,new String[]{"idT","dateT","photo","taille"},
                new int[]{R.id.textViewIdT, R.id.textViewDateT, R.id.textViewPhoto, R.id.textViewTaille});
        lv.setAdapter(adapter);

        /*SQLiteDatabase dbw = new ClientDbHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idT", 2);
        values.put("dateT","15/03/2019");
        byte[] photo = {Byte.MAX_VALUE,Byte.MIN_VALUE,Byte.MAX_VALUE};
        values.put("photo",photo);
        values.put("taille",35);
        dbw.insert("Troupeau", null, values);
        dbw.close();*/

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
