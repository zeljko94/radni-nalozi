package services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dialogs.LoadingDialog;
import models.User;

public class VolleyService {
    private String API_URL = "https://radni-nalozi-final.herokuapp.com";
    private RequestQueue queue;
    private Context ctx;
    LoadingDialog dialog;

    public VolleyService(Context ctx){
        this.ctx = ctx;
        this.queue = Volley.newRequestQueue(ctx);
        this.dialog = new LoadingDialog(this.ctx);
    }

    public void request(String url, int method, final Object data, final VolleyCallback callback){
        this.dialog.show();
        StringRequest req = new StringRequest(method, API_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.hide();
                        callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
                callback.onError(error);
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                String your_string_json = new Gson().toJson(data);
                return your_string_json.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(req);
    }


    public void request(String url, int method, final String json, final VolleyCallback callback){
        this.dialog.show();
        StringRequest req = new StringRequest(method, API_URL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.hide();
                        callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.hide();
                callback.onError(error);
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return json.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(req);
    }
}
