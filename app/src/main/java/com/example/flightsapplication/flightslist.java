package com.example.flightsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

public class flightslist extends AppCompatActivity {

    String _source;
    String _des;
    LinearLayout l;
    ConstraintLayout c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flightslist);



        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            _source = bundle.getString("departure");
            _des = bundle.getString("arrival");
            Log.d("******","***RESPONSE = "+_source);
            Log.d("******","***RESPONSE = "+_des);
        }

        TextView header = findViewById(R.id.heading);
        String txt="From "+_source+" to "+_des;
        header.setText(txt);

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

                    String _src= "";
                    String _desti="";
                    Vector<Integer> indexes = new Vector<Integer>();
                    for(int i = 0; i < 100; i++){
                        _src= data.getJSONObject(i).getJSONObject("departure").getString("airport");
                        _desti = data.getJSONObject(i).getJSONObject("arrival").getString("airport");
                        if(_src.equals(_source) && _desti.equals(_des)){
                            indexes.add(i);
                            Log.d("*****","***RESPONSE = "+i);
                        }
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Integer x = indexes.size();
                            Log.d("*****","***RESPONSE = "+x.toString());
                            l = (LinearLayout) findViewById(R.id.l1);

                            for(int i = 0; i < indexes.size(); i++){
                                Log.d("***---","***RESPONSE = "+indexes.get(i).toString());
                                String display = null;
                                try {
                                    display = data.getJSONObject(indexes.get(i)).getJSONObject("departure").getString("scheduled");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String dis = "Date: "+display.substring(0,10)+"\t"+"Time: "+display.substring(11,16);


                                TextView tv = new TextView(getApplicationContext());
                                tv.setText(dis.toString());

                                tv.setTextSize(20);
                                tv.setPadding(10,10,10,10);
                                tv.setTextColor(Color.BLACK);

                                l.addView(tv);
                                Log.d("**XXX*","***RESPONSE = "+dis);

                                String num = "";
                                try {
                                    num = data.getJSONObject(indexes.get(i)).getJSONObject("flight").getString("iata");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String dat = display.substring(0,10);

                                final String numb = num;
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent in=new Intent(getApplicationContext(), Info_Display.class);
                                        in.putExtra("flight_no", numb);
                                        in.putExtra("flight_date",dat);
                                        startActivity(in);
                                    }
                                });

                            }

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