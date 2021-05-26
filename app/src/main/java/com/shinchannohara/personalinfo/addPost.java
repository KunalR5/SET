package com.shinchannohara.personalinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.nio.channels.GatheringByteChannel;

public class addPost extends AppCompatActivity {


    private ImageView selectimage;
    private EditText selectposttitle,selectpostdesc;
    private Button addpostbtn;
    private static final int GALLERY_REQUEST = 1;
    private Uri uri = null;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference currentDatabase;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        selectimage = (ImageView)findViewById(R.id.addpostimage);
        selectposttitle = (EditText)findViewById(R.id.addposttitle);
        selectpostdesc = (EditText)findViewById(R.id.addpostdescription);
        addpostbtn = (Button)findViewById(R.id.addpostbutton);

        progressDialog = new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        currentDatabase = FirebaseDatabase.getInstance().getReference().child(currentUser.getUid());

        addpostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_REQUEST);
            }
        });
    }

    private void startPosting(){
        final String title_val = selectposttitle.getText().toString();
        final String desc_val = selectpostdesc.getText().toString();

        if(!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && uri != null)
        {
            progressDialog.setMessage("Posting to Blog .....");
            progressDialog.show();
            StorageReference filepath = storageReference.child("Blogimages").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final DatabaseReference newPost = databaseReference.push();
                    currentDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Userprofile userprofile = dataSnapshot.getValue(Userprofile.class);
                            newPost.child("title").setValue(title_val);
                            newPost.child("desc").setValue(desc_val);
                            newPost.child("uid").setValue(currentUser.getUid());
                            newPost.child("username").setValue(userprofile.getUserName());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String photoLink = uri.toString();
                            newPost.child("image").setValue(photoLink);
                        }
                    });
                    progressDialog.dismiss();
                    finish();
                    startActivity(new Intent(addPost.this,calenderactivity.class));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK && data.getData() != null)
        {
            uri = data.getData();
            selectimage.setImageURI(uri);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
