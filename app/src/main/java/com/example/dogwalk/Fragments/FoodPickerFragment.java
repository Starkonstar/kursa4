package com.example.dogwalk.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dogwalk.Backend.Objects.DogObject;
import com.example.dogwalk.R;

public class FoodPickerFragment extends Fragment {
    public static FoodPickerFragment newInstance() {
        return new FoodPickerFragment();
    }
    public DogObject currentDog;
//    public String time;
    public int weight;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //MainMenu.newDogImg = true;

        View root = inflater.inflate(R.layout.food_amount, container, false);
        //TextView date = root.findViewById(R.id.DefiniteDate);
        //date.setText(current.getDate().substring(3));
//        list = root.findViewById(R.id.dayStatistics);
//
//        adapter = new StatsAdapter(this.getContext(), current.getStats());
//        list.setAdapter(adapter);
//        list.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
//        list.setClickable(false);

        return root;
    }
}
