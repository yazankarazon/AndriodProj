package com.example.finalproject.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.finalproject.clsCurrentUser;


import com.example.finalproject.R;

public class ProfileFragment extends Fragment {
    private TextView txtFullName, txtStudentNumber;


    public ProfileFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        txtFullName = view.findViewById(R.id.txtFullName);
        txtStudentNumber = view.findViewById(R.id.txtStudentNumber);
        printInfo();
        return view;
    }

    private void printInfo() {
        String fullName = clsCurrentUser.CurrentUser.getFirstname() + " " + clsCurrentUser.CurrentUser.getLastname();
        txtFullName.setText("Welcome, " + fullName);

        txtStudentNumber.setText("Student Number: "+clsCurrentUser.CurrentUser.getUsername());
    }
}