package ru.sbt.social.photo;

import ru.sbt.social.photo.domain.Photo;
import ru.sbt.social.profile.domain.Profile;

import java.util.List;

public interface PhotoService {

    Photo uploadPhoto(Profile profile);

    Profile viewPhoto(Photo photo);

    List<Photo> getAllPhotos(Profile profile);
}
