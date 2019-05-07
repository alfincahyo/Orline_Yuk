package com.example.orlineyuk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orlineyuk.model.model_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private Button butRegister;
    private EditText namauser;
    private EditText nouser;
    private EditText emailreg;
    private EditText passreg;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseUser;
    ArrayList<model_user> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        databaseUser = FirebaseDatabase.getInstance().getReference("TabelUser");

        butRegister = (Button) findViewById(R.id.register);
        emailreg = (EditText) findViewById(R.id.email);
        passreg = (EditText) findViewById(R.id.password);
        namauser = (EditText) findViewById(R.id.namauser);
        nouser = (EditText) findViewById(R.id.nohpuser);

        butRegister.setOnClickListener(this);

    }

    public void registerUser(){
        String email = emailreg.getText().toString().trim();
        String pass = passreg.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Masukkan email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("Sedang Registrasi..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Berhasil Register", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }else{
                            Toast.makeText(Register.this, "Register Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    public void TambahUser(){
        String nama = namauser.getText().toString().trim();
        String email = emailreg.getText().toString().trim();
        String nohp = nouser.getText().toString().trim();

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(nohp) || TextUtils.isEmpty(email)){
            Toast.makeText(this, "Isi Semua Form", Toast.LENGTH_LONG).show();
        }else {
            String id = databaseUser.push().getKey();

            model_user user = new model_user(id, nama, nohp, email);
            databaseUser.child(id).setValue(user);
        }
    }



    @Override
    public void onClick(View v) {
        if (v == butRegister){
            TambahUser();
            registerUser();
        }
    }
}
