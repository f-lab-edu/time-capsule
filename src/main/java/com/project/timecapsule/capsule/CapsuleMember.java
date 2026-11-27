package com.project.timecapsule.capsule;

import com.project.timecapsule.enums.CapsuleMemberRole;

import java.util.Objects;
import java.util.UUID;

public final class CapsuleMember {

    private final UUID capsuleId;
    private final UUID userId;
    private final CapsuleMemberRole role;

    private CapsuleMember(UUID capsuleId, UUID userId, CapsuleMemberRole role) {
        this.capsuleId = capsuleId;
        this.userId = userId;
        this.role = role;
    }

    static CapsuleMember create(UUID capsuleId, UUID userId, CapsuleMemberRole role) {
        return new CapsuleMember(capsuleId, userId, role);
    }

    boolean isUser(UUID userId) {
        return Objects.equals(this.userId, userId);
    }
}
