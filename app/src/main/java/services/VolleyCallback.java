package services;

import com.android.volley.VolleyError;

public interface VolleyCallback{
    void onSuccess(String response);
    void onError(VolleyError response);
}
