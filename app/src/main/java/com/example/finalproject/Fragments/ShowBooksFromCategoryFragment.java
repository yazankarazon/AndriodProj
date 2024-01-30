package com.example.finalproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.example.finalproject.Book.BookAdapter;
import com.example.finalproject.Book.clsBook;
import com.example.finalproject.Book.clsCategory;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class ShowBooksFromCategoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    private RecyclerView recyclerView3;

    public static ShowBooksFromCategoryFragment newInstance(String param1) {
        ShowBooksFromCategoryFragment fragment = new ShowBooksFromCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView2(view);
        ListData2();
    }

    private void setupRecyclerView2(View view) {
        recyclerView3 = view.findViewById(R.id.recycler_view3);
        recyclerView3.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(layoutManager);
    }

    private void ListData2() {
        // Create and set adapter for RecyclerView
        BookAdapter adapter = new BookAdapter(HomeFragment.bookList, requireContext());
        recyclerView3.setAdapter(adapter);
    }
}


