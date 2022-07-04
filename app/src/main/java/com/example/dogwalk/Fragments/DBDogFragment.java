package com.example.dogwalk.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dogwalk.Adapters.DBDogAdapter;
import com.example.dogwalk.Adapters.DogAdapter;
import com.example.dogwalk.Backend.Objects.DogObject;
import com.example.dogwalk.R;

import java.util.ArrayList;
import java.util.List;

public class DBDogFragment extends Fragment {
    public static DBDogFragment newInstance() {
        return new DBDogFragment();
    }
    public ListView list;
    public List<DogObject> dogs = new ArrayList<>();
    public DBDogAdapter adapter;
    public View root;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.db_pets, container, false);
        list = root.findViewById(R.id.DBList);
        adapter = new DBDogAdapter(this.getContext(), dogs);
        list.setClickable(false);
        return root;
    }

}
