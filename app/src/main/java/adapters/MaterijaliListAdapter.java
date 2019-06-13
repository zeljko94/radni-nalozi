package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.domain.owner.radninalozifinal.R;

import java.util.ArrayList;
import java.util.HashMap;

import models.Materijal;
import services.VolleyCallback;
import services.VolleyService;

public class MaterijaliListAdapter extends ArrayAdapter<Materijal> {
    Context ctx;
    ArrayList<Materijal> materijaliList;

    public MaterijaliListAdapter(Context ctx, ArrayList<Materijal> materijaliList){
        super(ctx, R.layout.materijal_list_row_item, materijaliList);
        this.ctx = ctx;
        this.materijaliList = materijaliList;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Materijal materijal = materijaliList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.materijal_list_row_item, null);

        TextView txtNaziv = (TextView) convertView.findViewById(R.id.txtNaziv);
        TextView txtCijena = (TextView) convertView.findViewById(R.id.txtCijena);
        final ImageButton imageButtonDelete = (ImageButton) convertView.findViewById(R.id.imageButtonDelete);

        txtNaziv.setText(String.valueOf(materijal.getNaziv()));
        txtCijena.setText(String.valueOf(materijal.getCijena()));

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Delete")
                        .setMessage("Jeste li sigurni da želite obrisati materijal?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                VolleyService volley = new VolleyService(ctx);
                                volley.request("/materijali/" + materijal.getUid(),
                                        Request.Method.DELETE,
                                        new HashMap<String, String>(),
                                        new VolleyCallback() {
                                            @Override
                                            public void onSuccess(String response) {
                                                materijaliList.remove(materijal);
                                                notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError(VolleyError response) {

                                            }
                                        }
                                );
                            }})
                        .setNegativeButton("NE", null).show();
            }

        });
        return convertView;

    }
}
