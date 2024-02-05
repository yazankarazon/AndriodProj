package com.example.finalproject.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.BorrowedBooksFragment;
import com.example.finalproject.LoginActivity;
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
        Button btnBorrowedbooks = view.findViewById(R.id.btnBorrowedbooks);
        Button btnLogout = view.findViewById(R.id.btnLogout); // Add this line


        printInfo();

        btnBorrowedbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment borrowedBooksFragment = new BorrowedBooksFragment();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.FrameLayout, borrowedBooksFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        return view;
    }

    private void printInfo() {
        String fullName = clsCurrentUser.CurrentUser.getFirstname() + " " + clsCurrentUser.CurrentUser.getLastname();
        txtFullName.setText("Welcome, " + fullName);

        txtStudentNumber.setText("Student Number: "+clsCurrentUser.CurrentUser.getUsername());
    }
}