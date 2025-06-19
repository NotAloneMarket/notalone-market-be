package com.ddwu.notalonemarket.dto;

public class UserRegisterDTO {
    private String loginId;     // 로그인용 ID
    private String password;
    private String nickname;
    private String phoneNum;
    private String accountNumber;

    // 기본 생성자
    public UserRegisterDTO() {}

    // 전체 필드를 받는 생성자
    public UserRegisterDTO(String loginId, String password, String nickname, String phoneNum, String accountNumber) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.accountNumber = accountNumber;
    }

    // Getter & Setter
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
