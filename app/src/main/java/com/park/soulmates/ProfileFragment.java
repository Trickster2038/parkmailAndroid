package com.park.soulmates;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button saveButton = view.findViewById(R.id.saveChangesBtn);
        saveButton.setOnClickListener(v -> {
            TextInputEditText editName = view.findViewById(R.id.editTextName);
            TextInputEditText editSurname = view.findViewById(R.id.editTextSurname);
            TextInputEditText editBio = view.findViewById(R.id.editTextBio);
            TextInputEditText editDate = view.findViewById(R.id.editTextDate);
            RadioButton radioRomantic = view.findViewById(R.id.radioRomantic);
            CheckBox checkIT = view.findViewById(R.id.checkIT);
            CheckBox checkMusic = view.findViewById(R.id.checkMusic);
            CheckBox checkSport = view.findViewById(R.id.checkSport);
            CheckBox checkGames = view.findViewById(R.id.checkGames);
            CheckBox checkReading = view.findViewById(R.id.checkReading);

            UserPusher.push(
                    editName.getText().toString(),
                    editSurname.getText().toString(),
                    editBio.getText().toString(),
                    editDate.getText().toString(),
                    radioRomantic.isChecked(),
                    new Boolean[] {
                            checkIT.isChecked(),
                            checkMusic.isChecked(),
                            checkSport.isChecked(),
                            checkGames.isChecked(),
                            checkReading.isChecked(),
                    }
            );
            Log.d("log DB_status", "db_feed - OK");
        });

        //TODO: add logout button listener
        return view;
    }
}
