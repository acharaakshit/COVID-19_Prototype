package com.example.htc20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Set;

public class DashboardStoreActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseAuth fbAuth;
    private TextView storeName;

    private String location;
    private String store_name;
    private FirebaseFirestore db;

    private void SetLocationAndName(String location, String store_name){
        this.location = location;
        this.store_name = store_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_store);

        logout = (Button) findViewById(R.id.etLogout);
        storeName = findViewById(R.id.tv_store_name);

        fbAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fbAuth.getCurrentUser();
        String email = user.getEmail();
        int index = email.indexOf('@');
        String unique_id = email.substring(0, index);
        storeName.setText(unique_id + "'s Dashboard");

        db = FirebaseFirestore.getInstance();
        db.collection("store")
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            Integer lat_location = Integer.parseInt(String.valueOf(doc.getData().get("latitude")));
                            Integer long_location = Integer.parseInt(String.valueOf(doc.getData().get("longitude")));
                            String final_location = lat_location + "\u00B0" + "\t" + long_location + "\u00B0";
                            String store_name = String.valueOf(doc.getData().get("shop_name"));
                            SetLocationAndName(final_location, store_name);
                        }
                    }
                });

        MyStoreAdapter tabsPagerAdapter = new MyStoreAdapter(this, getSupportFragmentManager(), 3, unique_id, store_name, location);
        ViewPager viewPager = findViewById(R.id.etViewPager);
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbAuth.signOut();
                finish();
                Toast.makeText(DashboardStoreActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardStoreActivity.this, LauncherActivity.class));
            }
        });
    }
}
