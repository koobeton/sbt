package ru.sbt.social.profile;

import ru.sbt.social.profile.domain.Profile;

public class ViewProfileServiceImpl implements ViewProfileService {

    @Override
    public void viewProfile(Profile profile) {

        System.out.printf("ID: %d%n", profile.getUserId());
        System.out.printf("Name: %s%n", profile.getName());
        System.out.printf("Birth date: %s%n", profile.getBirthDate());
        System.out.printf("E-mail: %s%n", profile.getEmail());
    }
}
