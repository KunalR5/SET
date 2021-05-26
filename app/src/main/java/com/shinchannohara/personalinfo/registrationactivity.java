package com.shinchannohara.personalinfo;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class registrationactivity extends AppCompatActivity {

    private TextInputLayout userName,email,password,userage;
    private Button register;
    private FirebaseAuth firebaseAuth;
    String name,mail,pass,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationactivity);
        userName = (TextInputLayout) findViewById(R.id.username);
        email = (TextInputLayout) findViewById(R.id.gmail);
        password = (TextInputLayout) findViewById(R.id.password);
        register = (Button)findViewById(R.id.registerbutton);
        userage = (TextInputLayout) findViewById(R.id.agetext);

        firebaseAuth = FirebaseAuth.getInstance();

        /*profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });*/


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    String user_mail = email.getEditText().getText().toString().trim();
                    String user_password = password.getEditText().getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_mail,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                //sendemailverification();
                                Toast.makeText(registrationactivity.this,"Registration successfull",Toast.LENGTH_SHORT).show();
                                senduserdata();
                                firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(registrationactivity.this,startanimation.class));
                            }
                            else
                            {
                                Toast.makeText(registrationactivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private Boolean validate()
    {
        Boolean result = false;

        name = userName.getEditText().getText().toString();
        mail = email.getEditText().getText().toString();
        pass = password.getEditText().getText().toString();
        age = userage.getEditText().getText().toString();

        if( name.isEmpty() || pass.isEmpty() || mail.isEmpty() ||age.isEmpty() )
        {
            Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
        }else
        {
            result = true;
        }
        return result;
    }

    private void senduserdata()
    {
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());

        HashMap<String, String> usermap = new HashMap<>();
        usermap.put("userAge",age);
        usermap.put("userEmail",mail);
        usermap.put("userName",name);
        myref.setValue(usermap);
    }
}
