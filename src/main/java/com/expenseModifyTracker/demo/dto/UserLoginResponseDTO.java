package com.expenseModifyTracker.demo.dto;

public class UserLoginResponseDTO {
    private Long userID;
    private String userName;
    private String userEmail;
    private String message;

    public UserLoginResponseDTO() {
    }

    public UserLoginResponseDTO(Long userID, String userName, String userEmail, String message) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.message = message;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
