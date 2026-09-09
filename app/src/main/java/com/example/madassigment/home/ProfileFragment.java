package com.example.madassigment.home; // Adjust your package name if needed

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.madassigment.R;
import com.example.madassigment.auth.LoginActivity; // Adjust to your actual Login Activity

public class ProfileFragment extends Fragment {

    private EditText etName, etEmail, etAge, etSalary;
    private Spinner spinnerGender;
    private SharedPreferences profilePrefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etName = view.findViewById(R.id.et_emp_name);
        etEmail = view.findViewById(R.id.et_emp_email);
        etAge = view.findViewById(R.id.et_emp_age);
        etSalary = view.findViewById(R.id.et_emp_salary);
        spinnerGender = view.findViewById(R.id.spinner_gender);

        Button btnSave = view.findViewById(R.id.btn_save_profile);
        Button btnLogout = view.findViewById(R.id.btn_logout);

        profilePrefs = requireActivity().getSharedPreferences("EmployeeProfile", Context.MODE_PRIVATE);

        loadProfileData();

        btnSave.setOnClickListener(v -> saveProfileData());

        btnLogout.setOnClickListener(v -> {
            SharedPreferences sessionPrefs = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sessionPrefs.edit();
            editor.putBoolean("is_logged_in", false);
            editor.apply();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }

    private void saveProfileData() {
        SharedPreferences.Editor editor = profilePrefs.edit();

        editor.putString("emp_name", etName.getText().toString().trim());
        editor.putString("emp_email", etEmail.getText().toString().trim());
        editor.putString("emp_age", etAge.getText().toString().trim());
        editor.putString("emp_salary", etSalary.getText().toString().trim());

        editor.putInt("emp_gender_position", spinnerGender.getSelectedItemPosition());

        editor.apply();

        Toast.makeText(requireContext(), "Profile Details Saved!", Toast.LENGTH_SHORT).show();
    }

    private void loadProfileData() {
        etName.setText(profilePrefs.getString("emp_name", ""));
        etEmail.setText(profilePrefs.getString("emp_email", ""));
        etAge.setText(profilePrefs.getString("emp_age", ""));
        etSalary.setText(profilePrefs.getString("emp_salary", ""));

        spinnerGender.setSelection(profilePrefs.getInt("emp_gender_position", 0));
    }
}