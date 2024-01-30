package com.example.finalproject.Book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.Fragments.CategoryClickListener;
import com.example.finalproject.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<clsCategory> bookList;
    private Context context;
    private CategoryClickListener clickListener;

    public CategoryAdapter(List<clsCategory> bookList, Context context, CategoryClickListener clickListener) {
        this.bookList = bookList;
        this.context = context;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_categories, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        clsCategory book = bookList.get(position);

        holder.titleTextView.setText(book.getName());
        Glide.with(context)
                .load(book.getImageCover())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCategoryClick(book.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_img);
            titleTextView = itemView.findViewById(R.id.book_category);
        }
    }
}