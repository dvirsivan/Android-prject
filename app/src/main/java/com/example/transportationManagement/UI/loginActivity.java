package com.example.transportationManagement.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.transportationManagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Time;

public class loginActivity extends AppCompatActivity {

    private String email;
    private String password;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void onLoginClick(View view) {
        email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(loginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getBaseContext(),"Something wrong",Toast.LENGTH_LONG).show();
            }
        });


    }

    public void onSignUpClick(View view) throws InterruptedException {
        final SharedPreferences sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
        /***************validation****************/
        Toast.makeText(getBaseContext(),"Please verify your mail",Toast.LENGTH_LONG).show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() ) {
                       // SharedPreferences.Editor editor = sharedPreferences.edit();
                       // editor.putString("email", email);
                       // editor.putString("password", password);
                       // editor.commit();
                        currentUser = mAuth.getCurrentUser();
                        sendMail();
                        Toast.makeText(getBaseContext(),"Excellent!",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getBaseContext(),"Your mail is wrong",Toast.LENGTH_LONG).show();
                    Log.d("TAG",task.getException().getMessage());
                });
        
        Toast.makeText(getBaseContext(),"Excellent!",Toast.LENGTH_LONG).show();

    }

    private void sendMail() {
        currentUser.sendEmailVerification().addOnCompleteListener(this,new OnCompleteListener<Void>(){

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getBaseContext(),"Mail is sent",Toast.LENGTH_LONG).show();
                    if (currentUser.isEmailVerified())
                        Toast.makeText(getBaseContext(),"Mail is sent",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}