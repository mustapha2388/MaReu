package com.example.projet4.controllers.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet4.repository.MeetingRepository;
import com.example.projet4.viewModel.MeetingViewModel;

@SuppressWarnings("unchecked")
public class MeetingViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final MeetingRepository meetingRepository;

    public MeetingViewModelFactory(Application application, MeetingRepository meetingRepository) {
        this.application = application;
        this.meetingRepository = meetingRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingViewModel.class)) {
            return (T) new MeetingViewModel(application, meetingRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
