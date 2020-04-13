package com.example.htc20;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PendingOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingOrdersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String unique_id;
    private String mParam2;

    private ListView listview;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    private ArrayList<Order> order_list;

    FirebaseFirestore db;

    private Button refresh;
    private Spinner spinner;

    public PendingOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param unique_id Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PendingOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PendingOrdersFragment newInstance(String unique_id, String param2) {
        PendingOrdersFragment fragment = new PendingOrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, unique_id);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            unique_id = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void addOrderToList(String orders, Integer order_size, String customer_details, String id){
        Order new_order = new Order(order_size, orders, customer_details, id);
        order_list.add(new_order);
    }

    private void displayOrders(){
        for(Order new_order: order_list){
            list.add(new_order.toString());
        }
    }

    private void populateEntries(){
        order_list.clear();
        list.clear();
        CollectionReference addref = db.collection("orders");
        Query query = addref.whereEqualTo("unique_id", unique_id);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            public void onComplete(@NonNull Task<QuerySnapshot> task){
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        String orders = String.valueOf(document.getData().get("order_placed"));
                        Integer order_size = Integer.parseInt(String.valueOf(document.getData().get("order_size")));
                        String customer_details = String.valueOf(document.getData().get("customer_details"));
                        String id = document.getId();
                        addOrderToList(orders, order_size, customer_details, id);
                    }
                }
            }
        });

        displayOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_pending_orders, container, false);

        listview = root.findViewById(R.id.simpleListView);
        refresh = root.findViewById(R.id.etRefreshOrders);
        spinner = root.findViewById(R.id.etSpinner);

        order_list = new ArrayList<Order>();

        db = FirebaseFirestore.getInstance();
        populateEntries();

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.pending_order_listview, list);
        listview.setAdapter(adapter);
        listview.setClickable(true);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.order_display_dialog);
                final Order order = order_list.get(position);
                Button back = (Button) dialog.getWindow().findViewById(R.id.etBack);
                Button accept = (Button) dialog.getWindow().findViewById(R.id.etAccept);
                TextView order_display = (TextView) dialog.getWindow().findViewById(R.id.etOrderDisplay);

                order_display.setText(order.getOrders_map());

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = order.getOrder_id();
                        db.collection("orders").document(id).delete();
                        populateEntries();
                    }
                });
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateEntries();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }
}

class Order{
    private Integer order_size;
    private HashMap<String, Integer> orders_map;
    private String customer_name;
    private String customer_number;
    private String order_id;

    public Order(Integer order_size, String orders, String customer_details, String order_id){
        this.order_size = order_size;
        this.order_id = order_id;
        orders_map = new HashMap<>(order_size);
        extractCustomerDetails(customer_details);
        extractOrders(orders);
    }

    private void extractCustomerDetails(String customer_details){
        customer_name = customer_details.split("#", -1)[0];
        customer_number = customer_details.split("#", -1)[1];
    }

    private void extractOrders(String orders){
        String[] order_list = orders.split("@", -1);
        for(String order: order_list){
            String order_name = order.split("X", -1)[0];
            String order_quantity = order.split("X", -1)[1];
            orders_map.put(order_name, Integer.parseInt(order_quantity));
        }
    }

    @Override
    public String toString(){
        String result = "Name: " + customer_name + " \n" + "Phone Number: " + customer_number + "\n" + "Order Size: " + order_size;
        return result;
    }

    public String getOrders_map() {
        String result = "";
        for(String item: orders_map.keySet()){
            result += item + "X" + orders_map.get(item) + "\n";
        }
        return result;
    }

    public String getOrder_id() {
        return order_id;
    }
}
