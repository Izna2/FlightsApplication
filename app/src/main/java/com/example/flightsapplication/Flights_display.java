package com.example.flightsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Array;
import java.util.Vector;

public class Flights_display extends AppCompatActivity {
    //receiving data
    Intent i2 = getIntent();

    String source;
    String destination;
    LinearLayout l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights_display);

        l = findViewById(R.id.flightslist);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Log.d("*****hello", "hellohello");
            source = bundle.getString("Source");
            destination = bundle.getString("Destination");
        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    String str = "http://api.aviationstack.com/v1/flights?access_key=1e0ba203193fbb98261cb4af9c21cdb3";
                    URL u = new URL(str);
                    BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
                    String response = "";
                    String line;
                    while((line = in.readLine() )!=null)
                    {
                        response += line;
                    }
                    Log.d("*","***RESPONSE = "+response);

                    JSONObject j = new JSONObject(response);
                    JSONArray data = j.getJSONArray("data");
                    String _src= "";
                    String _des="";
                    Integer index = 0;
                    Vector<Integer> indexes = new Vector<Integer>();
                    for(int i = 0; i < 100; i++){
                        _src= data.getJSONObject(i).getJSONObject("departure").getString("airport");
                        _des = data.getJSONObject(i).getJSONObject("arrival").getString("airport");
                        if(_src.equals(source) && _des.equals(destination)){
                            indexes.add(i);
                        }
                    }



                    for(int i = 0; i < indexes.size(); i++){
                        RelativeLayout rl = new RelativeLayout(getApplicationContext());
                        l.addView(rl);
                    }


                    String flight_status = data.getJSONObject(index).getString("flight_status");
                    //flight number


                    //=====departure details
                    String depart = data.getJSONObject(index).getJSONObject("departure").getString("iata");
                    //edit to display date only
                    String datedep = data.getJSONObject(index).getJSONObject("departure").getString("scheduled");
                    String airportdep =data.getJSONObject(index).getJSONObject("departure").getString("airport");
                    String terminaldep = data.getJSONObject(index).getJSONObject("departure").getString("terminal");
                    String timedep = data.getJSONObject(index).getJSONObject("departure").getString("scheduled");


                    //=====arrival details
                    String arriv = data.getJSONObject(index).getJSONObject("arrival").getString("iata");
                    //edit to display date only
                    String datearr = data.getJSONObject(index).getJSONObject("arrival").getString("scheduled");
                    String airportarr =data.getJSONObject(index).getJSONObject("arrival").getString("airport");
                    String terminalarr = data.getJSONObject(index).getJSONObject("arrival").getString("terminal");
                    String timearr = data.getJSONObject(index).getJSONObject("arrival").getString("scheduled");

                    String flightNumber = data.getJSONObject(index).getJSONObject("flight").getString("iata");




                    final String fs=flight_status;
                    final String fno = flightNumber;

                    final String dep = depart;
                    final String ddep = datedep+" Airport";
                    final String airdep = airportdep;
                    final String terdep = terminaldep;
                    final String tdep = timedep;

                    final String arr = arriv;
                    final String darr = datearr;
                    final String airarr = airportarr+" Airport";
                    final String terarr = terminalarr;
                    final String tarr = timearr;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView t0=findViewById(R.id.flight_statustxt);
                            t0.setText(fs);
                            TextView t1 = findViewById(R.id.flight_numbertxt);
                            t1.setText(fno.toString());
                            TextView t2 = findViewById(R.id.departuretxt);
                            t2.setText(dep.toString());
                            TextView t3 = findViewById(R.id.date_deptxt);
                            t3.setText(ddep.toString());
                            TextView t4 = findViewById(R.id.airport_deptxt);
                            t4.setText(airdep.toString());
                            TextView t5 = findViewById(R.id.terminal_deptxt);
                            t5.setText(terdep.toString());

                            TextView t6 = findViewById(R.id.time_deptxt);
                            t6.setText(tdep.toString());
                            TextView t7 = findViewById(R.id.arrivaltxt);
                            t7.setText(arr.toString());
                            TextView t8 = findViewById(R.id.date_arrtxt);
                            t8.setText(darr.toString());
                            TextView t9 = findViewById(R.id.airport_arrtxt);
                            t9.setText(airarr.toString());
                            TextView t10 = findViewById(R.id.terminal_arrtxt);
                            t10.setText(terarr.toString());
                            TextView t11 = findViewById(R.id.time_arrtxt);
                            t11.setText(tarr.toString());
                        }
                    });

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();

        finish();
    }


}