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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.domain.owner.radninalozifinal.R;

import java.util.ArrayList;
import java.util.HashMap;

import models.Obavijest;
import models.User;
import services.VolleyCallback;
import services.VolleyService;

public class ObavijestiListAdapter extends ArrayAdapter<Obavijest> {
    Context ctx;
    ArrayList<Obavijest> obavijestiList;

    public ObavijestiListAdapter(Context ctx, ArrayList<Obavijest> obavijestiList){
        super(ctx, R.layout.radnik_obavijest_list_row_item, obavijestiList);
        this.ctx = ctx;
        this.obavijestiList = obavijestiList;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Obavijest obavijest = obavijestiList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.radnik_obavijest_list_row_item, null);

        ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        TextView txtNaslov = (TextView) convertView.findViewById(R.id.txtNaslov);
        TextView txtBody = (TextView) convertView.findViewById(R.id.txtBody);

        if(obavijest.isRead()){
            imageViewIcon.setImageResource(R.drawable.messageopen);
        }
        else{
            imageViewIcon.setImageResource(R.drawable.message);
        }
        txtNaslov.setText(String.valueOf(obavijest.getNaslov()));
        txtBody.setText(String.valueOf(obavijest.getBody()));

        return convertView;

    }


}
