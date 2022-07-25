package com.openclassrooms.mareu.service;



import com.openclassrooms.mareu.model.Meeting;

import java.util.List;


public interface MeetingApiService {

    /**
     * Get all my meetings
     *
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Delete a meeting
     *
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     *
     * @param meeting
     */
    void createMeeting(Meeting meeting);

}
