package com.project.timecapsule.user;

import com.project.timecapsule.capsule.Capsule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    @Getter
    private final UUID id;
    private final String nickname;
    private final String password;

    public User(String nickname, String password) {
        this.id = UUID.randomUUID();
        this.nickname = nickname;
        this.password = password;
    }

    public Capsule createSingleCapsule(String title, LocalDateTime openAt,
                                       String placeName, double latitude, double longitude) {
        return Capsule.single(id, title, openAt, placeName, latitude, longitude);
    }

    public Capsule createMultiCapsule(String title, LocalDateTime openAt,
                                      String placeName, double latitude, double longitude,
                                      int maxMembers, LocalDateTime buryDeadlineAt) {
        return Capsule.multi(id, title, openAt, placeName, latitude, longitude, maxMembers, buryDeadlineAt);
    }
}
