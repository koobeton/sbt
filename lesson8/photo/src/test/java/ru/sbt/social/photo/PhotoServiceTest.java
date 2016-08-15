package ru.sbt.social.photo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.sbt.social.photo.domain.Photo;
import ru.sbt.social.profile.domain.Profile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PhotoServiceTest {

    @Mock
    private Profile profile1;
    @Mock
    private Profile profile2;
    private PhotoService photoService;

    @Before
    public void setUp() throws Exception {
        photoService = new PhotoServiceImpl();
    }

    @Test
    public void uploadPhoto() throws Exception {
        when(profile1.getName()).thenReturn("Alice");

        Photo photo = photoService.uploadPhoto(profile1);
        assertEquals("Alice", photo.getUploadedBy().getName());
    }

    @Test
    public void viewPhoto() throws Exception {
        when(profile1.getName()).thenReturn("Bob");

        Photo photo = new Photo(profile1);
        Profile uploadedBy = photoService.viewPhoto(photo);
        assertEquals("Bob", uploadedBy.getName());
    }

    @Test
    public void getAllPhotos() throws Exception {
        when(profile1.getUserId()).thenReturn(100L);
        when(profile2.getUserId()).thenReturn(500L);

        photoService.uploadPhoto(profile1);
        photoService.uploadPhoto(profile2);
        photoService.uploadPhoto(profile1);
        photoService.uploadPhoto(profile1);
        photoService.uploadPhoto(profile2);

        assertEquals(3, photoService.getAllPhotos(profile1).size());
        assertEquals(2, photoService.getAllPhotos(profile2).size());

        verify(profile1, atLeastOnce()).getUserId();
        verify(profile2, atLeastOnce()).getUserId();
    }

    @Test
    public void userHasNotUploadedPhotos() throws Exception {
        when(profile1.getUserId()).thenReturn(100L);
        when(profile2.getUserId()).thenReturn(500L);

        photoService.uploadPhoto(profile1);

        assertEquals(1, photoService.getAllPhotos(profile1).size());
        assertEquals(0, photoService.getAllPhotos(profile2).size());

        verify(profile1, atLeastOnce()).getUserId();
        verify(profile2, atLeastOnce()).getUserId();
    }
}