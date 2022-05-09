package com.devd.projetofirebase;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FormCadastro extends AppCompatActivity {

    // Variables
    private EditText    editName, editEmail, editPassword;
    private Button      button;
    String[]            messages = { "Preencha todos os campos!", "Cadastro realizado com Sucesso!" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Logic
        setComponents();

        button.setOnClickListener(view ->
        {
            String name = editName.getText().toString();
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty())
            {
                Snackbar snackbar = Snackbar
                    .make(view, messages[0], Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(Color.BLACK);
                snackbar.show();
            }
            else register(view);
        });
    }

    // Register Function
    private void register(View view)
    {
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
            {
                saveUser();

                Snackbar snackbar = Snackbar
                    .make(view, messages[1], Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(Color.BLACK);
                snackbar.show();
            }
            else
            {
                String error;

                try                                                 { throw Objects.requireNonNull(task.getException()); }
                catch (FirebaseAuthWeakPasswordException e)         { error = "Digite uma senha de, no mínimo, 6 caracteres"; }
                catch (FirebaseAuthUserCollisionException e)        { error = "Esta conta ja foi cadastrada"; }
                catch (FirebaseAuthInvalidCredentialsException e)   { error = "E-mail inválido"; }
                catch (Exception e)                                 { error = "Erro ao cadastrar usuário"; }

                Snackbar snackbar = Snackbar
                        .make(view, error, Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(Color.WHITE)
                        .setTextColor(Color.BLACK);
                snackbar.show();
            }
        });
    }

    // Save User Function
    private void saveUser()
    {
        String name = editName.getText().toString();
        String userID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Map<String, Object> users = new HashMap<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firestore.collection("Users").document(userID);

        users.put("name", name);

        documentReference.set(users)
                .addOnSuccessListener(unused -> Log.d("firestore", "Sucesso ao salvar dados"))
                .addOnFailureListener(e -> Log.d("firestore_error", "Erro ao salvar dados" + e));
    }

    // Set Components Function
    private void setComponents()
    {
        editName = findViewById(R.id.edt_name);
        editEmail = findViewById(R.id.edt_email);
        editPassword = findViewById(R.id.edt_password);
        button = findViewById(R.id.btn_register);
    }
}