package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class type_utilisateur extends AppCompatActivity {

    private CheckBox checkBoxYes, checkBoxNo;
    private Button buttonValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_utilisateur);

        checkBoxYes = findViewById(R.id.checkbox_yes);
        checkBoxNo = findViewById(R.id.checkbox_no);
        buttonValidate = findViewById(R.id.buttonValidate);

        checkBoxYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxYes.isChecked()) {
                    checkBoxNo.setChecked(false);
                }
            }
        });

        checkBoxNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxNo.isChecked()) {
                    checkBoxYes.setChecked(false);
                }
            }
        });

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueClicked();
            }
        });
    }

    private void onContinueClicked() {
        if (checkBoxYes.isChecked()) {
            // Rediriger vers l'inscription en tant que candidat
            Intent intent = new Intent(type_utilisateur.this, InscriptionCandidatActivity.class);
            startActivity(intent);
        } else if (checkBoxNo.isChecked()) {
            // Rediriger vers l'inscription en tant qu'employeur
            Intent intent = new Intent(type_utilisateur.this, InscriptionEmployeurActivity.class);
            startActivity(intent);
        }
    }
}
