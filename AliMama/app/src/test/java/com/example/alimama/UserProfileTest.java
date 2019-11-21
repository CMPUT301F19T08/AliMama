package com.example.alimama;

import com.example.alimama.Model.UserProfile;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for UserProfile Model Class
 * */
public class UserProfileTest {

    private UserProfile mockUserProfile() {
        return new UserProfile("test", "password");
    }
    @Test
    public void testGetUsername() {
        UserProfile userProfile = mockUserProfile();
        assertEquals("test", userProfile.getUsername());

    }
    @Test
    public void testSetUsername() {
        UserProfile userProfile = mockUserProfile();
        assertEquals("test", userProfile.getUsername());
        userProfile.setUsername("changed");
        assertEquals("changed", userProfile.getUsername());

    }
    @Test
    public void testGetPassword() {
        UserProfile userProfile = mockUserProfile();
        assertEquals("password", userProfile.getPassword());


    }
    @Test
    public void testSetPassword() {
        UserProfile userProfile = mockUserProfile();
        assertEquals("password", userProfile.getPassword());
        userProfile.setPassword("changed");
        assertEquals("changed", userProfile.getPassword());


    }

}
