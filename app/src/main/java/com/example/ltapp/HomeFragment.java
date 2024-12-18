package com.example.ltapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewCategory, recyclerViewSport, recyclerViewSportSorted;
    private CategoryAdapter categoryAdapter;
    private UserSportAdapter userSportAdapter;
    private UserSportAdapter sortedSportAdapter;
    private MyDatabase db;
    private List<category> categoryList;
    private List<Sport> sportList, sortedSportList;
    private AutoCompleteTextView searchEditText;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategory);
        recyclerViewSport = view.findViewById(R.id.recyclerViewSport);
        recyclerViewSportSorted = view.findViewById(R.id.recyclerViewSportSorted);
        searchEditText = view.findViewById(R.id.searchEditText);
        spinner = view.findViewById(R.id.spinnerCity);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.cities, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                // Toast.makeText(MainActivity.this, "Đã chọn: " + selectedCity, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không cần xử lý
            }
        });
        db = new MyDatabase(getContext());
        ArrayList<String> sNameList = db.getSNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, sNameList);
        searchEditText.setAdapter(adapter);
        searchEditText.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedSName = adapter.getItem(position);
            if (selectedSName != null) {
                // Find the sport object by its name
                Sport selectedSport = db.getSportByName(selectedSName);
                if (selectedSport != null) {
                    openSportDetail(selectedSport);
                } else {
                    Toast.makeText(getContext(), "Sport not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Item not found", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> sNameList = db.getSNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, sNameList);
        searchEditText.setAdapter(adapter);
        db.updateAllSportsRatings();

        // Load categories
        categoryList = db.getAllCategories();
        Log.d("HomeFragment", "Categories loaded: " + categoryList.size());

        categoryAdapter = new CategoryAdapter(getContext(), categoryList, this::filterSports);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setAdapter(categoryAdapter);

        // Load sports data
        sportList = db.getAllSports();
        Log.d("HomeFragment", "Sports loaded: " + sportList.size());
        userSportAdapter = new UserSportAdapter(getContext(), sportList, this::openSportDetail);
        recyclerViewSport.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSport.setAdapter(userSportAdapter);

        // Load sorted sports data
        loadSortedSportsData();

    }

    private void filterSports(int position) {
        String categoryName = categoryList.get(position).getName();
        List<Sport> filteredSports = db.getSportsByCategory(categoryName);
        userSportAdapter.updateSports(filteredSports);
        sortedSportAdapter.updateSports(filteredSports);
    }

    private void openSportDetail(Sport sport) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("sport", sport);
        startActivity(intent);
    }



    private void loadSortedSportsData() {
        // Load sports data from the database
        sortedSportList = db.getAllSports();

        // Sort the sports by rating in descending order
        Collections.sort(sortedSportList, new Comparator<Sport>() {
            @Override
            public int compare(Sport s1, Sport s2) {
                float rating1 = 0;
                float rating2 = 0;
                try {
                    rating1 = Float.parseFloat(s1.getS_RATING());
                    rating2 = Float.parseFloat(s2.getS_RATING());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                return Float.compare(rating2, rating1);
            }
        });

        // Set up the RecyclerView and Adapter for sorted data
        sortedSportAdapter = new UserSportAdapter(getContext(), sortedSportList, this::openSportDetail);
        recyclerViewSportSorted.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSportSorted.setAdapter(sortedSportAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data when the fragment resumes
        sportList = db.getAllSports();
        userSportAdapter.updateSports(sportList);

        loadSortedSportsData();
        sortedSportAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();  // Close the database connection when the activity is destroyed
        }


    }
}
