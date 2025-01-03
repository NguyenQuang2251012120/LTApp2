package com.example.ltapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> comments;

    // Constructor
    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    // Set new comments and refresh the list
    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();  // Notify the adapter that the data has changed
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.username.setText(comment.getUsername());
        holder.content.setText(comment.getContent());
        holder.ratingBar.setRating(comment.getRating());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    // Add a new comment to the list and notify the adapter
    public void addComment(Comment comment) {
        comments.add(comment);
        notifyItemInserted(comments.size() - 1);
    }

    // ViewHolder class for each comment item
    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView content;
        RatingBar ratingBar;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tv_username);
            content = itemView.findViewById(R.id.tv_content);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }
}
