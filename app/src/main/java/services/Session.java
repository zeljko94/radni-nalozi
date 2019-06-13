package services;

import android.content.Context;
import android.content.Intent;

import com.domain.owner.radninalozifinal.MainActivity;

import models.User;

public class Session {
    public static User loggedUser;

    public static void setLoggedUser(User user){ loggedUser = user; }
    public static User getLoggedUser(){ return loggedUser; }

    public static void destroy(Context ctx){
        loggedUser = null;
        Intent intent = new Intent(ctx, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

}
