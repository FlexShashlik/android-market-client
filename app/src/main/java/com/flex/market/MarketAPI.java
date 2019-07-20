package com.flex.market;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class MarketAPI {
    static String token;
    private static String api_server = "http://192.168.1.162/api/v1/";
    static int selectedSubCatalog = -1;

// TODO: Helper class or method for requests

    static void GetCatalog(final Context context) {
        // Reset previous values
        ExpandableListAdapter.catalog.clear();
        ExpandableListAdapter.subCatalog.clear();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = api_server + "catalog/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        final JSONObject catalog = response.getJSONObject(i);
                        ExpandableListAdapter.catalog.add(new Catalog() {
                            {
                                ID = catalog.getInt("id");
                                Name = catalog.getString("name");
                            }
                        });
                    }

                    CatalogFragment.SetCatalog(ExpandableListAdapter.catalog);
                    GetSubCatalog(context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error:  " + error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonArrayRequest);
    }

    private static void GetSubCatalog(final Context context) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = api_server + "sub_catalog/";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        final JSONObject catalog = response.getJSONObject(i);
                        ExpandableListAdapter.subCatalog.add(new SubCatalog() {
                            {
                                ID = catalog.getInt("id");
                                Name = catalog.getString("name");
                                CatalogID = catalog.getInt("catalog_id");
                            }
                        });
                    }

                    CatalogFragment.SetSubCatalog(ExpandableListAdapter.catalog, ExpandableListAdapter.subCatalog);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error:  " + error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonArrayRequest);
    }

    static void GetToken(final Context context, String email, String password) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = api_server + "auth/login/";

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
                        Singleton.getInstance()
                                .getManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_left, R.anim.exit_left, R.anim.exit_left, R.anim.enter_left)
                                .replace(
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
                public Map <String, String> getHeaders() {
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

    static void SignUp(final Context context, String firstName, String lastName, String email, String password) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String URL = api_server + "sign_up/";

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
