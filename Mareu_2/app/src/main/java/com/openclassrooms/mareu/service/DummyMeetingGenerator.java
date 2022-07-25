package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyMeetingGenerator {

    /**
     * The constant DUMMY_MEETING.
     */
    public static List<Meeting> DUMMY_MEETING = Arrays.asList(


            new Meeting( DummySalleGenerator.DUMMY_SALLES.get(0),"réunion 1", Arrays.asList("test@1.com","test@2.com"), new Date())
    );

    /**
     * Generate meetings list.
     *
     * @return the list
     */
    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }
}
