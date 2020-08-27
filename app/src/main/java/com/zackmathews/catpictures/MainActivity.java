package com.zackmathews.catpictures;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    HorizontalListAdapter adapter;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(MainViewModel.class);
        viewModel.getCatImages().observe(this, new Observer<List<CatImage>>() {
            @Override
            public void onChanged(List<CatImage> catImages) {
                adapter.setData(catImages);
            }
        });
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(
                MainActivity.this,
                2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new HorizontalListAdapter();
        recyclerView.setAdapter(adapter);
    }
}