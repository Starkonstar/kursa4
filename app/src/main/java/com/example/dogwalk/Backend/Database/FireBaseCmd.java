package com.example.dogwalk.Backend.Database;

import static com.google.android.gms.tasks.Tasks.await;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.dogwalk.Backend.Objects.DayStatObject;
import com.example.dogwalk.Backend.Objects.DogObject;
import com.example.dogwalk.Backend.Objects.StatsObject;
import com.example.dogwalk.Fragments.ChangeDogFragment;
import com.example.dogwalk.MainMenu;
import com.example.dogwalk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class FireBaseCmd {
    //public static int i = 1;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void AddDog(DogObject dog){
        Date currentTime = Calendar.getInstance().getTime();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Map<String, Object> dogStats = new HashMap<>();
        String[] date_splitted = currentTime.toString().split(" ");
        Map<String, Object> dogMap = new HashMap<>();
        Map<String,Object> dogDayStats = new HashMap<>();
        assert currentUser != null;
        dogMap.put("name", dog.getName());
        dogMap.put("breed", dog.getBreed());
        dogMap.put("age", dog.getAge());
        dogMap.put("id", dog.getId());
        dogMap.put("food", Integer.toString(0));
        dogMap.put("walk", Integer.toString(0));
        dogMap.put("date", currentTime.toString());
        dogStats.put("date",date_splitted[0]+date_splitted[1]+date_splitted[2]);
        dogStats.put("food", dog.getFoodCounter());
        dogStats.put("walk", dog.getWalkCounter());
        dogDayStats.put("food weight",0+"");
        dogDayStats.put("walk time",0+"");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId())
                .set(dogMap).addOnSuccessListener(aVoid -> {});//Exception
        db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId()).collection("Stats")
                .document(date_splitted[0]+date_splitted[1]+date_splitted[2]).set(dogStats).addOnSuccessListener(aVoid -> {});//Exception
    }

    public void ChangeDog(DogObject dog){
        Date currentTime = Calendar.getInstance().getTime();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Map<String, Object> dogMap = new HashMap<>();
        Map<String, Object> dogStats = new HashMap<>();
        Map<String,Object> dogDayStats = new HashMap<>();
        String[] date_splitted = currentTime.toString().split(" ");
        assert currentUser != null;
        dogMap.put("name", dog.getName());
        dogMap.put("breed", dog.getBreed());
        dogMap.put("age", dog.getAge());
        dogMap.put("id", dog.getId());
        dogMap.put("food", dog.getFoodCounter());
        dogMap.put("walk", dog.getWalkCounter());
        dogMap.put("date", currentTime.toString());
        dogStats.put("date",date_splitted[0]+date_splitted[1]+date_splitted[2]);
        dogStats.put("food", dog.getFoodCounter());
        dogStats.put("walk", dog.getWalkCounter());
        //dogDayStats.put("food weight",dog.stats.get(dog.stats.size()-1).dayStats.get(dog.stats.get(dog.stats.size()-1).dayStats.size()-1).getWeight());
        //dogDayStats.put("walk time",dog.stats.get(dog.stats.size()-1).dayStats.get(dog.stats.get(dog.stats.size()-1).dayStats.size()-1).getTime());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId())
                .update(dogMap).addOnSuccessListener(aVoid -> {});//Exception
        db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId()).collection("Stats")
                .document(date_splitted[0]+date_splitted[1]+date_splitted[2]).set(dogStats).addOnSuccessListener(aVoid -> {});//Exception
        //db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId()).collection("Stats")
                //.document(date_splitted[0]+date_splitted[1]+date_splitted[2]).collection("DayStats").document(currentTime.toString()).set(dogDayStats).addOnSuccessListener(aVoid -> {});
    }

    public void AddChange(DogObject dog){
        Date currentTime = Calendar.getInstance().getTime();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Map<String, Object> dogMap = new HashMap<>();
        Map<String, Object> dogStats = new HashMap<>();
        Map<String,Object> dogDayStats = new HashMap<>();
        String[] date_splitted = currentTime.toString().split(" ");
        assert currentUser != null;
        dogMap.put("name", dog.getName());
        dogMap.put("breed", dog.getBreed());
        dogMap.put("age", dog.getAge());
        dogMap.put("id", dog.getId());
        dogMap.put("food", dog.getFoodCounter());
        dogMap.put("walk", dog.getWalkCounter());
        dogMap.put("date", currentTime.toString());
        dogDayStats.put("food weight",dog.stats.get(dog.stats.size()-1).dayStats.get(dog.stats.get(dog.stats.size()-1).dayStats.size()-1).getWeight());
        dogDayStats.put("walk time",dog.stats.get(dog.stats.size()-1).dayStats.get(dog.stats.get(dog.stats.size()-1).dayStats.size()-1).getTime());
        dogStats.put("date",date_splitted[0]+date_splitted[1]+date_splitted[2]);
        dogStats.put("food", dog.getFoodCounter());
        dogStats.put("walk", dog.getWalkCounter());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId())
                .update(dogMap).addOnSuccessListener(aVoid -> {});
        db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId()).collection("Stats")
                .document(date_splitted[0]+date_splitted[1]+date_splitted[2]).set(dogStats).addOnSuccessListener(aVoid -> {});
        db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId()).collection("Stats")
                .document(date_splitted[0]+date_splitted[1]+date_splitted[2]).collection("DayStats").document(currentTime.toString()).set(dogDayStats).addOnSuccessListener(aVoid -> {});
    }

    public void DeleteDog(String id){

        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(id)
                .delete().addOnSuccessListener(aVoid -> {});//Exception
    }

    public void GetDogStats(DogObject dog){

        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId()).collection("Stats")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //List<Map<String, Object>> dogsToReturn = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            UpdateDogStats(dog, document.getData());
                        }
                    } else {
                        Log.w("", "Error getting documents.", task.getException());
                    }
                });
        for(int i=0;i<dog.stats.size();i++){

            db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs").document(dog.getId()).collection("Stats").document(dog.stats.get(i).getDate())
                    .collection("DayStats").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            //List<Map<String, Object>> dogsToReturn = new ArrayList<>();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                               // UpdateDogStats2(dog, document.getData());
                            }
                        } else {
                            Log.w("", "Error getting documents.", task.getException());
                        }
                    });
        }

    }

    public void GetAllDogs(List<DogObject> dogs){
        try {

            FirebaseUser currentUser = mAuth.getCurrentUser();
            assert currentUser != null;
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Users").document(Objects.requireNonNull(currentUser.getEmail())).collection("Dogs")
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //List<Map<String, Object>> dogsToReturn = new ArrayList<>();
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        UpdateList(dogs, document.getData());
                    }
                    WriteList(dogs);
                } else {
                    Log.w("", "Error getting documents.", task.getException());
                }
            });
        }
        catch(Exception ignored){
        }
    }

    public void UpdateDogStats(DogObject dog ,Map<String, Object> stats) {
        boolean update = true;
        for (int i = 0;i < dog.getStats().size();i++){
            if(dog.getStats().get(i).getDate().equals(stats.get("date").toString())){
                dog.getStats().get(i).setDate(stats.get("date").toString());
                dog.getStats().get(i).setFood(Integer.parseInt(stats.get("food").toString()));
                dog.getStats().get(i).setWalk(Integer.parseInt(stats.get("walk").toString()));
                //dog.getStats().get(i).setDayStats();
                update = false;
            }
        }
        if(update){dog.getStats().add(new StatsObject(Integer.parseInt(stats.get("walk").toString()),Integer.parseInt(stats.get("food").toString()),stats.get("date").toString()));}
    }

    public void UpdateDogStats2(DogObject dog ,Map<String, Object> stats) {
        boolean update = true;
        for (int i = 0;i < dog.getStats().size();i++){
            //Log.d("RRR",stats.get("food weight").toString());
            //Log.d("RRR",stats.get("date")+"");
            //if(dog.getStats().get(i).getDate().equals(stats.get("date").toString())){
                dog.getStats().get(i).dayStats.add(new DayStatObject(Integer.parseInt(stats.get("food weight").toString()),stats.get("walk time").toString()));
                //dog.getStats().get(i).setDayStats();
                update = false;
            //}
        }
        //if(update){dog.getStats().add(new StatsObject(Integer.parseInt(stats.get("walk").toString()),Integer.parseInt(stats.get("food").toString()),stats.get("date").toString()));}
    }

    public void UpdateList(List<DogObject> list ,Map<String, Object> obj){
        boolean update = true;
        Date currentTime = Calendar.getInstance().getTime();
        String[] timeString = currentTime.toString().split(" ");

        DogObject dog = new DogObject(obj.get("name").toString(),obj.get("age").toString(),
                obj.get("breed").toString(),obj.get("id").toString());

        //Ежедневное обнуление прогулок/кормежки
        if(!timeString[2].equals(obj.get("date").toString().split(" ")[2])||(!timeString[1].equals(obj.get("date").toString().split(" ")[1]))){
            Log.d("Change time ",  timeString[2] + " / " + obj.get("date").toString().split(" ")[2]);
            Log.d("Change date ",  timeString[1] + " / " + obj.get("date").toString().split(" ")[1]);
            dog.setWalkCounter(Integer.parseInt(Integer.toString(0)));
            dog.setFoodCounter(Integer.parseInt(Integer.toString(0)));
            ChangeDog(dog);
        }
        else {
            dog.setWalkCounter(Integer.parseInt(obj.get("walk").toString()));
            dog.setFoodCounter(Integer.parseInt(obj.get("food").toString()));
        }

        for(int i = 0;i < list.size();i++)
            if (list.get(i).getId().equals(dog.getId())) {
                list.get(i).setName(dog.getName());
                list.get(i).setAge(dog.getAge());
                list.get(i).setBreed(dog.getBreed());
                list.get(i).setFoodCounter(dog.getFoodCounter());
                list.get(i).setWalkCounter(dog.getWalkCounter());
                update = false;
            }
        if(update){list.add(dog);}

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference= storage.getReference();
        StorageReference ref = storageReference.child("images/"+ obj.get("id").toString());
        ref.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        for(int i = 0;i < list.size();i++)
                            if (list.get(i).getId().equals(dog.getId())) {
                                dog.setUri(uri);
                                list.get(i).setUri(dog.getUri());
                            }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    public void WriteList(List<DogObject> dogs){
        Log.d(" Dogs to return ", dogs.toString());
    }
}