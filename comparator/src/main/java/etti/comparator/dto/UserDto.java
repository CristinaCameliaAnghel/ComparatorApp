package etti.comparator.dto;

public class UserDto {

    private String email;
    private String password;
    private String role;
    private String fullName;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = true;
    }

    private boolean isActive;


    public UserDto() {

    }

    public UserDto(String email, String password, String role, String fullName, boolean isActive) {
        super();
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.isActive = isActive;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }






}