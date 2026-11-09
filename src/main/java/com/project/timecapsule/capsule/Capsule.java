package com.project.timecapsule.capsule;

import com.project.timecapsule.enums.CapsuleMode;
import com.project.timecapsule.enums.CapsuleStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Capsule {

    private final UUID id;
    private final String title;
    private final CapsuleMode mode;
    private final int maxMembers;
    private CapsuleStatus status;
    private final LocalDateTime openAt;

    private final String placeName;
    private final double latitude;
    private final double longitude;

    private final LocalDateTime createdAt;
    private final UUID createdBy;
    private LocalDateTime modifiedAt;
    private UUID modifiedBy;
    private boolean isDeleted;
    private LocalDateTime deletedAt;
    private UUID deletedBy;

    private Capsule(UUID createdBy, String title, CapsuleMode mode, int maxMembers,
                    LocalDateTime openAt, String placeName, double latitude, double longitude) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.mode = mode;
        this.maxMembers = maxMembers;
        this.status = CapsuleStatus.DRAFT;
        this.openAt = openAt;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;

        this.createdAt = LocalDateTime.now();
        this.createdBy = createdBy;
        this.modifiedAt = null;
        this.modifiedBy = null;
        this.isDeleted = false;
        this.deletedAt = null;
        this.deletedBy = null;
    }

    public static Capsule single(UUID createdBy, String title, LocalDateTime openAt,
                                 String placeName, double latitude, double longitude) {
        return new Capsule(createdBy, title, CapsuleMode.SINGLE, 1,
                openAt, placeName, latitude, longitude);
    }

    public boolean isCreatedBy(UUID userId) {
        return createdBy.equals(userId);
    }

}
