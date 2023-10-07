package com.example.projet4.services;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.projet4.models.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final LiveData<List<Meeting>> meetings = DummyMeetingGenerator.generateMeetingLiveData();

    @Override
    public LiveData<List<Meeting>> getAllMeetingsLiveData() {
        return meetings;
    }

    @Override
    public void insert(Meeting meeting) {
        DummyMeetingGenerator.insert(meeting);
    }

    @Override
    public void delete(Meeting meeting) {
        DummyMeetingGenerator.delete(meeting);
    }

    public void initDummyMeeting(Context context){
        DummyMeetingGenerator.initDummyMeeting(context);
    }

}
