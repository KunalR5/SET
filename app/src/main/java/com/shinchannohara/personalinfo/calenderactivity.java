package com.shinchannohara.personalinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class calenderactivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calenderactivity);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blog");

        recyclerView = (RecyclerView)findViewById(R.id.blog_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog,BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(

                Blog.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                databaseReference
        ) {

            @Override
            protected void populateViewHolder(BlogViewHolder blogViewHolder, Blog blog, int i) {

                final String post_key = getRef(i).getKey();

                blogViewHolder.setTitle(blog.getTitle());
                blogViewHolder.setDesc(blog.getDesc());
                blogViewHolder.setUserNam(blog.getUsername());
                blogViewHolder.setImage(blog.getImage());

                blogViewHolder.currentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleBlogIntent = new Intent(calenderactivity.this,Blogsingleactivity.class);
                        singleBlogIntent.putExtra("blog_id",post_key);
                        startActivity(singleBlogIntent);
                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View currentView;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            currentView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView)currentView.findViewById(R.id.Post_Title);
            post_title.setText(title);
        }
        public  void setDesc(String description)
        {
            TextView post_description = (TextView)currentView.findViewById(R.id.Post_Desc);
            post_description.setText(description);
        }
        public void setUserNam(String usernam)
        {
            TextView post_username = (TextView)currentView.findViewById(R.id.Post_UserName);
            post_username.setText("~ By "+usernam);
        }
        public  void setImage(String image){
            ImageView posim = (ImageView)currentView.findViewById(R.id.Post_Image);
            Picasso.get().load(image).into(posim);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.addanewpost:{
                startActivity(new Intent(calenderactivity.this,addPost.class));
            }
        }
        return true;
    }
}
