package com.devd.projetofirebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Objects;

public class FormLogin extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button loginButton, registerButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setComponents();

        loginButton.setOnClickListener(view ->
        {
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty())
            {
                Snackbar snackbar = Snackbar
                        .make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.WHITE)
                        .setTextColor(Color.BLACK);
                snackbar.show();
            }
            else authenticate();
        });

        registerButton.setOnClickListener(view -> startActivity(new Intent(FormLogin.this, FormCadastro.class)));
    }

    private void setComponents()
    {
        editEmail = findViewById(R.id.edt_email);
        editPassword = findViewById(R.id.edt_password);
        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
    }

    private void authenticate()
    {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
            {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed((Runnable) this::startIntent, 3000);
            }
        });
    }

    private void startIntent()
    {
        Intent intent = new Intent(FormLogin.this, TelaPrincipal.class);
        startActivity(intent);
        finish();
    }
}