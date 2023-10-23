package com.example.projet4.DI;

import com.example.projet4.services.DummyMeetingApiService;
import com.example.projet4.services.MeetingApiService;

@SuppressWarnings("unused")
public class DI {

    private static final MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }

}
