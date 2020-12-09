package com.example.transportationManagement.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                Intent intent = new Intent(loginActivity.this,MainActivity.class);
                startActivity(intent);
            }
            else
                Toast.makeText(getBaseContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
        });


    }

    public void onSignUpClick(View view) throws InterruptedException {
       // final SharedPreferences sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        email = ((EditText)findViewById(R.id.email)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
        if (validFields() != "") {
            Toast.makeText(getBaseContext(), "Please verify your mail", Toast.LENGTH_LONG).show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // SharedPreferences.Editor editor = sharedPreferences.edit();
                            // editor.putString("email", email);
                            // editor.putString("password", password);
                            // editor.commit();
                            currentUser = mAuth.getCurrentUser();
                            sendMail();
                        } else {
                            Toast.makeText(getBaseContext(), "Your mail is wrong/n task.getException().getMessage()",
                                    Toast.LENGTH_LONG).show();
                            Log.d("TAG", task.getException().getMessage());
                        }
                    });
        }
        else
            Toast.makeText(getBaseContext(), validFields(), Toast.LENGTH_LONG);
    }

    private String validFields() {
        String message = "";
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            message = "Mail is sent to you, please verify the mail";
        if (password.length() < 6 )
            message = "The password have to be a least 6 symbols";
        return message;
    }

    private void sendMail() {
        currentUser.sendEmailVerification().addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Toast.makeText(getBaseContext(),"Mail is sent to you, please verify the mail",Toast.LENGTH_LONG).show();
                if (currentUser.isEmailVerified())
                    Toast.makeText(getBaseContext(),"Excellent! You can login now",Toast.LENGTH_LONG).show();
            }
            else {
                Log.d("TAG", task.getException().getMessage());
                Toast.makeText(getBaseContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}