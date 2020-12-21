package com.park.soulmates.views.other;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.park.soulmates.R;
import com.park.soulmates.models.AdvancedUserModel;
import com.park.soulmates.utils.CustomLocationListener;
import com.park.soulmates.utils.FirebaseUtils;
import com.park.soulmates.views.other.AuthActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    private ImageView mImageView;

    public ProfileFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mImageView = view.findViewById(R.id.imageProfile);

        Button saveButton = view.findViewById(R.id.saveChangesBtn);

        TextInputEditText editName = view.findViewById(R.id.editTextName);
        TextInputEditText editSurname = view.findViewById(R.id.editTextSurname);
        TextInputEditText editBio = view.findViewById(R.id.editTextBio);
        TextInputEditText editDate = view.findViewById(R.id.editTextDate);
        TextInputEditText editContacts = view.findViewById(R.id.editTextContacts);

        RadioButton radioRomantic = view.findViewById(R.id.radioRomantic);
        RadioButton radioFriend = view.findViewById(R.id.radioFriend);

        RadioButton radioMale = view.findViewById(R.id.radioMale);
        RadioButton radioFemale = view.findViewById(R.id.radioFemale);

        CheckBox checkIT = view.findViewById(R.id.checkIT);
        CheckBox checkMusic = view.findViewById(R.id.checkMusic);
        CheckBox checkSport = view.findViewById(R.id.checkSport);
        CheckBox checkGames = view.findViewById(R.id.checkGames);
        CheckBox checkReading = view.findViewById(R.id.checkReading);

        saveButton.setOnClickListener(v -> {
            uploadImage();

            FirebaseUtils.pushUser(
                    editName.getText().toString(),
                    editSurname.getText().toString(),
                    editBio.getText().toString(),
                    editDate.getText().toString(),
                    editContacts.getText().toString(),
                    radioRomantic.isChecked(),
                    radioMale.isChecked(),
                    new Boolean[]{
                            checkIT.isChecked(),
                            checkMusic.isChecked(),
                            checkSport.isChecked(),
                            checkGames.isChecked(),
                            checkReading.isChecked(),
                    }
            );

            Log.d("dev_DB_status", "db_feed - OK");
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                SharedPreferences mPrefs = getActivity().getSharedPreferences("profilePrefs", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String json = mPrefs.getString("UserSettings", "no data");
                Log.d("dev_prof", json);
                if (!json.equals("no data")) {
                    AdvancedUserModel userSettings = gson.fromJson(json, AdvancedUserModel.class);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editName.setText(userSettings.getName());
                            editSurname.setText(userSettings.getSurname());
                            if(userSettings.getBirthdate() != null) {
                                editDate.setText(userSettings.getBirthdate());
                            }
                            editContacts.setText(userSettings.getContacts());
                            editBio.setText(userSettings.getBio());

                            radioRomantic.setChecked(userSettings.getRomanticSearch());
                            radioFriend.setChecked(!userSettings.getRomanticSearch());

                            radioMale.setChecked(userSettings.getGender());
                            radioFemale.setChecked(!userSettings.getGender());

                            ArrayList<Boolean> interests = userSettings.getInterests().getInterests();
                            checkIT.setChecked(interests.get(0));
                            checkMusic.setChecked(interests.get(1));
                            checkSport.setChecked(interests.get(2));
                            checkGames.setChecked(interests.get(3));
                            checkReading.setChecked(interests.get(4));

                        }
                    });
                }
            }
        }).start();




        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        // TODO: catch signOut error if raised
        Button logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(inflater.getContext(), AuthActivity.class);
            startActivity(intent);
        });

        //DatePicker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a birthdate");
        final MaterialDatePicker datePicker = builder.build();
        //TextInputEditText editDate = view.findViewById(R.id.editTextDate);
        Button datePickBtn = view.findViewById(R.id.datePick);

        datePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getFragmentManager(),"DATE_PICKER");
            }
        });

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                long date_utc = Long.valueOf(datePicker.getSelection().toString());
                Date date = new Date();
                date.setTime(date_utc);
                String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
                editDate.setText(formattedDate);
            }
        });

        return view;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        // filePatch.class == Uri
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("users/" + FirebaseAuth.getInstance().getUid() + "/avatar");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Log.d("dev_img_upload", "uploaded");
                            Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.d("dev_img_upload_uri", filePath.getPath());
                            Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }
}
