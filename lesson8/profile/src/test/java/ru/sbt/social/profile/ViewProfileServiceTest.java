package ru.sbt.social.profile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.sbt.social.profile.domain.Profile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewProfileServiceTest {

    @Mock
    private Profile profile;
    private ViewProfileService viewProfileService;

    @Before
    public void setUp() throws Exception {
        viewProfileService = new ViewProfileServiceImpl();
    }

    @Test
    public void viewProfile() throws Exception {
        viewProfileService.viewProfile(profile);
        verify(profile).getUserId();
        verify(profile).getName();
        verify(profile).getBirthDate();
        verify(profile).getEmail();
    }
}