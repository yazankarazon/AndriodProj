package com.example.finalproject.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.Book.BookAdapter;
import com.example.finalproject.Book.clsBook;
import com.example.finalproject.R;


import java.util.ArrayList;
import java.util.List;

public class ShowBooksFromCategoryFragment extends Fragment implements BookAdapter.OnItemClickListener {


    public static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private RecyclerView recyclerView3;
    private TextView txtCategoryName;

    public static BookAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_books_from_category, container, false);

        String categoryName = getArguments().getString(ARG_PARAM1);

        txtCategoryName = view.findViewById(R.id.txtCategoryName);
        txtCategoryName.setText(categoryName + " Books");

        setupRecyclerView2(view);
        ListData2(categoryName);

        return view;
    }

    private void setupRecyclerView2(View view) {
        recyclerView3 = view.findViewById(R.id.recycler_view3);
        recyclerView3.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(layoutManager);

    }

    private void ListData2(String categoryName) {

        List<clsBook> filteredBookList = new ArrayList<>();
        for (clsBook book : HomeFragment.bookList) {
            if (book.getCategory().equals(categoryName)) {
                filteredBookList.add(book);
            }
        }

        adapter = new BookAdapter(filteredBookList, requireContext(), this);
        recyclerView3.setAdapter(adapter);
    }


    @Override
    public void onItemClick(clsBook book) {
        Fragment bookDetailsFragment = new BookDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable("book", book);
        bookDetailsFragment.setArguments(args);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, bookDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}


