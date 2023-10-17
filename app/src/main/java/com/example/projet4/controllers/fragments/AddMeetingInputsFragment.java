package com.example.projet4.controllers.fragments;

import static com.example.projet4.utils.Utils.AreEmailValid;
import static com.example.projet4.utils.Utils.setupTextInputLayoutTextWatcher;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet4.R;
import com.example.projet4.databinding.FragAddMeetingInputsBinding;
import com.example.projet4.models.Meeting;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMeetingInputsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMeetingInputsFragment extends Fragment {

    public FragAddMeetingInputsBinding bindingInputsFrag;
    private ClickListener clickListener;
    private ClickListenerSavaData clickListenerSavaData;

    public interface ClickListener {
        void onClickListener();
    }

    public interface ClickListenerSavaData {
        void onClickListenerSaveData(Meeting meeting);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        clickListener = (AddMeetingInputsFragment.ClickListener) context;
        clickListenerSavaData = (AddMeetingInputsFragment.ClickListenerSavaData) context;
    }

    public static AddMeetingInputsFragment newInstance(ClickListener clickListener, ClickListenerSavaData clickListenerSavaData) {
        AddMeetingInputsFragment fragment = new AddMeetingInputsFragment();
        fragment.clickListener = clickListener;
        fragment.clickListenerSavaData = clickListenerSavaData;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindingInputsFrag = FragAddMeetingInputsBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return bindingInputsFrag.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupButtonListener();
        setupTextInputLayoutTextWatcher(requireContext(), bindingInputsFrag.subject);
        setupTextInputLayoutTextWatcher(requireContext(), bindingInputsFrag.emails);
    }

    private void setupButtonListener() {
        bindingInputsFrag.insertMeeting.setOnClickListener(view -> clickListener.onClickListener());

    }

    public void receiveData(Meeting meeting) {

        meeting.setSubject(Objects.requireNonNull(bindingInputsFrag.subject.getEditText()).getText().toString());
        ArrayList<String> emails = new ArrayList<>();
        emails.add(Objects.requireNonNull(bindingInputsFrag.emails.getEditText()).getText().toString());
        meeting.setEmails(emails);
        updateUIError();
        clickListenerSavaData.onClickListenerSaveData(meeting);
    }

    private void updateUIError() {
        if (Objects.requireNonNull(bindingInputsFrag.subject.getEditText()).getText().toString().equals("")) {
            bindingInputsFrag.subject.setError("*Field necessary");
            bindingInputsFrag.subject.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        }
        if (Objects.requireNonNull(bindingInputsFrag.emails.getEditText()).getText().toString().equals("")) {
            bindingInputsFrag.emails.setError("*Field necessary");
            bindingInputsFrag.emails.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        } else if (!AreEmailValid(bindingInputsFrag.emails)) {
            bindingInputsFrag.emails.setError("*Invalid email address");
            bindingInputsFrag.emails.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        }
    }


}