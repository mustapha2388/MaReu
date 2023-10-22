package com.example.projet4.services;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projet4.models.Meeting;
import com.example.projet4.utils.Utils;

import java.util.List;

public class DummyMeetingGenerator {

    static final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();
    public static List<Meeting> DUMMY_MEETINGS;


    public static List<Meeting> getMeetingListFromJson(Context context) {

        String meetingListJsonFileToString = Utils.getJsonFromAssets(context.getApplicationContext());
        return Utils.parseJsonToMeetings(meetingListJsonFileToString);
    }


    public static LiveData<List<Meeting>> generateMeetingLiveData() {
        return meetingsLiveData;
    }

    public static void resetDummyMeeting(Context context) {
        initDummyMeeting(context);
//        meetingsLiveData.setValue(DUMMY_MEETINGS);
    }

    public static void initDummyMeeting(Context context) {
        DUMMY_MEETINGS = getMeetingListFromJson(context);
        meetingsLiveData.setValue(DUMMY_MEETINGS);
    }

    public static void insert(Meeting meeting) {
        DUMMY_MEETINGS.add(meeting);
        meetingsLiveData.setValue(DUMMY_MEETINGS);
    }

    public static void delete(Meeting meeting) {
        DUMMY_MEETINGS.remove(meeting);
        meetingsLiveData.setValue(DUMMY_MEETINGS);
    }
}
