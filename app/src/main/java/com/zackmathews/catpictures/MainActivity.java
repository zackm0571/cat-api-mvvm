package com.zackmathews.catpictures;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    HorizontalListAdapter adapter;
    MainViewModel viewModel;
    FloatingActionButton fab;


    AlertDialog searchDialog;
    View searchView;

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
        viewModel.getCatImages().observe(this, catImages -> {
            adapter.setData(catImages);
            progressBar.setVisibility(View.GONE);
        });

        viewModel.getBreeds().observe(this, catBreeds -> {
            for (CatBreed breed : catBreeds) {
                Chip chip = new Chip(this);
                chip.setText(breed.getName());
                chip.setTag(breed);
                chip.setCheckable(true);
                ((ChipGroup) getSearchView().findViewById(R.id.breedChipGroup)).addView(chip);
            }
        });
        viewModel.getCategories().observe(this, catCategories -> {
            for (CatCategory category : catCategories) {
                Chip chip = new Chip(this);
                chip.setText(category.getName());
                chip.setTag(category);
                chip.setCheckable(true);
                ((ChipGroup) getSearchView().findViewById(R.id.categoryChipGroup)).addView(chip);
            }
        });
    }

    private void initUI() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            getSearchDialog().show();
        });
        progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(
                MainActivity.this,
                2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new HorizontalListAdapter();
        recyclerView.setAdapter(adapter);
    }

    private AlertDialog getSearchDialog() {
        if (searchDialog == null) {
            searchDialog = new AlertDialog.Builder(this).setView(getSearchView()).create();
        }
        return searchDialog;
    }

    private void setSearchViewChips(View v, List<CatBreed> breeds, List<CatCategory> categories) {
        for (CatBreed breed : breeds) {
            Chip chip = new Chip(this);
            chip.setText(breed.getName());
            chip.setTag(breed);
            chip.setCheckable(true);
            ((ChipGroup) v.findViewById(R.id.breedChipGroup)).addView(chip);
        }
        for (CatCategory category : categories) {
            Chip chip = new Chip(this);
            chip.setText(category.getName());
            chip.setTag(category);
            chip.setCheckable(true);
            ((ChipGroup) v.findViewById(R.id.categoryChipGroup)).addView(chip);
        }
    }

    private List<CatBreed> getCheckedBreeds(ChipGroup cg) {
        List<CatBreed> breeds = new ArrayList<>();
        for (int i = 0; i < cg.getChildCount(); i++) {
            View v = cg.getChildAt(i);
            if (v instanceof Chip) {
                Chip chip = (Chip) v;
                if (chip.isChecked()) {
                    breeds.add((CatBreed) chip.getTag());
                }
            }
        }
        return breeds;
    }

    private List<CatCategory> getCheckedCategories(ChipGroup cg) {
        List<CatCategory> categories = new ArrayList<>();
        for (int i = 0; i < cg.getChildCount(); i++) {
            View v = cg.getChildAt(i);
            if (v instanceof Chip) {
                Chip chip = (Chip) v;
                if (chip.isChecked()) {
                    categories.add((CatCategory) chip.getTag());
                }
            }
        }
        return categories;
    }

    private View getSearchView() {
        if (searchView == null) {
            searchView = LayoutInflater.from(this).inflate(R.layout.search_layout, null);
            searchView.findViewById(R.id.searchButton).setOnClickListener(view -> {
                List<CatCategory> checkedCategories = getCheckedCategories(searchView.findViewById(R.id.categoryChipGroup));
                List<CatBreed> checkedBreed = getCheckedBreeds(searchView.findViewById(R.id.breedChipGroup));
                viewModel.getImagesFromSearchFilter(checkedBreed, checkedCategories);
                progressBar.setVisibility(View.VISIBLE);
                getSearchDialog().dismiss();
            });
        }
        return searchView;
    }
}
