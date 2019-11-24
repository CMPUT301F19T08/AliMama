package com.example.alimama;
import com.example.alimama.Model.MoodEvent;
import com.google.firebase.firestore.GeoPoint;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for MoodEvent Model class
 *
 * */

public class MoodEventTest {


    private MoodEvent mockMoodEvent() {
        return new MoodEvent("test", "sad");

    }
    @Test
    public void testGetUsername() {
        MoodEvent moodEvent = mockMoodEvent();
        assertEquals("test",moodEvent.getUsername() );

    }
    @Test
    public void testSetUsername() {
        MoodEvent moodEvent = mockMoodEvent();
        assertEquals("test", moodEvent.getUsername());
        moodEvent.setUsername("changed");
        assertEquals("changed", moodEvent.getUsername());

    }
    @Test
    public void testGetEmotionalState() {
        MoodEvent moodEvent = mockMoodEvent();
        assertEquals("sad",moodEvent.getEmotionalState());

    }
    @Test
    public void testSetEmotionalState() {
        MoodEvent moodEvent = mockMoodEvent();
        assertEquals("sad", moodEvent.getEmotionalState() );
        moodEvent.setEmotionalState("happy");
        assertEquals("happy", moodEvent.getEmotionalState());

    }
    @Test
    public void testGetEmoticon() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getEmoticon());

    }
    @Test
    public void testSetEmoticon() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getEmoticon());
        moodEvent.setEmoticon("emoticon");
        assertEquals("emoticon", moodEvent.getEmoticon() );


    }
    @Test
    public void testGetReasonInText() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getReasonInText());

    }
    @Test
    public void testSetReasonInText() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getReasonInText());
        moodEvent.setReasonInText("reason in text");
        assertEquals("reason in text", moodEvent.getReasonInText() );

    }
    @Test
    public void testGetPathToPhoto() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getPathToPhoto());

    }
    @Test
    public void testSetPathToPhoto() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getPathToPhoto());
        moodEvent.setPathToPhoto("path/to/photo");
        assertEquals("path/to/photo", moodEvent.getPathToPhoto());

    }
    @Test
    public void testGetLocationOfMoodEvent() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getLocationOfMoodEvent());

    }
    @Test
    public void testSetLocationOfMoodEvent() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getLocationOfMoodEvent());
        moodEvent.setLocationOfMoodEvent(new GeoPoint(12.0, 12.0));
        assertEquals(12.0, moodEvent.getLocationOfMoodEvent().getLatitude());
        assertEquals(12.0, moodEvent.getLocationOfMoodEvent().getLongitude());

    }
    @Test
    public void testGetSocialSituation() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getSocialSituation());

    }
    @Test
    public void testSetSocialSituation() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNull(moodEvent.getSocialSituation());
        moodEvent.setSocialSituation("alone");
        assertEquals("alone", moodEvent.getSocialSituation());

    }

    @Test
    public void testGetDate() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNotNull(moodEvent.getDate());

    }
    @Test
    public void testSetDate() {
        MoodEvent moodEvent = mockMoodEvent();
        assertNotNull(moodEvent.getDate());
        moodEvent.setDate(null);
        assertNull(moodEvent.getDate());

    }
}
