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

    // TODO: Rename and change types of parameters
    private String unique_id;
    private TextView store_unique_id;


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
    public static StoreDetailsFragment newInstance(String unique_id) {
        StoreDetailsFragment fragment = new StoreDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, unique_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unique_id = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store_details, container, false);
        store_unique_id = root.findViewById(R.id.etStoreUniqueID);
        store_unique_id.setText(unique_id);
        return root;
    }
}
