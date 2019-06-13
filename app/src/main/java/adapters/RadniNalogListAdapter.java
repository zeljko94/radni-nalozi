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
import models.RadniNalogDialogData;
import services.VolleyCallback;
import services.VolleyService;

public class RadniNalogListAdapter extends ArrayAdapter<RadniNalogDialogData> {
    Context ctx;
    ArrayList<RadniNalogDialogData> radniNaloziList;

    public RadniNalogListAdapter(Context ctx, ArrayList<RadniNalogDialogData> radniNaloziList){
        super(ctx, R.layout.radni_nalog_list_row_item, radniNaloziList);
        this.ctx = ctx;
        this.radniNaloziList = radniNaloziList;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final RadniNalogDialogData rn = radniNaloziList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.radni_nalog_list_row_item, null);

        TextView txtNazivProjekta = (TextView) convertView.findViewById(R.id.txtNazivProjekta);
        TextView txtNazivKlijenta = (TextView) convertView.findViewById(R.id.txtNazivKlijenta);
        final ImageButton imageButtonDelete = (ImageButton) convertView.findViewById(R.id.imageButtonDelete);

        txtNazivProjekta.setText(String.valueOf(rn.getNaziv()));
        txtNazivKlijenta.setText(String.valueOf(rn.getKlijent().getNaziv()));

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Delete")
                        .setMessage("Jeste li sigurni da želite obrisati radni nalog?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                VolleyService volley = new VolleyService(ctx);
                                volley.request("/radni-nalozi/" + rn.getUid(),
                                        Request.Method.DELETE,
                                        new HashMap<String, String>(),
                                        new VolleyCallback() {
                                            @Override
                                            public void onSuccess(String response) {
                                                radniNaloziList.remove(rn);
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
