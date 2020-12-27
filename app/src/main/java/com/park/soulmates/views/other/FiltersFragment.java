package com.park.soulmates.views.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.park.soulmates.R;

public class FiltersFragment extends Fragment {
    private SwitchMaterial mDistanceSw;
    private SeekBar mDistanceSeek;
    private SwitchMaterial mNullDistanceSw;
    private SwitchMaterial mGenderSw;
    private TextView mDistEcho;
    //private View state;

    public FiltersFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);
        mDistanceSw = view.findViewById(R.id.distanceFilterSwitch);
        mDistanceSeek = view.findViewById(R.id.DistanceFilterBar);
        mNullDistanceSw = view.findViewById(R.id.distanceFilterNullSwitch);
        mGenderSw = view.findViewById(R.id.genderFilterSwitch);
        mDistEcho = view.findViewById(R.id.distEcho);

        SharedPreferences prefs = getActivity().getSharedPreferences("customPrefs", Context.MODE_PRIVATE);
        boolean distanceOn = prefs.getBoolean("distanceOn", false);
        boolean distanceKnown = prefs.getBoolean("distanceKnown", false);
        boolean oppositeGender = prefs.getBoolean("oppositeGender", false);
        int distanceVal = prefs.getInt("distanceVal", 1);

        mDistanceSw.setChecked(distanceOn);
        //distanceSw.setChecked(false);
        mDistanceSeek.setProgress(distanceVal);
        mNullDistanceSw.setChecked(distanceKnown);
        mGenderSw.setChecked(oppositeGender);
        mDistEcho.setText(distanceVal * 10 + " km");

        mDistanceSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                int distanceVal2 = mDistanceSeek.getProgress();
                mDistEcho.setText(distanceVal2 * 10 + " km");

            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences prefs = getActivity().getSharedPreferences("customPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putString(APP_PREFERENCES_NAME, strNickName);
        editor.putBoolean("distanceOn", mDistanceSw.isChecked());
        editor.putBoolean("distanceKnown", mNullDistanceSw.isChecked());
        editor.putBoolean("oppositeGender", mGenderSw.isChecked());
        editor.putInt("distanceVal", mDistanceSeek.getProgress());
        editor.apply();
    }
}
