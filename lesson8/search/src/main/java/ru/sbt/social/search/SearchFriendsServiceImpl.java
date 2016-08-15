package ru.sbt.social.search;

import ru.sbt.social.photo.PhotoService;
import ru.sbt.social.photo.domain.Photo;
import ru.sbt.social.profile.domain.Profile;

import java.util.*;
import java.util.List;

public class SearchFriendsServiceImpl implements SearchFriendsService {

    private final HashMap<Profile, Set<Profile>> relations = new HashMap<>();
    private final PhotoService photoService;

    public SearchFriendsServiceImpl(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Override
    public void addFriends(Profile profile, Profile newFriend) {
        relations.putIfAbsent(profile, new HashSet<>());
        relations.get(profile).add(newFriend);

        relations.putIfAbsent(newFriend, new HashSet<>());
        relations.get(newFriend).add(profile);
    }

    @Override
    public Set<Profile> getFriends(Profile profile) {
        return relations.getOrDefault(profile, Collections.emptySet());
    }

    @Override
    public List<Photo> getAllFriendsPhoto(Profile profile) {
        List<Photo> allFriendsPhotos = new ArrayList<>();
        getFriends(profile)
                .forEach(f -> allFriendsPhotos.addAll(photoService.getAllPhotos(f)));
        return allFriendsPhotos;
    }
}
