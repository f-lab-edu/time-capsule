package com.project.timecapsule.capsule;

import com.project.timecapsule.enums.ErrorCode;

public final class Location {

    // 국내 지도 선택 가능 영역
    private static final double MIN_LATITUDE = 33.0;
    private static final double MAX_LATITUDE = 38.7;
    private static final double MIN_LONGITUDE = 124.5;
    private static final double MAX_LONGITUDE = 132.0;

    private final String placeName;
    private final double latitude;
    private final double longitude;

    public Location(String placeName, double latitude, double longitude) {
        validateCoordinates(latitude, longitude);

        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private static void validateCoordinates(double latitude, double longitude) {
        if (!Double.isFinite(latitude) || !Double.isFinite(longitude)
                || latitude < MIN_LATITUDE || latitude > MAX_LATITUDE
                || longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new IllegalArgumentException(ErrorCode.LOCATION_OUT_OF_RANGE.getMessage());
        }
    }
}
