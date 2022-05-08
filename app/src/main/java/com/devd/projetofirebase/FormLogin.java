package com.devd.projetofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class FormLogin extends AppCompatActivity {

    private TextView txt_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setComponents();

        txt_cadastro.setOnClickListener(view -> {
            Intent intent = new Intent(FormLogin.this, FormCadastro.class);
            startActivity(intent);
        });
    }

    private void setComponents() {
        txt_cadastro = findViewById(R.id.txt_cadastro);
    }
}