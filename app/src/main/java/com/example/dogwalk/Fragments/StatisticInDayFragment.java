package com.example.dogwalk.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dogwalk.Adapters.StatsAdapter;
import com.example.dogwalk.Adapters.StatsFoodAdapter;
import com.example.dogwalk.Adapters.StatsTimeAdapter;
import com.example.dogwalk.Backend.Objects.DogObject;
import com.example.dogwalk.Backend.Objects.StatsObject;
import com.example.dogwalk.MainMenu;
import com.example.dogwalk.R;

public class StatisticInDayFragment extends Fragment {
    public static StatisticInDayFragment newInstance() {
        return new StatisticInDayFragment();
    }
    public ListView list1, list2;
    public StatsObject current;
    public DogObject currentDog;
    public StatsFoodAdapter adapter1;
    public StatsTimeAdapter adapter2;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MainMenu.newDogImg = true;

        View root = inflater.inflate(R.layout.statistics_in_definite_day, container, false);
        TextView date = root.findViewById(R.id.DefiniteDate);
        date.setText(current.getDate().substring(3));
        list1 = root.findViewById(R.id.dayStatisticsFood);
        list2 = root.findViewById(R.id.dayStatisticsWalks);

        adapter1 = new StatsFoodAdapter(this.getContext(), current.getDayStats());
        list1.setAdapter(adapter1);
        list1.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
        list1.setClickable(false);

        adapter2 = new StatsTimeAdapter(this.getContext(), current.getDayStats());
        list2.setAdapter(adapter2);
        list2.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
        list2.setClickable(false);


        return root;
    }
}
