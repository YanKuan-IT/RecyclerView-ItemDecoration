package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Star> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 15; j++) {
                if (i == 0){
                    list.add(new Star("hello"+j, "hello"));
                } else if (i == 1) {
                    list.add(new Star("world"+j, "world"));
                } else {
                    list.add(new Star("android"+j, "android"));
                }
            }
        }
        
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new StarDecoration(this));
        recyclerView.setAdapter(new MyAdapter(this, list));
    }
}
