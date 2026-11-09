package com.project.timecapsule;

import com.project.timecapsule.capsule.Capsule;
import com.project.timecapsule.enums.ErrorCode;
import com.project.timecapsule.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("캡슐 생성 테스트")
class CapsuleCreateTest {

    private User singleUser;

    @BeforeEach
    void beforeEach() {
        singleUser = new User("싱글유저", "password");
    }

    @Test
    void 싱글_캡슐을_생성할_수_있다() {
        // given
        String title = "싱글 캡슐";
        LocalDateTime openAt = LocalDateTime.now().plusYears(1);
        String placeName = "서울숲";
        double latitude = 37.5496;
        double longitude = 127.0416;
        Capsule expected = Capsule.single(
                singleUser.getId(), title, openAt, placeName, latitude, longitude);

        // when
        Capsule actual = singleUser.createSingleCapsule(title, openAt, placeName, latitude, longitude);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(expected);
    }

    @Test
    void 멀티_캡슐을_생성할_수_있다() {
    }

    @Test
    void 멀티_캡슐을_만든_생성자가_방장이_된다() {
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
    void 묻기_예정_시각이_현재보다_과거면_생성할_수_없다() {
    }

    @Test
    void 묻기_예정_시각이_개봉_예정_시각과_같거나_늦으면_생성할_수_없다() {
    }

    @Test
    void 싱글_캡슐에는_묻기_예정_시각을_설정할_수_없다() {
    }

    @Test
    void 지도에서_선택한_위치로_장소를_지정할_수_있다() {
    }

    @Test
    void 국내_지도_범위를_벗어난_위치로는_장소를_지정할_수_없다() {
    }

    @Test
    void 멀티_캡슐_정원이_2명_미만_20명_초과라면_생성할_수_없다() {
    }

    @Test
    void 이미_묻힌_캡슐은_설정한_내용을_수정할_수_없다() {
    }
}
