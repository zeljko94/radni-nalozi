package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.domain.owner.radninalozifinal.R;

import java.util.ArrayList;

import models.Materijal;
import models.MaterijalStavka;

public class MaterijalStavkaListAdapter extends ArrayAdapter<MaterijalStavka> {
    Context ctx;
    ArrayList<MaterijalStavka> stavkeList;

    public MaterijalStavkaListAdapter(Context ctx, ArrayList<MaterijalStavka> stavkeList){
        super(ctx, R.layout.materijal_stavka_list_row_item, stavkeList);
        this.ctx = ctx;
        this.stavkeList = stavkeList;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final MaterijalStavka stavka = stavkeList.get(position);
        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.materijal_stavka_list_row_item, null);

        TextView txtKolicina = (TextView) convertView.findViewById(R.id.txtKolicina);
        TextView txtNaziv = (TextView) convertView.findViewById(R.id.txtNaziv);
        final ImageButton imageButtonDelete = (ImageButton) convertView.findViewById(R.id.imageButtonDelete);

        txtNaziv.setText(String.valueOf(stavka.getMaterijal().getNaziv()));
        txtKolicina.setText(String.valueOf(stavka.getKolicina()));

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(stavka);
                notifyDataSetChanged();
            }

        });
        return convertView;

    }
}
