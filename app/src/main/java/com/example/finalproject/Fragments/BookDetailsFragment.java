package com.example.finalproject.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.Book.clsBook;
import com.example.finalproject.clsCurrentUser;



import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import org.json.JSONException;
import org.json.JSONObject;

public class BookDetailsFragment extends Fragment {

    private RequestQueue requestQueue;



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
        Button detailsBtnGetBook = view.findViewById(R.id.detailsBtnGetBook);

        Glide.with(requireContext())
                    .load(book.getImage())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(detailsImage);

        detailsTitle.setText("Title: "+book.getTitle());
        detailsCategory.setText("Category: "+book.getCategory());
        detailsPages.setText("Pages: "+book.getPages());
        detailsCopies.setText("Available Copies: "+book.getCopies());

        detailsBtnGetBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (book.getCopies() > 0) {
                    checkUserBorrowedBook(book.getId(), new BorrowedBookCallback() {
                        @Override
                        public void onResult(boolean hasBorrowedBook) {
                            if (hasBorrowedBook) {
                                Toast.makeText(requireContext(), "Sorry, You already have a copy of this book!", Toast.LENGTH_SHORT).show();
                            } else {
                                insertBookTransaction(book.getId(), clsCurrentUser.CurrentUser.getId());
                                updateBooksTable(book.getId(), book.getCopies() - 1);
                                int newCopies = book.getCopies() -1;
                                detailsCopies.setText("Available Copies: "+newCopies);
                                Toast.makeText(requireContext(), "Book borrowed successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(requireContext(), "Sorry, No copies available for this book!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }



    public interface BorrowedBookCallback {
        void onResult(boolean hasBorrowedBook);
    }
    private void checkUserBorrowedBook(int bookId, BorrowedBookCallback callback) {
        try {
            requestQueue = Volley.newRequestQueue(requireContext());
            String url = "http://10.0.2.2:5000/check-user-borrowed-book/" + bookId + "/" + clsCurrentUser.CurrentUser.getId();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                boolean hasBorrowedBook = jsonObject.getBoolean("hasBorrowedBook");
                                callback.onResult(hasBorrowedBook); // Pass the result to the callback
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            // Notify callback about the error, for example:
                            callback.onResult(false);
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
            // Notify callback about the error, for example:
            callback.onResult(false);
        }
    }



    private void insertBookTransaction(int bookId, int userId) {
        try {
            requestQueue = Volley.newRequestQueue(requireContext());
            String url = "http://10.0.2.2:5000/insert-transaction";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("bookId", bookId);
            jsonBody.put("userId", userId);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
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

}
