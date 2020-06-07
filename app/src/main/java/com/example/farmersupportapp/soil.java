package com.example.farmersupportapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class soil extends AppCompatActivity {
ImageView prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil);
        prev=findViewById(R.id.prev1);
        //Toast.makeText(Prediction.this, soil, Toast.LENGTH_LONG).show();
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(soil.this,MainActivity.class));
                soil.this.finish();
            }
        });
    }
}
