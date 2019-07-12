package com.flex.market;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class MarketAPI {
    static String token;

    static void GetToken(final Context context, String email, String password){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL ="http://192.168.43.187:8080/api/v1/auth/login/";

        try {
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Helper.showProgress(false, LoginFragment.progressBar);
                        token = response.getString("token");
                        Singleton.getInstance().getTransaction().replace(
                                R.id.fragment_container,
                                new ProfileFragment()
                        ).commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Helper.showProgress(false, LoginFragment.progressBar);
                    Toast.makeText(context, "Error:  " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }){
                // set headers
                @Override
                public Map <String, String> getHeaders(){
                    Map<String, String> params = new HashMap<>();
                    //params.put("Authorization: Bearer", TOKEN);
                    return params;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    5,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static void SignUp(final Context context, String firstName, String lastName, String email, String password){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL ="http://192.168.43.187:8080/api/v1/sign_up/";

        try {
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("first_name", firstName);
            jsonBody.put("last_name", lastName);
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Helper.showProgress(false, SignUpActivity.progressBar);
                        Toast.makeText(context, "UserID: " + response.getString("userID"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Helper.showProgress(false, SignUpActivity.progressBar);
                    Toast.makeText(context, "Error:  " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }){
                // set headers
                @Override
                public Map <String, String> getHeaders(){
                    Map<String, String> params = new HashMap<>();
                    //params.put("Authorization: Bearer", TOKEN);
                    return params;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    5,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
