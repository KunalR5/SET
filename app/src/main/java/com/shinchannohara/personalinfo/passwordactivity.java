package com.shinchannohara.personalinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class passwordactivity extends AppCompatActivity {

    private EditText emailpassword;
    private Button resetbtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordactivity);

        emailpassword = (EditText)findViewById(R.id.passwordemail);
        resetbtn = (Button)findViewById(R.id.passwordreset);

        firebaseAuth = FirebaseAuth.getInstance();

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredmail = emailpassword.getText().toString().trim();
                if(enteredmail.equals(""))
                {
                    Toast.makeText(passwordactivity.this,"Enter your registered Email ID",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(enteredmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(passwordactivity.this,"Password reset mail sent!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(passwordactivity.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(passwordactivity.this,"Error in sending password reset email",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
