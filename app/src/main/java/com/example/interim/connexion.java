package com.example.interim;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class connexion extends AppCompatActivity {
    private EditText passwordEditText;
    private boolean isPasswordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        passwordEditText = findViewById(R.id.password);
        passwordEditText.setOnTouchListener((v, event) -> {
            final int right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[right].getBounds().width())) {
                    int selection = passwordEditText.getSelectionEnd();
                    if (isPasswordVisible) {
                        // Set drawable here for password hide
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        isPasswordVisible = false;
                    } else {
                        // Set drawable here for password show
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        isPasswordVisible = true;
                    }
                    passwordEditText.setSelection(selection);
                    return true;
                }
            }
            return false;
        });
    }
}
