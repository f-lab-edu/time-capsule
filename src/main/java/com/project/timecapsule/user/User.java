package com.project.timecapsule.user;

import com.project.timecapsule.capsule.Capsule;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String nickname;
    private final String password;

    public User(UUID id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
    }

    public Capsule createSingleCapsule(String title, LocalDateTime openAt,
                                       String placeName, double latitude, double longitude) {
        return Capsule.single(id, title, openAt, placeName, latitude, longitude);
    }
}
