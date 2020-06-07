package com.example.farmersupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class weather extends AppCompatActivity {
ImageView prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        prev=findViewById(R.id.prev);
        //Toast.makeText(Prediction.this, soil, Toast.LENGTH_LONG).show();
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(weather.this,MainActivity.class));
                weather.this.finish();
            }
        });
    }
}
