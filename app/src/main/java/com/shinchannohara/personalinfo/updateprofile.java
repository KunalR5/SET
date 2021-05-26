package com.shinchannohara.personalinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class updateprofile extends AppCompatActivity {

    private EditText newname,newmail,newage;
    private Button savebtn;
    private FirebaseAuth firebaseAuth;
    private ImageView updateprofilepic;
    private FirebaseDatabase firebaseDatabase;
    private static int PICK_IMAGE = 1;
    Uri imagePath;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                updateprofilepic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

        newname = (EditText)findViewById(R.id.uUserName);
        newmail = (EditText)findViewById(R.id.uEmailID);
        newage = (EditText)findViewById(R.id.uAgetext);
        savebtn = (Button)findViewById(R.id.usavebtn);
        updateprofilepic = (ImageView)findViewById(R.id.uprofilepicture);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        storageReference.child(firebaseAuth.getUid()).child("Images").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(updateprofilepic);
            }
        });

        updateprofilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        final DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Userprofile userprofile = dataSnapshot.getValue(Userprofile.class);
                newname.setText(userprofile.getUserName());
                newage.setText(userprofile.getUserAge());
                newmail.setText(userprofile.getUserEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newname.getText().toString();
                String age = newage.getText().toString();
                String mail = newmail.getText().toString();

                Userprofile userprofile = new Userprofile(age,mail,name);
                myref.setValue(userprofile);
                firebaseStorage = FirebaseStorage.getInstance();
                StorageReference imageref = storageReference.child("Images");
                UploadTask uploadTask = imageref.putFile(imagePath);
                finish();
            }
        });

    }
}

