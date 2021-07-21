package com.shinchannohara.personalinfo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    LocationManager locationManager;
    LocationListener locationListener;
    String mylatitude;
    String myLongitude;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.sliderexercise,
            R.drawable.sliderdiet,
            R.drawable.slidergym,
            R.drawable.slidernews,
            R.drawable.sliderblog
    };

    public String[] slide_headings = {
            "EXERCISE GUIDE",
            "DIET GUIDE",
            "NEARBY GYM'S",
            "FITNESS NEWS",
            "BLOG SECTION"
    };

    public String[] slide_description = {
            "With the right fitness equipment and exercise plan, these advanced workouts bring you closer to your fitness goals.",
            "Looking for a free diet plan that is tailored according to the needs of your body? Instead of three large meals, try having three modest meals and a few snack breaks through the day in controlled portions.",
            "Find Fitness Centre and Gym Near Me. With SET, you get to workout anytime, anywhere at more than 100 gyms and fitness centres near you.",
            "Physical fitness and exercise. Check out the latest articles on physical fitness, and new methods for improving exercise",
            "Share your SET experience.\nWriting about and taking photos of your experiences is a great way to remember them later, and your stories could also inspire others to follow in your footsteps."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideimageview = (ImageView)view.findViewById(R.id.slide_image);
        TextView slidetitle = (TextView)view.findViewById(R.id.slide_title);
        TextView slidedescription = (TextView)view.findViewById(R.id.slide_description);
        Button slidermore = (Button)view.findViewById(R.id.slidermoreinfo);

        slideimageview.setImageResource(slide_images[position]);
        slidetitle.setText(slide_headings[position]);
        slidedescription.setText(slide_description[position]);

        slidermore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0)
                    context.startActivity(new Intent(context,egmain.class));
                else if(position==1)
                    context.startActivity(new Intent(context,exercise_guide.class));
                else if(position==2)
                {
                    if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        getCurrentLocation();
                        Uri gmmIntentUri = Uri.parse("geo:"+"28.5823"+","+"77.0500"+"?q=gyms");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        context.startActivity(mapIntent);
                    }
                    else{
                        ActivityCompat.requestPermissions((Activity) context
                                ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                    }
                }
                else if(position==4)
                    context.startActivity(new Intent(context,calenderactivity.class));
                else if(position==3)
                    context.startActivity(new Intent(context,newssection.class));
            }
        });

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    private void getCurrentLocation() {
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLongitude = String.valueOf(location.getLongitude());
                mylatitude = String.valueOf(location.getLatitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==44)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
                Uri gmmIntentUri = Uri.parse("geo:"+"28.5823"+","+"77.0500"+"?q=gyms");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                context.startActivity(mapIntent);
            }
        }
    }
}
