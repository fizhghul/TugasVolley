package com.hafiizh.appnews;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafiizh.appnews.adapter.AdapterNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String URL_DATA = "http://31.220.53.18/hafizhnaufal/ghulam/list.php";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    SwipeRefreshLayout swipe;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.newsapp);
        getSupportActionBar().setSubtitle("Aplikasi berita");

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (isOnline()) {
            loadRecyclerViewData();
        } else {
            checkConnection();
        }
        swipe = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipe.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                loadRecyclerViewData();
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void turnOn() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.newsapp);
        alert.setTitle("Info !!!");
        alert.setMessage("Your device is Not Connected to Internet !!! , Please Turn On your data to download !");
        alert.setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.android.settings",
                        "com.android.settings.Settings$DataUsageSummaryActivity"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    public void checkConnection() {
        if (isOnline()) {

        } else {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).setAction("Check", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    turnOn();
                }
            });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    private void loadRecyclerViewData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                List<HashMap<String, String>> listItems;
                listItems = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray array = jsonObject.getJSONArray("kategori");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", o.getString("id"));
                        map.put("judul", o.getString("judul"));
                        map.put("konten", o.getString("konten"));
                        map.put("gambar", o.getString("gambar"));
                        map.put("gambar2", o.getString("gambar2"));
                        map.put("gambar3", o.getString("gambar3"));
                        listItems.add(map);
                        swipe.setRefreshing(false);
                    }
                    adapter = new AdapterNews(listItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkConnection();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sync) {
            if (isOnline()) {
                swipe.setRefreshing(true);
                loadRecyclerViewData();
            } else {
                checkConnection();
            }
        } else if (id == R.id.about) {
            startActivity(new Intent(getApplicationContext(), About.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
