package com.example.projet4.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet4.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projet4.Models.Meeting;
import com.example.projet4.adapters.MeetingAdapter;
import com.example.projet4.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private ArrayList<Meeting> meetings;
    private MeetingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        setSupportActionBar(mBinding.toolbar.getRoot());
        mBinding.toolbar.getRoot().setPopupTheme(R.style.myPopupTheme);
        setupListener();
        initMeetings();
        initRecyclerView();

    }

    private void initRecyclerView() {

        // Create a LinearLayoutManager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.recyclerView.getContext(),
                layoutManager.getOrientation());

        mBinding.recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new MeetingAdapter(meetings); // meetings is your list of meetings
        mBinding.recyclerView.setAdapter(adapter);
    }

    private void initMeetings() {

        Date hour_meeting_1 = new Date();
        Date hour_meeting_2 = new Date();
        Date hour_meeting_3 = new Date();


        hour_meeting_1.setTime(57600000);
        hour_meeting_2.setTime(50400000);
        hour_meeting_3.setTime(0);

        meetings = new ArrayList<>(Arrays.asList(
                new Meeting(-25143, "Réunion A", hour_meeting_1, "Peach", new ArrayList<>(Collections.singleton("maxime@lamzone.com, alex@lamzome.com"))),
                new Meeting(-61180, "Réunion B", hour_meeting_2, "Mario", new ArrayList<>(Collections.singleton("paul@lamzome.com, viviane@lamzone.com"))),
                new Meeting(-16580839, "Réunion C", hour_meeting_3, "Luigi", new ArrayList<>(Collections.singleton("amandine@lamzome.com, luc@lamzone.com"))))
        );
    }

    private void setupListener() {
        mBinding.addButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddMeetingActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_filter_by_date) {
            Toast.makeText(this, "Filter by date apply", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.item_filter_by_place) {
            Toast.makeText(this, "Filter by place apply", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.item_filter_reset) {
            Toast.makeText(this, "Reset List", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}