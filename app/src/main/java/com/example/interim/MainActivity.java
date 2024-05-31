package com.example.interim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button voir_les_offres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        voir_les_offres = findViewById(R.id.voir_offres);
        voir_les_offres.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RechercheActivity.class);
            startActivity(intent);
        });

    }
}