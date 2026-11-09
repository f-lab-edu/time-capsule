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

    private UUID singleUserId;
    private User singleUser;

    @BeforeEach
    void beforeEach() {
        singleUserId = UUID.randomUUID();
        singleUser = new User(singleUserId, "싱글유저", "password");
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
        assertThat(capsule.isCreatedBy(singleUserId)).isTrue();
        assertThat(capsule)
                .extracting("mode", "maxMembers", "status")
                .containsExactly(CapsuleMode.SINGLE, 1, CapsuleStatus.DRAFT);
    }

    @Test
    void 멀티_캡슐을_생성할_수_있다() {
    }

    @Test
    void 멀티_캡슐을_만든_생성자가_방장이_된다() {
    }

    @Test
    void 필수_정보가_빠지면_생성할_수_없다() {
    }

    @Test
    void 제목이_비어있으면_생성할_수_없다() {
    }

    @Test
    void 제목이_최대_길이를_초과하면_생성할_수_없다() {
    }

    @Test
    void 개봉_시각이_캡슐_생성_시각과_같거나_이전이면_생성할_수_없다() {
    }

    @Test
    void 개봉_시각이_서비스_허용_기간을_넘으면_생성할_수_없다() {
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
