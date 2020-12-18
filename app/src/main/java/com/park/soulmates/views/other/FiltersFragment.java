package com.park.soulmates.views.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.park.soulmates.R;

public class FiltersFragment extends Fragment {
    private Switch distanceSw;
    private SeekBar distanceSeek;
    private Switch nullDistanceSw;
    private Switch genderSw;
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
