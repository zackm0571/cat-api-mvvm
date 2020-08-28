package com.zackmathews.catpictures;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
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

/**
 * MainActivity to display cat images from thecatapi.
 * If given more time I would have used a {@link androidx.fragment.app.FragmentActivity}.
 */
public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private CatImageAdapter adapter;
    private FloatingActionButton fab;
    private AlertDialog searchDialog;
    private View searchView;

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
        adapter = new CatImageAdapter();
        recyclerView.setAdapter(adapter);
    }

    /**
     * Inflates / returns the {@link AlertDialog} containing the search view.
     *
     * @return {@link AlertDialog}
     */
    private AlertDialog getSearchDialog() {
        if (searchDialog == null) {
            searchDialog = new AlertDialog.Builder(this).setView(getSearchView()).create();
        }
        return searchDialog;
    }

    /**
     * Given a {@link ChipGroup} will return the children that have been selected.
     *
     * @param cg ChipGroup that contains {@link CatBreed} tag.
     *           Note: Given more time would add type safety checks as well
     *           as a generic way to get the class tag you're searching for.
     * @returnn list of breeds that have been selected
     */
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

    /**
     * Given a {@link ChipGroup} will return the children that have been selected.
     *
     * @param cg ChipGroup that contains {@link CatCategory} tag.
     *           Note: Given more time would add type safety checks as well
     *           as a generic way to get the class tag you're searching for.
     * @returnn list of categories that have been selected
     */
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

    /**
     * Returns the search view and inflates it if it has not yet been created.
     *
     * @return View containing the search options.
     */
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
