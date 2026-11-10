package com.project.timecapsule.capsule;

import com.project.timecapsule.enums.CapsuleMode;
import com.project.timecapsule.enums.CapsuleStatus;
import com.project.timecapsule.enums.ErrorCode;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class Capsule {

    private final UUID id;
    private final String title;
    private final CapsuleMode mode;
    private final int maxMembers;
    private CapsuleStatus status;
    private final LocalDateTime buryDeadlineAt;
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
                    LocalDateTime buryDeadlineAt, LocalDateTime openAt,
                    String placeName, double latitude, double longitude) {
        LocalDateTime createdAt = LocalDateTime.now();

        //검증
        validateTitle(title);
        validateOpenAt(openAt, createdAt);
        if (mode == CapsuleMode.MULTI) {
            validateMaxMembers(maxMembers);
            validateBuryDeadlineAt(buryDeadlineAt, openAt, createdAt);
        }

        this.id = UUID.randomUUID();
        this.title = title;
        this.mode = mode;
        this.maxMembers = maxMembers;
        this.status = CapsuleStatus.DRAFT;
        this.buryDeadlineAt = buryDeadlineAt;
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
        return new Capsule(createdBy, title, CapsuleMode.SINGLE, 1,
                null, openAt, placeName, latitude, longitude);
    }

    public static Capsule multi(UUID createdBy, String title, LocalDateTime openAt,
                                String placeName, double latitude, double longitude,
                                int maxMembers, LocalDateTime buryDeadlineAt) {
        return new Capsule(createdBy, title, CapsuleMode.MULTI, maxMembers,
                buryDeadlineAt, openAt, placeName, latitude, longitude);
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

    private static void validateMaxMembers(int maxMembers) {
        if (maxMembers < 2 || maxMembers > 20) {
            throw new IllegalArgumentException(ErrorCode.MAX_MEMBERS_OUT_OF_RANGE.getMessage());
        }
    }

    private static void validateBuryDeadlineAt(LocalDateTime buryDeadlineAt, LocalDateTime openAt,
                                               LocalDateTime createdAt) {
        if (buryDeadlineAt == null) {
            throw new IllegalArgumentException(ErrorCode.BURY_DEADLINE_AT_REQUIRED.getMessage());
        }
        if (!buryDeadlineAt.isAfter(createdAt)) {
            throw new IllegalArgumentException(ErrorCode.BURY_DEADLINE_AT_NOT_IN_FUTURE.getMessage());
        }
        if (buryDeadlineAt.isAfter(createdAt.plusDays(14))) {
            throw new IllegalArgumentException(ErrorCode.BURY_DEADLINE_AT_TOO_FAR_IN_FUTURE.getMessage());
        }
        if (!buryDeadlineAt.isBefore(openAt)) {
            throw new IllegalArgumentException(ErrorCode.BURY_DEADLINE_AT_NOT_BEFORE_OPEN_AT.getMessage());
        }
    }


    public boolean isHost(UUID userId) {
        return mode == CapsuleMode.MULTI && createdBy.equals(userId);
    }

}
