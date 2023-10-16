package com.example.projet4.utils;

import static com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.projet4.R;
import com.example.projet4.models.Meeting;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Utils {

    public static final String jsonFileName = "meetings.json";
    public static final String FILTER_BY_ROOM = "FILTER_BY_ROOM";
    public static final String FILTER_BY_HOUR = "FILTER_BY_HOUR";
    public static final String FILTER_RESET = "FILTER_RESET";

    public static String getJsonFromAssets(Context context) {
        String jsonString;
        try {
            String path = context.getAssets().toString();
            Log.i("TAG", "getJsonFromAssets: " + path);
            InputStream is = context.getAssets().open(jsonFileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            int byteRead = is.read(buffer);
            if (byteRead != -1) {
                is.close();
            } else {
                Toast.makeText(context, "Error during reading of json file", Toast.LENGTH_SHORT).show();
            }

            jsonString = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }


    public static ArrayList<Meeting> parseJsonToMeetings(String jsonString) {

        ArrayList<Meeting> meetings = new ArrayList<>();

        // Parse the JSON string
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");

        for (JsonElement jsonElement : jsonArray) {


            int color = jsonElement.getAsJsonObject().get("color").getAsInt();
            String subject = jsonElement.getAsJsonObject().get("subject").getAsString();
            long hourTimestamp = jsonElement.getAsJsonObject().get("hour").getAsLong();
            String room = jsonElement.getAsJsonObject().get("room").getAsString();
            JsonArray emailsJsonArray = jsonElement.getAsJsonObject().get("emails").getAsJsonArray();

            ArrayList<String> emails = new ArrayList<>();

            for (JsonElement emailJsonElement : emailsJsonArray) {
                emails.add(emailJsonElement.getAsString());
            }


            // Convert the timestamp to a Date
            Date hour = new Date(hourTimestamp);

            // Create a Meeting object and add it to the list
            Meeting meeting = new Meeting(color, subject, hour, room, emails);
            meetings.add(meeting);
        }

        return meetings;
    }

    public static String getInfoMeeting(Meeting meeting) {
        String hour = convertMillisToTime(meeting.getHour().getTime());
        return meeting.getSubject() + " - " + hour +
                " - " + meeting.getRoom();
    }

    public static boolean checkRoomAndHourMeetingAreInformed(Meeting meeting) {
        return meeting.getRoom() != null && meeting.getHour() != null;
    }

    public static void showErrorFieldNotInformed(Context context, Meeting meeting) {

        if (meeting.getHour() == null) {

            errorFieldNotInformed(context, "hour");

        } else if (meeting.getRoom() == null) {

            errorFieldNotInformed(context, "room");

        } else if (meeting.getSubject().equals("")) {

            errorFieldNotInformed(context, "subject");
        } else {

            errorFieldNotInformed(context, "emails");

        }
    }

    public static void errorEmailNotValid(Context context) {
        Toast.makeText(context, "Invalid Email address", Toast.LENGTH_SHORT).show();
    }

    public static void errorFieldNotInformed(Context context, String fieldStr) {
        Toast.makeText(context, "None " + fieldStr + " selected, please select a " + fieldStr, Toast.LENGTH_SHORT).show();
    }

    ///MaterialTimePicker///
    public static MaterialTimePicker createTimerPicker() {
        return new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(0).setMinute(0).setTitleText("Select meeting time").setInputMode(INPUT_MODE_CLOCK).build();
    }

    public static void showPicker(FragmentManager fragmentManager, MaterialTimePicker picker) {
        picker.show(fragmentManager, "timePickerTag");
    }

    public static long convertTimeToMillis(int hour, int minute) {
        return ((hour * 60L + minute) * 60L * 1000L) - 3600000L; // Subtract 1 hour (3600000 milliseconds)
    }

    public static String convertMillisToTime(long millis) {

        long totalSeconds = (millis + (3600000)) / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        return String.format(Locale.FRANCE, "%02d:%02d", hours, minutes);
    }

    public static boolean AreEmailValid(TextInputLayout emailsInputLayout) {

        List<String> emails = Arrays.asList(Objects.requireNonNull(emailsInputLayout.getEditText()).getText().toString().split(","));

        AtomicBoolean emailSValid = new AtomicBoolean(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            emails.stream().map(String::trim).forEach(email -> {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailSValid.set(false);
                }
            });
        } else {
            for (String email : emails) {
                String trimmedEmail = email.trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
                    emailSValid.set(false);
                    break;
                }
            }
        }
        return emailSValid.get();
    }

    public static boolean AreEmailValid(Meeting meeting) {

        List<String> emails = Arrays.asList(meeting.getEmails().toString().replace("[", "").replace("]", "").split(","));

        AtomicBoolean emailSValid = new AtomicBoolean(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            emails.stream().map(String::trim).forEach(email -> {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailSValid.set(false);
                }
            });
        } else {
            for (String email : emails) {
                String trimmedEmail = email.trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
                    emailSValid.set(false);
                    break;
                }
            }
        }
        return emailSValid.get();
    }


    public static void setupTextInputLayoutTextWatcher(Context context,TextInputLayout textInputLayout) {
        Objects.requireNonNull(textInputLayout.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                textInputLayout.setError(null);
                textInputLayout.setStartIconTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.blue)));
            }
        });
    }
}
