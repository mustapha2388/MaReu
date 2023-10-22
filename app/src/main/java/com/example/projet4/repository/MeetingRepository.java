package com.example.projet4.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.projet4.DI.DI;
import com.example.projet4.models.Meeting;
import com.example.projet4.services.DummyMeetingApiService;
import com.example.projet4.services.MeetingApiService;

import java.util.ArrayList;
import java.util.List;

public class MeetingRepository {

    private final LiveData<List<Meeting>> allMeetings;
    private final MeetingApiService mApiService;

    public MeetingRepository() {

        mApiService = DI.getMeetingApiService();
        allMeetings = mApiService.getAllMeetingsLiveData();
    }

    public LiveData<List<Meeting>> allMeetingsLiveData() {
        return allMeetings;
    }

    public void insert(Meeting meeting) {
        mApiService.insert(meeting);
    }

    public void delete(Meeting meeting) {
        mApiService.delete(meeting);
    }

    public void initDummyMeeting(Context context) {
        mApiService.initDummyMeeting(context);
    }

    public ArrayList<Meeting> filterByRoom(String room) {
        return mApiService.filterByRoom(room);
    }

    public ArrayList<Meeting> filterByHour(long hour) {
        return mApiService.filterByHour(hour);
    }
}
