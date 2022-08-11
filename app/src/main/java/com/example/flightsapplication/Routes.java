package com.example.flightsapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Routes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Routes extends Fragment {

    public Button search;
    //public EditText source,destination;

    Spinner SpinnerSource;
    Spinner SpinnerDest;

    String source= "";
    String destination = "";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Routes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Routes.
     */
    // TODO: Rename and change types and number of parameters
    public static Routes newInstance(String param1, String param2) {
        Routes fragment = new Routes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View flightview =  inflater.inflate(R.layout.fragment_routes, container, false);

        search =flightview.findViewById(R.id.search_buttonr);
        SpinnerSource=flightview.findViewById(R.id.spinnersource);
        SpinnerDest=flightview.findViewById(R.id.spinnerdestination);


        ArrayList<String> arrayList = new ArrayList<>();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    URL u;
                    u = new URL("http://api.aviationstack.com/v1/flights?access_key=3f08127e0821f4900852012b8e3f2294");
                    BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
                    String response = "";
                    String line;
                    while ((line = in.readLine()) != null) {
                        response += line;
                    }
                    Log.d("*", "***RESPONSE=" + response);
                    JSONObject j = new JSONObject(response);
                    JSONArray data=j.getJSONArray("data");

                    Log.d("*", "***checkk="+ data.length());

                    int exists=0;
                    for (int i = 0; i < data.length(); i++) {
                        String str = data.getJSONObject(i).getJSONObject("departure").getString("airport");
                        exists=0;
                        for(int x=0; x< arrayList.size();x++)
                        {
                            if(str.equals(arrayList.get(x).toString()))
                            {
                                exists=1;
                                Log.d("*", "***existss");
                            }
                        }
                        if(exists==0 && !str.equals("null")) {
                            arrayList.add(str);
                            Log.d("*", "***ADD: "+str);
                        }
                    }
                    exists=0;
                    for (int i = 0; i < data.length(); i++) {
                        String str = data.getJSONObject(i).getJSONObject("arrival").getString("airport");
                        exists=0;
                        for(int x=0; x< arrayList.size();x++)
                        {
                            if(str.equals(arrayList.get(x).toString()))
                            {
                                exists=1;
                                Log.d("*", "***existss");
                            }
                        }
                        if(exists==0 && !str.equals("null")) {
                            arrayList.add(str);
                            Log.d("*", "***ADD: "+str);
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),                         android.R.layout.simple_spinner_item, arrayList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            SpinnerSource.setAdapter(arrayAdapter);
                            Log.d("*", "***HEREEE=");

                            SpinnerSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    source = parent.getItemAtPosition(position).toString();
                                    //Toast.makeText(parent.getContext(), "Selected: " + source,Toast.LENGTH_LONG).show();
                                    Log.d("*", "***HEREEE" + source);
                                }
                                @Override
                                public void onNothingSelected(AdapterView <?> parent) {
                                }
                            });


                            //
                            SpinnerDest.setAdapter(arrayAdapter);
                            Log.d("*", "***HEREEE=");

                            SpinnerDest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    destination = parent.getItemAtPosition(position).toString();
                                    //Toast.makeText(parent.getContext(), "Selected: " + destination,Toast.LENGTH_LONG).show();
                                    Log.d("*", "***HEREEE" + destination);
                                }
                                @Override
                                public void onNothingSelected(AdapterView <?> parent) {
                                }
                            });



                        }
                    });

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(r);
        t.start();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), flightslist.class);
                Bundle extras = new Bundle();
                extras.putString("departure",source);
                extras.putString("arrival",destination);
                // i.putExtra(“theValue", 997.5);
                i.putExtras(extras);
                startActivity(i);
            }
        });
        // Inflate the layout for this fragment
        return flightview;
    }

}