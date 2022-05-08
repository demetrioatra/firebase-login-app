package com.devd.projetofirebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class FormLogin extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button button;
    private TextView textCadastro;
    private ProgressBar progressBar;
    String[] messages = { "Preencha todos os campos!" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setComponents();

        button.setOnClickListener(view -> {

            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty())
            {
                Snackbar snackbar = Snackbar.make(view, messages[0], BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            } else authenticate();
        });

        textCadastro.setOnClickListener(view -> startActivity(new Intent(FormLogin.this, FormCadastro.class)));
    }

    private void authenticate() {

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            }
        });
    }

    private void setComponents() {
        textCadastro = findViewById(R.id.txt_cadastro);
        editEmail = findViewById(R.id.edt_email);
        editPassword = findViewById(R.id.edt_password);
        button = findViewById(R.id.btn_entrar);
        progressBar = findViewById(R.id.progressBar);
    }
}