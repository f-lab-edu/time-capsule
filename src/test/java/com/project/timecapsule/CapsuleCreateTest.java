package com.project.timecapsule;

import com.project.timecapsule.capsule.Capsule;
import com.project.timecapsule.enums.CapsuleMode;
import com.project.timecapsule.enums.CapsuleStatus;
import com.project.timecapsule.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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

        // when
        Capsule capsule = singleUser.createSingleCapsule(title, openAt, placeName, latitude, longitude);

        // then
        // 캡슐을 생성한 사용자
        assertThat(capsule.getCreatedBy()).isEqualTo(singleUser.getId());

        // 입력한 값이 그대로 저장
        assertThat(capsule.getTitle()).isEqualTo(title);
        assertThat(capsule.getOpenAt()).isEqualTo(openAt);
        assertThat(capsule.getPlaceName()).isEqualTo(placeName);
        assertThat(capsule.getLatitude()).isEqualTo(latitude);
        assertThat(capsule.getLongitude()).isEqualTo(longitude);

        // 싱글 캡슐로 생성
        assertThat(capsule.getMode()).isEqualTo(CapsuleMode.SINGLE);
        assertThat(capsule.getMaxMembers()).isEqualTo(1);
        assertThat(capsule.getStatus()).isEqualTo(CapsuleStatus.DRAFT);
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
        LocalDateTime openAt = LocalDateTime.now().plusYears(1);

        for (String title : invalidTitles) {
            // when
            IllegalArgumentException exception = null;
            try {
                singleUser.createSingleCapsule(title, openAt, "서울숲", 37.5496, 127.0416);
            } catch (IllegalArgumentException e) {
                exception = e;
            }

            // then
            assertThat(exception).isNotNull();
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

        for (LocalDateTime openAt : invalidOpenTimes) {
            // when
            IllegalArgumentException exception = null;
            try {
                singleUser.createSingleCapsule("싱글 캡슐", openAt, "서울숲", 37.5496, 127.0416);
            } catch (IllegalArgumentException e) {
                exception = e;
            }

            // then
            assertThat(exception).isNotNull();
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
