package com.example.dogwalk.Adapters;

import android.widget.BaseAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.dogwalk.Backend.Objects.DayStatObject;
import com.example.dogwalk.Backend.Objects.DogObject;
import com.example.dogwalk.Backend.Objects.StatsObject;
import com.example.dogwalk.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class StatsFoodAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<DayStatObject> stats;

    public StatsFoodAdapter(Context ctext, List<DayStatObject> list_stats) {
        context = ctext;
        stats = new ArrayList<>();
        for(int i=0;i<list_stats.size();i++){
            if (list_stats.get(i).getWeight()!=0) stats.add(list_stats.get(i));
        }
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return stats.size();}

    @Override
    public Object getItem(int position) {
        return stats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference= storage.getReference();

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.statistics_food, parent, false);
        }

        DayStatObject stat = getStat(position);

        TextView textFood = view.findViewById(R.id.foodWeight);
        ImageView img_food = view.findViewById(R.id.food);
        //TextView textWalk = view.findViewById(R.id.walkTime);
       // ImageView img_walk = view.findViewById(R.id.walk);

        textFood.setText(stat.getWeight()+"");
        //textWalk.setText(stat.getTime());

//        TextView text_date = view.findViewById(R.id.date);
//        TextView text_food = view.findViewById(R.id.foodCount);
//        TextView text_walk = view.findViewById(R.id.walkCount);
//        ImageView img_food = view.findViewById(R.id.food);
//        ImageView img_walk = view.findViewById(R.id.walk);
//
//        text_date.setText(stat.getDate().substring(3));
//        text_food.setText(Integer.toString(stat.getFood()));
//        text_walk.setText(Integer.toString(stat.getWalk()));

        return view;
    }

    DayStatObject getStat(int position) {
        return ((DayStatObject) getItem(position));
    }
}