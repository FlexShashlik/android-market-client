package com.flex.market;

import android.content.Context;
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
    //public void getResponse(int method, String url, JSONObject jsonValue, final VolleyCallback callback) {
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
                        ProfileFragment.showProgress(false);
                        response.getString("token");
                        Toast.makeText(context, response.getString("token"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ProfileFragment.showProgress(false);
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
