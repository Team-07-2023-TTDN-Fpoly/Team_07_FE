package com.team.team_07_fe.request;

import com.google.gson.annotations.SerializedName;
import com.team.team_07_fe.models.WorkShift;

import java.util.Date;

public class EmployeeRequest {
    @SerializedName("emp_id")
    private String emp_id;

    @SerializedName("emp_name")
    private String emp_name;

    @SerializedName("emp_phone")
    private String emp_phone;

    @SerializedName("emp_birthday")
    private Date emp_birthday;

    @SerializedName("basic_salary")
    private long basic_salary;

    @SerializedName("emp_address")
    private String emp_address;

    @SerializedName("role")
    private String role;

    @SerializedName("workShift")
    private WorkShift workShift;

    @SerializedName("join_date")
    private Date join_date;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;


    //Khi tạo tài khoản
    public EmployeeRequest(String emp_name, String emp_phone, Date emp_birthday, long basic_salary, String emp_address, String role, WorkShift workShift, Date join_date, String email, String password) {
        this.emp_name = emp_name;
        this.emp_phone = emp_phone;
        this.emp_birthday = emp_birthday;
        this.basic_salary = basic_salary;
        this.emp_address = emp_address;
        this.role = role;
        this.workShift = workShift;
        this.join_date = join_date;
        this.email = email;
        this.password = password;
    }

    //Khi update thông tin
    public EmployeeRequest(String emp_name, String emp_phone, Date emp_birthday, long basic_salary, String emp_address, String role, WorkShift workShift, Date join_date) {
        this.emp_name = emp_name;
        this.emp_phone = emp_phone;
        this.emp_birthday = emp_birthday;
        this.basic_salary = basic_salary;
        this.emp_address = emp_address;
        this.role = role;
        this.workShift = workShift;
        this.join_date = join_date;
    }
    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_phone() {
        return emp_phone;
    }

    public void setEmp_phone(String emp_phone) {
        this.emp_phone = emp_phone;
    }

    public Date getEmp_birthday() {
        return emp_birthday;
    }

    public void setEmp_birthday(Date emp_birthday) {
        this.emp_birthday = emp_birthday;
    }

    public long getBasic_salary() {
        return basic_salary;
    }

    public void setBasic_salary(long basic_salary) {
        this.basic_salary = basic_salary;
    }

    public String getEmp_address() {
        return emp_address;
    }

    public void setEmp_address(String emp_address) {
        this.emp_address = emp_address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public WorkShift getWorkShift() {
        return workShift;
    }

    public void setWorkShift(WorkShift workShift) {
        this.workShift = workShift;
    }

    public Date getJoin_date() {
        return join_date;
    }

    public void setJoin_date(Date join_date) {
        this.join_date = join_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
