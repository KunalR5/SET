package com.shinchannohara.personalinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class newssection extends AppCompatActivity {

    private static final String API_KEY = "de1bba5ac5644c53bafd10d046feb458";
    private static final String BASE_URL = "https://newsapi.org/v2/";
    List<Articles> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private newsrecyclerviewadapter recyclerviewadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newssection);

        recyclerView = (RecyclerView)findViewById(R.id.news_result_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ChipGroup chipGroup = (ChipGroup)findViewById(R.id.news_language_option);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = (Chip)group.findViewById(checkedId);
                if(chip==null)
                {
                    shownews("");
                }
                else
                {
                    String cur = chip.getText().toString();
                    if(cur.equals("English"))
                        shownews("en");
                    else if(cur.equals("French"))
                        shownews("fr");
                    else if(cur.equals("Italian"))
                        shownews("it");
                    else if(cur.equals("Russian"))
                        shownews("ru");
                    else if(cur.equals("Spanish"))
                        shownews("es");
                    else if(cur.equals("Dutch"))
                        shownews("nl");
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        shownews("");
    }

    void shownews(String language)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<Headlines> call = api.getHeadlines("workout",language,API_KEY);

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful())
                {
                    articles.clear();
                    articles = response.body().getArticles();
                    recyclerviewadapter = new newsrecyclerviewadapter(newssection.this,articles);
                    recyclerView.setAdapter(recyclerviewadapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(newssection.this,"failed",Toast.LENGTH_LONG).show();
            }
        });
    }
}
