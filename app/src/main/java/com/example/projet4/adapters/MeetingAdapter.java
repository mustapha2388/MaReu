package com.example.projet4.adapters;

import static com.example.projet4.utils.Utils.getInfoMeeting;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet4.databinding.MeetingItemBinding;
import com.example.projet4.models.Meeting;
import com.example.projet4.services.OnMeetingListener;

import java.util.ArrayList;
import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {

    private final List<Meeting> meetings;
    private final OnMeetingListener OnMeetingListener;

    public MeetingAdapter(ArrayList<Meeting> meetings, OnMeetingListener OnMeetingListener) {
        this.meetings = meetings;
        this.OnMeetingListener = OnMeetingListener;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MeetingItemBinding binding = MeetingItemBinding.inflate(layoutInflater, parent, false);
        return new MeetingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.MeetingViewHolder holder, int position) {
        holder.displayMeeting(meetings.get(position), OnMeetingListener);
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        MeetingItemBinding mMeetingItemBinding;

        public MeetingViewHolder(@NonNull MeetingItemBinding binding) {
            super(binding.getRoot());
            this.mMeetingItemBinding = binding;
        }

        public void displayMeeting(Meeting meeting, OnMeetingListener OnMeetingListener) {
            String info = getInfoMeeting(meeting);
            mMeetingItemBinding.infoHeaderMeeting.setText(info);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                mMeetingItemBinding.circle.setColorFilter(meeting.getColor());
                mMeetingItemBinding.emails.setText(String.join(", ", meeting.getEmails()));
            } else {
                for (String email : meeting.getEmails()) {
                    mMeetingItemBinding.circle.setColorFilter(meeting.getColor());
                    mMeetingItemBinding.emails.setText(email);

                }
            }
            mMeetingItemBinding.trash.setOnClickListener(view -> OnMeetingListener.onItemClick(meeting));
        }
    }
}
