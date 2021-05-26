package com.shinchannohara.personalinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class startanimation extends AppCompatActivity {

    TextView title,subtitle,btn;
    ImageView image;
    Animation imgpage,one,two,three,ltr;
    View vprogress,vprogressstop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startanimation);

        imgpage = AnimationUtils.loadAnimation(this,R.anim.imgpage);
        one = AnimationUtils.loadAnimation(this,R.anim.one);
        two = AnimationUtils.loadAnimation(this,R.anim.two);
        three = AnimationUtils.loadAnimation(this,R.anim.three);
        ltr = AnimationUtils.loadAnimation(this,R.anim.ltr);

        title = (TextView)findViewById(R.id.animationtitle);
        subtitle = (TextView)findViewById(R.id.animationsubtitle);
        btn = (TextView)findViewById(R.id.animationbtn);
        image = (ImageView)findViewById(R.id.animationimage);
        vprogress = (View)findViewById(R.id.animationprog);
        vprogressstop = (View)findViewById(R.id.animationprogstop);

        image.startAnimation(imgpage);
        title.startAnimation(one);
        btn.startAnimation(three);
        subtitle.startAnimation(one);
        vprogress.startAnimation(two);
        vprogressstop.startAnimation(ltr);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(startanimation.this,set.class));
            }
        });

    }
}
