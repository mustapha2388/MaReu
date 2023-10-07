package com.example.projet4.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.projet4.models.Meeting;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class Utils {

    public static final String jsonFileName = "meetings.json";

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
}
