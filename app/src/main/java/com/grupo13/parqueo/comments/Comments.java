package com.grupo13.parqueo.comments;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.grupo13.parqueo.R;

import java.util.ArrayList;

public class Comments extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //vars
    private ArrayList<String> nNames = new ArrayList<>();
    private ArrayList<String> nImagesUrls = new ArrayList<>();
    private ArrayList<String> nComments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImageBitmas();
    }

    private void initImageBitmas(){
        //for add the images

        //calling the recyclerview
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclew_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, nNames, nImagesUrls, nComments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
