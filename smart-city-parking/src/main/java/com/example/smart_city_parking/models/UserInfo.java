package com.example.smart_city_parking.models;

public class UserInfo {
    private final int userId;
    private final String userName;
    private final String userEmail;
    private final int reservationCount;

    public UserInfo(int userId, String userName, String userEmail, int reservationCount) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.reservationCount = reservationCount;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getReservationCount() {
        return reservationCount;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", reservationCount=" + reservationCount +
                '}';
    }
}
