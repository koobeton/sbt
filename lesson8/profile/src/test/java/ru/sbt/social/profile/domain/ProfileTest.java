package ru.sbt.social.profile.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileTest {

    private Profile profile1;
    private Profile profile2;

    @Before
    public void setUp() throws Exception {
        profile1 = new Profile("Alice");
        profile2 = new Profile("Bob");
    }

    @Test
    public void createProfile() throws Exception {
        assertEquals(1, profile1.getUserId());
        assertEquals("Alice", profile1.getName());

        assertEquals(2, profile2.getUserId());
        assertEquals("Bob", profile2.getName());
    }
}