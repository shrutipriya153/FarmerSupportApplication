package com.example.farmersupportapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;
import com.google.firebase.ml.custom.FirebaseCustomLocalModel;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;

import java.util.ArrayList;
import java.util.List;

public class Prediction extends AppCompatActivity {
TextView yesno;
int[] soil;
ImageView prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        yesno=findViewById(R.id.yesno);
        Intent intent = getIntent();
        Bundle extras=intent.getExtras();
        soil=new int[3];
        soil[0]=intent.getIntExtra("soil1",0);
        soil[1]=intent.getIntExtra("soil2",0);
        soil[2]=intent.getIntExtra("soil3",0);
        for(int i:soil){
            Log.d("bdja","prediction "+i);
        }

        prev=findViewById(R.id.prev);
        //Toast.makeText(Prediction.this, soil, Toast.LENGTH_LONG).show();
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Prediction.this,MainActivity.class));
                Prediction.this.finish();
            }
        });
        try {
            loadModel();
        } catch (FirebaseMLException e) {
            e.printStackTrace();
        }
    }

    private void populateTextView(float n) {
        if(n==1){
            yesno.setText("The Predicted outcome value is: "+(int)n+"\nYes!!, you can\n Following crop can produce a good result " +
                    "\n1.Cauliflower\n2.Kale\n3.Bean,\n4.Pea\n5.Potato");

        }
        else {
            yesno.setText("The Predicted outcome value is: "+(int)n+"\nSorry, you Can't");
        }
    }
    public void loadModel() throws FirebaseMLException {
        FirebaseModelDownloadConditions.Builder conditionsBuilder =
                new FirebaseModelDownloadConditions.Builder().requireWifi();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Enable advanced conditions on Android Nougat and newer.
            conditionsBuilder = conditionsBuilder
                    .requireCharging()
                    .requireDeviceIdle();
        }
        FirebaseModelDownloadConditions conditions = conditionsBuilder.build();
        final FirebaseCustomRemoteModel remoteModel =
                new FirebaseCustomRemoteModel.Builder("minor_hard_coded").build();


        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Success.
                        Log.d("jcbakjbsjksd","Dowloaded");
                    }
                });
        final FirebaseCustomLocalModel localModel = new FirebaseCustomLocalModel.Builder()
                .setAssetFilePath("minor_hard_coded.tflite")
                .build();
        Log.d("naskj","Remote and locqal build");
         FirebaseModelInterpreter interpreter = null;
        try {
            FirebaseModelInterpreterOptions options =
                    new FirebaseModelInterpreterOptions.Builder(localModel).build();
            interpreter = FirebaseModelInterpreter.getInstance(options);
        } catch (FirebaseMLException e) {
           Log.d("jnasjk",e.getMessage());
        }
       /* FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isDownloaded) {
                        FirebaseModelInterpreterOptions options;
                        if (isDownloaded) {
                            options = new FirebaseModelInterpreterOptions.Builder(remoteModel).build();
                        } else {
                            options = new FirebaseModelInterpreterOptions.Builder(localModel).build();
                        }
                        try {
                            interpreter = FirebaseModelInterpreter.getInstance(options);
                        } catch (FirebaseMLException e) {
                            Log.d("dbskjsa",e.getMessage());
                        }
                        // ...
                    }
                });*/
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // Download complete. Depending on your app, you could enable
                        // the ML feature, or switch from the local model to the remote
                        // model, etc.
                        Log.d("kdjbjkas","akjbcjasi");
                    }
                });
       // List<Double> l=new ArrayList<>();
        float l[][][]=new float[1][7][1];
        l[0][0][0]= (float) 12.78;
        l[0][1][0]=(float)0.00;
        l[0][2][0]=(float)63.00;
        l[0][3][0]=(float)56.00;
        l[0][4][0]=(float)soil[0];
        l[0][5][0]=(float)soil[1];
        l[0][6][0]=(float)soil[2];


        FirebaseModelInputOutputOptions inputOutputOptions =
                new FirebaseModelInputOutputOptions.Builder()
                        .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1,7,1})
                        .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1,1})
                        .build();
        FirebaseModelInputs inputs = new FirebaseModelInputs.Builder()
                .add(l)  // add() as many input arrays as your model requires
                .build();
        Log.d("fdn","inputoutputoption");
        interpreter.run(inputs, inputOutputOptions)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseModelOutputs>() {
                            @Override
                            public void onSuccess(FirebaseModelOutputs result) {
                                // ...
                                float n[][]= result.getOutput(0);
                                float out[]=n[0];
                                for(int i=0;i<n.length;i++){
                                    for(int j=0;j<n[i].length;j++){
                                        Log.d("djbj",String.valueOf(n[i][j]));
                                    }
                                }
                                Log.d("ddgv",String.valueOf(out[0]));
                                populateTextView(out[0]);

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                                Log.d("sabd",e.getMessage());
                            }
                        });
        Log.d("adsa","afterinputoutputoption");

    }


    public void again(View view) {
        startActivity(new Intent(Prediction.this,MainActivity.class));
    }
}
