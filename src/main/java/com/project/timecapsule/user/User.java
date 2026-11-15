package com.project.timecapsule.user;

import com.project.timecapsule.capsule.Capsule;
import com.project.timecapsule.capsule.Location;
import com.project.timecapsule.capsule.MultiCapsule;
import com.project.timecapsule.capsule.SingleCapsule;
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

    public Capsule createCapsule(String title, LocalDateTime openAt, Location location) {
        return SingleCapsule.create(id, title, openAt, location);
    }

    public Capsule createCapsule(String title, LocalDateTime openAt, Location location,
                                 int maxMembers, LocalDateTime buryDeadlineAt) {
        return MultiCapsule.create(id, title, openAt, location, maxMembers, buryDeadlineAt);
    }
}
