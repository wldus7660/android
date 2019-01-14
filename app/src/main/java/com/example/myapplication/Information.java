package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.example.myapplication.MainActivity.store;
import static java.security.AccessController.getContext;

public class Information extends BaseActivity {
    private static Context context;
    private Button button;
    private TextView Number,Name,mTextView;
    public static String numberofpeople="a";
    int aa = 1000000;
    int j=0;
    int number=0;
    String ID;
    int kiki=0;

    //    private int k=1;
    public static Context getContext() {
        return context;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = getApplicationContext();
        setContentView(R.layout.information);
        button = (Button) findViewById(R.id.reservation);
        Name = (TextView) findViewById(R.id.textViewName);
        final EditText edit = (EditText) findViewById(R.id.edittext);
        mTextView= (TextView) findViewById(R.id.tete);
        Intent intent = new Intent(this.getIntent());
        final String s = intent.getStringExtra(store);
        final String url = "http://socrip3.kaist.ac.kr:5880/stores";
        final String urll = "http://socrip3.kaist.ac.kr:5880/customers";
        Name.setText(s);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String numberofpeople = edit.getText().toString();       //this will get a string
                number = Integer.parseInt(numberofpeople);
///////////////////가게에서 지금 손님수랑, 웨이팅넘버수 받아오기////////////////
                JsonArrayRequest jjjArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONArray contact = response;
                                    for (int i = 0; i < contact.length(); i++) {
                                        JSONObject jjObject = contact.getJSONObject(i);
                                        if (jjObject.getString("store_name").equals(s)) {
                                            aa=jjObject.getInt("waiting_number");
                                            ID=jjObject.getString("_id");
                                            kiki=jjObject.getInt("customer_number");
                                            JSONObject hiObject = new JSONObject();
                                            try {
                                                hiObject.put("_id",ID);
                                                hiObject.put("store_name", s);
                                                hiObject.put("waiting_number",aa+1);
                                                hiObject.put("token", "hihi");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
/////////////////////store 에 post하는 함수임///////////////////////
                                            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url+ "/"+ID, hiObject, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    //TODO: handle success
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    error.printStackTrace();
                                                    //TODO: handle failure
                                                }
                                            });
                                            Volley.newRequestQueue(Information.getContext()).add(jsonRequest);}


                                    JSONObject jObject = new JSONObject();
                                    try {
                                        jObject.put("store_name", s);
                                        jObject.put("waiting_number",aa+1);
                                        jObject.put("people_count",number);
                                        jObject.put("token", "hihihi");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JsonObjectRequest jjsonRequest = new JsonObjectRequest(Request.Method.POST, urll, jObject, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            //TODO: handle success
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                            //TODO: handle failure
                                        }
                                    });
                                    Volley.newRequestQueue(getContext()).add(jjsonRequest);}
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // Do something with response
                                //mTextView.setText(response.toString());
                                // Process the JSON
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do something when error occurred
                                error.printStackTrace();
                            }
                        }
        );
                Volley.newRequestQueue(getContext()).add(jjjArrayRequest);
              //////////////////////////////  .////////////////////////////////////////////////////////

                /////////////////////////////////////

             Intent intent2 = new Intent(getContext(), third.class);
               intent2.putExtra("wow",aa);
               intent2.putExtra("kiki",kiki);
                startActivity(intent2);
            }
        });
    }
}