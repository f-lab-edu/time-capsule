package com.project.timecapsule.capsule;

import com.project.timecapsule.enums.CapsuleMode;

import java.time.LocalDateTime;
import java.util.UUID;

public final class SingleCapsule extends Capsule {

    private SingleCapsule(UUID createdBy, String title, LocalDateTime openAt, Location location) {
        super(createdBy, title, CapsuleMode.SINGLE, 1, openAt, location);
    }

    public static SingleCapsule create(UUID createdBy, String title, LocalDateTime openAt, Location location) {
        return new SingleCapsule(createdBy, title, openAt, location);
    }
}
