package com.ddwu.notalonemarket.dto;
import jakarta.validation.constraints.*;

public class UserRegisterDTO {
	@NotBlank(message = "로그인 ID는 필수입니다.")
    @Size(min = 3, max = 20, message = "ID는 3~20자 사이여야 합니다.")
    private String loginId;
	
	@NotBlank(message = "비밀번호는 필수입니다.")
	@Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String password;
	
	@NotBlank(message = "닉네임은 필수입니다.")
    @Size(max = 20, message = "닉네임은 20자 이내여야 합니다.")
    private String nickname;
	
	@Pattern(regexp = "^(010\\d{8})?$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNum;
	
	@Size(max = 30, message = "계좌번호는 30자 이내여야 합니다.")
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
