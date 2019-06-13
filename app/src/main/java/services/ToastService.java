package services;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.domain.owner.radninalozifinal.MainActivity;

public class ToastService {

    public static void center(String msg, Context ctx){
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
