package com.example.finalproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Book.BookAdapter;
import com.example.finalproject.Book.clsBook;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements BookAdapter.OnItemClickListener {

    private SearchView searchView;
    private RecyclerView recyclerView4;
    private BookAdapter adapter;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.searchView);
        setupRecyclerView2(view);
        adapter = new BookAdapter(HomeFragment.bookList, requireContext(), this );
        recyclerView4.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });
        return view;
    }

    private void setupRecyclerView2(View view) {
        recyclerView4 = view.findViewById(R.id.recycler_view4);
        recyclerView4.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView4.setLayoutManager(layoutManager);

    }


    private void filter(String newText){
        List<clsBook> filterlist = new ArrayList<>();

        for (clsBook item : HomeFragment.bookList){
            if(item.getTitle().toLowerCase().contains(newText.toLowerCase())){
                filterlist.add(item);
            }
        }

        if(filterlist.isEmpty()) {
            Toast.makeText(getContext(), "No Books Found", Toast.LENGTH_LONG).show();
        }else{
            adapter.setFilterList(filterlist);
        }
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