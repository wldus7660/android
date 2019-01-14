package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.IDNA;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

public class MainActivity extends BaseActivity {
    //view Objects
    private Button buttonScan;

    //qr code scanner object
    private IntentIntegrator qrScan;
    public static final String store = "hi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //View Objects
        buttonScan = (Button) findViewById(R.id.buttonScan);


        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //button onClick
        buttonScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //scan option
                qrScan.setPrompt("Scanning...");
                //qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, requestCode);
        }
        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(MainActivity.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(MainActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try {
                    String k = result.getContents();

                    Intent intent = new Intent(MainActivity.this, Information.class);
                    intent.putExtra(store,k);

                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
//                    textViewResult.setText(result.getContents());
                    Intent intent = new Intent(MainActivity.this, Information.class);

                    startActivity(intent);
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
            Intent intent = new Intent(MainActivity.this, Information.class);
            startActivity(intent);
        }
    }
}