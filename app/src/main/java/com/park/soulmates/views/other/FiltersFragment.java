package com.park.soulmates.views.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.park.soulmates.R;

public class FiltersFragment extends Fragment {
    private Switch distanceSw;
    private SeekBar distanceSeek;
    private Switch nullDistanceSw;
    private Switch genderSw;
    private TextView distEcho;
    //private View state;


    public FiltersFragment(){

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
        distanceSw = view.findViewById(R.id.distanceFilterSwitch);
        distanceSeek = view.findViewById(R.id.DistanceFilterBar);
        nullDistanceSw = view.findViewById(R.id.distanceFilterNullSwitch);
        genderSw = view.findViewById(R.id.genderFilterSwitch);
        distEcho = view.findViewById(R.id.distEcho);

        SharedPreferences prefs = getActivity().getSharedPreferences("customPrefs", Context.MODE_PRIVATE);
        Boolean distanceOn = prefs.getBoolean("distanceOn", false);
        Boolean distanceKnown = prefs.getBoolean("distanceKnown", false);
        Boolean oppositeGender = prefs.getBoolean("oppositeGender", false);
        Integer distanceVal = prefs.getInt("distanceVal", 1);

        distanceSw.setChecked(distanceOn);
        //distanceSw.setChecked(false);
        distanceSeek.setProgress(distanceVal);
        nullDistanceSw.setChecked(distanceKnown);
        genderSw.setChecked(oppositeGender);
        distEcho.setText(String.valueOf(distanceVal*10) + " km");

        distanceSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                Integer distanceVal2 = distanceSeek.getProgress();
                distEcho.setText(String.valueOf(distanceVal2*10) + " km");

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
        editor.putBoolean("distanceOn", distanceSw.isChecked());
        editor.putBoolean("distanceKnown", nullDistanceSw.isChecked());
        editor.putBoolean("oppositeGender", genderSw.isChecked());
        editor.putInt("distanceVal", distanceSeek.getProgress());
        editor.apply();
    }
}
