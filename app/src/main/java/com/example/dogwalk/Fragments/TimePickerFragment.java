package com.example.dogwalk.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dogwalk.Backend.Objects.DogObject;
import com.example.dogwalk.MainMenu;
import com.example.dogwalk.R;

public class TimePickerFragment extends Fragment {
    public static TimePickerFragment newInstance() {
        return new TimePickerFragment();
    }
    public DogObject currentDog;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //MainMenu.newDogImg = true;

        View root = inflater.inflate(R.layout.time_picker, container, false);
//        TextView date = root.findViewById(R.id.DefiniteDate);
//        date.setText(current.getDate().substring(3));
//        list = root.findViewById(R.id.dayStatistics);
//
//        adapter = new StatsAdapter(this.getContext(), current.getStats());
//        list.setAdapter(adapter);
//        list.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
//        list.setClickable(false);

        return root;
    }
}
