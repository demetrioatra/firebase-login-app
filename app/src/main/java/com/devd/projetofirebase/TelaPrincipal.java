package com.devd.projetofirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;

public class TelaPrincipal extends AppCompatActivity {

    // Variables
    private TextView    textName, textEmail;
    private Button      button;
    String              userId;
    FirebaseFirestore   firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Logic
        setComponents();

        button.setOnClickListener(view ->
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(TelaPrincipal.this, FormLogin.class));
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Logic
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        //String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DocumentReference documentReference = firestore.collection("Users").document(userId);

        documentReference.addSnapshotListener((documentSnapshot, error) ->
        {
            if (documentSnapshot != null)
            {
                textName.setText(documentSnapshot.getString("name"));
                textEmail.setText(userId);
            }
        });
    }

    // Set Components Function
    private void setComponents()
    {
        textName = findViewById(R.id.txt_name);
        textEmail = findViewById(R.id.edt_email);
        button = findViewById(R.id.btn_logoff);
    }
}