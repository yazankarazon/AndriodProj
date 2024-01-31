package com.example.finalproject.Book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<clsBook> bookList;
    private Context context;
    private OnItemClickListener listener;

    public void setFilterList(List<clsBook> filterList){
        this.bookList = filterList;
        notifyDataSetChanged();
    }

    public BookAdapter(List<clsBook> bookList, Context context, OnItemClickListener listener) {
        this.bookList = bookList;
        this.context = context;
        this.listener = listener;
    }

    public BookAdapter(List<clsBook> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_items, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        clsBook book = bookList.get(position);

        holder.titleTextView.setText(book.getTitle());
        Glide.with(context)
                .load(book.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (listener != null && clickedPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(bookList.get(clickedPosition)); // Pass clicked book to listener
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.book_img);
            titleTextView = itemView.findViewById(R.id.book_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(clsBook book);
    }
}
