package services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.domain.owner.radninalozifinal.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import models.User;

public class AuthService {
    private Context ctx;
    private VolleyService volley;

    public AuthService(Context ctx){
        this.ctx = ctx;
        this.volley = new VolleyService(this.ctx);
    }

    public void login(String email, String password, VolleyCallback callback){
        HashMap<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        volley.request("/users/login", Request.Method.POST, data, callback);
    }

    public void signup(User user, VolleyCallback callback){
        HashMap<String, String> data = ConvertService.toHashMap(user);
        volley.request("/users/signup", Request.Method.POST, data, callback);
    }
}
