package com.example.ltapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RatingBar;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {
    public TextView sportType, sportName, sportLocation, sportRating, sportPrice, sportDescription, sportService;
    private RecyclerView rvComments;
    private CommentAdapter commentAdapter;
    private EditText etComment;
    private RatingBar ratingInput;
    private Button btnPostComment;
    private List<Comment> comments;
    private TextView tvAverageRating;
    private Button btnYeuThich;
    private SharedPreferences sharedPreferences;
    private MyDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ConstraintLayout back = findViewById(R.id.btBack);
        sportName = findViewById(R.id.sportName);
        sportLocation = findViewById(R.id.sportLocation);
        sportPrice = findViewById(R.id.sportPrice);
        sportDescription = findViewById(R.id.sportDescription);
        sportService = findViewById(R.id.sportService);
        Button book1 = findViewById(R.id.btn_book);
        database = new MyDatabase(this);


        rvComments = findViewById(R.id.rv_comments);
        etComment = findViewById(R.id.et_comment);
        ratingInput = findViewById(R.id.rating_input);
        btnPostComment = findViewById(R.id.btn_post_comment);
        tvAverageRating = findViewById(R.id.tv_average_rating);
        btnYeuThich = findViewById(R.id.btn_yeuthich);
        sharedPreferences = getSharedPreferences("FavoritePrefs", MODE_PRIVATE);


        Sport sport = (Sport) getIntent().getSerializableExtra("sport");


        if (sport != null) {
            sportName.setText(sport.getS_NAME());
            sportLocation.setText(sport.getS_LOCATION());
            double price = 0;
            try {
                price = Double.parseDouble(sport.getS_PRICE());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            sportPrice.setText("Giá: " + String.format("%,.0f", price) + " Đ");
            sportDescription.setText(sport.getS_DESCRIPTION());
            sportService.setText(sport.getS_SERVICE());
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, BookingActivity.class);
                assert sport != null;
                intent.putExtra("sportName", sport.getS_NAME());
                intent.putExtra("sportPrice", Integer.parseInt(sport.getS_PRICE()));
                startActivity(intent);
            }
        });

        btnYeuThich.setOnClickListener(v -> {
                    String sportName = sport.getS_NAME();
                    String sportPrice = sport.getS_PRICE();
            String sportDistrict = sport.getS_DISTRICT();
            sharedPreferences = getSharedPreferences("LoginUser", MODE_PRIVATE);
            String loggedInUser = sharedPreferences.getString("User", null);
            if (loggedInUser != null) {
                // Check if the sport is already in the user's favorites list
                favorite sport1 = new favorite(loggedInUser, sportName, sportPrice, sportDistrict);
                boolean isFavorite = database.isSportFavorite(sport1);
                if (isFavorite) {
                    // If it's already a favorite, remove it
                    database.removeSportFromFavorites(sport1);
                    Toast.makeText(this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    btnYeuThich.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
                } else {
                    // If it's not a favorite, add it
                    database.addSportToFavorites(sport1);
                    Toast.makeText(this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    btnYeuThich.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                }
            } else {
                Toast.makeText(this, "Vui lòng đăng nhập để sử dụng tính năng yêu thích", Toast.LENGTH_SHORT).show();
            }
        });

        initCommentSection();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initCommentSection() {
        comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(comments);

        Sport sport = (Sport) getIntent().getSerializableExtra("sport");
        sharedPreferences = getSharedPreferences("LoginUser", MODE_PRIVATE);

        String courtName = sport.getS_NAME();
        comments = database.getCommentsByCourtName(courtName);
        commentAdapter.setComments(comments);

        rvComments.setLayoutManager(new LinearLayoutManager(this));
        rvComments.setAdapter(commentAdapter);

        btnPostComment.setOnClickListener(v -> {
            String content = etComment.getText().toString().trim();
            float rating = ratingInput.getRating();
            String username = sharedPreferences.getString("User", null);
            String sportName = sport.getS_NAME();

            // Check if the content is not empty and the rating is greater than zero
            if (!content.isEmpty()) {
                if (rating > 0) {  // Check if the rating is not zero
                    Comment newComment = new Comment("", content, username, rating, sportName); // Use sportName as courtName
                    database.addComment(newComment);  // Add the new comment to the database
                    commentAdapter.addComment(newComment);  // Add the new comment to the list in the RecyclerView

                    // Clear input fields
                    etComment.setText("");
                    ratingInput.setRating(0);

                    // Update the average rating
                    updateAverageRating();

                    Toast.makeText(this, "Đã đăng bình luận", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Vui lòng chọn đánh giá (rating)", Toast.LENGTH_SHORT).show(); // Notify user to provide a rating
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập nội dung bình luận", Toast.LENGTH_SHORT).show(); // Notify user to enter comment content
            }
        });


        updateAverageRating();
    }

    private void updateAverageRating() {
        float totalRating = 0;
        for (Comment comment : comments) {
            totalRating += comment.getRating();
        }
        float averageRating = comments.isEmpty() ? 0 : totalRating / comments.size();
        String formattedRating = String.format("%.1f", averageRating);
        tvAverageRating.setText(formattedRating + " ★");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();  // Close the database connection when the activity is destroyed
        }
    }
}
