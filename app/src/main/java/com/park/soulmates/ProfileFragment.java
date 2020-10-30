package com.park.soulmates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

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
            TextInputEditText editContacts = view.findViewById(R.id.editTextContacts);
            RadioButton radioRomantic = view.findViewById(R.id.radioRomantic);
            RadioButton radioMale = view.findViewById(R.id.radioMale);
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
                    editContacts.getText().toString(),
                    radioRomantic.isChecked(),
                    radioMale.isChecked(),
                    new Boolean[] {
                            checkIT.isChecked(),
                            checkMusic.isChecked(),
                            checkSport.isChecked(),
                            checkGames.isChecked(),
                            checkReading.isChecked(),
                    }
            );
            Log.d("dev_DB_status", "db_feed - OK");
        });

        // TODO: catch signOut error if raised
        Button logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(inflater.getContext(), AuthActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
