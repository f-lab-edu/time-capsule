package com.project.timecapsule.capsule;

import com.project.timecapsule.enums.CapsuleMode;
import com.project.timecapsule.enums.CapsuleStatus;
import com.project.timecapsule.enums.ErrorCode;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Capsule {

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

    Capsule(UUID createdBy, String title, CapsuleMode mode, int maxMembers,
            LocalDateTime openAt, String placeName, double latitude, double longitude) {
        LocalDateTime createdAt = LocalDateTime.now();

        //검증
        validateTitle(title);
        validateOpenAt(openAt, createdAt);

        this.id = UUID.randomUUID();
        this.title = title;
        this.mode = mode;
        this.maxMembers = maxMembers;
        this.status = CapsuleStatus.DRAFT;
        this.openAt = openAt;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;

        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.modifiedAt = null;
        this.modifiedBy = null;
        this.isDeleted = false;
        this.deletedAt = null;
        this.deletedBy = null;
    }

    public static Capsule single(UUID createdBy, String title, LocalDateTime openAt,
                                 String placeName, double latitude, double longitude) {
        return new SingleCapsule(createdBy, title, openAt, placeName, latitude, longitude);
    }

    public static Capsule multi(UUID createdBy, String title, LocalDateTime openAt,
                                String placeName, double latitude, double longitude,
                                int maxMembers, LocalDateTime buryDeadlineAt) {
        return new MultiCapsule(createdBy, title, openAt, placeName, latitude, longitude,
                maxMembers, buryDeadlineAt);
    }

    private static void validateTitle(String title) {
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException(ErrorCode.TITLE_REQUIRED.getMessage());
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException(ErrorCode.TITLE_TOO_LONG.getMessage());
        }
    }

    private static void validateOpenAt(LocalDateTime openAt, LocalDateTime createdAt) {
        if (openAt == null) {
            throw new IllegalArgumentException(ErrorCode.OPEN_AT_REQUIRED.getMessage());
        }
        if (!openAt.isAfter(createdAt)) {
            throw new IllegalArgumentException(ErrorCode.OPEN_AT_NOT_IN_FUTURE.getMessage());
        }
        if (openAt.isAfter(createdAt.plusYears(10))) {
            throw new IllegalArgumentException(ErrorCode.OPEN_AT_TOO_FAR_IN_FUTURE.getMessage());
        }
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }

    boolean isCreatedBy(UUID userId) {
        return createdBy.equals(userId);
    }


    public boolean isHost(UUID userId) {
        return false;
    }

}
