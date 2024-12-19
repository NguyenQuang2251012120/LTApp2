package com.example.ltapp;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfitFragment extends Fragment {

    private List<booking> bookings;
    private ListView listView;
    private Spinner spinner;
    private ProfitAdapter adapter;
    private MyDatabase myDatabase; // Reference to MyDatabase
    private TextView tvTotalProfit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profit, container, false);

        // Initialize the MyDatabase instance
        myDatabase = new MyDatabase(getContext());

        listView = view.findViewById(R.id.listView);
        spinner = view.findViewById(R.id.spinner);
        tvTotalProfit = view.findViewById(R.id.tvTotalProfit);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.profit_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateListView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Initialize ListView with default data
        updateListView(0);

        return view;
    }

    private void updateListView(int option) {
        List<Pair<String, Integer>> profitList = new ArrayList<>();
        if (option == 0) {
            Map<String, Integer> profitByTenSan = myDatabase.calculateProfitByTenSan();
            for (Map.Entry<String, Integer> entry : profitByTenSan.entrySet()) {
                profitList.add(new Pair<>(entry.getKey(), entry.getValue()));
            }
        } else if (option == 1) {
            Map<String, Integer> profitByUser = myDatabase.calculateProfitByUser();
            for (Map.Entry<String, Integer> entry : profitByUser.entrySet()) {
                profitList.add(new Pair<>(entry.getKey(), entry.getValue()));
            }
        }

        adapter = new ProfitAdapter(getContext(), profitList);
        listView.setAdapter(adapter);

        int totalProfit = 0;
        for (Pair<String, Integer> pair : profitList) {
            totalProfit += pair.second;
        }
        tvTotalProfit.setText("Tổng doanh thu: " + totalProfit + " đ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myDatabase.close(); // Close the database when the fragment view is destroyed
    }
}
