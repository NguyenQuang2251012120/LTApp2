package com.example.ltapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ACategoryFragment extends Fragment {

    public Button buttonThem, buttonXoa, buttonSua;
    public List<category> categoryList;
    public CategoryAdapter categoryAdapter;
    public RecyclerView recyclerView;

    public static DatabaseHelper dbHelper;
    public static MyDatabase db;
    private int selectedPosition = RecyclerView.NO_POSITION;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(getActivity());
        db = new MyDatabase(getActivity());
        buttonSua = view.findViewById(R.id.buttonSua);
        buttonThem = view.findViewById(R.id.buttonThem);
        buttonXoa = view.findViewById(R.id.buttonXoa);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryList = db.getAllCategories();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryList, position -> selectedPosition = position);
        recyclerView.setAdapter(categoryAdapter);

        buttonThem.setOnClickListener(v -> showAddCategoryDialog());
        buttonXoa.setOnClickListener(v -> deleteSelectedCategory());
        buttonSua.setOnClickListener(v -> {
            if (selectedPosition != RecyclerView.NO_POSITION) {
                showEditCategoryDialog(selectedPosition);
            }
        });
    }

    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialogue_add_category, null);
        EditText categoryNameInput = view.findViewById(R.id.categoryNameInput);
        Button addCategoryButton = view.findViewById(R.id.addCategoryButton);

        builder.setView(view);
        AlertDialog dialog = builder.create();

        addCategoryButton.setOnClickListener(v -> {
            String newCategoryName = categoryNameInput.getText().toString().trim();
            if (!newCategoryName.isEmpty()) {
                category newCategory = new category(0, newCategoryName);
                if (!db.categoryExists(newCategory)) {
                    db.addCategory(new category(0, newCategoryName));
                    categoryList.add(new category(0, newCategoryName));
                    categoryAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    categoryNameInput.setError("Danh mục đã tồn tại");
                }
            } else {
                categoryNameInput.setError("Ô danh mục không được để trống");
            }
        });

        dialog.show();
    }

    private void showEditCategoryDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.dialogue_edit_category, null);
        EditText categoryNameInput = view.findViewById(R.id.categoryNameInput);
        Button addCategoryButton = view.findViewById(R.id.addCategoryButton);

        categoryNameInput.setText(categoryList.get(position).getName());

        builder.setView(view);
        AlertDialog dialog = builder.create();

        addCategoryButton.setOnClickListener(v -> {
            String newCategoryName = categoryNameInput.getText().toString().trim();
            if (!newCategoryName.isEmpty()) {
                category updatedCategory = new category(categoryList.get(position).getId(), newCategoryName);
                db.updateCategory(categoryList.get(position).getId(), updatedCategory);
                categoryList.set(position, updatedCategory);
                categoryAdapter.notifyDataSetChanged();
                dialog.dismiss();
            } else {
                categoryNameInput.setError("Category name cannot be empty");
            }
        });

        dialog.show();
    }

    private void deleteSelectedCategory() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            category selectedCategory = categoryList.get(selectedPosition);
            db.deleteCategory(selectedCategory.getId());
            categoryList.remove(selectedPosition);
            categoryAdapter.notifyDataSetChanged();
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
