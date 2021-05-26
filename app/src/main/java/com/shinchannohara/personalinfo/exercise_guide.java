package com.shinchannohara.personalinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class exercise_guide extends AppCompatActivity {

    Spinner spinner;
    biceps bicepsaction;
    chest chestaction;
    dietguidemain dietmainpage;
    List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_guide);

        spinner = (Spinner)findViewById(R.id.eg_spinner);

        bicepsaction = new biceps();
        chestaction = new chest();
        dietmainpage = new dietguidemain();

        names = new ArrayList<>();
        names.add("Nutrients");names.add("Weight loss");names.add("Weight gain");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(exercise_guide.this,R.layout.eg_item,names);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
                        selectFragment(dietmainpage);
                        break;
                    }
                    case 1:{
                        selectFragment(bicepsaction);
                        break;
                    }
                    case 2:{
                        selectFragment(chestaction);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.eg_framelayout,dietmainpage);
        fragmentTransaction.commit();
    }

    private void selectFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.eg_framelayout,fragment);
        fragmentTransaction.commit();
    }

}
