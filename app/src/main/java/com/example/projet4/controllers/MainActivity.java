package com.example.projet4.controllers;

import static com.example.projet4.services.DummyMeetingGenerator.resetDummyMeeting;
import static com.example.projet4.utils.Utils.jsonFileName;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.projet4.R;
import com.example.projet4.adapters.MeetingAdapter;
import com.example.projet4.databinding.ActivityMainBinding;
import com.example.projet4.models.Meeting;
import com.example.projet4.services.OnMeetingListener;
import com.example.projet4.viewModel.MeetingViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnMeetingListener {

    private ActivityMainBinding mBinding;

    private MeetingViewModel mMeetingViewModel;

    private ArrayList<Meeting> meetings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        setSupportActionBar(mBinding.toolbar.getRoot());
        mBinding.toolbar.getRoot().setPopupTheme(R.style.myPopupTheme);

        setupListener();

        if (checkAssetsAreAvailable()) {
            initViewModel();
            initDummyMeeting();
            getMeetingListFromService();
        }
    }

    private boolean checkAssetsAreAvailable() {
        try {
            return getAssets().open(jsonFileName).available() > 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initViewModel() {
        mMeetingViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);
    }

    private void initRecyclerView() {

        // Create a LinearLayoutManager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.recyclerView.getContext(),
                layoutManager.getOrientation());

        mBinding.recyclerView.addItemDecoration(dividerItemDecoration);

        MeetingAdapter adapter = new MeetingAdapter(meetings, this);
        mBinding.recyclerView.setAdapter(adapter);
    }

    private void initDummyMeeting() {
        mMeetingViewModel.initDummyMeeting(this);
    }

    private void getMeetingListFromService() {
        mMeetingViewModel.allMeetingsLiveData().observe(this, m -> {
            meetings = new ArrayList<>(m);
            initRecyclerView();
        });
    }

    private void setupListener() {
        mBinding.addButton.setOnClickListener(view -> {
//            Intent intent = new Intent(this, AddMeetingActivity.class);
//            startActivity(intent);

            mMeetingViewModel.insert(new Meeting(1, "test", new Date(), "roomTest", new ArrayList<>()));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetDummyMeeting(this);
    }

    @Override
    public void onItemClick(Meeting itemMeeting) {
        Toast.makeText(this, "Meeting:" + itemMeeting.getSubject() + " deleted", Toast.LENGTH_SHORT).show();
        mMeetingViewModel.delete(itemMeeting);
    }
}