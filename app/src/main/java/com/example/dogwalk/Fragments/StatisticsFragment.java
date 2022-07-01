package com.example.dogwalk.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dogwalk.Adapters.DogAdapter;
import com.example.dogwalk.Adapters.StatsAdapter;
import com.example.dogwalk.Backend.Objects.DogObject;
import com.example.dogwalk.Backend.Objects.StatsObject;
import com.example.dogwalk.MainMenu;
import com.example.dogwalk.R;
import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }
    public ListView list;
    public DogObject currentDog;
    public StatsAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MainMenu.newDogImg = true;

        View root = inflater.inflate(R.layout.statistics, container, false);
        list = root.findViewById(R.id.dayStatistics);

        adapter = new StatsAdapter(this.getContext(), currentDog.getStats());
        list.setAdapter(adapter);
        list.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
        list.setClickable(false);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StatsObject selected = (StatsObject) adapterView.getItemAtPosition(i);
                StatisticInDayFragment fragment = new StatisticInDayFragment();
                fragment.current = selected;
                fragment.currentDog = currentDog;
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragmentActivity, fragment);
                fragmentTransaction.commit();
            }
        });

        return root;
    }
}