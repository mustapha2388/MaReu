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
import com.example.projet4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        setSupportActionBar(mBinding.toolbar.getRoot());
        mBinding.toolbar.getRoot().setPopupTheme(R.style.myPopupTheme);
        setupListener();
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