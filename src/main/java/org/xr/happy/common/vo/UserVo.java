package org.xr.happy.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import org.xr.happy.common.annotation.NotEmpty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class UserVo {

    @NotEmpty(msg = "userId 不能为空", required = true)
    private Long userId;

    @NotEmpty(msg = "username 不能为空", required = true)
    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private String userUniqueToken;

    public UserVo() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserUniqueToken() {
        return userUniqueToken;
    }

    public void setUserUniqueToken(String userUniqueToken) {
        this.userUniqueToken = userUniqueToken;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userUniqueToken='" + userUniqueToken + '\'' +
                '}';
    }

}
