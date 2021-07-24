package org.xr.happy.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.xr.happy.common.annotation.NotEmpty;
import org.xr.happy.common.annotation.Valid;
import org.xr.happy.common.view.ResultJsonView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVo {

    @NotEmpty(msg = "userId 不能为空", required = true)
    private Long userId;

    @NotEmpty(msg = "username 不能为空", required = true)
    @JsonView(value = ResultJsonView.UserSimple.class)
    private String username;

    @JsonView(value = ResultJsonView.UserDetail.class)
    private String password;

    private String email;

    private String userUniqueToken;

    @Valid
    private OrderVo orderVo;


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
                ", orderVo=" + orderVo +
                '}';
    }

}
