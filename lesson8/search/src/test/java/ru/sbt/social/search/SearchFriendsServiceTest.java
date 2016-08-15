package ru.sbt.social.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.sbt.social.photo.PhotoService;
import ru.sbt.social.profile.domain.Profile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SearchFriendsServiceTest {

    @Mock
    private Profile profile1;
    @Mock
    private Profile profile2;
    @Mock
    private PhotoService photoService;
    private SearchFriendsService searchFriendsService;

    @Before
    public void setUp() throws Exception {
        searchFriendsService = new SearchFriendsServiceImpl(photoService);
    }

    @Test
    public void addFriends() throws Exception {
        searchFriendsService.addFriends(profile1, profile2);

        assertTrue(searchFriendsService.getFriends(profile1).contains(profile2));
        assertTrue(searchFriendsService.getFriends(profile2).contains(profile1));
    }

    @Test
    public void youHaveNoFriends() {
        searchFriendsService.addFriends(profile1, profile1);

        assertFalse(searchFriendsService.getFriends(profile1).contains(profile2));
        assertFalse(searchFriendsService.getFriends(profile2).contains(profile1));
    }

    @Test
    public void getAllFriendsPhoto() throws Exception {
        searchFriendsService.addFriends(profile1, profile2);

        searchFriendsService.getAllFriendsPhoto(profile1);

        verify(photoService, never()).getAllPhotos(profile1);
        verify(photoService).getAllPhotos(profile2);
    }
}