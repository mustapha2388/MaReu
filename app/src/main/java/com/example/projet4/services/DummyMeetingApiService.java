package com.example.projet4.services;

import android.content.Context;
import android.os.Build;

import androidx.lifecycle.LiveData;

import com.example.projet4.models.Meeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public ArrayList<Meeting> filterByRoom(String room) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Objects.requireNonNull(meetings.getValue()).stream().filter(m -> m.getRoom().equals(room)).collect(Collectors.toCollection(ArrayList::new));
        }else {
         return null;
        }
    }

    @Override
    public ArrayList<Meeting> filterByHour(long hour) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Objects.requireNonNull(meetings.getValue()).stream().filter(m -> m.getHour().getTime()==(hour)).collect(Collectors.toCollection(ArrayList::new));
        }else {
            return null;
        }
    }

}
