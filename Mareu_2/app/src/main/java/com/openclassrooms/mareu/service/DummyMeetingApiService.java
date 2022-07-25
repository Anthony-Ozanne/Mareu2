package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();


    /**
     * {@inheritDoc}
     * Get all my list Meetings
     *
     * @return meetings
     * Retourne la liste des réunions
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }


    /**
     * {@inheritDoc}
     * Deletes a meeting
     * @param meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /**
     * {@inheritDoc}
     * Ajoute une réunion
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

}
