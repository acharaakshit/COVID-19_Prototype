package com.example.htc20;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";


    // TODO: Rename and change types of parameters
    private String unique_id;
    private String name;
    private String location;
    private TextView store_unique_id;
    private TextView store_name;
    private TextView store_location;

    public StoreDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param unique_id Parameter 1.
     * @return A new instance of fragment StoreDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreDetailsFragment newInstance(String unique_id, String shop_location, String shop_name) {
        StoreDetailsFragment fragment = new StoreDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, unique_id);
        args.putString(ARG_PARAM2, shop_location);
        args.putString(ARG_PARAM3, shop_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unique_id = getArguments().getString(ARG_PARAM1);
            name = getArguments().getString(ARG_PARAM3);
            location = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store_details, container, false);
        store_unique_id = root.findViewById(R.id.etStoreUniqueID);
        store_location = root.findViewById(R.id.etStoreLocation);
        store_name = root.findViewById(R.id.etStoreName);


        store_unique_id.setText("Store unique ID: " + unique_id);
        store_location.setText("Store Location: " + location);
        store_name.setText("Store Name: " + name);
        return root;
    }
}
