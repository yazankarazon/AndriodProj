package com.example.finalproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.Book.BookAdapter;
import com.example.finalproject.Book.clsBook;
import com.example.finalproject.Book.clsCategory;
import com.example.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowBooksFromCategoryFragment extends Fragment implements BookAdapter.OnItemClickListener {


    public static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private RecyclerView recyclerView3;
    private TextView txtCategoryName;



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

        BookAdapter adapter = new BookAdapter(filteredBookList, requireContext(), this);
        recyclerView3.setAdapter(adapter);
    }


    @Override
    public void onItemClick(clsBook book) {
        // Create a new instance of BookDetailsFragment
        BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();

        // Pass the clicked book as an argument to the fragment
        Bundle args = new Bundle();
        args.putSerializable("book", book);
        bookDetailsFragment.setArguments(args);

        // Replace the current fragment with BookDetailsFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, bookDetailsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}


