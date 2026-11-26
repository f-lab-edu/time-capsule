package com.project.timecapsule.capsule;

import com.project.timecapsule.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.collection;
import static org.mockito.Mockito.mockStatic;

@DisplayName("캡슐 멤버 테스트")
class CapsuleMemberTest {

    private User singleUser;
    private User multiUser;
    private UUID participantId;
    private Location location;
    private LocalDateTime openAt;
    private LocalDateTime buryDeadlineAt;
    private UUID capsuleId;

    @BeforeEach
    void beforeEach() {
        singleUser = new User("싱글유저", "password");
        multiUser = new User("멀티유저", "password");
        participantId = UUID.fromString("00000000-0000-0000-0000-000000000003");
        location = new Location("서울숲", 37.55, 127.04);

        LocalDateTime now = LocalDateTime.now();
        openAt = now.plusYears(1);
        buryDeadlineAt = now.plusDays(7);
        capsuleId = UUID.fromString("00000000-0000-0000-0000-000000000004");
    }

    @Test
    void 멀티_캡슐_생성자는_멤버로_등록된다() {
        // given
        CapsuleMember expectedMember = CapsuleMember.create(capsuleId, multiUser.getId(), 0, 5);

        try (MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
            mockedUuid.when(UUID::randomUUID).thenReturn(capsuleId);

            // when
            Capsule capsule = multiUser.createCapsule("멀티 캡슐", openAt, location,
                    5, buryDeadlineAt);

            // then
            assertThat(capsule)
                    .extracting("members", collection(CapsuleMember.class))
                    .singleElement()
                    .usingRecursiveComparison()
                    .isEqualTo(expectedMember);
        }
    }

    @Test
    void 싱글_캡슐은_멤버_없이_생성된다() {
        // when
        Capsule actual = singleUser.createCapsule("싱글 캡슐", openAt, location);

        // then
        assertThat(actual)
                .extracting("members", collection(CapsuleMember.class))
                .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 20})
    void 멀티_캡슐의_정원이_차면_새로운_멤버를_추가할_수_없다(int maxMembers) {
        // given
        Capsule capsule = multiUser.createCapsule("멀티 캡슐", openAt, location,
                maxMembers, buryDeadlineAt);

        for (int cnt = 1; cnt < maxMembers; cnt++) {
            UUID memberId = new UUID(1L, cnt);
            capsule.registerMember(memberId);
        }

        // when, then
        assertThatThrownBy(() -> capsule.registerMember(participantId))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 같은_캡슐에_동일한_사용자를_중복_등록할_수_없다() {
        // given
        Capsule capsule = multiUser.createCapsule("멀티 캡슐", openAt, location,
                5, buryDeadlineAt);
        capsule.registerMember(participantId);
        UUID sameUserId = UUID.fromString(participantId.toString());

        // when, then
        assertThatThrownBy(() -> capsule.registerMember(sameUserId))
                .isInstanceOf(IllegalStateException.class);
    }
}
