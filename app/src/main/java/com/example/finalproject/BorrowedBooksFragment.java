package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.Book.BorrowedBooksAdapter;
import com.example.finalproject.Book.CategoryAdapter;
import com.example.finalproject.Book.clsBook;
import com.example.finalproject.Book.clsCategory;
import com.example.finalproject.Fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BorrowedBooksFragment extends Fragment implements BorrowedBooksAdapter.OnReturnButtonClickListener {

    private RecyclerView recyclerViewBorrowed;
    private List<clsBook> bookList;
    private RequestQueue requestQueue;


    public BorrowedBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrowed_books, container, false);

        setupRecyclerView(view);
        ListData();


        return view;
    }



    private void setupRecyclerView(View view) {
        recyclerViewBorrowed = view.findViewById(R.id.recyclerViewBorrowed);
        bookList = new ArrayList<>();
        recyclerViewBorrowed.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewBorrowed.setLayoutManager(layoutManager);
    }

    private void ListData() {
        String url = "http://10.0.2.2:5000/borrowed-books/"+clsCurrentUser.CurrentUser.getId();
        requestQueue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject bookObject = response.getJSONObject(i);
                                int id = bookObject.getInt("id");
                                String title = bookObject.getString("title");
                                String imageUrl = bookObject.getString("image");
                                int copies = bookObject.getInt("copies");
                                clsBook book = new clsBook(id, title, imageUrl, copies);
                                bookList.add(book);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        BorrowedBooksAdapter adapter = new BorrowedBooksAdapter(requireContext(), bookList, BorrowedBooksFragment.this);
                        recyclerViewBorrowed.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onReturnButtonClick(clsBook book) {
        updateBooksTable(book.getId(), book.getCopies() + 1);
        deleteBookFromBorrowedTables(book.getId());

        int position = bookList.indexOf(book);
        if (position != -1) {
            BorrowedBooksAdapter adapter = (BorrowedBooksAdapter) recyclerViewBorrowed.getAdapter();
            adapter.removeItem(position);
        }

    }

    private void updateBooksTable(int bookId, int newCopies) {
        try {
            requestQueue = Volley.newRequestQueue(requireContext());
            String url = "http://10.0.2.2:5000/update-books";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("bookId", bookId);
            jsonBody.put("newCopies", newCopies);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void deleteBookFromBorrowedTables(int bookId) {
        try {
            requestQueue = Volley.newRequestQueue(requireContext());
            String url = "http://10.0.2.2:5000/delete-book/" + bookId;

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle successful deletion if needed
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}