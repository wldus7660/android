package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.myapplication.MainActivity.store;

public class third extends BaseActivity {
    private static Context context;
    private Button button1,button2;
    private TextView Number,Name;
    public static Context getContext() {
        return context;
    }
    int a;
    Intent intent = new Intent(this.getIntent());
    final String s = intent.getStringExtra("store");
    int t =intent.getIntExtra("kiki",0);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.third);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        Name = (TextView) findViewById(R.id.textViewName);
        final EditText edit = (EditText) findViewById(R.id.edittext);



        final String url = "http://socrip3.kaist.ac.kr:5880/stores";
        final String urll = "http://socrip3.kaist.ac.kr:5880/customers";
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JsonArrayRequest jjjArrayRequest = new JsonArrayRequest(Request.Method.GET, urll, null, new Response.Listener<JSONArray>() {
                            JSONObject jjj = new JSONObject();
                            @Override
                            public void onResponse(JSONArray response) {
                                // Do something with response
                                //mTextView.setText(response.toString());
                                // Process the JSON

                                try {
                                    JSONArray contact = response;
                                    for (int i = 0; i < contact.length(); i++) {
                                        JSONObject jjObject = contact.getJSONObject(i);
                                        if (t==jjObject.getInt("custmoer_number")){
                                            jjj = jjObject;
                                            a= jjj.getInt("waiting_number");
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Do something when error occurred
                            }
                        }


                );
                Volley.newRequestQueue(getContext()).add(jjjArrayRequest);

                Intent intent = new Intent(third.this, reservation.class);
                intent.putExtra("www", a);
                startActivity(intent);
            }
        });


        ///버튼 2 누르면 생기는일
//                button2.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    String urlll = "http://socrip3.kaist.ac.kr:5880/customers";
//
//                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.DELETE, urlll, null
//                            new Response.Listener<String>()
//                            {
//                                @Override
//                                public void onResponse(String response) {
//                                    // response
//                                }
//                            },
//                            new Response.ErrorListener()
//                            {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    // error.
//
//                                }
//                            }
//                    );
//                    Volley.newRequestQueue(third.getContext()).add(jsonArrayRequest);
//
//
//                    // Access the RequestQueue through your singleton class.
//                }
//
//                });



    }
}
