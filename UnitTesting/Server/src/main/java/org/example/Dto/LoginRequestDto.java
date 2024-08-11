package org.example.Dto;

public class LoginRequestDto {
    private String employeeId;
    private String userName;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "LoginRequestDto{" +
                "employeeId='" + employeeId + '\'' +
                '}';
    }
}
