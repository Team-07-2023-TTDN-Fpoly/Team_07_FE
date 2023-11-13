package com.team.team_07_fe.anotition;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Role {
    public static final String CHOOSE = "Chọn vai trò";
    public static final String ADMIN = "Quản lý";
    public static final String EMPLOYEE = "Nhân viên";

    @StringDef({CHOOSE,ADMIN,EMPLOYEE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusSizeDef{}

    private String role;

    @StatusSizeDef
    public String getRole() {
        return role;
    }

    public void setRole(@StatusSizeDef String statusSize) {
        this.role = statusSize;
    }
}
