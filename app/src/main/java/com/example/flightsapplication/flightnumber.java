package com.example.flightsapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link flightnumber#newInstance} factory method to
 * create an instance of this fragment.
 */
public class flightnumber extends Fragment {

   public Button search;
   public EditText flight_number,Date;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public flightnumber() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment flightnumber.
     */
    // TODO: Rename and change types and number of parameters
    public static flightnumber newInstance(String param1, String param2) {
        flightnumber fragment = new flightnumber();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View flightview =  inflater.inflate(R.layout.fragment_flightnumber, container, false);

        search =flightview.findViewById(R.id.search_buttonf);
        flight_number=flightview.findViewById(R.id.flight_number);
        Date=flightview.findViewById(R.id.date);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), Info_Display.class);
                Bundle extras = new Bundle();
                extras.putString("flight_no",flight_number.getText().toString());
                extras.putString("flight_date",Date.getText().toString());
                // i.putExtra(“theValue", 997.5);
                in.putExtras(extras);
                startActivity(in);
            }
        });
        // Inflate the layout for this fragment
        return flightview;
    }
}