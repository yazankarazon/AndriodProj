package com.example.finalproject.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.Book.BookAdapter;
import com.example.finalproject.Book.CategoryAdapter;
import com.example.finalproject.Book.clsBook;
import com.example.finalproject.Book.clsCategory;
import com.example.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CategoryClickListener {

    private RecyclerView recyclerView, recyclerView2;
    public static List<clsBook> bookList;
    private List<clsCategory> categoryList;
    private RequestQueue requestQueue;

    private int currentPosition = 0;
    private Handler handler;
    private Runnable runnable;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(view);
        ListData();
        setupRecyclerView2(view);
        ListData2();
    }

    private void setupRecyclerView2(View view) {
        recyclerView2 = view.findViewById(R.id.recycler_view2);
        recyclerView2.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager);
        categoryList = new ArrayList<>();
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        bookList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void ListData2() {
        String url = "http://10.0.2.2:5000/getcategory";
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
                                String name = bookObject.getString("name");
                                String imageCover = bookObject.getString("image_cover");
                                clsCategory book = new clsCategory(name, imageCover);

                                categoryList.add(book);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        CategoryAdapter adapter = new CategoryAdapter(categoryList, requireContext(), HomeFragment.this);
                        recyclerView2.setAdapter(adapter);
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

    private void ListData() {
        String url = "http://10.0.2.2:5000/getbooks";
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
                                String title = bookObject.getString("title");
                                String category = bookObject.getString("categoryName");
                                String pages = bookObject.getString("pages");
                                String imageUrl = bookObject.getString("image");
                                int copies = bookObject.getInt("copies");
                                String imageCover = bookObject.getString("image_cover");
                                clsBook book = new clsBook(title, category, pages, imageUrl, copies, imageCover);
                                bookList.add(book);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        BookAdapter adapter = new BookAdapter(bookList, requireContext());
                        recyclerView.setAdapter(adapter);
                        startAutoScroll();

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

    private void startAutoScroll() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition == bookList.size()) {
                    currentPosition = 0;
                }
                recyclerView.smoothScrollToPosition(currentPosition++);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onCategoryClick(String categoryName) {
        ShowBooksFromCategoryFragment showBooksFragment = new ShowBooksFromCategoryFragment();

        // Pass any necessary data to the new fragment using a Bundle if needed
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", categoryName);
        showBooksFragment.setArguments(bundle);

        // Replace the current fragment with the new fragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, showBooksFragment);
        transaction.addToBackStack(null); // Optional: Adds the transaction to the back stack
        transaction.commit();
    }


}
