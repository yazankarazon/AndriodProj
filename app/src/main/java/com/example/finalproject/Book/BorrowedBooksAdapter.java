package com.example.finalproject.Book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Book.clsBook;
import com.example.finalproject.R;


import java.util.List;
import com.bumptech.glide.Glide;

public class BorrowedBooksAdapter extends RecyclerView.Adapter<BorrowedBooksAdapter.ViewHolder> {

    private List<clsBook> booksList;
    private Context context;
    private OnReturnButtonClickListener returnButtonClickListener;

    public BorrowedBooksAdapter(Context context, List<clsBook> booksList, OnReturnButtonClickListener listener) {
        this.context = context;
        this.booksList = booksList;
        this.returnButtonClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_borrowed_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        clsBook book = booksList.get(position);

        holder.borrowTitleTextView.setText(book.getTitle());
        Glide.with(context)
                .load(book.getImage())
                .into(holder.borrowImageView);

        holder.borrowButton.setOnClickListener(v -> {
            if (returnButtonClickListener != null) {
                returnButtonClickListener.onReturnButtonClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView borrowImageView;
        TextView borrowTitleTextView;
        Button borrowButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            borrowImageView = itemView.findViewById(R.id.borrowImageView);
            borrowTitleTextView = itemView.findViewById(R.id.borrowTitleTextView);
            borrowButton = itemView.findViewById(R.id.borrowButton);
        }
    }

    public interface OnReturnButtonClickListener {
        void onReturnButtonClick(clsBook book);
    }

    public void removeItem(int position) {
        booksList.remove(position);
        notifyItemRemoved(position);
    }
}
