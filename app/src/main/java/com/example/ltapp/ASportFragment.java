package com.example.ltapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ASportFragment extends Fragment {

    private Button buttonCreateSport, buttonEditSport, buttonDeleteSport;
    private List<Sport> sportList;
    private SportAdapter sportAdapter;
    private RecyclerView recyclerViewSport;
    private EditText sportNameInput, sportLocationInput, sportDistrictInput, sportPriceInput, sportDescriptionInput;

    private MyDatabase db;
    private int selectedPosition = RecyclerView.NO_POSITION;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_sport, container, false);

        db = new MyDatabase(getActivity());

        buttonCreateSport = view.findViewById(R.id.buttonCreateSport);
        buttonEditSport = view.findViewById(R.id.buttonEditSport);
        buttonDeleteSport = view.findViewById(R.id.buttonDeleteSport);
        recyclerViewSport = view.findViewById(R.id.recyclerViewSport);

        recyclerViewSport.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        sportList = db.getAllSports();
        sportAdapter = new SportAdapter(getActivity(), sportList, position -> selectedPosition = position);
        recyclerViewSport.setAdapter(sportAdapter);

        buttonCreateSport.setOnClickListener(v -> showCreateSportDialog());
        buttonEditSport.setOnClickListener(v -> {
            if (selectedPosition != RecyclerView.NO_POSITION) {
                showEditSportDialog(selectedPosition);
            }
        });
        buttonDeleteSport.setOnClickListener(v -> deleteSelectedSport());

        return view;
    }

    private void showCreateSportDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialogue_add_sport, null);
        EditText sportNameInput = view.findViewById(R.id.sportNameInput);
        EditText sportLocationInput = view.findViewById(R.id.sportLocationInput);
        EditText sportDistrictInput = view.findViewById(R.id.sportDistrictInput);
        EditText sportPriceInput = view.findViewById(R.id.sportPriceInput);
        EditText sportDescriptionInput = view.findViewById(R.id.sportDescriptionInput);
        EditText sportServiceInput = view.findViewById(R.id.sportServiceInput);
        Spinner sportTypeSpinner = view.findViewById(R.id.sportTypeSpinner);
        Button addSportButton = view.findViewById(R.id.addSportButton);

        // Populate spinner with category data
        List<category> categories = db.getAllCategories();
        ArrayAdapter<category> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportTypeSpinner.setAdapter(adapter);

        builder.setView(view);
        AlertDialog dialog = builder.create();

        addSportButton.setOnClickListener(v -> {
            String sportName = sportNameInput.getText().toString().trim();
            String sportLocation = sportLocationInput.getText().toString().trim();
            String sportDistrict = sportDistrictInput.getText().toString().trim();
            String sportPrice = sportPriceInput.getText().toString().trim();
            String sportDescription = sportDescriptionInput.getText().toString().trim();
            String sportService = sportServiceInput.getText().toString().trim();

            if (sportTypeSpinner.getCount() == 0) {
                Toast.makeText(getContext(), "Vui lòng thêm danh mục trước khi tạo sân", Toast.LENGTH_SHORT).show();
                return;
            }

            Object selectedItem = sportTypeSpinner.getSelectedItem();
            if (selectedItem == null) {
                Toast.makeText(getContext(), "Vui lòng chọn một danh mục hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            String sportType = selectedItem.toString();

            if (!sportName.isEmpty() && !sportLocation.isEmpty() && !sportDistrict.isEmpty() && !sportPrice.isEmpty() && !sportDescription.isEmpty()) {
                Sport newSport = new Sport("", sportType, sportName, sportLocation, sportDistrict, "", sportPrice, sportDescription, sportService);
                db.addSport(newSport);
                sportList.add(newSport);
                sportAdapter.notifyDataSetChanged();
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin trước khi tạo sân", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void showEditSportDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialogue_edit_sport, null);
        EditText sportNameInput = view.findViewById(R.id.sportNameInput);
        EditText sportLocationInput = view.findViewById(R.id.sportLocationInput);
        EditText sportDistrictInput = view.findViewById(R.id.sportDistrictInput);
        EditText sportPriceInput = view.findViewById(R.id.sportPriceInput);
        EditText sportDescriptionInput = view.findViewById(R.id.sportDescriptionInput);
        EditText sportServiceInput = view.findViewById(R.id.sportServiceInput);
        Spinner sportTypeSpinner = view.findViewById(R.id.sportTypeSpinner);
        Button addSportButton = view.findViewById(R.id.editSportButton);

        Sport sport = sportList.get(position);
        sportNameInput.setText(sport.getS_NAME());
        sportLocationInput.setText(sport.getS_LOCATION());
        sportDistrictInput.setText(sport.getS_DISTRICT());
        sportPriceInput.setText(sport.getS_PRICE());
        sportDescriptionInput.setText(sport.getS_DESCRIPTION());

        // Populate spinner with category data
        List<category> categories = db.getAllCategories();
        ArrayAdapter<category> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportTypeSpinner.setAdapter(adapter);

        builder.setView(view);
        AlertDialog dialog = builder.create();

        addSportButton.setOnClickListener(v -> {
            String sportName = sportNameInput.getText().toString().trim();
            String sportLocation = sportLocationInput.getText().toString().trim();
            String sportDistrict = sportDistrictInput.getText().toString().trim();
            String sportPrice = sportPriceInput.getText().toString().trim();
            String sportDescription = sportDescriptionInput.getText().toString().trim();
            String sportType = sportTypeSpinner.getSelectedItem().toString();
            String sportService = sportServiceInput.getText().toString();

            if (!sportName.isEmpty() && !sportLocation.isEmpty() && !sportDistrict.isEmpty() && !sportPrice.isEmpty() && !sportDescription.isEmpty()) {
                Sport updatedSport = new Sport(sport.getS_ID(), sportType, sportName, sportLocation, sportDistrict, "", sportPrice, sportDescription, sportService);
                db.updateSport(updatedSport);
                sportList.set(position, updatedSport);
                sportAdapter.notifyDataSetChanged();
                dialog.dismiss();
            } else {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin trước khi sửa sân", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void deleteSelectedSport() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            Sport selectedSport = sportList.get(selectedPosition);
            db.deleteSport(selectedSport.getS_ID());
            sportList.remove(selectedPosition);
            sportAdapter.notifyDataSetChanged();
            selectedPosition = RecyclerView.NO_POSITION;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();  // Close the database connection when the activity is destroyed
        }
    }
}
