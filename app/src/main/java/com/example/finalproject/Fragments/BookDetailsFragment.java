package com.example.finalproject.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.Book.clsBook;


import com.bumptech.glide.Glide;
import com.example.finalproject.R;

public class BookDetailsFragment extends Fragment {

    public BookDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        clsBook book = (clsBook) getArguments().getSerializable("book");

            ImageView detailsImage = view.findViewById(R.id.detailsImage);
            TextView detailsTitle = view.findViewById(R.id.detailsTitle);
            TextView detailsCategory = view.findViewById(R.id.detailsCategory);
            TextView detailsPages = view.findViewById(R.id.detailsPages);
            TextView detailsCopies = view.findViewById(R.id.detailsCopies);

            Glide.with(requireContext())
                    .load(book.getImage())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(detailsImage);

            detailsTitle.setText("Title: "+book.getTitle());
            detailsCategory.setText("Category: "+book.getCategory());
            detailsPages.setText("Pages: "+book.getPages());
            detailsCopies.setText("Available Copies: "+book.getCopies());


        return view;
    }

}
