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

    private static final String CIPHERED_PWD = "2dc38cc3282736325000b17b763c6ef"; // le mot de passe est $moutons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_layout);
        setTitle(R.string.titleConnexion);

        SQLiteDatabase db = new ClientDbHelper(this).getReadableDatabase();

        String[] col = {"idU", "pwd"};
        String[] select = {};
        Cursor curs = db.query("User", col, "", select, null, null, null);

        if (!curs.moveToFirst()) {
            SQLiteDatabase dbw = new ClientDbHelper(this).getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("idU", 1);
            values.put("pwd",CIPHERED_PWD);
            dbw.insert("User", null, values);
            dbw.close();
            curs.moveToFirst();
        }

        String pwd = curs.getString(curs.getColumnIndexOrThrow("pwd"));

        final android.support.design.widget.FloatingActionButton btnConnexion = findViewById(R.id.buttonConnexion);
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = findViewById(R.id.editPwd);
                if (checkPassword(et.getText().toString()))
                    startActivity(new Intent(view.getContext(),Accueil.class));
                else
                    Toast.makeText(view.getContext(),"Le mot de passe est erroné, les moutons ne seront pas comptés :(",Toast.LENGTH_SHORT).show();
            }
        });


        curs.close();
        db.close();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

     private boolean checkPassword(String pwd) {

        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(pwd.getBytes(),0,pwd.length());
        String hashPwd = new BigInteger(1,m.digest()).toString(16);

        return hashPwd.equals(CIPHERED_PWD);
    }



}
