package com.example.projet4.services;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.projet4.models.Meeting;

import java.util.List;

public interface MeetingApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    LiveData<List<Meeting>> getAllMeetingsLiveData();
    void  insert(Meeting meeting);

    void delete(Meeting meeting);
    void initDummyMeeting(Context context);
}
