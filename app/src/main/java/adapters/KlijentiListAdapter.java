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

import models.Klijent;
import services.VolleyCallback;
import services.VolleyService;

public class KlijentiListAdapter extends ArrayAdapter<Klijent> {
    Context ctx;
    ArrayList<Klijent> klijentiList;

    public KlijentiListAdapter(Context ctx, ArrayList<Klijent> klijentiList){
        super(ctx, R.layout.klijent_list_row_item, klijentiList);
        this.ctx = ctx;
        this.klijentiList = klijentiList;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Klijent klijent = klijentiList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.klijent_list_row_item, null);

        TextView txtNaziv = (TextView) convertView.findViewById(R.id.txtNaziv);
        TextView txtPopust = (TextView) convertView.findViewById(R.id.txtPopust);
        final ImageButton imageButtonDelete = (ImageButton) convertView.findViewById(R.id.imageButtonDelete);

        txtNaziv.setText(String.valueOf(klijent.getNaziv()));
        txtPopust.setText(String.valueOf(klijent.getPopust()));

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Delete")
                        .setMessage("Jeste li sigurni da želite obrisati klijenta?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                VolleyService volley = new VolleyService(ctx);
                                volley.request("/klijenti/" + klijent.getUid(),
                                        Request.Method.DELETE,
                                        new HashMap<String, String>(),
                                        new VolleyCallback() {
                                            @Override
                                            public void onSuccess(String response) {
                                                klijentiList.remove(klijent);
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
