package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class clsUser {
    private int id;
    private String firstname,lastname, username, password;


    public clsUser(int id,String firstname, String lastname, String username, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public interface ExistCallback {
        void onExistCheck(boolean exists);
    }
    public static void isExist(Context context, String username, ExistCallback callback) {
        String url = "http://10.0.2.2:5000/isExist/" + username;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean exists = response.getBoolean("exists");
                            callback.onExistCheck(exists);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onExistCheck(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                        callback.onExistCheck(false);  // Handle error, consider user doesn't exist
                    }
                }
        );

        queue.add(request);
    }




    public interface ExistCallbackForLogin {
        void onExistCheck(boolean exists, clsUser user);
    }

    public static void checkByUsernameAndPassword(Context context, String username, String password, ExistCallbackForLogin callback) {
        String url = "http://10.0.2.2:5000/checkuser";

        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("username", username);
            jsonParams.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.isNull("firstname") && !response.isNull("lastname") &&
                                    !response.isNull("username") && !response.isNull("password")) {

                                int Id = response.getInt("id");
                                String FirstName = response.getString("firstname");
                                String LastName = response.getString("lastname");
                                String Username = response.getString("username");
                                String Password = response.getString("password");


                                clsUser fetchedUser = new clsUser(Id,FirstName, LastName, Username, Password);

                                // Update the callback to pass both boolean and User
                                callback.onExistCheck(true, fetchedUser);
                            } else {
                                callback.onExistCheck(false, null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onExistCheck(false, null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                        callback.onExistCheck(false, null);
                    }
                }
        );

        queue.add(request);
    }


    public static void addUser(Context context, String firstName, String lastName, String username, String password) {
        String url = "http://10.0.2.2:5000/adduser";

        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("firstName", firstName);
            jsonParams.put("lastName", lastName);
            jsonParams.put("username", username);
            jsonParams.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String str = "";
                        try {
                            str = response.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }
                }
        );

        queue.add(request);
    }


}
