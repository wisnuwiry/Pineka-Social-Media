package com.wisnusaputra.pinekaapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.wisnusaputra.pinekaapp.R;

public class ForgotPassActivity extends AppCompatActivity {

    EditText email;
    Button reset;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        email = findViewById(R.id.email);
        reset = findViewById(R.id.reset);
        firebaseAuth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(ForgotPassActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_email = email.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(ForgotPassActivity.this, "Please Enter Yor Email", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(str_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPassActivity.this, "Password reset email sesnt", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
                                finish();
                            }else{
                                pd.dismiss();
                                String error = task.getException().getMessage().toString();
                                Toast.makeText(ForgotPassActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    pd.dismiss();
                }
            }
        });
    }
}
