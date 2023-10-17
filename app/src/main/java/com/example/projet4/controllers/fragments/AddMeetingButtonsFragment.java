package com.example.projet4.controllers.fragments;

import static com.example.projet4.utils.Utils.convertTimeToMillis;
import static com.example.projet4.utils.Utils.createTimerPicker;
import static com.example.projet4.utils.Utils.showPicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projet4.R;
import com.example.projet4.databinding.FragAddMeetingButtonsBinding;
import com.example.projet4.models.Meeting;
import com.google.android.material.timepicker.MaterialTimePicker;

import java.util.Date;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMeetingButtonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMeetingButtonsFragment extends Fragment {
    public FragAddMeetingButtonsBinding bindingButtonsFrag;
    private DataPassListener dataPassListener;
    private Meeting mMeeting;
    private ArrayAdapter<String> adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPassListener = (DataPassListener) context;
    }

    public void clickDetected() {
        if (dataPassListener != null) {
            setColorPicker();
            dataPassListener.onDataPass(mMeeting);
        } else {
            Toast.makeText(requireContext(), "listener is null", Toast.LENGTH_SHORT).show();
        }

    }

    private void setColorPicker() {

        int color = Objects.requireNonNull(bindingButtonsFrag.colorPickerButton.getBackgroundTintList()).getDefaultColor();
        mMeeting.setColor(color);
    }


    public interface DataPassListener {
        void onDataPass(Meeting meeting);
    }

    public static AddMeetingButtonsFragment newInstance(DataPassListener dataPassListener) {
        AddMeetingButtonsFragment fragment = new AddMeetingButtonsFragment();
        fragment.dataPassListener = dataPassListener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bindingButtonsFrag = FragAddMeetingButtonsBinding.inflate(inflater, container, false);
        setAdapterRoom(savedInstanceState);

        // Inflate the layout for this fragment
        return bindingButtonsFrag.getRoot();
    }

    private void setAdapterRoom(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            String[] adapterData = savedInstanceState.getStringArray("adapterData");
            if (adapterData != null) {
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, adapterData);
                bindingButtonsFrag.roomSelected.setAdapter(adapter);
            }
        }
        if (adapter == null) {
            initRoomsAdapter();
        }
    }

    private void initRoomsAdapter() {
        // Set up the adapter and populate items
        String[] initialData = getResources().getStringArray(R.array.my_string_array);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, initialData);
        bindingButtonsFrag.roomSelected.setAdapter(adapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMeeting = new Meeting();

        initColorForPicker();
        setupListeners();


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            int count = adapter.getCount();
            String[] items = new String[count];
            for (int i = 0; i < count; i++) {
                items[i] = adapter.getItem(i);
            }
            outState.putStringArray("adapterData", items);
        }
    }

    private void initColorForPicker() {
        int color = -96704;
        bindingButtonsFrag.colorPickerButton.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    private void setupListeners() {

        bindingButtonsFrag.setTime.setOnClickListener(view -> {

            MaterialTimePicker picker = createTimerPicker();
            showPicker(getChildFragmentManager(), picker);
            setupListenerPicker(picker);
        });


        bindingButtonsFrag.roomSelected.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected item from the adapter
            String selectedItem = (String) parent.getItemAtPosition(position);
            mMeeting.setRoom(selectedItem);

        });

    }

    private void setupListenerPicker(MaterialTimePicker picker) {
        picker.addOnPositiveButtonClickListener(view -> {
            int hour = picker.getHour();
            int minutes = picker.getMinute();
            long time = convertTimeToMillis(hour, minutes);
            Date date = new Date();
            date.setTime(time);
            mMeeting.setHour(date);

            updateUITime(picker);

        });
    }

    private void updateUITime(MaterialTimePicker picker) {

        StringBuilder hourStringBuilder = new StringBuilder();
        int hour = picker.getHour();
        int minutes = picker.getMinute();
        if (hour < 10) {
            hourStringBuilder.append("0").append(hour).append(" : ");
        } else {
            hourStringBuilder.append(hour).append(" : ");
        }
        if (minutes < 10) {
            hourStringBuilder.append("0").append(minutes);
        } else {
            hourStringBuilder.append(minutes);
        }
        bindingButtonsFrag.setTime.setText(hourStringBuilder.toString());
    }
}