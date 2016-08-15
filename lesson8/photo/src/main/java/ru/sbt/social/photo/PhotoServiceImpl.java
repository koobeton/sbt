package ru.sbt.social.photo;

import ru.sbt.social.photo.domain.Photo;
import ru.sbt.social.profile.domain.Profile;

import java.util.*;
import java.util.stream.Collectors;

public class PhotoServiceImpl implements PhotoService {

    private final List<Photo> photos = new ArrayList<>();

    @Override
    public Photo uploadPhoto(Profile profile) {
        Photo photo = new Photo(profile);
        photos.add(photo);
        return photo;
    }

    @Override
    public Profile viewPhoto(Photo photo) {
        return photo.getUploadedBy();
    }

    @Override
    public List<Photo> getAllPhotos(Profile profile) {
        return photos.stream()
                .filter(p -> p.getUploadedBy().getUserId() == profile.getUserId())
                .collect(Collectors.toList());
    }
}
