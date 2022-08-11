package com.example.flightsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Info_Display extends AppCompatActivity {

        //receiving data
        //Intent i2 = getIntent();
        String flightNumber;
        String flightDate;


       // String flightNumber = "6J25";
       // String flightDate = "2022-02-09";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_info_display);
            Bundle bundle = getIntent().getExtras();
            if(bundle!=null) {
                flightNumber = bundle.getString("flight_no");
                flightDate = bundle.getString("flight_date");
            }

            Runnable r = new Runnable() {
                @Override
                public void run() {


                    try {
                        String str = "http://api.aviationstack.com/v1/flights?access_key=3f08127e0821f4900852012b8e3f2294";
                        URL u = new URL(str);
                        BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
                        String response = "";
                        String line;
                        while((line = in.readLine() )!=null)
                        {
                            response += line;
                        }

                        Log.d("***","***RESPONSE = "+response);


                        JSONObject j = new JSONObject(response);
                        JSONArray data = j.getJSONArray("data");
                        String textt1= data.getJSONObject(0).getJSONObject("flight").getString("iata");
                        String textt= data.getJSONObject(99).getJSONObject("flight").getString("iata");
                        Log.d("*****","***RESPONSE = "+textt1);
                        Log.d("*****","***RESPONSE = "+textt);


                        String _date="";
                        String _fnumber= "";
                        Integer index = -1;


                        for(int i = 0; i < data.length(); i++){
                            _date = data.getJSONObject(i).getString("flight_date");
                            _fnumber = data.getJSONObject(i).getJSONObject("flight").getString("iata");
                            if(_date.equals(flightDate) && _fnumber.equals(flightNumber)){
                                index = i;
                                break;
                            }
                        }

                        Log.d("*****","***RESPONSE = "+index.toString());

                        String flight_status = data.getJSONObject(index).getString("flight_status");
                        //flight number
                        Log.d("*****","***RESPONSE = "+flight_status);
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




                        final String fs = flight_status;
                        final String fno = flightNumber;

                        final String dep = depart;
                        final String ddep = datedep.substring(0,10);
                        final String airdep = airportdep;
                        final String terdep = terminaldep;
                        final String tdep = timedep.substring(11,16);

                        final String arr = arriv;
                        final String darr = datearr.substring(0,10);
                        final String airarr = airportarr;
                        final String terarr = terminalarr;
                        final String tarr = timearr.substring(11,16);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("*****","***RESPONSE = Entered");
                                TextView t0=findViewById(R.id.flight_statustxt);
                                t0.setText(fs);
                                TextView t1 = findViewById(R.id.flight_numbertxt);
                                t1.setText(fno);
                                TextView t2 = findViewById(R.id.departuretxt);
                                t2.setText(dep);
                                TextView t3 = findViewById(R.id.date_deptxt);
                                t3.setText(ddep);
                                TextView t4 = findViewById(R.id.airport_deptxt);
                                t4.setText(airdep);
                                TextView t5 = findViewById(R.id.terminal_deptxt);
                                t5.setText(terdep);

                                TextView t6 = findViewById(R.id.time_deptxt);
                                t6.setText(tdep);
                                TextView t7 = findViewById(R.id.arrivaltxt);
                                t7.setText(arr);
                                TextView t8 = findViewById(R.id.date_arrtxt);
                                t8.setText(darr);
                                TextView t9 = findViewById(R.id.airport_arrtxt);
                                t9.setText(airarr);
                                TextView t10 = findViewById(R.id.terminal_arrtxt);
                                t10.setText(terarr);
                                TextView t11 = findViewById(R.id.time_arrtxt);
                                t11.setText(tarr);
                            }
                        });

                    } catch(Exception e)
                    {
                        e.printStackTrace();
                    }


                }
            };

            Thread t = new Thread(r);
            t.start();
        }

    }

