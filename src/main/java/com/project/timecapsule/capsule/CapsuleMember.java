package com.project.timecapsule.capsule;

import com.project.timecapsule.enums.ErrorCode;

import java.util.Objects;
import java.util.UUID;

public final class CapsuleMember {

    private final UUID capsuleId;
    private final UUID userId;

    private CapsuleMember(UUID capsuleId, UUID userId) {
        this.capsuleId = capsuleId;
        this.userId = userId;
    }

    static CapsuleMember create(UUID capsuleId, UUID userId, int memberCount, int maxMembers) {
        if (memberCount >= maxMembers) {
            throw new IllegalStateException(ErrorCode.CAPSULE_IS_FULL.getMessage());
        }
        return new CapsuleMember(capsuleId, userId);
    }

    boolean isUser(UUID userId) {
        return Objects.equals(this.userId, userId);
    }

}
