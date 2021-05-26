package com.shinchannohara.personalinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class egmain extends AppCompatActivity {

    CardView bicep,chest,shoulder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egmain);

        bicep = (CardView)findViewById(R.id.bicepscv);
        chest = (CardView)findViewById(R.id.chestcv);
        shoulder = (CardView)findViewById(R.id.shouldercv);

        shoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(egmain.this,shouldermain.class));
            }
        });

        bicep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(egmain.this,bicepsmain.class));
            }
        });

        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(egmain.this,chestmain.class));
            }
        });

    }
}
