package com.example.comptemoutons;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        // Bouton de retour sur la barre de menu
        assert getSupportActionBar() != null; // éviter le nullpointerexception
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setLink(R.id.imageViewKebab,"https://github.com/Royalix/projetAndroid");
        setLink(R.id.imageViewLacroux,"https://fr.linkedin.com/in/nathan-lacroux");
        setLink(R.id.imageViewBedex,"https://fr.linkedin.com/in/antoine-b%C3%A9dex-a09451170");

    }

    @Override
    public boolean onSupportNavigateUp(){
        // retour à l'activité précédente
        finish();
        return super.onSupportNavigateUp();
    }

    private void setLink(int id, final String url) {
        ImageView image = (ImageView) findViewById(id);
        image.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
