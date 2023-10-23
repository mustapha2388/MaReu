package com.example.projet4;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.projet4.models.Meeting;
import com.example.projet4.repository.MeetingRepository;
import com.example.projet4.viewModel.MeetingViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Mock
    private MeetingRepository mMeetingRepository;

    @Mock
    private Application mockApplication;
    private MeetingViewModel mMeetingViewModel;

    private Meeting meetingInserted;
    private Meeting meetingToDelete;

    private MutableLiveData<List<Meeting>> liveData;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Date hour_meeting = new Date();
        hour_meeting.setTime(57600000);
        meetingInserted = new Meeting(-61180, "Réunion Z", hour_meeting, "Mario", new ArrayList<>(Collections.singleton("paul@lamzome.com, viviane@lamzone.com")));

        Date hour_meeting_2 = new Date();
        hour_meeting_2.setTime(57600000);
        meetingToDelete = new Meeting(-25143, "Réunion A", hour_meeting_2, "Peach", new ArrayList<>(Collections.singleton("maxime@lamzone.com, alex@lamzome.com")));

        liveData = new MutableLiveData<>();

        when(mMeetingRepository.allMeetingsLiveData()).thenReturn(liveData);
        mMeetingViewModel = new MeetingViewModel(mockApplication, mMeetingRepository);

    }


    @Test
    public void testLiveDataWithMeetings() {
        // Créez une liste de chaînes de caractères fictive que vous attendez
        List<Meeting> meetingListFromJson = getMeetingList(); // Remplacez par la liste que vous attendez

        // Créez un MutableLiveData simulé pour la liste de chaînes
        MutableLiveData<List<Meeting>> liveData = new MutableLiveData<>();
        liveData.setValue(meetingListFromJson);

        // Définissez le comportement du repository simulé
        when(mMeetingRepository.allMeetingsLiveData()).thenReturn(liveData);

        mMeetingViewModel = new MeetingViewModel(mockApplication, mMeetingRepository);


        // Obtenez la liste de chaînes à partir du ViewModel
        List<Meeting> meetingListFromViewModel = mMeetingViewModel.allMeetingsLiveData().getValue();

        // Vérifiez si la liste retournée correspond à la liste attendue
        assertEquals(meetingListFromJson, meetingListFromViewModel);
    }


    @Test
    public void deleteMeetings() {

        List<Meeting> meetingListFromRepository = new ArrayList<>(getMeetingList());
        liveData.setValue(meetingListFromRepository);


        // Utilisez doAnswer pour mettre à jour la liste de réunions dans le mock du MeetingRepository lors de l'insertion
        doAnswer(invocation -> {
            meetingListFromRepository.remove(meetingToDelete);
            liveData.setValue(meetingListFromRepository);
            return null;
        }).when(mMeetingRepository).delete(meetingToDelete);


        int sizeListBeforeDelete = Objects.requireNonNull(mMeetingViewModel.allMeetingsLiveData().getValue()).size();

        mMeetingViewModel.delete(meetingToDelete);

        int sizeListAfterDelete = Objects.requireNonNull(mMeetingViewModel.allMeetingsLiveData().getValue()).size();

        verify(mMeetingRepository).delete(meetingToDelete);
        assertEquals(sizeListBeforeDelete - 1, sizeListAfterDelete);

    }


    @Test
    public void insertMeeting() {
        List<Meeting> meetingListFromRepository = new ArrayList<>(getMeetingList());
        liveData.setValue(meetingListFromRepository);

        // Utilisez doAnswer pour mettre à jour la liste de réunions dans le mock du MeetingRepository lors de l'insertion
        doAnswer(invocation -> {
            meetingListFromRepository.add(meetingInserted);
            liveData.setValue(meetingListFromRepository);
            return null;
        }).when(mMeetingRepository).insert(meetingInserted);


        int sizeListBeforeInsertion = Objects.requireNonNull(mMeetingViewModel.allMeetingsLiveData().getValue()).size();

        mMeetingViewModel.insert(meetingInserted);

        int sizeListAfterInsertion = Objects.requireNonNull(mMeetingViewModel.allMeetingsLiveData().getValue()).size();

        verify(mMeetingRepository).insert(meetingInserted);
        assertEquals(sizeListBeforeInsertion + 1, sizeListAfterInsertion);
    }


    @Test
    public void FilterByRoom() {


        when(mMeetingRepository.filterByRoom("Peach")).thenReturn(new ArrayList<>(
                Collections.singletonList(getMeetingList().get(0))));

        List<Meeting> listFiltered = Objects.requireNonNull(mMeetingViewModel.filterByRoom("Peach"));
        assertEquals(1, listFiltered.size());

        Meeting meeting_filter = listFiltered.get(0);
        assertEquals("Peach", meeting_filter.getRoom());
    }

    @Test
    public void FilterByHour() {

        when(mMeetingRepository.filterByHour(0)).thenReturn(new ArrayList<>(
                Collections.singletonList(getMeetingList().get(2))));

        List<Meeting> listFiltered = Objects.requireNonNull(mMeetingViewModel.filterByHour(0));
        assertEquals(1, listFiltered.size());

        Meeting meeting_filter = listFiltered.get(0);
        assertEquals(0, meeting_filter.getHour().getTime());
    }


    private List<Meeting> getMeetingList() {

        Date hour_meeting_1 = new Date();
        Date hour_meeting_2 = new Date();
        Date hour_meeting_3 = new Date();

        hour_meeting_1.setTime(57600000);
        hour_meeting_2.setTime(50400000);
        hour_meeting_3.setTime(0);

        return Arrays.asList(
                new Meeting(-25143, "Réunion A", hour_meeting_1, "Peach", new ArrayList<>(Collections.singleton("maxime@lamzone.com, alex@lamzome.com"))),
                new Meeting(-61180, "Réunion B", hour_meeting_2, "Mario", new ArrayList<>(Collections.singleton("paul@lamzome.com, viviane@lamzone.com"))),
                new Meeting(-16580839, "Réunion C", hour_meeting_3, "Luigi", new ArrayList<>(Collections.singleton("amandine@lamzome.com, luc@lamzone.com")))
        );
    }
}