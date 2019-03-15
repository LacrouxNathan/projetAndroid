package com.example.comptemoutons;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Connexion extends AppCompatActivity {

    // le mot de passe est $moutons
    private static final String CIPHERED_PWD = "2dc38cc3282736325000b17b763c6ef";
    private SQLiteDatabase dbr; // db de lecture
    private Cursor curs; // curseur de parcours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_layout);
        setTitle(R.string.titleConnexion);

        this.refreshDatabase();
        assert(this.curs != null); // on s'assure que le refresh a bien été effectué

        // si il n'y a pas d'utilisateur enregistré
        if (!this.curs.moveToFirst()) {
            SQLiteDatabase dbw = new ClientDbHelper(this).getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("pwd",CIPHERED_PWD); // enregistrement du mot de passe dans la BDD
            dbw.insert("User", null, values);
            dbw.close();
            this.closeDatabase();
            this.refreshDatabase();
            assert(this.curs != null); // on s'assure que le refresh a bien été effectué
            curs.moveToFirst();
        }

        final String storedHashedPwd = curs.getString(curs.getColumnIndexOrThrow("pwd"));

        final android.support.design.widget.FloatingActionButton btnConnexion = findViewById(R.id.buttonConnexion);
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = findViewById(R.id.editPwd);
                if (checkPassword(storedHashedPwd,et.getText().toString()))
                    startActivity(new Intent(view.getContext(),Accueil.class));
                else
                    Toast.makeText(view.getContext(),R.string.textErrMdp,Toast.LENGTH_SHORT).show();
            }
        });

        this.closeDatabase();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private boolean checkPassword(String storedHashedPwd, String inputPwd) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(inputPwd.getBytes(),0,inputPwd.length());
        String hashPwd = new BigInteger(1,m.digest()).toString(16);

        return hashPwd.equals(storedHashedPwd);
    }

    private void refreshDatabase() {
        this.dbr = new ClientDbHelper(this).getReadableDatabase();

        String[] col = {"idU", "pwd"};
        String[] select = {};
        this.curs = dbr.query("User", col, "", select, null, null, null);
    }

    private void closeDatabase() {
        this.curs.close();
        this.dbr.close();
    }

}
