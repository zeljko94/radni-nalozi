package dialogs;

import android.app.ProgressDialog;
import android.content.Context;

import com.domain.owner.radninalozifinal.R;

public class LoadingDialog {
    public Context ctx;
    public ProgressDialog dialog;
    public String title;
    public String msg;
    public boolean cancelable;

    public LoadingDialog(Context ctx){
        this.msg = "Loading....";
        this.cancelable = false;
        this.title = "";
        this.ctx = ctx;

        this.dialog = new ProgressDialog(this.ctx, R.style.MyAlertDialogStyle);
        this.dialog.setCanceledOnTouchOutside(this.cancelable);
        this.dialog.setMessage(this.msg);
        this.dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.dialog.setIndeterminate(true);
    }

    public void show(){
        if(this.dialog != null){
            this.dialog.show();
        }
    }

    public void hide(){
        if(this.dialog != null && dialog.isShowing()){
            this.dialog.dismiss();
            this.dialog = null;
        }
    }

}
