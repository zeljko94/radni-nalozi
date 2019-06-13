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

import java.util.ArrayList;
import java.util.HashMap;

import com.domain.owner.radninalozifinal.R;
import models.User;
import services.VolleyCallback;
import services.VolleyService;

public class UsersListAdapter extends ArrayAdapter<User> {
    Context ctx;
    ArrayList<User> usersList;

    public UsersListAdapter(Context ctx, ArrayList<User> usersList){
        super(ctx, R.layout.user_list_row_item, usersList);
        this.ctx = ctx;
        this.usersList = usersList;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final User user = usersList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.user_list_row_item, null);

        ImageView imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        TextView textViewIme = (TextView) convertView.findViewById(R.id.textViewIme);
        final ImageButton imageButtonDelete = (ImageButton) convertView.findViewById(R.id.imageButtonDelete);

        imageViewIcon.setImageResource(R.drawable.user_icon);
        textViewIme.setText(user.getEmail());

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ctx)
                        .setTitle("Delete")
                        .setMessage("Jeste li sigurni da želite obrisati korisnika?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                VolleyService volley = new VolleyService(ctx);
                                volley.request("/users/" + user.getUid(),
                                        Request.Method.DELETE,
                                        new HashMap<String, String>(),
                                        new VolleyCallback() {
                                            @Override
                                            public void onSuccess(String response) {
                                                usersList.remove(user);
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
