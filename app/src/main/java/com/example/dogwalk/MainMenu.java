package com.example.dogwalk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.dogwalk.Backend.Database.FireBaseCmd;
import com.example.dogwalk.Backend.Objects.DayStatObject;
import com.example.dogwalk.Backend.Objects.DogObject;
import com.example.dogwalk.Backend.Objects.StatsObject;
import com.example.dogwalk.Fragments.AddDogFragment;
import com.example.dogwalk.Fragments.ChangeDogFragment;
import com.example.dogwalk.Fragments.DBDogFragment;
import com.example.dogwalk.Fragments.FoodPickerFragment;
import com.example.dogwalk.Fragments.MainMenuFragment;
import com.example.dogwalk.Fragments.StatisticInDayFragment;
import com.example.dogwalk.Fragments.StatisticsFragment;
import com.example.dogwalk.Fragments.TimePickerFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainMenu extends FragmentActivity {
    public List<DogObject> dogs = new ArrayList<>();
    FireBaseCmd cmd = new FireBaseCmd();

    private Handler mainHandler = new Handler();
    public static boolean pauseThread = true;
    private boolean stopThread = false;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startThread();
        //dogs = new ArrayList<DogObject>();
        //cmd.GetAllDogs(dogs);
        //for(int i = 0 ; i < 5 ; i++){
            //dogs.add(new DogObject("first" , "1", "test"," 1 "));
            //dogs.add(new DogObject("lada" , "3", "corgi"," 2 "));
        //}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainMenuFragment menuFragment = new MainMenuFragment();
        menuFragment.dogs = dogs;
        fragmentTransaction.replace(R.id.FragmentActivity,menuFragment );
        fragmentTransaction.commit();

        pauseThread = false;
    }
    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainMenuFragment menuFragment = new MainMenuFragment();
        menuFragment.dogs = dogs;
        fragmentTransaction.replace(R.id.FragmentActivity,menuFragment );
        fragmentTransaction.commit();

        pauseThread = false;
        //
    }



    public void BackToDog_Time(View view) {
        pauseThread = true;
        TimePickerFragment nowObj = (TimePickerFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        ChangeDogFragment fragment = new ChangeDogFragment();
        fragment.currentDog = nowObj.currentDog;

        fragment.name = nowObj.currentDog.getName();
        fragment.age = nowObj.currentDog.getAge();
        fragment.breed = nowObj.currentDog.getBreed();
        fragment.id = nowObj.currentDog.getId();
        fragment.uri = nowObj.currentDog.getUri();
        fragment.foodCounter = nowObj.currentDog.getFoodCounter();
        fragment.walkCounter = nowObj.currentDog.getWalkCounter();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
        //
    }

    public void BackToDog_Food(View view) {
        pauseThread = true;
        FoodPickerFragment nowObj = (FoodPickerFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        ChangeDogFragment fragment = new ChangeDogFragment();
        fragment.currentDog = nowObj.currentDog;

        fragment.name = nowObj.currentDog.getName();
        fragment.age = nowObj.currentDog.getAge();
        fragment.breed = nowObj.currentDog.getBreed();
        fragment.id = nowObj.currentDog.getId();
        fragment.uri = nowObj.currentDog.getUri();
        fragment.foodCounter = nowObj.currentDog.getFoodCounter();
        fragment.walkCounter = nowObj.currentDog.getWalkCounter();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
        //
    }

    private final int Pick_image = 1;

    public static boolean newDogImg = false;

    public void DBonClick(View view){
        pauseThread = true;
        DBDogFragment fragment = new DBDogFragment();
        MainMenuFragment nowObj = (MainMenuFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        fragment.dogs = nowObj.dogs;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
    }
    public void DBBackClick(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainMenuFragment menuFragment = new MainMenuFragment();
        menuFragment.dogs = dogs;
        fragmentTransaction.replace(R.id.FragmentActivity,menuFragment );
        fragmentTransaction.commit();

        pauseThread = false;
    }

    public void clickAddImg(View view) {

                //???????????????? ?????????????????????? ?????????????? ?????? ???????????? ?????????????????????? ?? ?????????????? Intent.ACTION_PICK:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //?????? ???????????????????? ???????????????? - image:
                photoPickerIntent.setType("image/*");
                //?????????????????? ?????????????? ?? ?????????????????? ?????????????????? ???????????????????? ?? ???????? ???????????????????? ???? ??????????????????????:
                startActivityForResult(photoPickerIntent, Pick_image);
        //???????????????????????? ?????????????????? ???????????? ?? ??????????????:
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {
                        //???????????????? URI ??????????????????????, ?????????????????????? ?????? ?? Bitmap
                        //???????????? ?? ???????????????????? ?? ???????????????? ImageView ???????????? ????????????????????:
                        final Uri imageUri = imageReturnedIntent.getData();
                        filePath = imageUri;
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        ImageView img = findViewById(R.id.dogImage);
                        Glide.with(this).load(imageUri).placeholder(R.drawable.love).diskCacheStrategy(DiskCacheStrategy.ALL).apply(RequestOptions.circleCropTransform()).into(img);
                        String id = null;
                        if(!newDogImg){
                            ChangeDogFragment nowObj = (ChangeDogFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
                            if (nowObj != null) {
                                id = nowObj.id;
                                if (id != null) {
                                    uploadImage(id);
                                }
                            }
                        }
                        else{
                            AddDogFragment nowObj = (AddDogFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
                            if (nowObj != null) {
                                nowObj.uri = imageUri;
                            }
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }}
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }
    //?????????????? ???????????? ?? ??????????
    public void startThread() {
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }
    class ExampleRunnable implements Runnable {
        ExampleRunnable() {
        }
        @Override
        public void run() {
            for (int i = 0; i > -1; i++) {
                Log.d(TAG, "uiDatabaseThreadRunning: " + isInternetAvailable());
                if (!stopThread) {
                    if (!pauseThread) {
                        int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //dogs.add(new DogObject("foo" , Integer.toString(finalI), "dolmatin"," 1 "));
                                UpdateList();
                            }
                        });
                        Log.d(TAG, "uiDatabaseThreadRunning: " + i);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "uiDatabaseThreadPaused: " + i);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{i=-10;}
            }
        }
    }
    ////


/*
    public void UpdateDogList(View view) {
        cmd.GetAllDogs(dogs);
        MainMenuFragment menuFragment = (MainMenuFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        if(menuFragment!=null) {
            Toast toast = Toast.makeText(MainMenu.this , "Updated!" , Toast.LENGTH_SHORT );
            ListView listView = (ListView) menuFragment.list;
            //listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            DogAdapter adapter = new DogAdapter(this, dogs);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            toast.show();
        }

        if(!pauseThread) {
            Toast toast = Toast.makeText(MainMenu.this, "PauseThread!", Toast.LENGTH_SHORT);
            toast.show();
            pauseThread = true;
        }
        else{
            Toast toast = Toast.makeText(MainMenu.this, "ResumeThread!", Toast.LENGTH_SHORT);
            toast.show();
            pauseThread = false;
        }
        stopThread = true;

    }
    */

    public void UpdateList() {
        cmd.GetAllDogs(dogs);
        for (int i = 0; i < dogs.size(); i++)
            cmd.GetDogStats(dogs.get(i));

        //SERVICE LAYER
        TextView layout_controller = findViewById(R.id.layout_controller_important_NODELETE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("service_java_constants").document("java_com_android_constantAppCompat").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        layout_controller.setText(document.get("jdk_path_constants_service").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        //


        MainMenuFragment menuFragment = (MainMenuFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        if(menuFragment!=null) {
            //Toast toast = Toast.makeText(MainMenu.this , "Updated!" , Toast.LENGTH_SHORT );
            //ListView listView = (ListView)menuFragment.list;
            //listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
            //DogAdapter adapter = new DogAdapter(this, dogs);
            //listView.setAdapter(adapter);
            if (dogs.size() == 0) {
                menuFragment.root.findViewById(R.id.linear_layout).setBackgroundResource(R.drawable.empty_background);
            } else {
                menuFragment.root.findViewById(R.id.linear_layout).setBackgroundResource(R.drawable.full_background);
            }
            menuFragment.adapter.notifyDataSetChanged();
            //toast.show();
        }
    }

    public void newUpdateList() {
        cmd.GetAllDogs(dogs);
        for (int i = 0; i < dogs.size(); i++)
            cmd.GetDogStats(dogs.get(i));

        //SERVICE LAYER
        TextView layout_controller = findViewById(R.id.layout_controller_important_NODELETE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("service_java_constants").document("java_com_android_constantAppCompat").get();
        //


//        MainMenuFragment menuFragment = (MainMenuFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
//        if(menuFragment!=null) {
//            //Toast toast = Toast.makeText(MainMenu.this , "Updated!" , Toast.LENGTH_SHORT );
//            //ListView listView = (ListView)menuFragment.list;
//            //listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
//            //DogAdapter adapter = new DogAdapter(this, dogs);
//            //listView.setAdapter(adapter);
//            if (dogs.size() == 0) {
//                menuFragment.root.findViewById(R.id.linear_layout).setBackgroundResource(R.drawable.empty_background);
//            } else {
//                menuFragment.root.findViewById(R.id.linear_layout).setBackgroundResource(R.drawable.full_background);
//            }
//            menuFragment.adapter.notifyDataSetChanged();
//            //toast.show();
//        }
    }

    /*public void SettingsClick(View view) {
        Button addDog = findViewById(R.id.addDogButton);
        Button logOut = findViewById(R.id.logoutButton);
        if(addDog.getVisibility()==View.VISIBLE) {
            addDog.setVisibility(View.INVISIBLE);
            logOut.setVisibility(View.INVISIBLE);
        }
        else{
            addDog.setVisibility(View.VISIBLE);
            logOut.setVisibility(View.VISIBLE);
        }
    }*/

    @SuppressLint("SetTextI18n")
    public void PlusFoodClick(View view) {
        ChangeDogFragment nowObj = (ChangeDogFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
//        int foodCounter = nowObj.foodCounter;
//        foodCounter++;
//        nowObj.foodCounter++;
//        TextView label;
//        label = nowObj.foodCounterLabel;
//        label.setText(Integer.toString(foodCounter));

        pauseThread = true;
        FoodPickerFragment fragment = new FoodPickerFragment();
       // fragment.time = nowObj.time;
        fragment.weight = nowObj.weight;
        fragment.currentDog = nowObj.currentDog;
        fragment.currentDog.foodCounter = nowObj.foodCounter;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
    }
    @SuppressLint("SetTextI18n")
    public void MinusFoodClick(View view) {
        ChangeDogFragment nowObj = (ChangeDogFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        int foodCounter = nowObj.foodCounter;
        if(foodCounter>0) {
            foodCounter--;
            nowObj.foodCounter--;
            TextView label;
            label = nowObj.foodCounterLabel;
            label.setText(Integer.toString(foodCounter));
        }
        else{
            foodCounter=0;
            nowObj.foodCounter=0;
            TextView label;
            label = nowObj.foodCounterLabel;
            label.setText(Integer.toString(foodCounter));
        }
    }

    public void AddFood(View view){

        FoodPickerFragment nowObj = (FoodPickerFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        ChangeDogFragment fragment = new ChangeDogFragment();
        EditText textView = findViewById(R.id.amount_of_food);
        if (isNumeric(textView.getText().toString())) {
            fragment.weight = Integer.parseInt(textView.getText().toString());
        }
        else{
            textView.setError("???????????????????? ?????????? ??????????");
            return;
        }
        nowObj.currentDog.foodCounter++;
        //fragment.time = nowObj.time;
        fragment.currentDog = nowObj.currentDog;
        fragment.name = nowObj.currentDog.getName();
        fragment.age = nowObj.currentDog.getAge();
        fragment.breed = nowObj.currentDog.getBreed();
        fragment.id = nowObj.currentDog.getId();
        fragment.uri = nowObj.currentDog.getUri();
        fragment.walkCounter = nowObj.currentDog.getWalkCounter();
        fragment.foodCounter = nowObj.currentDog.getFoodCounter();
        nowObj.weight=Integer.parseInt(textView.getText().toString());
        AddChangeDogFood(view);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
        newUpdateList();
        //pauseThread = false;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @SuppressLint("SetTextI18n")
    public void PlusWalkClick(View view) {
        ChangeDogFragment nowObj = (ChangeDogFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        //int walkCounter = nowObj.walkCounter;
        //walkCounter++;
//        nowObj.walkCounter++;
//        TextView label;
//        label = nowObj.walkCounterLabel;
//        label.setText(Integer.toString(nowObj.walkCounter));

        pauseThread = true;
        //nowObj = (ChangeDogFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        TimePickerFragment fragment = new TimePickerFragment();
        //fragment.weight = nowObj.weight;
        fragment.currentDog = nowObj.currentDog;
        fragment.currentDog.walkCounter = nowObj.walkCounter;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
    }
    @SuppressLint("SetTextI18n")
    public void MinusWalkClick(View view) {
        ChangeDogFragment nowObj = (ChangeDogFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        int walkCounter = nowObj.walkCounter;
        if(walkCounter>0) {
            walkCounter--;
            nowObj.walkCounter--;
            TextView label;
            label = nowObj.walkCounterLabel;
            label.setText(Integer.toString(walkCounter));
        }
        else{
            walkCounter=0;
            nowObj.walkCounter=0;
            TextView label;
            label = nowObj.walkCounterLabel;
            label.setText(Integer.toString(walkCounter));
        }
    }

    public void AddWalk(View view){

        TimePickerFragment nowObj = (TimePickerFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        nowObj.currentDog.walkCounter++;
        ChangeDogFragment fragment = new ChangeDogFragment();
        //fragment.weight = nowObj.weight;
        EditText textView = findViewById(R.id.timeAdd);
        fragment.time = textView.getText().toString();
        fragment.currentDog = nowObj.currentDog;
        fragment.name = nowObj.currentDog.getName();
        fragment.age = nowObj.currentDog.getAge();
        fragment.breed = nowObj.currentDog.getBreed();
        fragment.id = nowObj.currentDog.getId();
        fragment.uri = nowObj.currentDog.getUri();
        fragment.walkCounter = nowObj.currentDog.getWalkCounter();
        fragment.foodCounter = nowObj.currentDog.getFoodCounter();
        nowObj.time = fragment.time;
        AddChangeDogTime(view);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
        newUpdateList();
        //pauseThread = false;
    }

    public void AddDogClick(View view)
    {
        pauseThread = true;
        Fragment fragment = new AddDogFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();

    }

    public void StatsClick(View view)
    {
        pauseThread = true;
        ChangeDogFragment nowObj = (ChangeDogFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.currentDog = nowObj.currentDog;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
    }

    public void BackClick(View view){
        pauseThread = true;
        StatisticInDayFragment nowObj = (StatisticInDayFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.currentDog = nowObj.currentDog;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
    }

    public void StatsExitOnClick(View view){
        pauseThread = true;
        StatisticsFragment nowObj = (StatisticsFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        ChangeDogFragment fragment = new ChangeDogFragment();
        fragment.currentDog = nowObj.currentDog;
        fragment.name = nowObj.currentDog.getName();
        fragment.age = nowObj.currentDog.getAge();
        fragment.breed = nowObj.currentDog.getBreed();
        fragment.id = nowObj.currentDog.getId();
        fragment.uri = nowObj.currentDog.getUri();
        fragment.walkCounter = nowObj.currentDog.getWalkCounter();
        fragment.foodCounter = nowObj.currentDog.getFoodCounter();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentActivity, fragment);
        fragmentTransaction.commit();
    }


    public void LogoutClick(View view)
    {
        stopThread = true;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            final Intent intent = new Intent(MainMenu.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void AddChangeDogFood(View view) {
        ProgressDialog dialog = ProgressDialog.show(MainMenu.this, "",
                "????????????????. ????????????????????, ??????????????????...", true);

        FoodPickerFragment nowObj = (FoodPickerFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        //nowObj.currentDog.stats.get(nowObj.currentDog.stats.size()-1).dayStats.add(new DayStatObject(nowObj.weight, "00:00"));

                //???????????????? ???????????? ?? ??????????
                FireBaseCmd cmd = new FireBaseCmd();
                cmd.AddChange(nowObj.currentDog,new DayStatObject(nowObj.weight, "00:00"));
        dialog.dismiss();
    }

    public void AddChangeDogTime(View view) {
        ProgressDialog dialog = ProgressDialog.show(MainMenu.this, "",
                "????????????????. ????????????????????, ??????????????????...", true);

        TimePickerFragment nowObj = (TimePickerFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        //nowObj.currentDog.stats.get(nowObj.currentDog.stats.size()-1).dayStats.add(new DayStatObject(0, nowObj.time));

        //???????????????? ???????????? ?? ??????????
        FireBaseCmd cmd = new FireBaseCmd();
        cmd.AddChange(nowObj.currentDog,new DayStatObject(0, nowObj.time));
        dialog.dismiss();
    }

    public void ChangeDogClick(View view) {
        ProgressDialog dialog = ProgressDialog.show(MainMenu.this, "",
                "????????????????. ????????????????????, ??????????????????...", true);

        ChangeDogFragment nowObj = (ChangeDogFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        MainMenuFragment fragment = new MainMenuFragment();
        //nowObj.currentDog.stats.get(nowObj.currentDog.stats.size()-1).dayStats.add(new DayStatObject(nowObj.weight, nowObj.time));
        fragment.dogs = dogs;
        if(nowObj!=null) {
            if ((nowObj.nameText.getText().toString().length() > 0)
                    && (nowObj.breedText.getText().toString().length() > 0)
                    && (nowObj.ageText.getText().toString().length() > 0)) {
                DogObject newDog = new DogObject(nowObj.nameText.getText().toString()
                        , nowObj.ageText.getText().toString(), nowObj.breedText.getText().toString(), nowObj.id);

                newDog.setUri(nowObj.uri);
                newDog.setFoodCounter(nowObj.foodCounter);
                newDog.setWalkCounter(nowObj.walkCounter);
                newDog.setStats(nowObj.currentDog.stats);

                //???????????????? ???????????? ?? ??????????
                FireBaseCmd cmd = new FireBaseCmd();
                cmd.ChangeDog(newDog);

                for (int i = 0; i < dogs.size(); i++) {
                    if (dogs.get(i).getId().equals(newDog.getId())) {
                        dogs.set(i, newDog);
                    }
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragmentActivity, fragment);
                fragmentTransaction.commit();
                dialog.dismiss();
                pauseThread = false;
            } else {
                dialog.dismiss();
                if (nowObj.nameText.getText().toString().length() == 0)
                    nowObj.nameText.setError("???????????? ???????? ??????");
                if (nowObj.ageText.getText().toString().length() == 0)
                    nowObj.ageText.setError("???????????? ???????? ??????????????");
                if (nowObj.breedText.getText().toString().length() == 0)
                    nowObj.breedText.setError("???????????? ???????? ????????????");
            }
        }
    }

    public void DeleteDogClick(View view) {
        new AlertDialog.Builder(MainMenu.this)
                .setTitle("?????????????? ??????????????")
                .setMessage("???? ??????????????, ?????? ???????????? ?????????????? ???????????????")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ChangeDogFragment nowObj = (ChangeDogFragment)getSupportFragmentManager()
                                .findFragmentById(R.id.FragmentActivity);
                        MainMenuFragment fragment = new MainMenuFragment();
                        fragment.dogs = dogs;
                        if(nowObj!=null) {
                            //???????????????? ???????????? ?? ??????????
                            FireBaseCmd cmd = new FireBaseCmd();
                            cmd.DeleteDog(nowObj.id);

                            for (int i = 0; i < dogs.size(); i++) {
                                if (dogs.get(i).getId().equals(nowObj.id)) {
                                    dogs.remove(i);
                                }
                            }
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.FragmentActivity, fragment);
                            fragmentTransaction.commit();
                            pauseThread = false;
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void CommitDogClick(View view)
    {
        ProgressDialog dialog = ProgressDialog.show(MainMenu.this, "",
                "????????????????. ????????????????????, ??????????????????...", true);
        AddDogFragment nowObj = (AddDogFragment)getSupportFragmentManager().findFragmentById(R.id.FragmentActivity);
        MainMenuFragment fragment = new MainMenuFragment();
        fragment.dogs = dogs;
        if(nowObj!=null) {
            if ((nowObj.name.getText().toString().length() > 0)
                    && (nowObj.breed.getText().toString().length() > 0)
                    && (nowObj.age.getText().toString().length() > 0)) {
                String id = UUID.randomUUID().toString();
                DogObject newDog = new DogObject(nowObj.name.getText().toString()
                        , nowObj.age.getText().toString(), nowObj.breed.getText().toString(), id);

                newDog.setUri(nowObj.uri);
                uploadImage(id);

                //???????????????? ???????????? ???????????? ????????
                FireBaseCmd cmd = new FireBaseCmd();
                cmd.AddDog(newDog);
                dogs.add(newDog);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragmentActivity, fragment);
                fragmentTransaction.commit();
                dialog.dismiss();
                pauseThread = false;
            } else {
                dialog.dismiss();
                if (nowObj.name.getText().toString().length() == 0)
                    nowObj.name.setError("???????????? ???????? ??????");
                if (nowObj.age.getText().toString().length() == 0)
                    nowObj.age.setError("???????????? ???????? ??????????????");
                if (nowObj.breed.getText().toString().length() == 0)
                    nowObj.breed.setError("???????????? ???????? ????????????");
            }
        }

    }

    private static Uri filePath = null;
    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static StorageReference storageReference= storage.getReference();

    public static void uploadImage(String id) {

        if(filePath == null) {
            filePath = Uri.parse("android.resource://"+ R.class.getPackage().getName()+"/"+R.drawable.love);
        }
            StorageReference ref = storageReference.child("images/"+ id);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
        filePath = null;
    }


}