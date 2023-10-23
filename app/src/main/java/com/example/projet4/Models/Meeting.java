package com.example.projet4.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Meeting {

    private int color;
    private String subject;
    private Date hour;
    private String room;
    private ArrayList<String> emails;

    public Meeting() {
    }

    public Meeting(int color, String subject, Date hour, String room, ArrayList<String> emails) {
        this.color = color;
        this.subject = subject;
        this.hour = hour;
        this.room = room;
        this.emails = emails;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    @NonNull
    @Override
    public String toString() {
        return "Meeting{" +
                "color=" + color +
                ", subject='" + subject + '\'' +
                ", hour=" + hour +
                ", room='" + room + '\'' +
                ", emails=" + emails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return getColor() == meeting.getColor() &&
                getHour().equals(meeting.getHour()) &&
                getRoom().equals(meeting.getRoom()) &&
                getEmails().equals(meeting.getEmails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColor(), getHour(), getRoom(), getEmails());
    }
}
