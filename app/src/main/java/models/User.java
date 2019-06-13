package models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import services.VolleyService;

public class User {
    private String uid;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String role;


    public User(){

    }

    public User(String uid, String name, String lastname, String email, String password, String role) {
        this.uid = uid;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }


/*
    public static HashMap<String, String> toHashMap(User user){
        HashMap<String, String> map = new HashMap<>();
        map.put("_id", user.getUid());
        map.put("name", user.getName());
        map.put("lastname", user.getLastname());
        map.put("email", user.getEmail());
        map.put("password", user.getPassword());
        map.put("role", user.getRole());
        return map;
    }*/

    public static User jsonToUser(String json) {
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            String id = jsonObject.getString("_id");
            String name = jsonObject.getString("name");
            String lastname = jsonObject.getString("lastname");
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            String role = jsonObject.getString("role");
            user = new User(id, name, lastname, email, password, role);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  user;
    }

    public static ArrayList<User> jsonToUserList(String json){
        ArrayList<User> users = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                users.add(jsonToUser(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }
/*
    public static void update(User user){
        VolleyService volley = new VolleyService(null);
        volley.request(

        );
    }
*/

    @Override
    public String toString() {
        return getName() + " " + getLastname();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
