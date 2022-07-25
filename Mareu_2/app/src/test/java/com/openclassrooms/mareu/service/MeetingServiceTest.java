package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService service;


    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

   /**
     * Unit test for Meeting
     */
    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> neighbours = service.getMeetings();
        List<Meeting> expectedNeighbours = DummyMeetingGenerator.DUMMY_MEETING;
        assertThat(service.getMeetings(), IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    /*@Test
    public void createNeighbourWithSuccess() {
        // créer une réunion
        Meeting meetingToCreate = new Meeting();
        service.createMeeting(meetingToCreate);
        // verifier que la liste contient bien une réunion en plus
        assertTrue(service.getMeetings().contains(meetingToCreate));
    } */

    @Test
    public void deleteNeighbourWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

}
