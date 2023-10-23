package com.example.projet4.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projet4.models.Meeting;
import com.example.projet4.repository.MeetingRepository;

import java.util.ArrayList;
import java.util.List;

public class MeetingViewModel extends AndroidViewModel {

    private final LiveData<List<Meeting>> allMeetings;
    private final MeetingRepository mRepository;

    public MeetingViewModel(Application application, MeetingRepository mRepository) {
        super(application);
        this.mRepository = mRepository;
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

    public ArrayList<Meeting> filterByRoom(String room) {
        return mRepository.filterByRoom(room);
    }

    public ArrayList<Meeting> filterByHour(long hour) {
        return mRepository.filterByHour(hour);
    }
}
