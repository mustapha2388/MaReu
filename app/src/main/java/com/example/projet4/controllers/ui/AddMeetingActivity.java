package com.example.projet4.controllers.ui;

import static com.example.projet4.utils.Utils.AreEmailValid;
import static com.example.projet4.utils.Utils.checkRoomAndHourMeetingAreInformed;
import static com.example.projet4.utils.Utils.errorEmailNotValid;
import static com.example.projet4.utils.Utils.showErrorFieldNotInformed;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet4.R;
import com.example.projet4.controllers.fragments.AddMeetingButtonsFragment;
import com.example.projet4.controllers.fragments.AddMeetingInputsFragment;
import com.example.projet4.databinding.ActivityAddMeetingBinding;
import com.example.projet4.models.Meeting;
import com.example.projet4.repository.MeetingRepository;
import com.example.projet4.viewModel.MeetingViewModel;

public class AddMeetingActivity extends AppCompatActivity implements AddMeetingInputsFragment.ClickListener, AddMeetingButtonsFragment.DataPassListener, AddMeetingInputsFragment.ClickListenerSavaData {

    private AddMeetingButtonsFragment addMeetingFragmentButtons;
    private AddMeetingInputsFragment addMeetingFragmentInputs;

    private MeetingViewModel mMeetingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.projet4.databinding.ActivityAddMeetingBinding binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar.getRoot());
        // Enable the back arrow in the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();
        setupFragment();
    }

    private void initViewModel() {
        MeetingViewModelFactory viewModelFactory = new MeetingViewModelFactory(getApplication(), new MeetingRepository());
        mMeetingViewModel = new ViewModelProvider(this, viewModelFactory).get(MeetingViewModel.class);
    }

    private void setupFragment() {

        addMeetingFragmentButtons = (AddMeetingButtonsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_part_1);

        if (addMeetingFragmentButtons == null) {
            addMeetingFragmentButtons = AddMeetingButtonsFragment.newInstance(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view_part_1, addMeetingFragmentButtons)
                    .commit();
        }

        addMeetingFragmentInputs = (AddMeetingInputsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_part_2);

        if (addMeetingFragmentInputs == null) {
            addMeetingFragmentInputs = AddMeetingInputsFragment.newInstance(this, this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view_part_2, addMeetingFragmentInputs)
                    .commit();
        }

    }

    @Override
    public void onDataPass(Meeting dataMeeting) {
        if (addMeetingFragmentInputs != null) {
            if (checkRoomAndHourMeetingAreInformed(dataMeeting)) {
                addMeetingFragmentInputs.receiveData(dataMeeting);
            } else {
                showErrorFieldNotInformed(this, dataMeeting);
            }
        } else {
            Toast.makeText(this, "addMeetingFragmentButtons is null", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClickListener() {
        if (addMeetingFragmentButtons != null) {
            addMeetingFragmentButtons.clickDetected();
        } else {
            Toast.makeText(this, "addMeetingFragmentButtons is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickListenerSaveData(Meeting meeting) {
        Toast.makeText(this, meeting.toString(), Toast.LENGTH_SHORT).show();

        if (checkSubjectAndEmailAreInformed(meeting)) {
            if (AreEmailValid(meeting)) {
                mMeetingViewModel.insert(meeting);
                finish();
            } else {
                errorEmailNotValid(this);
            }
        } else {
            showErrorFieldNotInformed(this, meeting);
        }
    }

    private boolean checkSubjectAndEmailAreInformed(Meeting meeting) {
        return !meeting.getSubject().equals("") && !meeting.getEmails().isEmpty();
    }


}