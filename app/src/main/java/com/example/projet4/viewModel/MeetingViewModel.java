package com.example.projet4.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projet4.models.Meeting;
import com.example.projet4.repository.MeetingRepository;

import java.util.List;

public class MeetingViewModel extends AndroidViewModel {

    private final LiveData<List<Meeting>> allMeetings;
    private MeetingRepository mRepository;

    public MeetingViewModel(Application application) {
        super(application);
        mRepository = new MeetingRepository();
        allMeetings = mRepository.allMeetingsLiveData();
    }

    public LiveData<List<Meeting>> allMeetingsLiveData() {
        return allMeetings;
    }

    public void insert(Meeting meeting) {
        mRepository.insert(meeting);
    }

    public void delete(Meeting meeting) {
        mRepository.delete(meeting);
    }
    public void initDummyMeeting(Context context) {
        mRepository.initDummyMeeting(context);
    }
}
