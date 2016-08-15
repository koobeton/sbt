package ru.sbt.social.search;

import ru.sbt.social.photo.domain.Photo;
import ru.sbt.social.profile.domain.Profile;

import java.util.List;
import java.util.Set;

public interface SearchFriendsService {

    void addFriends(Profile profile, Profile newFriend);

    Set<Profile> getFriends(Profile profile);

    List<Photo> getAllFriendsPhoto(Profile profile);
}
