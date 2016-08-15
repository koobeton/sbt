package ru.sbt.social.photo.domain;

import ru.sbt.social.profile.domain.Profile;

public class Photo {

    private final Profile uploadedBy;

    public Photo(Profile uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Profile getUploadedBy() {
        return uploadedBy;
    }
}
