package com.shinchannohara.personalinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Blogsingleactivity extends AppCompatActivity {

    private String post_key;
    private DatabaseReference databaseReference;
    private ImageView image;
    private TextView title;
    private TextView description;
    private Button postdelete;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogsingleactivity);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");
        post_key = getIntent().getExtras().getString("blog_id");
        image = (ImageView)findViewById(R.id.postimageedit);
        title = (TextView)findViewById(R.id.posttitleedit);
        description = (TextView)findViewById(R.id.postdescedit);
        postdelete = (Button)findViewById(R.id.removepostbtn);
        firebaseAuth = FirebaseAuth.getInstance();

        postdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(post_key).removeValue();
                finish();
                startActivity(new Intent(Blogsingleactivity.this,calenderactivity.class));
            }
        });
        databaseReference.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String post_title = (String)dataSnapshot.child("title").getValue();
                String post_desc = (String)dataSnapshot.child("desc").getValue();
                String post_image = (String)dataSnapshot.child("image").getValue();
                String post_uid = (String)dataSnapshot.child("uid").getValue();

                title.setText(post_title);
                description.setText(post_desc);
                Picasso.get().load(post_image).into(image);

                if(firebaseAuth.getCurrentUser().getUid().equals(post_uid))
                {
                    postdelete.setVisibility(View.VISIBLE);
                }
                else
                {
                    postdelete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
