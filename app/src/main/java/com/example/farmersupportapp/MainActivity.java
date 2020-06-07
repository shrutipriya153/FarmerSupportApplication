package com.example.farmersupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
Spinner spinner;
Button predict,weather,soil;
ImageView prev;
int[] array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        array=new int[3];
        prev=findViewById(R.id.prev);
        predict=findViewById(R.id.predict);
        weather=findViewById(R.id.weather);
        soil=findViewById(R.id.soil);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.soil));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
       predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Array",String.valueOf(array.length));
                if(checker==0)
                    Toast.makeText(MainActivity.this, "Please Choose any soil", Toast.LENGTH_SHORT).show();
                else {
                    //Toast.makeText(MainActivity.this, soil, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,Prediction.class);
                    intent.putExtra("soil1",array[0]);
                    intent.putExtra("soil2",array[1]);
                    intent.putExtra("soil3",array[2]);

                    startActivity(intent);
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Splash.class));
                MainActivity.this.finish();
            }
        });
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, com.example.farmersupportapp.weather.class));
                MainActivity.this.finish();
            }
        });
        soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, soil.class));
                MainActivity.this.finish();

            }
        });

    }
int checker;
    public void checked_array(View view) {
        boolean checked=((CheckBox)view).isChecked();

        switch (view.getId()){
            case R.id.alkaline:
                if(checked) {
                    array[0] = 1;
                    checker=1;
                }
                break;
            case R.id.chalky:
                if(checked) {
                    array[1] = 1;
                    checker=1;
                }
                break;
            case R.id.clay:
                if(checked){
                    array[2]=1;
                    checker=1;

                }

                break;
            case R.id.none:
                checker=1;
                break;
        }
        Log.d("Array",String.valueOf(array[0]));
        Log.d("Array",String.valueOf(array[1]));
        Log.d("Array",String.valueOf(array[2]));
        Log.d("Array","-----------------------");
    }
}
