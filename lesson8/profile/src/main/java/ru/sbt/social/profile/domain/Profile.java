package ru.sbt.social.profile.domain;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Profile {

    private static final AtomicLong ID_GENERATOR = new AtomicLong();

    private final long userId;
    private String name;
    private Date birthDate;
    private String email;

    public Profile() {
        userId = ID_GENERATOR.incrementAndGet();
    }

    public Profile(String name) {
        this();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }
}
