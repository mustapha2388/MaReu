package com.example.projet4.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet4.databinding.MeetingItemBinding;
import com.example.projet4.models.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder> {

    private List<Meeting> meetings;

    public MeetingAdapter(ArrayList<Meeting> meetings) {
        this.meetings = meetings;
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
        holder.displayMeeting(meetings.get(position));
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

        public void displayMeeting(Meeting meeting) {
            mMeetingItemBinding.infoHeaderMeeting.setText(meeting.getSubject());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                mMeetingItemBinding.emails.setText(String.join(", ", meeting.getEmails()));

            } else {
                for (String email : meeting.getEmails()) {
                    mMeetingItemBinding.emails.setText(email);

                }
            }
        }
    }
}
