package com.project.timecapsule.capsule;

import com.project.timecapsule.enums.CapsuleMode;
import com.project.timecapsule.enums.ErrorCode;

import java.time.LocalDateTime;
import java.util.UUID;

public final class MultiCapsule extends Capsule {

    private final LocalDateTime buryDeadlineAt;

    MultiCapsule(UUID createdBy, String title, LocalDateTime openAt, Location location,
                 int maxMembers, LocalDateTime buryDeadlineAt) {
        super(createdBy, title, CapsuleMode.MULTI, maxMembers, openAt, location);

        validateMaxMembers(maxMembers);
        validateBuryDeadlineAt(buryDeadlineAt, openAt);

        this.buryDeadlineAt = buryDeadlineAt;
    }

    private static void validateMaxMembers(int maxMembers) {
        if (maxMembers < 2 || maxMembers > 20) {
            throw new IllegalArgumentException(ErrorCode.MAX_MEMBERS_OUT_OF_RANGE.getMessage());
        }
    }

    private void validateBuryDeadlineAt(LocalDateTime buryDeadlineAt, LocalDateTime openAt) {
        LocalDateTime createdAt = getCreatedAt();

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

    @Override
    public boolean isHost(UUID userId) {
        return isCreatedBy(userId);
    }
}
