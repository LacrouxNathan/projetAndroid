package com.example.comptemoutons;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<Map<String,String>> listeTroupeaux;
    private final List<Bitmap> imagesTroupeaux;

    public CustomListAdapter(Activity context,
     List<Map<String,String>> listeTroupeaux, List<Bitmap> imagesTroupeaux) {

        super(context, R.layout.list_row,new String[listeTroupeaux.size()]);
        this.context = context;
        this.listeTroupeaux = listeTroupeaux;
        this.imagesTroupeaux = imagesTroupeaux;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_row, null,true);

        TextView textViewIdT = (TextView) rowView.findViewById(R.id.textViewIdT);
        TextView textViewDateT = (TextView) rowView.findViewById(R.id.textViewDateT);
        TextView textViewTaille = (TextView) rowView.findViewById(R.id.textViewTaille);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewTroupeau);

        if (position < this.imagesTroupeaux.size() && position < this.listeTroupeaux.size()) {
            textViewIdT.setText(listeTroupeaux.get(position).get("idT"));
            textViewDateT.setText(listeTroupeaux.get(position).get("dateT"));
            textViewTaille.setText(listeTroupeaux.get(position).get("taille"));
            imageView.setImageBitmap(imagesTroupeaux.get(position));
        }
        return rowView;
    }

}
