package com.example.projet4.controllers.ui;

import static com.example.projet4.services.DummyMeetingGenerator.resetDummyMeeting;
import static com.example.projet4.utils.Utils.convertTimeToMillis;
import static com.example.projet4.utils.Utils.createTimerPicker;
import static com.example.projet4.utils.Utils.jsonFileName;
import static com.example.projet4.utils.Utils.showPicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.material.timepicker.MaterialTimePicker;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMeetingListener {

    private ActivityMainBinding mBinding;

    private MeetingViewModel mMeetingViewModel;

    private ArrayList<Meeting> meetings = new ArrayList<>();
    private MeetingAdapter adapter;

    private AlertDialog dialog;
    private ListView listView;
    private View dialogView;
    private AlertDialog.Builder builder;

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

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.recyclerView.getContext(), layoutManager.getOrientation());

        mBinding.recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new MeetingAdapter(meetings, this);

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
            Intent intent = new Intent(this, AddMeetingActivity.class);
            startActivity(intent);

//            mMeetingViewModel.insert(new Meeting(1, "test", new Date(), "roomTest", new ArrayList<>()));
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
        meetings.clear();

        //TODO refacto with item_filter_by_date
        if (id == R.id.item_filter_by_date) {

            MaterialTimePicker picker = createTimerPicker();
            showPicker(getSupportFragmentManager(),picker);
            setupListenerPicker(picker);

        } else if (id == R.id.item_filter_by_place) {

            initAlertDialogBuilder();
            initListView();
            setupListenerListView();

        } else if (id == R.id.item_filter_reset) {
            resetDummyMeeting(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAlertDialogBuilder() {
        builder = new AlertDialog.Builder(MainActivity.this).setTitle("Select a Room");
    }

    private void initListView() {
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_list, null);

        // Get a reference to the ListView from the dialogView
        listView = dialogView.findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, getStringArray());

        listView.setAdapter(adapter);
    }

    private void setupListenerListView() {

        final String[] myStringArray = getStringArray();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = myStringArray[position];
            showToast(selectedItem);
            meetings.addAll(mMeetingViewModel.filterByRoom(selectedItem));
            this.adapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        builder.setView(dialogView);
        dialog = builder.create();
        dialog.show();
    }


    private String[] getStringArray() {
        return getResources().getStringArray(R.array.my_string_array);
    }


    private void showToast(String message) {
        Toast.makeText(this, "Selected: " + message, Toast.LENGTH_SHORT).show();
    }

    private void setupListenerPicker(MaterialTimePicker picker) {
        picker.addOnPositiveButtonClickListener(view -> {
            int hour = picker.getHour();
            int minutes = picker.getMinute();
            long time = convertTimeToMillis(hour, minutes);
            meetings.addAll(mMeetingViewModel.filterByHour(time));
            adapter.notifyDataSetChanged();

        });
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