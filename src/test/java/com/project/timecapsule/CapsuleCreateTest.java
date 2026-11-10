package com.project.timecapsule;

import com.project.timecapsule.capsule.Capsule;
import com.project.timecapsule.enums.ErrorCode;
import com.project.timecapsule.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;

@DisplayName("캡슐 생성 테스트")
class CapsuleCreateTest {

    private User singleUser;
    private User multiUser;

    @BeforeEach
    void beforeEach() {
        singleUser = new User("싱글유저", "password");
        multiUser = new User("멀티유저", "password");
    }

    @Test
    void 싱글_캡슐을_생성할_수_있다() {
        // given
        String title = "싱글 캡슐";
        LocalDateTime openAt = LocalDateTime.now().plusYears(1);
        String placeName = "서울숲";
        double latitude = 37.5496;
        double longitude = 127.0416;
        UUID fixedId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        try (MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
            mockedUuid.when(UUID::randomUUID).thenReturn(fixedId);
            Capsule expected = Capsule.single(
                    singleUser.getId(), title, openAt, placeName, latitude, longitude);

            // when
            Capsule actual = singleUser.createSingleCapsule(title, openAt, placeName, latitude, longitude);

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .ignoringFields("createdAt")
                    .isEqualTo(expected);
        }
    }

    @Test
    void 멀티_캡슐을_생성할_수_있다() {
        // given
        String title = "멀티 캡슐";
        int maxMembers = 5;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime buryDeadlineAt = now.plusDays(7);
        LocalDateTime openAt = now.plusYears(1);
        String placeName = "서울숲";
        double latitude = 37.5496;
        double longitude = 127.0416;
        UUID fixedId = UUID.fromString("00000000-0000-0000-0000-000000000002");

        try (MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
            mockedUuid.when(UUID::randomUUID).thenReturn(fixedId);
            Capsule expected = Capsule.multi(
                    multiUser.getId(), title, openAt, placeName, latitude, longitude, maxMembers, buryDeadlineAt);

            // when
            Capsule actual = multiUser.createMultiCapsule(title, openAt, placeName, latitude, longitude,
                    maxMembers, buryDeadlineAt);

            // then
            assertThat(actual)
                    .usingRecursiveComparison()
                    .ignoringFields("createdAt")
                    .isEqualTo(expected);
        }
    }

    @Test
    void 멀티_캡슐을_만든_생성자가_방장이_된다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime buryDeadlineAt = now.plusDays(7);
        LocalDateTime openAt = now.plusYears(1);

        // when
        Capsule capsule = multiUser.createMultiCapsule("멀티 캡슐", openAt, "서울숲",
                37.5496, 127.0416, 5, buryDeadlineAt);

        // then
        assertThat(capsule.isHost(multiUser.getId())).isTrue();
    }

    @Test
    void 캡슐_제목이_유효하지_않으면_캡슐을_생성할_수_없다() {
        // given
        String[] invalidTitles = {null, "   ", "123456789012345678901234567890123456789012345678901"};
        ErrorCode[] expectedErrors = {ErrorCode.TITLE_REQUIRED, ErrorCode.TITLE_REQUIRED, ErrorCode.TITLE_TOO_LONG};
        LocalDateTime openAt = LocalDateTime.now().plusYears(1);

        for (int i = 0; i < invalidTitles.length; i++) {
            String title = invalidTitles[i];

            // when, then
            assertThatThrownBy(() -> singleUser.createSingleCapsule(title, openAt, "서울숲", 37.5496, 127.0416))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(expectedErrors[i].getMessage());
        }
    }

    @Test
    void 캡슐_제목이_비어있지_않고_50자_이하이면_캡슐을_생성할_수_있다() {
        // given
        String[] validTitles = {"1", "12345678901234567890123456789012345678901234567890"};

        LocalDateTime openAt = LocalDateTime.now().plusYears(1);

        for (String title : validTitles) {
            // when
            Capsule capsule = singleUser.createSingleCapsule(title, openAt, "서울숲", 37.5496, 127.0416);

            // then
            assertThat(capsule).isNotNull();
        }
    }

    @Test
    void 캡슐_오픈_시각이_생성_시각_이전이거나_10년_이후라면_캡슐을_생성할_수_없다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime[] invalidOpenTimes = {null, now.minusDays(1), now, now.plusYears(11)};
        ErrorCode[] expectedErrors = {ErrorCode.OPEN_AT_REQUIRED, ErrorCode.OPEN_AT_NOT_IN_FUTURE,
                ErrorCode.OPEN_AT_NOT_IN_FUTURE, ErrorCode.OPEN_AT_TOO_FAR_IN_FUTURE};

        for (int i = 0; i < invalidOpenTimes.length; i++) {
            LocalDateTime openAt = invalidOpenTimes[i];

            // when, then
            assertThatThrownBy(() -> singleUser.createSingleCapsule("싱글 캡슐", openAt, "서울숲", 37.5496, 127.0416))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(expectedErrors[i].getMessage());
        }
    }

    @Test
    void 캡슐_오픈_시각이_생성_시각_이후이고_10년_이내이면_캡슐을_생성할_수_있다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime[] validOpenTimes = {now.plusDays(1), now.plusYears(10)};

        for (LocalDateTime openAt : validOpenTimes) {
            // when
            Capsule capsule = singleUser.createSingleCapsule("싱글 캡슐", openAt, "서울숲", 37.5496, 127.0416);

            // then
            assertThat(capsule).isNotNull();
        }
    }

    @Test
    void 묻기_예정_시각이_캡슐_생성_시각과_같거나_과거라면_캡슐을_생성할_수_없다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime openAt = now.plusYears(1);
        LocalDateTime[] invalidBuryTimes = {null, now.minusDays(1), now};

        for (LocalDateTime buryDeadlineAt : invalidBuryTimes) {
            // when, then
            assertThatThrownBy(() -> multiUser.createMultiCapsule(
                    "멀티 캡슐", openAt, "서울숲", 37.5496, 127.0416, 5, buryDeadlineAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 묻기_예정_시각이_캡슐_개봉_예정_시각과_같거나_늦으면_캡슐을_생성할_수_없다() {
        // given
        LocalDateTime openAt = LocalDateTime.now().plusDays(7);
        LocalDateTime[] invalidBuryTimes = {openAt, openAt.plusMinutes(1)};

        for (LocalDateTime buryDeadlineAt : invalidBuryTimes) {
            // when, then
            assertThatThrownBy(() -> multiUser.createMultiCapsule(
                    "멀티 캡슐", openAt, "서울숲", 37.5496, 127.0416, 5, buryDeadlineAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 묻기_예정_시각이_생성_시각으로부터_14일을_초과하면_캡슐을_생성할_수_없다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime openAt = now.plusYears(1);
        LocalDateTime buryDeadlineAt = now.plusDays(14).plusMinutes(1);

        // when, then
        assertThatThrownBy(() -> multiUser.createMultiCapsule(
                "멀티 캡슐", openAt, "서울숲", 37.5496, 127.0416, 5, buryDeadlineAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 묻기_예정_시각이_생성_시각_이후_14일_이내이고_개봉_예정_시각_이전이면_캡슐을_생성할_수_있다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime openAt = now.plusYears(1);
        LocalDateTime buryDeadlineAt = now.plusDays(14);

        // when
        Capsule capsule = multiUser.createMultiCapsule("멀티 캡슐", openAt, "서울숲", 37.5496, 127.0416,
                5, buryDeadlineAt);

        // then
        assertThat(capsule).isNotNull();
    }

    @Test
    void 지도에서_선택한_위치로_장소를_지정할_수_있다() {
    }

    @Test
    void 국내_지도_범위를_벗어난_위치로는_장소를_지정할_수_없다() {
    }

    @Test
    void 멀티_캡슐_정원이_2명_미만이거나_20명_초과라면_캡슐을_생성할_수_없다() {
        // given
        int[] invalidMaxMembers = {1, 21};
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime buryDeadlineAt = now.plusDays(7);
        LocalDateTime openAt = now.plusYears(1);

        for (int maxMembers : invalidMaxMembers) {
            // when, then
            assertThatThrownBy(() -> multiUser.createMultiCapsule(
                    "멀티 캡슐", openAt, "서울숲", 37.5496, 127.0416, maxMembers, buryDeadlineAt))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 멀티_캡슐_정원이_2명_이상_20명_이하이면_캡슐을_생성할_수_있다() {
        // given
        int[] validMaxMembers = {2, 20};
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime buryDeadlineAt = now.plusDays(7);
        LocalDateTime openAt = now.plusYears(1);

        for (int maxMembers : validMaxMembers) {
            // when
            Capsule capsule = multiUser.createMultiCapsule("멀티 캡슐", openAt, "서울숲", 37.5496, 127.0416,
                    maxMembers, buryDeadlineAt);

            // then
            assertThat(capsule).isNotNull();
        }
    }

    @Test
    void 이미_묻힌_캡슐은_설정한_내용을_수정할_수_없다() {
    }
}
