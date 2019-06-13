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

import models.User;
import services.VolleyCallback;
import services.VolleyService;

public class SpinnerRadniciAdapter extends ArrayAdapter<User> {
    Context ctx;
    ArrayList<User> usersList;

    public SpinnerRadniciAdapter(Context ctx, ArrayList<User> usersList){
        super(ctx, R.layout.radnik_spinner_item, usersList);
        this.ctx = ctx;
        this.usersList = usersList;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final User user = usersList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate( R.layout.radnik_spinner_item, null);

        TextView textViewIme = (TextView) convertView.findViewById(R.id.textViewIme);
        textViewIme.setText(String.valueOf(user.getName() + " " + user.getLastname()));

        return convertView;

    }
}
