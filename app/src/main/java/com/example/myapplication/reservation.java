package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class reservation extends BaseActivity {
    private TextView wow, mTextView;
    Intent intent = new Intent(this.getIntent());
    final String s = intent.getStringExtra("store");
    int t =intent.getIntExtra("www",0);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);
        wow = (TextView) findViewById(R.id.hi);
         mTextView= (TextView) findViewById(R.id.haha);
         mTextView.setText(t);
    }
}
