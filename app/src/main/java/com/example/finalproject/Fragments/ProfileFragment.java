package com.example.finalproject.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.finalproject.clsCurrentUser;


import com.example.finalproject.R;

public class ProfileFragment extends Fragment {
    private TextView txtFullName;


    public ProfileFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        txtFullName = view.findViewById(R.id.txtFullName); // Initialize txtFullName here
        printFullName();
        return view;
    }

    private void printFullName() {
        String fullName = clsCurrentUser.CurrentUser.getFirstname() + " " + clsCurrentUser.CurrentUser.getLastname();
        txtFullName.setText("Welcome, " + fullName);
    }
}